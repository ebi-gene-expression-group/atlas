package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.collect.ImmutableSortedMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class DifferentialFacetsReader {

    private static final String[] FACET_FIELDS = {"kingdom", "species", "experimentType", "factors", "numReplicates", "regulation"};

    public String generateFacetsTreeJson(String solrResponseAsJson) {
        JsonObject facets = new JsonObject();

        ReadContext jsonReadContext = JsonPath.parse(solrResponseAsJson);

        for (String facetField : FACET_FIELDS) {
            JsonArray facet = new JsonArray();
            List<Object> facetFieldValues = jsonReadContext.read("$.." + facetField + "..val");
            for (Object facetFieldValue : facetFieldValues) {
                JsonObject facetItem = new JsonObject();

                facetItem.addProperty("name", facetFieldValue.toString());
                facetItem.addProperty("value", FacetFieldMapConverter.get(facetFieldValue.toString()));

                facet.add(facetItem);
            }

            facets.add(facetField, facet);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeSpecialFloatingPointValues().create();
        return gson.toJson(facets);
    }

    protected static class FacetFieldMapConverter {
        private static final Map<String, String> FACET_FIELDS_MAP = new ImmutableSortedMap.Builder<String, String>(String.CASE_INSENSITIVE_ORDER)
                .put("rnaseq_mrna_baseline", "RNA-seq mRNA baseline")
                .put("rnaseq_mrna_differential", "RNA-seq mRNA differential")
                .put("proteomics_baseline", "proteomics baseline")
                .put("microarray_1colour_microrna_differential", "Microarray 1-colour microRNA differential")
                .put("microarray_1colour_mrna_differential", "Microarray 1-colour mRNA differential")
                .put("microarray_2colour_mrna_differential", "Microarray 2-colour mRNA differential")
                .put("rna interference", "RNA interference")
                .put("rna", "RNA")
                .build();

        public static String get(String field) {
            if(FACET_FIELDS_MAP.get(field) != null) {
                return FACET_FIELDS_MAP.get(field);
            } else {
                return field.substring(0, 1).toUpperCase() + field.substring(1).toLowerCase();
            }
        }
    }
}
