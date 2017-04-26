package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.jayway.jsonpath.JsonPath;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
public class BaselineAnalyticsSearchDao {

    //TODO no need for a filter there
    public static final String EXPERIMENTS_PATH = "$.facets.experimentType.buckets[?(@.val=='RNASEQ_MRNA_BASELINE' ||" +
            " @.val=='PROTEOMICS_BASELINE')]..experimentAccession.buckets[*]";
    static final String FACET_TREE_PATH = "$.facets.experimentType.buckets[?(@.val=='RNASEQ_MRNA_BASELINE' || @.val=='PROTEOMICS_BASELINE')].species.buckets[*]";

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

    List<Map<String, Object>> fetchExpressionLevelsPayload(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species, String defaultQueryFactorType) {
        String response = analyticsQueryClient.queryBuilder()
                .baselineFacets()
                .queryIdentifierSearch(geneQuery)
                .queryConditionsSearch(conditionQuery)
                .ofSpecies(species)
                .withFactorType(defaultQueryFactorType)
                .fetch();

        return JsonPath.read(response,EXPERIMENTS_PATH);
    }

    public Map<String, Map<String, Double>> fetchExpressionLevels(SemanticQuery geneQuery, SemanticQuery conditionQuery, String species, String defaultQueryFactorType){
        return aggregateExpressionLevelByDataColumnAndExperiment(fetchExpressionLevelsPayload(geneQuery, conditionQuery, species, defaultQueryFactorType));
    }

    public Map<String, Map<String, Double>> aggregateExpressionLevelByDataColumnAndExperiment(List<Map<String, Object>> results) {

        Map<String, Map<String, Double>> result = new HashMap<>();

        for (Map<String, Object> experiment : results) {
            String experimentAccession = (String) experiment.get("val");
            int numberOfGenesExpressedAcrossAllAssayGroups = (int) experiment.get("uniqueIdentifiers");

            @SuppressWarnings("unchecked")
            Map<String, Object> assayGroupIdRoot = (Map<String, Object>) experiment.get("assayGroupId");
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> buckets = (List<Map<String, Object>>) assayGroupIdRoot.get("buckets");

            for(Map<String, Object> assayGroup : buckets)  {
                String assayGroupId = (String) assayGroup.get("val");
                double sumExpressionLevel;
                sumExpressionLevel = ((Number) assayGroup.get("sumExpressionLevel")).doubleValue();

                double expression =
                        BaselineExpressionLevelRounder.round(
                                sumExpressionLevel / numberOfGenesExpressedAcrossAllAssayGroups);

                if(!result.containsKey(experimentAccession)){
                    result.put(experimentAccession, new HashMap<String, Double>());
                }
                result.get(experimentAccession).put(assayGroupId, expression);
            }
        }

        return result;

    }

}
