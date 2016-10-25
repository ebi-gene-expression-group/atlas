package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.jayway.jsonpath.JsonPath;
import org.apache.solr.client.solrj.SolrQuery;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsRestClient;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class BaselineAnalyticsSearchDao {

    //TODO no need for a filter there
    static final String EXPERIMENTS_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')]..experimentAccession.buckets[*]";
    static final String FACET_TREE_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')].species.buckets[*]";

    private final AnalyticsRestClient analyticsRestClient;
    private final AnalyticsQueryFactory analyticsQueryFactory;

    @Inject
    public BaselineAnalyticsSearchDao(AnalyticsRestClient analyticsRestClient, AnalyticsQueryFactory analyticsQueryFactory) {
        this.analyticsRestClient = analyticsRestClient;
        this.analyticsQueryFactory = analyticsQueryFactory;
    }

    public List<Map<String, Object>> fetchFacetsThatHaveExpression(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .baselineOnly()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                .build();

        String response = analyticsRestClient.fetchResults(solrQuery);
        return JsonPath.read(response, FACET_TREE_PATH);
    }

    public List<Map<String, Object>> fetchFacetsThatHaveExpression(SemanticQuery geneQuery) {
        SolrQuery solrQuery = analyticsQueryFactory.builder().queryIdentifierSearch(geneQuery).build();

        String response = analyticsRestClient.fetchResults(solrQuery);
        return JsonPath.read(response, FACET_TREE_PATH);
    }

    public List<Map<String, Object>> fetchExpressionLevelFaceted(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species, String defaultQueryFactorType) {
        SolrQuery solrQuery =
                analyticsQueryFactory.builder()
                        .baselineOnly()
                        .queryIdentifierSearch(geneQuery)
                        .queryConditionsSearch(conditionQuery)
                        .ofSpecies(species)
                        .withFactorType(defaultQueryFactorType)
                .build();

        return JsonPath.read(analyticsRestClient.fetchResults(solrQuery),EXPERIMENTS_PATH);
    }

    public List<Map<String, Object>> fetchExpressionLevelFaceted(SemanticQuery query, String species, String defaultQueryFactorType) {
        SolrQuery solrQuery =analyticsQueryFactory.builder()
                .baselineOnly()
                .queryIdentifierOrConditionsSearch(query)
                .ofSpecies(species)
                .withFactorType(defaultQueryFactorType).build();

        return JsonPath.read(analyticsRestClient.fetchResults(solrQuery),EXPERIMENTS_PATH);
    }

}
