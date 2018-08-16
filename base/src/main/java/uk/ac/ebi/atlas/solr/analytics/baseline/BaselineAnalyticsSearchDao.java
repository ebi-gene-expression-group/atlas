package uk.ac.ebi.atlas.solr.analytics.baseline;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import org.apache.commons.math.util.MathUtils;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.analytics.query.AnalyticsQueryClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

@Component
public class BaselineAnalyticsSearchDao {
    static final int MIN_PRECISION = 7;
    static final DoubleUnaryOperator BASELINE_EXPRESSION_ROUNDER =
            value ->
                    value >= 1 ?
                            MathUtils.round(value, 0) :
                    value >= 0.1 ?
                            MathUtils.round(value, 1) :
                    // value < 0.1
                            MathUtils.round(value, MIN_PRECISION);

    // We don’t need to worry about the type, analyticsQueryClient.baselineFacets filters only baseline experiments
    private static final String EXPERIMENTS_PATH = "$.facets.experimentType.buckets..experimentAccession.buckets[*]";
    static final String FACET_TREE_PATH = "$.facets.experimentType.buckets[*].species.buckets[*]";

    private final AnalyticsQueryClient analyticsQueryClient;

    public BaselineAnalyticsSearchDao(AnalyticsQueryClient analyticsQueryClient) {
        this.analyticsQueryClient = analyticsQueryClient;
    }

    public List<Map<String, Object>> fetchFacetsThatHaveExpression(SemanticQuery geneQuery,
                                                                   SemanticQuery conditionQuery,
                                                                   String species) {
        String response =
                analyticsQueryClient.queryBuilder()
                .baselineFacets()
                .queryIdentifierSearch(geneQuery)
                .queryConditionsSearch(conditionQuery)
                .ofSpecies(species)
                .fetch();

        Configuration jsonPathConfiguration =
                Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS);

        return JsonPath.using(jsonPathConfiguration).parse(response).read(FACET_TREE_PATH);
    }

    public List<Map<String, Object>> fetchExpressionLevelsPayload(SemanticQuery geneQuery,
                                                           SemanticQuery conditionQuery,
                                                           String species,
                                                           String defaultQueryFactorType) {
        String response = analyticsQueryClient.queryBuilder()
                .baselineFacets()
                .queryIdentifierSearch(geneQuery)
                .queryConditionsSearch(conditionQuery)
                .ofSpecies(species)
                .withFactorType(defaultQueryFactorType)
                .fetch();

        return JsonPath.read(response, EXPERIMENTS_PATH);
    }

    public Map<String, Map<String, Double>> fetchExpressionLevels(SemanticQuery geneQuery,
                                                                  SemanticQuery conditionQuery,
                                                                  String species,
                                                                  String defaultQueryFactorType) {
        return aggregateExpressionLevelByDataColumnAndExperiment(
                fetchExpressionLevelsPayload(geneQuery, conditionQuery, species, defaultQueryFactorType));
    }

    private Map<String, Map<String, Double>> aggregateExpressionLevelByDataColumnAndExperiment(
            List<Map<String, Object>> results) {

        Map<String, Map<String, Double>> result = new HashMap<>();

        for (Map<String, Object> experiment : results) {
            String experimentAccession = (String) experiment.get("val");

            @SuppressWarnings("unchecked")
            Map<String, Object> assayGroupIdRoot = (Map<String, Object>) experiment.get("assayGroupId");
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> buckets = (List<Map<String, Object>>) assayGroupIdRoot.get("buckets");

            for (Map<String, Object> assayGroup : buckets) {
                String assayGroupId = (String) assayGroup.get("val");
                double sumExpressionLevel = ((Number) assayGroup.get("sumExpressionLevel")).doubleValue();
                long numberOfGenesExpressedInAssayGroup = ((Number) assayGroup.get("count")).longValue();

                double expression =
                        BASELINE_EXPRESSION_ROUNDER.applyAsDouble(
                                sumExpressionLevel / numberOfGenesExpressedInAssayGroup);

                if (!result.containsKey(experimentAccession)) {
                    result.put(experimentAccession, new HashMap<>());
                }

                result.get(experimentAccession).put(assayGroupId, expression);
            }
        }

        return result;
    }
}
