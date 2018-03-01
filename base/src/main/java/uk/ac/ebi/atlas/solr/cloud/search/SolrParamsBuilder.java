package uk.ac.ebi.atlas.solr.cloud.search;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SolrParamsBuilder {
    // A general way to do single and multiple value field searches
    private static final String TERMS_CLAUSE_TEMPLATE = "({!terms f=%s}%s)";
    // If we want to add an exclusive value syntax: %s:{%f TO *]
    private static final String RANGE_UPPER_BOUND_CLAUSE_TEMPLATE = "%s:[* TO %f]";
    private static final String RANGE_LOWER_BOUND_CLAUSE_TEMPLATE = "%s:[%f TO *]";
    private static final String RANGE_DOUBLE_BOUND_CLAUSE_TEMPLATE = "%s:([* TO %f] OR [%f TO *])";

    private ImmutableSet.Builder<String> fqClausesBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<String> qClausesBuilder = ImmutableSet.builder();

    public SolrParamsBuilder addQueryTermsClause(String field, String... values) {
        qClausesBuilder.add(createTermsQuery(field, ImmutableSet.copyOf(values)));
        return this;
    }

    public SolrParamsBuilder addFilterTermsClause(String field, String... values) {
        fqClausesBuilder.add(createTermsQuery(field, ImmutableSet.copyOf(values)));
        return this;
    }

    public SolrParamsBuilder addQueryUpperRangeClause(String field, Double rangeUpperBound) {
        qClausesBuilder.add(createUpperRangeQuery(field, rangeUpperBound));
        return this;
    }

    public SolrParamsBuilder addFilterUpperRangeClause(String field, Double rangeUpperBound) {
        fqClausesBuilder.add(createUpperRangeQuery(field, rangeUpperBound));
        return this;
    }

    public SolrParamsBuilder addQueryLowerRangeClause(String field, Double rangeLowerBound) {
        qClausesBuilder.add(createLowerRangeQuery(field, rangeLowerBound));
        return this;
    }

    public SolrParamsBuilder addFilterLowerRangeClause(String field, Double rangeLowerBound) {
        fqClausesBuilder.add(createLowerRangeQuery(field, rangeLowerBound));
        return this;
    }

    public SolrParamsBuilder addQueryDoubleRangeClause(String field, Double rangeUpperBound, Double rangeLowerBound) {
        qClausesBuilder.add(createDoubleRangeQuery(field, rangeUpperBound, rangeLowerBound));
        return this;
    }

    public SolrParamsBuilder addFilterDoubleRangeClause(String field, Double rangeUpperBound, Double rangeLowerBound) {
            fqClausesBuilder.add(createDoubleRangeQuery(field, rangeUpperBound, rangeLowerBound));
        return this;
    }


    public SolrParams build() {
        ImmutableSet<String> fqClauses = fqClausesBuilder.build();
        ImmutableSet<String> qClauses = qClausesBuilder.build();

        return mapParams("fq", joinWithAnd(fqClauses), "q", qClauses.isEmpty() ? "*:*" : joinWithAnd(qClauses));
    }

    private static String createTermsQuery(String field, Set<String> searchValues) {
        return String.format(
                TERMS_CLAUSE_TEMPLATE,
                field,
                searchValues.stream()
                        // The terms query parser searches for values verbatim, no escaping is necessary
                        // .map(ClientUtils::escapeQueryChars)
                        .collect(Collectors.joining(",")));
    }

    private static String createUpperRangeQuery(String field, Double rangeEnd) {
        return String.format(RANGE_UPPER_BOUND_CLAUSE_TEMPLATE, field, rangeEnd);
    }

    private static String createLowerRangeQuery(String field, Double rangeStart) {
        return String.format(RANGE_LOWER_BOUND_CLAUSE_TEMPLATE, field, rangeStart);
    }

    private static String createDoubleRangeQuery(String field, Double rangeEnd, Double rangeStart) {
        return String.format(RANGE_DOUBLE_BOUND_CLAUSE_TEMPLATE, field, rangeEnd, rangeStart);
    }

//    public FacetStreamingExpressionBuilder queryIdentifierSearch(SemanticQuery semanticQuery) {
//        if(isNotEmpty(semanticQuery)){
//            AnalyticsQueryTree.createForIdentifierSearch(semanticQuery);
//        }
//        return this;
//    }

    private static String joinWithAnd(Collection<String> clauses) {
        return clauses.stream().collect(Collectors.joining(" AND "));
    }

    private static SolrParams mapParams(String... vals) {
        ModifiableSolrParams params = new ModifiableSolrParams();
        for (int idx = 0; idx < vals.length; idx += 2) {
            params.add(vals[idx], vals[idx + 1]);
        }

        return params;
    }
}
