package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import javax.inject.Named;
import java.util.*;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 12/11/2015.
 */
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

            facets.add(FacetFieldMapConverter.get(facetField), facet);
        }

        return facets.toString();
    }

    protected static class FacetFieldMapConverter {
        private static final Map<String,String> FACET_FIELDS_MAP = ImmutableMap.<String, String>builder()
                .put("kingdom", "kingdom")
                .put("species", "species")
                .put("experimentType", "experiment type")
                .put("factors", "experimental variables")
                .put("numReplicates", "number of replicates")
                .put("regulation", "regulation")
                .put("rnaseq_mrna_baseline", "RNA-seq mRNA baseline")
                .put("rnaseq_mrna_differential", "RNA-seq mRNA differential")
                .put("proteomics_baseline", "proteomics baseline")
                .put("microarray_1colour_microrna_differential", "microarray 1-colour microRNA differential")
                .put("microarray_1colour_mrna_differential", "microarray 1-colour mRNA differential")
                .put("microarray_2colour_mrna_differential", "microarray 2-colour mRNA differential")
                .build();

        public static String get(String type) {
            if(FACET_FIELDS_MAP.get(type) != null) {
                return FACET_FIELDS_MAP.get(type);
            } else {
                return type.toLowerCase();
            }
        }
    }
}
