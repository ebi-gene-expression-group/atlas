package uk.ac.ebi.atlas.search.analyticsindex;

import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryClient;

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

    String searchBioentityIdentifiers(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species, int facetLimit) {
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
                        .queryIdentifierSearch(geneQuery)
                        .fetch();
    }

    String getBioentityIdentifiersForSpecies(String species) {
        return analyticsQueryClient.queryBuilder()
                        .bioentityIdentifierFacets(45000) // Something less than 50k because of sitemap limitations, plus some wiggle room for extra data
                        .ofSpecies(species)
                        .fetch();
    }

}
