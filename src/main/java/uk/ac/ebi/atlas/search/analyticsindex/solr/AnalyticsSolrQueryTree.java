package uk.ac.ebi.atlas.search.analyticsindex.solr;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AnalyticsSolrQueryTree {
    public enum Operator {
        AND(" AND "),
        OR(" OR ");

        private final String opString;

        Operator(String opString) {
            this.opString = opString;
        }
    }

    private static abstract class TreeNode {
        abstract TreeNode map(final Function<Pair<String, String> ,Pair<String, String>> f);

        abstract TreeNode filter(final Function<Pair<String, String> ,Boolean> f);
    }

    private static class Null extends TreeNode {

        public static TreeNode INSTANCE = new Null();
        private Null(){

        }
        @Override
        TreeNode map(Function<Pair<String, String>, Pair<String, String>> f) {
            return this;
        }

        @Override
        TreeNode filter(Function<Pair<String, String>, Boolean> f) {
            return this;
        }
    }

    private static class Leaf extends TreeNode {
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

        @Override
        TreeNode map(final Function<Pair<String, String>, Pair<String, String>> f) {
            Pair<String, String> p = f.apply(Pair.of(searchField, searchValue));
            return new Leaf(p.getLeft(), p.getRight());
        }

        @Override
        TreeNode filter(final Function<Pair<String, String>, Boolean> f) {
            return f.apply(Pair.of(searchField, searchValue)) ? this : Null.INSTANCE;
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
        TreeNode map(final Function<Pair<String, String>, Pair<String, String>> f) {
            return new Parent(operator, Collections2.transform(children, new Function<TreeNode,
                    TreeNode>() {
                @Nullable
                @Override
                public TreeNode apply(@Nullable TreeNode child) {
                    return child.map(f);
                }
            }));
        }

        @Override
        TreeNode filter(final Function<Pair<String, String>, Boolean> f) {

            Collection<TreeNode> newChildren = FluentIterable.from(children)
                    .transform(new Function<TreeNode, TreeNode>() {
                @Nullable
                @Override
                public TreeNode apply(@Nullable TreeNode child) {
                    return child.filter(f);
                }
            }).filter(new Predicate<TreeNode>() {
                @Override
                public boolean apply(@Nullable TreeNode treeNode) {
                    return ! treeNode.equals(Null.INSTANCE);
                }
            }).toList();

            return newChildren.size() == 0
                    ? Null.INSTANCE
                    : newChildren.size() ==1
                        ? newChildren.iterator().next()
                        : new Parent(operator, newChildren);
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

    List<String> toQueryPlan(){
        return ImmutableList.of(toString());
    }
}
