package uk.ac.ebi.atlas.search.analyticsindex.differential;

import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryClient;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DifferentialAnalyticsSearchDao {

    private final AnalyticsQueryClient analyticsQueryClient;

    @Inject
    public DifferentialAnalyticsSearchDao(AnalyticsQueryClient analyticsQueryClient) {
        this.analyticsQueryClient = analyticsQueryClient;
    }


    public String fetchFacetsAboveDefaultFoldChangeForSearch(SemanticQuery query) {
        return analyticsQueryClient.queryBuilder()
                        .differentialFacets()
                        .queryIdentifierOrConditionsSearch(query)
                        .fetch();
    }

    public String fetchFacetsAboveDefaultFoldChangeForQuery(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        return analyticsQueryClient.queryBuilder()
                        .differentialFacets()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .fetch();
    }

    public String fetchDifferentialResultsAboveDefaultFoldChangeForSearch(SemanticQuery query) {
        return analyticsQueryClient.queryBuilder()
                        .differentialResults()
                        .queryIdentifierOrConditionsSearch(query)
                        .fetch();
    }

    public String fetchDifferentialResultsAboveDefaultFoldChangeForQuery(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        return analyticsQueryClient.queryBuilder()
                        .differentialResults()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .fetch();
    }
}
