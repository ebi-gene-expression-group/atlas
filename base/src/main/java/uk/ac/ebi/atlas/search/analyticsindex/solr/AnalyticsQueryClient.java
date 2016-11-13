package uk.ac.ebi.atlas.search.analyticsindex.solr;

import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.utils.ResourceUtils;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryClient.Field.*;

@Named
@Scope("prototype")
public class AnalyticsQueryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsQueryClient.class);
    private final RestTemplate restTemplate;
    private final String solrBaseUrl;
    private final Resource baselineFacetsQueryJSON;
    private final Resource differentialFacetsQueryJSON;
    private final Resource experimentTypesQueryJson;
    private final Resource bioentityIdentifiersQueryJson;

    @Inject
    public AnalyticsQueryClient(RestTemplate restTemplate, @Qualifier("solrAnalyticsServerURL") String solrBaseUrl,
                                @Value("classpath:/solr-queries/baseline.heatmap.pivot.query.json") Resource  baselineFacetsQueryJSON,
                                @Value("classpath:/solr-queries/differential.facets.query.json") Resource differentialFacetsQueryJSON,
                                @Value("classpath:/solr-queries/experimentType.query.json") Resource experimentTypesQueryJson,
                                @Value("classpath:/solr-queries/bioentityIdentifier.query.json") Resource bioentityIdentifiersQueryJson){
        this.restTemplate = restTemplate;
        this.solrBaseUrl = solrBaseUrl;
        this.baselineFacetsQueryJSON = baselineFacetsQueryJSON;
        this.differentialFacetsQueryJSON = differentialFacetsQueryJSON;
        this.experimentTypesQueryJson = experimentTypesQueryJson;
        this.bioentityIdentifiersQueryJson = bioentityIdentifiersQueryJson;
    }

    private String fetchResults(SolrQuery... qs ) {
        String result = "{}";

        for(SolrQuery q: qs){
            Stopwatch stopwatch = Stopwatch.createStarted();
            LOGGER.debug("fetchResults q={} took {} seconds", q, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);
            result = fetchResponseAsString(MessageFormat.format("{0}query?{1}", solrBaseUrl, q.toString()));
            stopwatch.stop();
            if(responseNonEmpty(result)){
                break;
            }
        }
        return result;
    }

    boolean responseNonEmpty(String jsonFromSolr){
        Integer numFound =  JsonPath.read(jsonFromSolr, "$.response.numFound");
        return numFound!= null && numFound>0;
    }


    String fetchResponseAsString(String url) {
        try {
            return restTemplate.getForObject(new URI(url), String.class);
        } catch (RestClientException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Builder queryBuilder(){
        return new Builder();
    }


    public class Builder {

        private static final String DEFAULT_QUERY = "*:*";

        private ImmutableList.Builder<AnalyticsSolrQueryTree> queryClausesBuilder = ImmutableList.builder();

        final SolrQuery solrQuery = new SolrQuery();

        private Builder(){
            /*
             we put some more data than matches the 0.05 pValue limit to give ourselves some wiggle
             room but we don't actually want them
             */
            solrQuery.addFilterQuery("-pValue:[0.05 TO *]");
            solrQuery.set("omitHeader", true);
        }

        /*
        Interesting lack of symmetry - baselineResults are retrieved with a different code path! :)
         */

        private void setFacets(Resource r){
            solrQuery.setRows(0);
            solrQuery.set("json.facet", ResourceUtils.readPlainTextResource(r).replaceAll("\\s+",""));
        }

        public Builder baselineFacets(){
            setFacets(baselineFacetsQueryJSON);
            solrQuery.addFilterQuery("experimentType:(rnaseq_mrna_baseline OR proteomics_baseline)");
            return this;
        }

        private Builder differential(){
            solrQuery.addFilterQuery("experimentType:(rnaseq_mrna_differential OR " +
                    "microarray_1colour_mrna_differential OR microarray_2colour_mrna_differential OR microarray_1colour_microrna_differential)");
            return this;
        }

        public Builder differentialResults(){
            solrQuery.setRows(1000);
            solrQuery.set("sort", "abs(foldChange)desc");
            return differential();
        }

        public Builder differentialFacets(){
            setFacets(differentialFacetsQueryJSON);
            return differential();
        }

        public Builder experimentTypeFacets(){
            setFacets(experimentTypesQueryJson);
            return this;
        }

        public Builder bioentityIdentifierFacets(int facetLimit){
            solrQuery.setRows(0);
            solrQuery.set("json.facet", ResourceUtils.readPlainTextResource(bioentityIdentifiersQueryJson).replace("\"limit\": -1",
                    MessageFormat.format("\"limit\": {0}", Integer.toString(facetLimit))).replaceAll("\\s+",""));
            return this;
        }

        private void addQueryClause(Field searchField, String searchValue) {
            if (!searchValue.isEmpty()) {
                queryClausesBuilder.add(new AnalyticsSolrQueryTree(searchField.toString(), searchValue));
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
            if(geneQuery.isNotEmpty()){
                queryClausesBuilder.add(AnalyticsSolrQueryTree.createForIdentifierSearch(geneQuery));
            }
            return this;
        }

        private AnalyticsSolrQueryTree conditionsSearchQuery(SemanticQuery conditionQuery){
            ImmutableList.Builder<String> b = ImmutableList.builder();
            for(SemanticQueryTerm term: conditionQuery.terms()){
                if(term.hasValue()){
                    b.add(term.value());
                }
            }
            return new AnalyticsSolrQueryTree(CONDITIONS_SEARCH.toString(), b.build().toArray(new String[0]));
        }

        public Builder queryConditionsSearch(SemanticQuery conditionQuery) {
            if(conditionQuery.isNotEmpty()){
                queryClausesBuilder.add(conditionsSearchQuery(conditionQuery));
            }
            return this;
        }

        public Builder withFactorType(String factorType) {
            addQueryClause(FACTOR_TYPE, factorType);
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

        public String fetch(){

            List<String> qsForQueryClauses = qsForQueryClauses(queryClausesBuilder.build());
            SolrQuery[] solrQueries = new SolrQuery[qsForQueryClauses.size()];

            for(int i = 0; i< qsForQueryClauses.size(); i++){
                SolrQuery c = solrQuery.getCopy();
                c.setQuery(qsForQueryClauses.get(i));
                solrQueries[i] = c;
            }

            /*
            TODO here for the "split the identifier search field into multiple fields" story
            if the query involves identifier search:
                split the query clauses into how they should be
                build an array of solrQueries using solrQuery.getCopy()
                send off!
            else:
                just one sweet query
             */



            return fetchResults(solrQueries);
        }
    }

    private static List<String> qsForQueryClauses(List<AnalyticsSolrQueryTree> queryClauses){

        if (queryClauses.isEmpty()) {
            return ImmutableList.of(Builder.DEFAULT_QUERY);
        } else {
            return new AnalyticsSolrQueryTree(AnalyticsSolrQueryTree.Operator.AND, queryClauses.toArray(new
                    AnalyticsSolrQueryTree[0])).toQueryPlan();
        }

    }

    enum Field {
        EXPERIMENT_TYPE("experimentType"),
        EXPERIMENT_ACCESSION("experimentAccession"),
        BIOENTITY_IDENTIFIER("bioentityIdentifier"),
        SPECIES("species"),
        IDENTIFIER_SEARCH("identifierSearch"),
        CONDITIONS_SEARCH("conditionsSearch"),
        FACTOR_TYPE("defaultQueryFactorType");
        final String name;

        Field(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
