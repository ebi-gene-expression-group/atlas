package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsClient;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder;
import uk.ac.ebi.atlas.solr.SolrUtil;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;

@Named
public class AnalyticsIndexSearchDAO {

    private AnalyticsClient analyticsClient;

    @Inject
    public AnalyticsIndexSearchDAO(AnalyticsClient analyticsClient) {
        this.analyticsClient = analyticsClient;
    }

    ImmutableSet<String> fetchExperimentTypesInAnyField(SemanticQuery query) {
        SolrQuery solrQuery =
                new AnalyticsQueryBuilder()
                        .queryIdentifierSearch(query)
                        .queryConditionsSearch(query)
                        .facetBy(AnalyticsQueryBuilder.Field.EXPERIMENT_TYPE)
                        .filterAboveDefaultCutoff()
                        .setRows(0)
                        .useOr()
                        .build();

        QueryResponse queryResponse = analyticsClient.query(solrQuery);
        return SolrUtil.extractFirstFacetValues(queryResponse);
    }


    ImmutableSet<String> fetchExperimentTypes(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        SolrQuery solrQuery =
                new AnalyticsQueryBuilder()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .facetBy(AnalyticsQueryBuilder.Field.EXPERIMENT_TYPE)
                        .filterAboveDefaultCutoff()
                        .setRows(0)
                        .build();

        QueryResponse queryResponse = analyticsClient.query(solrQuery);
        return SolrUtil.extractFirstFacetValues(queryResponse);
    }

    ImmutableSet<String> searchBioentityIdentifiers(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species, int facetLimit) {
        SolrQuery solrQuery =
                new AnalyticsQueryBuilder()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .setRows(0)
                        .facetBy(AnalyticsQueryBuilder.Field.BIOENTITY_IDENTIFIER)
                        .setFacetLimit(facetLimit)
                        .build();

        QueryResponse queryResponse = analyticsClient.query(solrQuery);
        return SolrUtil.extractFirstFacetValues(queryResponse);
    }

    ImmutableSet<String> searchBioentityIdentifiersForTissuesInBaselineExperiments(SemanticQuery geneQuery) {
        SolrQuery solrQuery =
                new AnalyticsQueryBuilder()
                        .queryIdentifierSearch(geneQuery)
                        .setRows(0)
                        .facetBy(AnalyticsQueryBuilder.Field.BIOENTITY_IDENTIFIER)
                        .filterBaselineAboveDefaultCutoff()
                        .setFacetLimit(1)
                        .build();

        QueryResponse queryResponse = analyticsClient.query(solrQuery);
        return SolrUtil.extractFirstFacetValues(queryResponse);
    }

    Collection<String> getBioentityIdentifiersForSpecies(String species) {
        List<FacetField> facetFields = analyticsClient.query(
                new AnalyticsQueryBuilder()
                        .ofSpecies(species)
                        .filterAboveDefaultCutoff()
                        .facetBy(AnalyticsQueryBuilder.Field.BIOENTITY_IDENTIFIER)
                        .setRows(0)
                        .setFacetLimit(45000)   // Something less than 50k because of sitemap limitations, plus some wiggle room for extra data
                        .build()).getFacetFields();

        return facetFields.size() != 1
                ? ImmutableSet.<String>of()
                : Collections2.transform(facetFields.get(0).getValues(), new Function<FacetField.Count, String>() {
            @Override
            public String apply(FacetField.Count count) {
                return count.getName();
            }
        });
    }

}
