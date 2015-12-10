package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.auto.value.AutoValue;
import com.google.common.collect.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.JsonPath;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentExpression;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
public class BaselineAnalyticsFacetsReader {

    private static final String EXPERIMENTS_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')].species.buckets[?(@.val=='%s')].defaultQueryFactorType.buckets[?(@.val=='%s')].experimentAccession.buckets[*]";
    private final BaselineExpressionLevelRounder baselineExpressionLevelRounder;

    private static final String FACET_TREE_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline' || @.val=='proteomics_baseline')].species.buckets[*]";

    @Inject
    public BaselineAnalyticsFacetsReader(BaselineExpressionLevelRounder baselineExpressionLevelRounder) {
        this.baselineExpressionLevelRounder = baselineExpressionLevelRounder;
    }


    public ImmutableList<BaselineExperimentExpression> extractAverageExpressionLevel(String json, String species, String defaultQueryFactorType) {
        String experimentsPath = String.format(EXPERIMENTS_PATH, species, defaultQueryFactorType);

        List<Map<String, Object>> results = JsonPath.read(json, experimentsPath);

        ImmutableList.Builder<BaselineExperimentExpression> builder = ImmutableList.builder();

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

                double expression = baselineExpressionLevelRounder.round(sumExpressionLevel / numberOfGenesExpressedAcrossAllAssayGroups);
                BaselineExperimentExpression bslnExpression = BaselineExperimentExpression.create(experimentAccession, assayGroupId, expression);

                builder.add(bslnExpression);
            }
        }

        return builder.build();
    }


    public static String generateFacetsTreeJson(String json) {

        List<Map<String, Object>> results = JsonPath.read(json, FACET_TREE_PATH);

        Multimap<String, FacetTree> facetTreeMultimap = HashMultimap.create();

        for (Map<String, Object> experiment : results) {
            String species = (String) experiment.get("val");

            @SuppressWarnings("unchecked")
            Map<String, Object> factorRoot = (Map<String, Object>) experiment.get("defaultQueryFactorType");
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> buckets = (List<Map<String, Object>>) factorRoot.get("buckets");

            for(Map<String, Object> defaultQueryFactorType : buckets)  {
                String key = (String) defaultQueryFactorType.get("val");
                String name = Factor.convertToLowerCase(key);
                FacetTree factor = FacetTree.create(key, name);

                facetTreeMultimap.put(species, factor);
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(facetTreeMultimap.asMap());
    }


    @AutoValue
    abstract static class FacetTree {
        static FacetTree create(String name, String value) {
            return new AutoValue_BaselineAnalyticsFacetsReader_FacetTree(name, value);
        }

        abstract String name();
        abstract String value();
    }
}
