package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.TreeMultimap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentExpression;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BaselineAnalyticsFacetsReader {

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
            } else  if ("homo sapiens".equalsIgnoreCase(o1)) {
                return -1;
            } else if ("homo sapiens".equalsIgnoreCase(o2)) {
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
            } else if ("organism_part".equalsIgnoreCase(o1.name())) {
                return -1;
            } else if ("organism_part".equalsIgnoreCase(o2.name())) {
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
