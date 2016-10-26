package uk.ac.ebi.atlas.search.analyticsindex.differential;

import org.apache.solr.client.solrj.SolrQuery;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsRestClient;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DifferentialAnalyticsSearchDao {

    private final AnalyticsRestClient analyticsRestClient;
    private final AnalyticsQueryFactory analyticsQueryFactory;

    @Inject
    public DifferentialAnalyticsSearchDao(AnalyticsRestClient analyticsRestClient, AnalyticsQueryFactory analyticsQueryFactory) {
        this.analyticsRestClient = analyticsRestClient;
        this.analyticsQueryFactory = analyticsQueryFactory;
    }


    public String fetchFacetsAboveDefaultFoldChangeForSearch(SemanticQuery query) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .differentialFacets()
                        .queryIdentifierOrConditionsSearch(query)
                        .build();

        return analyticsRestClient.fetchResults(solrQuery);
    }

    public String fetchFacetsAboveDefaultFoldChangeForQuery(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .differentialFacets()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .build();

        return analyticsRestClient.fetchResults(solrQuery);
    }

    public String fetchDifferentialResultsAboveDefaultFoldChangeForSearch(SemanticQuery query) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .differentialResults()
                        .queryIdentifierOrConditionsSearch(query)
                        .build();

        return analyticsRestClient.fetchResults(solrQuery);
    }

    public String fetchDifferentialResultsAboveDefaultFoldChangeForQuery(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .differentialResults()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .build();


        return analyticsRestClient.fetchResults(solrQuery);
    }
}
