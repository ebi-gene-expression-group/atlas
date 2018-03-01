package uk.ac.ebi.atlas.solr.analytics.query;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.solr.analytics.query.AnalyticsQueryTree.Operator.OR;

public class AnalyticsQueryTree {
    // https://www.biostars.org/p/99142/#99150, see also https://www.ensembl.org/info/genome/stable_ids/index.html
    private static final Pattern ENSEMBL_ID_REGEX_FROM_THE_INTERNET =
            Pattern.compile(
                    "ENS[A-Z]+[0-9]{11}|[A-Z]{3}[0-9]{3}[A-Za-z](-[A-Za-z])?|CG[0-9]+|[A-Z0-9]+\\.[0-9]+|YM[A-Z][0-9]{3}[a-z][0-9]",
                    Pattern.CASE_INSENSITIVE);

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
        // f can't return a Null or you'll mess up the tree
        abstract TreeNode map(final Function<Leaf, TreeNode> f);
        abstract TreeNode filter(final Predicate<Leaf> f);
        abstract String toQuery();
    }

    private static final class Null extends TreeNode {
        public static final TreeNode INSTANCE = new Null();

        @Override
        TreeNode map(final Function<Leaf, TreeNode> f) {
            return this;
        }

        @Override
        TreeNode filter(final Predicate<Leaf> f) {
            return this;
        }

        @Override
        String toQuery() {
            return "";
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
        TreeNode map(final Function<Leaf, TreeNode> f) {
            return f.apply(this);
        }

        @Override
        TreeNode filter(final Predicate<Leaf> f) {
            return f.test(this) ? this : Null.INSTANCE;
        }

        @Override
        public String toQuery() {
            return  String.format("%s:\"%s\"", searchField, searchValue);
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
        public String toQuery() {
            Function<TreeNode, String> wrapParentsInParenthesis =
                    treeNode -> treeNode instanceof Parent ? "(" + treeNode.toQuery() + ")" : treeNode.toQuery();

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

    //We want this search field to match at least one of these values
    AnalyticsQueryTree(String searchField, String... searchValues) {
        if (searchValues.length == 1) {
            root = new Leaf(searchField, searchValues[0]);
        } else {
            ImmutableList<TreeNode> children =
                    ImmutableList.copyOf(
                            Arrays.stream(searchValues)
                                    .map(searchValue -> new Leaf(searchField, searchValue))
                                    .collect(Collectors.toList()));

            root = new Parent(Operator.OR, children);
        }
    }

    AnalyticsQueryTree(Operator operator, AnalyticsQueryTree... queryTrees) {
        ImmutableList<TreeNode> children =
                ImmutableList.copyOf(
                        Arrays.stream(queryTrees)
                                .map(queryTree -> queryTree.root)
                                .collect(Collectors.toList()));

        root = new Parent(operator, children);
    }

    public static AnalyticsQueryTree createForIdentifierSearch(SemanticQuery geneQuery) {
        Multimap<String, String> m = HashMultimap.create();
        geneQuery.terms().stream()
                .filter(SemanticQueryTerm::hasValue)
                .forEach(term -> m.put(decideOnKeywordField(term), term.value()));

        List<AnalyticsQueryTree> possibleIdentifiers =
                m.asMap().entrySet().stream()
                        .map(e -> new AnalyticsQueryTree(e.getKey(), e.getValue().toArray(new String[0])))
                        .collect(Collectors.toList());
        if (possibleIdentifiers.size() == 1) {
            return possibleIdentifiers.get(0);
        } else {
            return new AnalyticsQueryTree(OR, possibleIdentifiers.toArray(new AnalyticsQueryTree[0]));
        }
    }

    private static String decideOnKeywordField(SemanticQueryTerm term) {
        if (term.hasNoCategory()) {
            if (ENSEMBL_ID_REGEX_FROM_THE_INTERNET.matcher(term.value()).matches()) {
                return BioentityPropertyName.BIOENTITY_IDENTIFIER.name;
            }
            // A phrase cannot be a keyword
            if (term.value().trim().contains(" ")) {
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

    public List<String> toQueryPlan() {
        TreeNode n = root.filter(leaf -> leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE));

        if (n.equals(Null.INSTANCE)) {
            return ImmutableList.of(root.toQuery());
        } else {
            /*
            If there were somehow two identifier search queries, this would be wrong, because we would search for
            both as keywords and then for both as text, missing the case when one matches as keyword and the other as
            text.
            */
            Function<Leaf, TreeNode> makeTreeWithKeywordQueriesForIdentifiers = leaf -> {
                if (leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE)) {
                    return new Parent(
                            OR,
                            possibleIdentifierKeywords().stream()
                                    .map(possibleIdentifierSearch -> new Leaf(possibleIdentifierSearch, leaf.searchValue))
                                    .collect(Collectors.toList()));
                } else {
                    return leaf;
                }
            };

            Function<Leaf, TreeNode> makeTreeWithTextSearchForIdentifiers = leaf -> {
                if(leaf.searchField.equals(UNRESOLVED_IDENTIFIER_SEARCH_FLAG_VALUE)) {
                    return new Leaf(AnalyticsQueryClient.Field.IDENTIFIER_SEARCH.name, leaf.searchValue);
                } else {
                    return leaf;
                }
            };

            return ImmutableList.of(
                    root.map(makeTreeWithKeywordQueriesForIdentifiers).toQuery(),
                    root.map(makeTreeWithTextSearchForIdentifiers).toQuery());
        }
    }

    private static ImmutableList<String> possibleIdentifierKeywords() {
        return ImmutableList.copyOf(
                ExperimentDataPoint.bioentityPropertyNames.stream()
                        .filter(bioentityPropertyName -> bioentityPropertyName.isId)
                        .map(BioentityPropertyName::asAnalyticsIndexKeyword)
                        .collect(Collectors.toList()));
    }
}
