package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.auto.value.AutoValue;
import com.google.common.collect.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentExpression;
import uk.ac.manchester.cs.bhig.util.Tree;

import javax.inject.Named;
import java.util.*;

@Named
public class BaselineAnalyticsFacetsReader {

    public ImmutableList<BaselineExperimentExpression> extractAverageExpressionLevel(List<Map<String, Object>> results) {

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

                double expression = BaselineExpressionLevelRounder.round(sumExpressionLevel / numberOfGenesExpressedAcrossAllAssayGroups);
                BaselineExperimentExpression bslnExpression = BaselineExperimentExpression.create(experimentAccession, assayGroupId, expression);

                builder.add(bslnExpression);
            }
        }

        return builder.build();
    }


    public static String generateFacetsTreeJson(List<Map<String, Object>> results) {

        TreeMultimap<String, FacetTreeItem> facetTreeMultimap = TreeMultimap.create();

        for (Map<String, Object> experiment : results) {
            String species = (String) experiment.get("val");

            @SuppressWarnings("unchecked")
            Map<String, Object> factorRoot = (Map<String, Object>) experiment.get("defaultQueryFactorType");
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> buckets = (List<Map<String, Object>>) factorRoot.get("buckets");

            for(Map<String, Object> defaultQueryFactorType : buckets)  {
                String key = (String) defaultQueryFactorType.get("val");
                String name = Factor.convertToLowerCase(key);
                FacetTreeItem factor = FacetTreeItem.create(key, name);

                facetTreeMultimap.put(species, factor);
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeSpecialFloatingPointValues().create();

        return gson.toJson(facetTreeMultimap.asMap());
    }

    @AutoValue
    abstract static class FacetTreeItem implements Comparable<FacetTreeItem> {
        static FacetTreeItem create(String name, String value) {
            return new AutoValue_BaselineAnalyticsFacetsReader_FacetTreeItem(name, value);
        }

        @Override
        public int compareTo(FacetTreeItem other) {
            if (this.name().equals(other.name())) {
                return 0;
            } else if (this.name().equals("ORGANISM_PART")) {
                return -1;
            } else if (other.name().equals("ORGANISM_PART")) {
                return 1;
            } else {
                return this.name().compareTo(other.name());
            }
        }

        abstract String name();
        abstract String value();
    }
}
