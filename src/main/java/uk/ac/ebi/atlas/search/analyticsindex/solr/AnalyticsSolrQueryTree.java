package uk.ac.ebi.atlas.search.analyticsindex.solr;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;

public class AnalyticsSolrQueryTree {
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
        private String searchValue;

        private Leaf(String searchField, String searchValue) {
            this.searchField = searchField;
            this.searchValue = searchValue;
        }

        @Override
        public String toString() {


            return String.format("%s:(%s)", searchField, searchValue);
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

    private final TreeNode root;

    public String toString() {
        return root.toString();
    }


    private static final Function<String, String> wrapFields = new Function<String, String>() {
        @Nullable
        @Override
        public String apply(@Nullable String token) {
            return StringUtils.wrap(token, "\"");
        }
    };

    private static final Function<SemanticQueryTerm, String> semanticQueryTermToSolr = new Function<SemanticQueryTerm, String>() {
        @Nullable
        @Override
        public String apply(@Nullable SemanticQueryTerm semanticQueryTerm) {
            return semanticQueryTerm.hasNoCategory()
                    ? semanticQueryTerm.value()
                    : String.format("%s:{%s}", semanticQueryTerm.category(), semanticQueryTerm.value());
        }
    };

    //convenience method
    AnalyticsSolrQueryTree(String searchField, SemanticQuery searchValue) {
        this(searchField, searchValue.terms());
    }

    AnalyticsSolrQueryTree(String searchField, Collection<SemanticQueryTerm> allowedValuesForField) {
        this(searchField,
                FluentIterable.from(allowedValuesForField)
                .transform(semanticQueryTermToSolr)
                .toArray(String.class));
    }

    AnalyticsSolrQueryTree(String searchField, String... searchValue){
        //We want this search field to match at least one of these values
        root = new Leaf(searchField, Joiner.on(Operator.OR.opString).join(FluentIterable.from(Arrays.asList(searchValue)).transform(wrapFields)));
    }

    AnalyticsSolrQueryTree(Operator operator, AnalyticsSolrQueryTree... queries) {
        ImmutableList.Builder<TreeNode> childrenBuilder = new ImmutableList.Builder<>();
        for (AnalyticsSolrQueryTree query : queries) {
            childrenBuilder.add(query.root);
        }

        root = new Parent(operator, childrenBuilder.build());
    }
}
