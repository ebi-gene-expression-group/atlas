package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryClient;
import com.jayway.jsonpath.JsonPath;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class BaselineAnalyticsSearchDao {

    //TODO no need for a filter there
    static final String EXPERIMENTS_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')]..experimentAccession.buckets[*]";
    static final String FACET_TREE_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')].species.buckets[*]";

    private final AnalyticsQueryClient analyticsQueryClient;

    @Inject
    public BaselineAnalyticsSearchDao(AnalyticsQueryClient analyticsQueryClient) {
        this.analyticsQueryClient = analyticsQueryClient;
    }

    public List<Map<String, Object>> fetchFacetsThatHaveExpression(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species) {
        String response =
                analyticsQueryClient.queryBuilder()
                .baselineFacets()
                .queryIdentifierSearch(geneQuery)
                .queryConditionsSearch(conditionQuery)
                .ofSpecies(species)
                .fetch();
        return JsonPath.read(response, FACET_TREE_PATH);
    }

    public List<Map<String, Object>> fetchFacetsThatHaveExpression(SemanticQuery geneQuery) {

        String response =
                analyticsQueryClient.queryBuilder()
                        .queryIdentifierSearch(geneQuery)
                        .fetch();
        return JsonPath.read(response, FACET_TREE_PATH);
    }

    public List<Map<String, Object>> fetchExpressionLevelFaceted(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species, String defaultQueryFactorType) {
        String response = analyticsQueryClient.queryBuilder()
                .baselineFacets()
                .queryIdentifierSearch(geneQuery)
                .queryConditionsSearch(conditionQuery)
                .ofSpecies(species)
                .withFactorType(defaultQueryFactorType)
                .fetch();

        return JsonPath.read(response,EXPERIMENTS_PATH);
    }

    public List<Map<String, Object>> fetchExpressionLevelFaceted(SemanticQuery query, String species, String defaultQueryFactorType) {
        String response = analyticsQueryClient.queryBuilder()
                .baselineFacets()
                .queryIdentifierOrConditionsSearch(query)
                .ofSpecies(species)
                .withFactorType(defaultQueryFactorType).fetch();

        return JsonPath.read(response,EXPERIMENTS_PATH);
    }

}
