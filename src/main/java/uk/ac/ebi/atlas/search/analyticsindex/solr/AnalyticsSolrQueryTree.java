package uk.ac.ebi.atlas.search.analyticsindex.solr;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Pattern;

import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQueryTree.Operator.OR;

public class AnalyticsSolrQueryTree {
    public static final String UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE = "__identifierSearch";

    public enum Operator {
        AND(" AND "),
        OR(" OR ");

        private final String opString;

        Operator(String opString) {
            this.opString = opString;
        }
    }

    private static abstract class TreeNode {
        //f can't return a Null or you'll mess up the tree
        abstract TreeNode map(final Function<Leaf, TreeNode> f);

        abstract TreeNode filter(final Predicate<Leaf> f);
    }

    private static class Null extends TreeNode {

        public static TreeNode INSTANCE = new Null();

        private Null() {

        }

        @Override
        TreeNode map(final Function<Leaf, TreeNode> f) {
            return this;
        }

        @Override
        TreeNode filter(Predicate<Leaf> f) {
            return this;
        }
    }

    private static class Leaf extends TreeNode {
        final String searchField;
        final String searchValue;

        private Leaf(String searchField, String searchValue) {
            this.searchField = searchField;
            this.searchValue = searchValue;
        }

        @Override
        public String toString() {
            return  String.format("%s:(%s)", searchField,
                    searchValue.contains(":") || searchValue.trim().contains(" ")? StringUtils.wrap(searchValue, "\"") : searchValue);
        }

        @Override
        TreeNode map(final Function<Leaf, TreeNode> f) {
            return f.apply(this);
        }

        @Override
        TreeNode filter(final Predicate<Leaf> f) {
            return f.apply(this) ? this : Null.INSTANCE;
        }
    }

    private static class Parent extends TreeNode {
        private Operator operator;
        private Collection<TreeNode> children;

        private Parent(Operator operator, Collection<TreeNode> children) {
            this.operator = operator;
            this.children = children;
        }

        @Override
        public String toString() {
            Function<TreeNode, String> wrapParentsInParenthesis = new Function<TreeNode, String>() {
                @Nullable
                @Override
                public String apply(@Nullable TreeNode treeNode) {
                    if (treeNode instanceof Parent) {
                        return "(" + treeNode.toString() + ")";
                    } else {
                        return treeNode.toString();
                    }
                }
            };

            return Joiner.on(this.operator.opString).join(FluentIterable.from(children).transform(wrapParentsInParenthesis));
        }


        @Override
        TreeNode map(final Function<Leaf, TreeNode> f) {
            return new Parent(operator,
                    Collections2.transform(children, new Function<TreeNode,TreeNode>() {
                @Override
                public TreeNode apply(TreeNode child) {
                    return child.map(f);
                }
            }));
        }

        @Override
        TreeNode filter(final Predicate<Leaf> f) {

            Collection<TreeNode> newChildren = FluentIterable.from(children)
                    .transform(new Function<TreeNode, TreeNode>() {
                        @Override
                        public TreeNode apply(TreeNode child) {
                            return child.filter(f);
                        }
                    }).filter(new Predicate<TreeNode>() {
                        @Override
                        public boolean apply(TreeNode treeNode) {
                            return !treeNode.equals(Null.INSTANCE);
                        }
                    }).toList();

            return newChildren.size() == 0
                    ? Null.INSTANCE
                    : newChildren.size() == 1
                    ? newChildren.iterator().next()
                    : new Parent(operator, newChildren);
        }
    }

    private final TreeNode root;

    public String toString() {
        return root.toString();
    }

    private static final Function<SemanticQueryTerm, String> semanticQueryTermToSolr = new Function<SemanticQueryTerm, String>() {
        @Nullable
        @Override
        public String apply(@Nullable SemanticQueryTerm semanticQueryTerm) {
            return semanticQueryTerm.hasNoCategory()
                    ? semanticQueryTerm.value()
                    : String.format("%s:{%s}", semanticQueryTerm.category(), semanticQueryTerm.value());
        }
    };

    @Deprecated
        //convenience method
    AnalyticsSolrQueryTree(String searchField, SemanticQuery searchValue) {
        //if searchValue has categories, it should really be identifierSearch or conditionsSearch
        this(searchField, searchValue.terms());
    }

    @Deprecated
    AnalyticsSolrQueryTree(String searchField, Collection<SemanticQueryTerm> allowedValuesForField) {
        this(searchField,
                FluentIterable.from(allowedValuesForField)
                        .transform(semanticQueryTermToSolr)
                        .toArray(String.class));
    }

    //We want this search field to match at least one of these values
    AnalyticsSolrQueryTree(String searchField, String... searchValues) {
        if (searchValues.length == 1) {
            root = new Leaf(searchField, searchValues[0]);
        } else {
            ImmutableList.Builder<TreeNode> childrenBuilder = new ImmutableList.Builder<>();
            for (String searchValue : searchValues) {
                childrenBuilder.add(new Leaf(searchField, searchValue));
            }
            root = new Parent(Operator.OR, childrenBuilder.build());
        }
    }

    AnalyticsSolrQueryTree(Operator operator, AnalyticsSolrQueryTree... queries) {
        ImmutableList.Builder<TreeNode> childrenBuilder = new ImmutableList.Builder<>();
        for (AnalyticsSolrQueryTree query : queries) {
            childrenBuilder.add(query.root);
        }

        root = new Parent(operator, childrenBuilder.build());
    }

    private static String decideOnKeywordField(SemanticQueryTerm term){
        if(term.hasNoCategory()){
            if(ensemblIdRegexFromTheInternet.matcher(term.value()).matches()){
                return "bioentityIdentifier";
            }
            //a multiword string cannot be a keyword
            if(term.value().trim().contains(" ")){
                return AnalyticsQueryClient.Field.IDENTIFIER_SEARCH.name;
            } else {
                return UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE;
            }
        } else {
            return "keyword_" + term.category();
        }
    }

    private static final Pattern ensemblIdRegexFromTheInternet = Pattern.compile
            ("ENS[A-Z]+[0-9]{11}|[A-Z]{3}[0-9]{3}[A-Za-z](-[A-Za-z])?|CG[0-9]+|[A-Z0-9]+\\.[0-9]+|YM[A-Z][0-9]{3}[a-z][0-9]");


    //package
    static AnalyticsSolrQueryTree createForIdentifierSearch(SemanticQuery geneQuery) {
        Multimap<String, String> m = HashMultimap.create();
        for (SemanticQueryTerm term : geneQuery.terms()) {
            if (term.hasValue()) {
                m.put(decideOnKeywordField(term),term.value());
            }
        }
        List<AnalyticsSolrQueryTree> possibleIdentifiers = new ArrayList<>();
        for (Map.Entry<String, Collection<String>> e : m.asMap().entrySet()) {
            possibleIdentifiers.add(new AnalyticsSolrQueryTree(e.getKey(),
                    ImmutableList.copyOf(e.getValue()).toArray(new String[0])));
        }
        if (possibleIdentifiers.size() == 1) {
            return possibleIdentifiers.get(0);
        } else {
            return new AnalyticsSolrQueryTree(OR, possibleIdentifiers.toArray(new AnalyticsSolrQueryTree[0]));
        }
    }


    List<String> toQueryPlan() {
        TreeNode n = root.filter(new Predicate<Leaf>() {
            @Override
            public boolean apply(@Nullable Leaf leaf) {
                return leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE);
            }
        });
        if(n.equals(Null.INSTANCE)){
            return ImmutableList.of(toString());
        } else {
            /*
            If there were somehow two identifier search queries, this would be wrong, because we would search for
            both as keywords and then for both as text, missing the case when one matches as keyword and the other as
             text.
             */
            Function<Leaf, TreeNode> makeTreeWithKeywordQueriesForIdentifiers = new Function<Leaf, TreeNode>() {
                @Override
                public TreeNode apply(final Leaf leaf) {
                    if(leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE)){
                        return new Parent(OR, Collections2.transform(possibleIdentifierKeywords(), new Function<String, TreeNode>() {
                            @Override
                            public TreeNode apply(String possibleIdentifierSearch) {
                                return new Leaf(possibleIdentifierSearch, leaf.searchValue);
                            }
                        }));
                    } else {
                        return leaf;
                    }
                }
            };

            Function<Leaf, TreeNode> makeTreeWithTextSearchForIdentifiers = new Function<Leaf, TreeNode>() {
                @Override
                public TreeNode apply(final Leaf leaf) {
                    if(leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE)){
                        return new Leaf(AnalyticsQueryClient.Field.IDENTIFIER_SEARCH.name, leaf.searchValue);
                    } else {
                        return leaf;
                    }
                }
            };

            return ImmutableList.of(root.map(makeTreeWithKeywordQueriesForIdentifiers).toString(), root.map
                    (makeTreeWithTextSearchForIdentifiers).toString());


        }

    }

    static ImmutableList<String> possibleIdentifierKeywords(){
        ImmutableList.Builder<String> b = ImmutableList.builder();
        for(BioentityPropertyName bioentityPropertyName: ExperimentDataPoint.bioentityPropertyNames){
            if(bioentityPropertyName.isId){
                b.add("keyword_"+bioentityPropertyName.name);
            }
        }
        return b.build();
    }
}
