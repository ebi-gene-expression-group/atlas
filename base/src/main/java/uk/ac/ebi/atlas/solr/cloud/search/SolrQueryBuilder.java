package uk.ac.ebi.atlas.solr.cloud.search;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.Set;

import static java.util.stream.Collectors.joining;

public class SolrQueryBuilder {
    // A general way to do single and multiple value field searches
    private static final String TERMS_CLAUSE_TEMPLATE = "({!terms f=%s}%s)";
    // If we want to add an exclusive value syntax: %s:{%f TO *]
    private static final String RANGE_UPPER_BOUND_CLAUSE_TEMPLATE = "%s:[* TO %f]";
    private static final String RANGE_LOWER_BOUND_CLAUSE_TEMPLATE = "%s:[%f TO *]";
    private static final String RANGE_DOUBLE_BOUND_CLAUSE_TEMPLATE = "%s:([* TO %f] OR [%f TO *])";

    private ImmutableSet.Builder<String> fqClausesBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<String> qClausesBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<String> flBuilder = ImmutableSet.builder();
    private int rows = Integer.MAX_VALUE;

    public SolrQueryBuilder addQueryTermsClause(String field, String... values) {
        qClausesBuilder.add(createTermsQuery(field, ImmutableSet.copyOf(values)));
        return this;
    }

    public SolrQueryBuilder addFilterTermsClause(String field, String... values) {
        fqClausesBuilder.add(createTermsQuery(field, ImmutableSet.copyOf(values)));
        return this;
    }

    public SolrQueryBuilder addQueryUpperRangeClause(String field, Double rangeUpperBound) {
        qClausesBuilder.add(createUpperRangeQuery(field, rangeUpperBound));
        return this;
    }

    public SolrQueryBuilder addFilterUpperRangeClause(String field, Double rangeUpperBound) {
        fqClausesBuilder.add(createUpperRangeQuery(field, rangeUpperBound));
        return this;
    }

    public SolrQueryBuilder addQueryLowerRangeClause(String field, Double rangeLowerBound) {
        qClausesBuilder.add(createLowerRangeQuery(field, rangeLowerBound));
        return this;
    }

    public SolrQueryBuilder addFilterLowerRangeClause(String field, Double rangeLowerBound) {
        fqClausesBuilder.add(createLowerRangeQuery(field, rangeLowerBound));
        return this;
    }

    public SolrQueryBuilder addQueryDoubleRangeClause(String field, Double rangeUpperBound, Double rangeLowerBound) {
        qClausesBuilder.add(createDoubleRangeQuery(field, rangeUpperBound, rangeLowerBound));
        return this;
    }

    public SolrQueryBuilder addFilterDoubleRangeClause(String field, Double rangeUpperBound, Double rangeLowerBound) {
        fqClausesBuilder.add(createDoubleRangeQuery(field, rangeUpperBound, rangeLowerBound));
        return this;
    }

    public SolrQueryBuilder setFieldList(String... fields) {
        flBuilder.add(fields);
        return this;
    }

    public SolrQueryBuilder setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public SolrQuery build() {
        ImmutableSet<String> fqClauses = fqClausesBuilder.build();
        ImmutableSet<String> qClauses = qClausesBuilder.build();
        ImmutableSet<String> fl = flBuilder.build();

        return mapParams(
                "fq", fqClauses.stream().collect(joining(" AND ")),
                "q", qClauses.isEmpty() ? "*:*" : qClauses.stream().collect(joining(" AND ")),
                "fl", fl.isEmpty() ? "*" : fl.stream().collect(joining(",")),
                "rows", Integer.toString(rows));
                // fl can also be left empty to return all fields, but "*" shows an explicit intent
    }

    private static String createTermsQuery(String field, Set<String> searchValues) {
        return String.format(
                TERMS_CLAUSE_TEMPLATE,
                field,
                searchValues.stream()
                        // The terms query parser searches for values verbatim, no escaping is necessary
                        // .map(ClientUtils::escapeQueryChars)
                        .collect(joining(",")));
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

    private static SolrQuery mapParams(String... vals) {
        SolrQuery solrQuery = new SolrQuery();
        for (int idx = 0; idx < vals.length; idx += 2) {
            solrQuery.add(vals[idx], vals[idx + 1]);
        }

        return solrQuery;
    }
}
