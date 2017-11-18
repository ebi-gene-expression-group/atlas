package uk.ac.ebi.atlas.solr.analytics.differential;

import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.analytics.query.AnalyticsQueryClient;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DifferentialAnalyticsSearchDao {

    private final AnalyticsQueryClient analyticsQueryClient;

    @Inject
    public DifferentialAnalyticsSearchDao(AnalyticsQueryClient analyticsQueryClient) {
        this.analyticsQueryClient = analyticsQueryClient;
    }

    public String fetchFacetsAboveDefaultFoldChange(
            SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        return analyticsQueryClient.queryBuilder()
                        .differentialFacets()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .fetch();
    }

    public String fetchFacetsAboveDefaultFoldChange(SemanticQuery geneQuery, SemanticQuery conditionQuery) {
        return analyticsQueryClient.queryBuilder()
                .differentialFacets()
                .queryIdentifierSearch(geneQuery)
                .queryConditionsSearch(conditionQuery)
                .fetch();
    }

    public String fetchResultsAboveDefaultFoldChange(
            SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        return analyticsQueryClient.queryBuilder()
                        .differentialResults()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .fetch();
    }

    public String fetchResultsAboveDefaultFoldChange(SemanticQuery geneQuery, SemanticQuery conditionQuery) {
        return analyticsQueryClient.queryBuilder()
                .differentialResults()
                .queryIdentifierSearch(geneQuery)
                .queryConditionsSearch(conditionQuery)
                .fetch();
    }

}
