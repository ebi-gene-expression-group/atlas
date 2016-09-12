package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.auto.value.AutoValue;
import com.google.common.collect.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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


    public static JsonObject generateFacetsTreeJson(List<Map<String, Object>> results) {

        TreeMultimap<String, FacetTreeItem> facetTreeMultimap = TreeMultimap.create(new HomoSapiensFirstComparator(), new OrganismPartFirstComparator());

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

        return new Gson().toJsonTree(facetTreeMultimap.asMap()).getAsJsonObject();
    }

    private static class HomoSapiensFirstComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            if (o1.equals(o2)) {
                return 0;
            } else  if (o1.equals("homo sapiens")) {
                return -1;
            } else if (o2.equals("homo sapiens")) {
                return 1;
            }
            else {
                return o1.compareTo(o2);
            }
        }
    }

    private static class OrganismPartFirstComparator implements Comparator<FacetTreeItem> {
        @Override
        public int compare(FacetTreeItem o1, FacetTreeItem o2) {
            if (o1.name().equals(o2.name())) {
                return 0;
            } else if (o1.name().equals("ORGANISM_PART")) {
                return -1;
            } else if (o2.name().equals("ORGANISM_PART")) {
                return 1;
            } else {
                return o1.name().compareTo(o2.name());
            }
        }
    }

    @AutoValue
    abstract static class FacetTreeItem {
        static FacetTreeItem create(String name, String value) {
            return new AutoValue_BaselineAnalyticsFacetsReader_FacetTreeItem(name, value);
        }

        abstract String name();
        abstract String value();
    }
}
