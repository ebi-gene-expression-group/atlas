package uk.ac.ebi.atlas.solr.analytics;

import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.analytics.query.AnalyticsQueryClient;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MiscellaneousAnalyticsSearchDao {

    private final AnalyticsQueryClient analyticsQueryClient;

    @Inject
    public MiscellaneousAnalyticsSearchDao(AnalyticsQueryClient analyticsQueryClient) {
        this.analyticsQueryClient = analyticsQueryClient;
    }

    String fetchExperimentTypesInAnyField(SemanticQuery query) {
        return analyticsQueryClient.queryBuilder()
                        .experimentTypeFacets()
                        .queryIdentifierOrConditionsSearch(query)
                        .fetch();
    }


    String fetchExperimentTypes(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        return analyticsQueryClient.queryBuilder()
                        .experimentTypeFacets()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .fetch();
    }

    String searchBioentityIdentifiers(SemanticQuery geneQuery,
                                      SemanticQuery conditionQuery,
                                      String species,
                                      int facetLimit) {
        return analyticsQueryClient.queryBuilder()
                        .bioentityIdentifierFacets(facetLimit)
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .fetch();
    }

    String searchBioentityIdentifiersForTissuesInBaselineExperiments(SemanticQuery geneQuery) {
        return analyticsQueryClient.queryBuilder()
                        .bioentityIdentifierFacets(1)
                        .filterBaselineExperiments()
                        .queryIdentifierSearch(geneQuery)
                        .fetch();
    }

    String getBioentityIdentifiersForSpecies(String species) {
        return analyticsQueryClient.queryBuilder()
                        // Something less than 50k because of sitemap limitations, plus some wiggle room for extra data
                        .bioentityIdentifierFacets(45000)
                        .ofSpecies(species)
                        .fetch();
    }

    String getSpecies(SemanticQuery geneQuery, SemanticQuery conditionQuery) {
        return analyticsQueryClient.queryBuilder()
                .speciesFacets()
                .queryIdentifierSearch(geneQuery)
                .queryConditionsSearch(conditionQuery)
                .fetch();
    }

}
