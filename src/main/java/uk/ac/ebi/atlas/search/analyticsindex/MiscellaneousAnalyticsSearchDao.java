package uk.ac.ebi.atlas.search.analyticsindex;

import org.apache.solr.client.solrj.SolrQuery;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MiscellaneousAnalyticsSearchDao {

    private final AnalyticsRestClient analyticsRestClient;
    private final AnalyticsQueryFactory analyticsQueryFactory;

    @Inject
    public MiscellaneousAnalyticsSearchDao(AnalyticsRestClient analyticsRestClient, AnalyticsQueryFactory analyticsQueryFactory) {
        this.analyticsRestClient = analyticsRestClient;
        this.analyticsQueryFactory = analyticsQueryFactory;
    }

    String fetchExperimentTypesInAnyField(SemanticQuery query) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .experimentTypeFacets()
                        .queryIdentifierOrConditionsSearch(query)
                        .build();

        return analyticsRestClient.fetchResults(solrQuery);
    }


    String fetchExperimentTypes(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .experimentTypeFacets()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .build();
        return analyticsRestClient.fetchResults(solrQuery);
    }

    String searchBioentityIdentifiers(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species, int facetLimit) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .bioentityIdentifierFacets(facetLimit)
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .build();
        return analyticsRestClient.fetchResults(solrQuery);
    }

    String searchBioentityIdentifiersForTissuesInBaselineExperiments(SemanticQuery geneQuery) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .bioentityIdentifierFacets(1)
                        .queryIdentifierSearch(geneQuery)
                        .build();
        return analyticsRestClient.fetchResults(solrQuery);
    }

    String getBioentityIdentifiersForSpecies(String species) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .bioentityIdentifierFacets(45000) // Something less than 50k because of sitemap limitations, plus some wiggle room for extra data
                        .ofSpecies(species)
                        .build();
        return analyticsRestClient.fetchResults(solrQuery);
    }

}
