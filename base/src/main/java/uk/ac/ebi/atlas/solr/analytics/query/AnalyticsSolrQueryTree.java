package uk.ac.ebi.atlas.solr.analytics.query;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.solr.analytics.query.AnalyticsSolrQueryTree.Operator.OR;

public class AnalyticsSolrQueryTree {
    private static final String UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE = "__identifierSearch";

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
            return  String.format("%s:\"%s\"", searchField, searchValue);
        }

        @Override
        TreeNode map(final Function<Leaf, TreeNode> f) {
            return f.apply(this);
        }

        @Override
        TreeNode filter(final Predicate<Leaf> f) {
            return f.test(this) ? this : Null.INSTANCE;
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
                public String apply(TreeNode treeNode) {
                    if (treeNode instanceof Parent) {
                        return "(" + treeNode.toString() + ")";
                    } else {
                        return treeNode.toString();
                    }
                }
            };

            return children.stream().map(wrapParentsInParenthesis).collect(Collectors.joining(this.operator.opString));
        }


        @Override
        TreeNode map(final Function<Leaf, TreeNode> f) {
            return new Parent(operator, children.stream().map(child -> child.map(f)).collect(Collectors.toList()));
        }

        @Override
        TreeNode filter(final Predicate<Leaf> f) {

            Collection<TreeNode> newChildren =
                    children.stream()
                            .map(child -> child.filter(f))
                            .filter(treeNode -> !treeNode.equals(Null.INSTANCE))
                            .collect(Collectors.toList());

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

    //We want this search field to match at least one of these values
    AnalyticsSolrQueryTree(String searchField, String... searchValues) {
        if (searchValues.length == 1) {
            root = new Leaf(searchField, searchValues[0]);
        } else {
            ImmutableList.Builder<TreeNode> childrenBuilder = new ImmutableList.Builder<>();
            for (String searchValue : searchValues) {
                childrenBuilder.add(new Leaf(searchField, searchValue));
            }
            root = new Parent(OR, childrenBuilder.build());
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
                return BioentityPropertyName.BIOENTITY_IDENTIFIER.name;
            }
            //a multiword string cannot be a keyword
            if(term.value().trim().contains(" ")){
                return AnalyticsQueryClient.Field.IDENTIFIER_SEARCH.name;
            } else {
                return UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE;
            }
        } else {
            return BioentityPropertyName.BIOENTITY_IDENTIFIER.name.equals(term.category())
                    ? BioentityPropertyName.BIOENTITY_IDENTIFIER.name
                    : "keyword_" + term.category();
        }
    }

    private static final Pattern ensemblIdRegexFromTheInternet =
            Pattern.compile(
                    "ENS[A-Z]+[0-9]{11}|[A-Z]{3}[0-9]{3}[A-Za-z](-[A-Za-z])?|CG[0-9]+|[A-Z0-9]+\\.[0-9]+|YM[A-Z][0-9]{3}[a-z][0-9]",
                    Pattern.CASE_INSENSITIVE);

    //package
    public static AnalyticsSolrQueryTree createForIdentifierSearch(SemanticQuery geneQuery) {
        Multimap<String, String> m = HashMultimap.create();

        geneQuery.terms().stream()
                .filter(SemanticQueryTerm::hasValue)
                .forEach(term -> m.put(decideOnKeywordField(term), term.value()));

        List<AnalyticsSolrQueryTree> possibleIdentifiers =
                m.asMap().entrySet().stream()
                        .map(e -> new AnalyticsSolrQueryTree(e.getKey(), e.getValue().toArray(new String[0])))
                        .collect(Collectors.toList());

        if (possibleIdentifiers.size() == 1) {
            return possibleIdentifiers.get(0);
        } else {
            return new AnalyticsSolrQueryTree(OR, possibleIdentifiers.toArray(new AnalyticsSolrQueryTree[0]));
        }
    }

    List<String> toQueryPlan() {
        TreeNode n = root.filter(leaf -> leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE));
        if(n.equals(Null.INSTANCE)){
            return ImmutableList.of(toString());
        } else {
            /*
            If there were somehow two identifier search queries, this would be wrong, because we would search for
            both as keywords and then for both as text, missing the case when one matches as keyword and the other as
             text.
             */
            Function<Leaf, TreeNode> makeTreeWithKeywordQueriesForIdentifiers = leaf -> {
                if(leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE)) {
                    return new Parent(
                            OR,
                            identifierKeywords().stream()
                                    .map(possibleIdentifierSearch ->
                                            new Leaf(possibleIdentifierSearch, leaf.searchValue))
                                    .collect(Collectors.toList()));
                } else {
                    return leaf;
                }
            };

            Function<Leaf, TreeNode> makeTreeWithTextSearchForIdentifiers = leaf -> {
                if(leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE)){
                    return new Leaf(AnalyticsQueryClient.Field.IDENTIFIER_SEARCH.name, leaf.searchValue);
                } else {
                    return leaf;
                }
            };

            return ImmutableList.of(
                    root.map(makeTreeWithKeywordQueriesForIdentifiers).toString(),
                    root.map(makeTreeWithTextSearchForIdentifiers).toString());
        }

    }

    private static ImmutableList<String> identifierKeyword() {
        return ImmutableList.of(AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER.name());
    }

    private static ImmutableList<String> identifierKeywords() {
        ImmutableList.Builder<String> builder = ImmutableList.builder();

        return builder
                .add(BioentityPropertyName.BIOENTITY_IDENTIFIER.name)
                .addAll(ExperimentDataPoint.bioentityPropertyNames.stream()
                        .filter(bioentityPropertyName -> bioentityPropertyName.isId)
                        .map(AnalyticsCollectionProxy::asAnalyticsIndexKeyword)
                        .collect(Collectors.toList()))
                .build();
    }
}