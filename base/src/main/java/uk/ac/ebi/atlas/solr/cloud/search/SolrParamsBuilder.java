package uk.ac.ebi.atlas.solr.cloud.search;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SolrParamsBuilder {
    // A general way to do single and multiple value field searches
    private static final String TERMS_CLAUSE_TEMPLATE = "({!terms f=%s}%s)";
    // If we want to add an exclusive value syntax: %s:{%f TO *]
    private static final String RANGE_CLAUSE_TEMPLATE = "%s:[%f TO *]";

    private ImmutableSet.Builder<String> fqClausesBuilder = ImmutableSet.builder();
    private ImmutableSet.Builder<String> qClausesBuilder = ImmutableSet.builder();

    public SolrParamsBuilder addQueryTermsClause(String field, Collection<String> values) {
        qClausesBuilder.add(createTermsQuery(field, ImmutableSet.copyOf(values)));
        return this;
    }

    public SolrParamsBuilder addFilterTermsClause(String field, Collection<String> values) {
        fqClausesBuilder.add(createTermsQuery(field, ImmutableSet.copyOf(values)));
        return this;
    }

    public SolrParamsBuilder addQueryRangeClause(String field, Double rangeLowerBound) {
        qClausesBuilder.add(createRangeQuery(field, rangeLowerBound));
        return this;
    }

    public SolrParamsBuilder addFilterRangeClause(String field, Double rangeLowerBound) {
        fqClausesBuilder.add(createRangeQuery(field, rangeLowerBound));
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
                field, searchValues.stream().map(ClientUtils::escapeQueryChars).collect(Collectors.joining(",")));
    }

    private static String createRangeQuery(String field, Double rangeStart) {
        return String.format(RANGE_CLAUSE_TEMPLATE, field, rangeStart);
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
