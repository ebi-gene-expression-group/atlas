package uk.ac.ebi.atlas.search.analyticsindex.solr;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.annotation.Nullable;

public class AdvancedSolrQuery {
    public enum Operator {
        AND(" AND "),
        OR(" OR ");

        private final String opString;

        Operator(String opString) {
            this.opString = opString;
        }
    }

    private abstract class TreeNode {
    }

    private class Leaf extends TreeNode {
        private String searchField;
        private SemanticQuery searchValue;

        private Leaf(String searchField, SemanticQuery searchValue) {
            this.searchField = searchField;
            this.searchValue = searchValue;
        }

        @Override
        public String toString() {
            return String.format("%s:(%s)", searchField, searchValue.asAnalyticsIndexQueryClause());
        }
    }

    private class Parent extends TreeNode {
        private Operator operator;
        private Iterable<TreeNode> children;

        private Parent(Operator operator, Iterable<TreeNode> children) {
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
    }

    private TreeNode root;

    public String toString() {
        return root.toString();
    }

    public AdvancedSolrQuery(String searchField, SemanticQuery searchValue) {
        root = new Leaf(searchField, searchValue);
    }

    public AdvancedSolrQuery(Operator operator, AdvancedSolrQuery... queries) {
        ImmutableList.Builder<TreeNode> childrenBuilder = new ImmutableList.Builder<>();
        for (AdvancedSolrQuery query : queries) {
            childrenBuilder.add(query.root);
        }

        root = new Parent(operator, childrenBuilder.build());
    }
}
