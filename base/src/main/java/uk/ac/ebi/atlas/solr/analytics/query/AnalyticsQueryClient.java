package uk.ac.ebi.atlas.solr.analytics.query;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.utils.ResourceUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.CONDITIONS_SEARCH;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.DEFAULT_FACTOR_TYPE;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.EXPERIMENT_ACCESSION;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.SPECIES;

@Named
@Scope("prototype")
public class AnalyticsQueryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsQueryClient.class);
    private final RestTemplate restTemplate;
    private final String solrBaseUrl;
    private final Resource baselineFacetsQueryJson;
    private final Resource differentialFacetsQueryJson;
    private final Resource experimentTypesQueryJson;
    private final Resource bioentityIdentifiersQueryJson;

    @Inject
    public AnalyticsQueryClient(
            RestTemplate restTemplate,
            @Value("${solr.host}") String solrHost,
            @Value("${solr.port}") String solrPort,
            @Value("classpath:/solr-queries/baseline.heatmap.pivot.query.json") Resource baselineFacetsQueryJson,
            @Value("classpath:/solr-queries/differential.facets.query.json") Resource differentialFacetsQueryJson,
            @Value("classpath:/solr-queries/experimentType.query.json") Resource experimentTypesQueryJson,
            @Value("classpath:/solr-queries/bioentityIdentifier.query.json") Resource bioentityIdentifiersQueryJson) {
        this.restTemplate = restTemplate;
        this.solrBaseUrl = "http://" + solrHost + ":" + solrPort + "/solr/analytics/";
        this.baselineFacetsQueryJson = baselineFacetsQueryJson;
        this.differentialFacetsQueryJson = differentialFacetsQueryJson;
        this.experimentTypesQueryJson = experimentTypesQueryJson;
        this.bioentityIdentifiersQueryJson = bioentityIdentifiersQueryJson;
    }

    private String fetchResults(SolrQuery... qs) {
        String result = "{}";

        for (SolrQuery q: qs) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            result = fetchResponseAsString(MessageFormat.format("{0}query", solrBaseUrl), q);
            stopwatch.stop();
            LOGGER.debug("fetchResults {} took {} seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);
            if (responseNonEmpty(result)) {
                break;
            }
        }
        return result;
    }

    protected boolean responseNonEmpty(String jsonFromSolr) {
        Integer numFound =  JsonPath.read(jsonFromSolr, "$.response.numFound");
        return numFound != null && numFound > 0;
    }


    protected String fetchResponseAsString(String url, SolrQuery query) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> request = new HttpEntity<>(query.toString(), headers);
            return restTemplate.postForObject(url, request, String.class);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public Builder queryBuilder() {
        return new Builder();
    }

    public class Builder {
        private static final String DEFAULT_QUERY = "*:*";

        private final SolrQuery solrQuery = new SolrQuery();
        private ImmutableList.Builder<AnalyticsSolrQueryTree> queryClausesBuilder = ImmutableList.builder();

        protected Builder() {
            solrQuery.set("omitHeader", true);
        }

        /*
        Interesting lack of symmetry - baselineResults are retrieved with a different code path! :)
         */

        private void setFacets(Resource r) {
            solrQuery.setRows(0);
            solrQuery.set("json.facet", ResourceUtils.readPlainTextResource(r).replaceAll("\\s+", ""));
        }

        public Builder filterBaselineExperiments() {
            solrQuery.addFilterQuery(
                    "(experiment_type:RNASEQ_MRNA_BASELINE AND expression_level:[0.5 TO *]) " +
                            "OR experiment_type:PROTEOMICS_BASELINE");
            return this;
        }

        public Builder baselineFacets() {
            setFacets(baselineFacetsQueryJson);
            filterBaselineExperiments();
            return this;
        }

        private Builder differential() {
            solrQuery.addFilterQuery("experiment_type:(" +
                    "RNASEQ_MRNA_DIFFERENTIAL " +
                    "OR MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL " +
                    "OR MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL " +
                    "OR MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL) " +
                    "AND p_value:[* TO 0.05] AND fold_change:([* TO -1.0] OR [1.0 TO *])");
            return this;
        }

        public Builder speciesFacets() {
            solrQuery.setRows(0);
            solrQuery.setFacet(true);
            solrQuery.addFacetField("species");
            solrQuery.setFacetMinCount(1);
            solrQuery.setFacetLimit(100); // Some number greater than the number of species indexed
            return this;
        }

        public Builder differentialResults() {
            solrQuery.setRows(1000);
            solrQuery.set("sort", "abs(fold_change)desc, p_value asc");
            return differential();
        }

        public Builder differentialFacets() {
            setFacets(differentialFacetsQueryJson);
            return differential();
        }

        public Builder experimentTypeFacets() {
            setFacets(experimentTypesQueryJson);
            return this;
        }

        public Builder bioentityIdentifierFacets(int facetLimit) {
            solrQuery.setRows(0);
            solrQuery.set(
                    "json.facet",
                    ResourceUtils.readPlainTextResource(bioentityIdentifiersQueryJson)
                            .replace("\"limit\": -1", "\"limit\": " + Integer.toString(facetLimit))
                            .replaceAll("\\s+", ""));
            return this;
        }

        private void addQueryClause(AnalyticsCollectionProxy.AnalyticsSchemaField searchField, String searchValue) {
            if (!isBlank(searchValue)) {
                queryClausesBuilder.add(new AnalyticsSolrQueryTree(searchField.name(), searchValue));
            }
        }

        public Builder queryIdentifierOrConditionsSearch(SemanticQuery query) {
            queryClausesBuilder.add(new AnalyticsSolrQueryTree(
                    AnalyticsSolrQueryTree.Operator.OR,
                    AnalyticsSolrQueryTree.createForIdentifierSearch(query),
                    conditionsSearchQuery(query)
            ));
            return this;
        }

        public Builder queryIdentifierSearch(SemanticQuery geneQuery) {
            if (geneQuery.isNotEmpty()) {
                queryClausesBuilder.add(AnalyticsSolrQueryTree.createForIdentifierSearch(geneQuery));
            }
            return this;
        }

        private AnalyticsSolrQueryTree conditionsSearchQuery(SemanticQuery conditionQuery) {
            Stream<String> var =
                    conditionQuery.terms().stream()
                            .filter(SemanticQueryTerm::hasValue)
                            .map(SemanticQueryTerm::value);

            return new AnalyticsSolrQueryTree(CONDITIONS_SEARCH.name(), var.toArray(String[]::new));
        }

        public Builder queryConditionsSearch(SemanticQuery conditionQuery) {
            if (conditionQuery.isNotEmpty()) {
                queryClausesBuilder.add(conditionsSearchQuery(conditionQuery));
            }
            return this;
        }

        public Builder withFactorType(String factorType) {
            addQueryClause(DEFAULT_FACTOR_TYPE, factorType);
            return this;
        }

        public Builder ofSpecies(String species) {
            addQueryClause(SPECIES, species);
            return this;
        }

        public Builder inExperiment(String accession) {
            addQueryClause(EXPERIMENT_ACCESSION, accession);
            return this;
        }


        public Builder setRows(int rows) {
            solrQuery.setRows(rows);
            return this;
        }

        public String fetch() {
            List<String> qsForQueryClauses = qsForQueryClauses(queryClausesBuilder.build());
            SolrQuery[] solrQueries = new SolrQuery[qsForQueryClauses.size()];

            for (int i = 0; i < qsForQueryClauses.size(); i++) {
                SolrQuery c = solrQuery.getCopy();
                c.setQuery(qsForQueryClauses.get(i));
                solrQueries[i] = c;
            }

            return fetchResults(solrQueries);
        }
    }

    private static List<String> qsForQueryClauses(List<AnalyticsSolrQueryTree> queryClauses) {
        if (queryClauses.isEmpty()) {
            return ImmutableList.of(Builder.DEFAULT_QUERY);
        } else {
            return
                    new AnalyticsSolrQueryTree(
                            AnalyticsSolrQueryTree.Operator.AND,
                            queryClauses.toArray(new AnalyticsSolrQueryTree[0])
                    ).toQueryPlan();
        }
    }
}
