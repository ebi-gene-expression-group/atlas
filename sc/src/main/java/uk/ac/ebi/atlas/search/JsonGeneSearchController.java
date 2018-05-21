package uk.ac.ebi.atlas.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.solr.utils.SchemaFieldNameUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public class JsonGeneSearchController extends JsonExceptionHandlingController {

    private GeneSearchService geneSearchService;

    public JsonGeneSearchController(GeneSearchService geneSearchService) {
        this.geneSearchService = geneSearchService;
    }

    @RequestMapping(
            value = "/json/search/{geneId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public String search(@PathVariable String geneId) {
        JsonObject resultObject = new JsonObject();
        JsonArray results = new JsonArray();

        Map<String, List<String>> cellIds = geneSearchService.getCellIdsInExperiments(geneId);

        if(!cellIds.isEmpty()) {
            List<String> allCellIds = cellIds
                    .values()
                    .stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

            Map<String, Map<String, List<String>>> factorFacets = geneSearchService.getFacets(allCellIds);
            Map<String, List<Pair<Integer, Integer>>> markerGeneFacets = geneSearchService.getMarkerGeneProfile(geneId);

            cellIds.forEach((experimentAccession, cells) -> {
                JsonObject resultEntry = new JsonObject();

                JsonObject experimentAttributes = GSON.toJsonTree(geneSearchService.getExperimentInformation(experimentAccession)).getAsJsonObject();
                JsonArray facetsJson = convertFacetModel(factorFacets.get(experimentAccession));
                if(markerGeneFacets.containsKey(experimentAccession)) {
                    facetsJson.add(facetValueObject("Marker genes", "Experiments with marker genes"));
                    experimentAttributes.add("markerGenes", convertMarkerGeneModel(markerGeneFacets.get(experimentAccession)));
                }

                resultEntry.add("element", experimentAttributes);
                resultEntry.add("facets",  facetsJson);

                results.add(resultEntry);
            });
        }

        resultObject.add("results", results);

        JsonArray checkboxes = new JsonArray();
        checkboxes.add("Marker genes");
        resultObject.add("checkboxFacetGroups", checkboxes);

        return GSON.toJson(resultObject);
    }

    /** The following two methods convert this model:
     *      {
     *          cell_type: ["stem", "enterocyte"],
     *          organism_part: ["small intestine"]
     *      }
     * into:
     *      [
     *          {
     *              group: "Cell type",
     *              value: "stem",
     *              label: "Stem"
     *          },
     *          {
     *              group: "Cell type",
     *              value: "enterocyte",
     *              label: "Enterocyte
     *          },
     *          {
     *              group: "Organism part",
     *              value: "small intestine",
     *              label: "Small intestine"
     *          }
     *      ]
     */
    private JsonArray convertFacetModel(Map<String, List<String>> model) {
        JsonArray result = new JsonArray();

        model.forEach((facetGroup, facetValuesList) ->
                facetValuesList.forEach(facetValue ->
                        result.add(facetValueObject(facetGroup, facetValue)))
        );

        return result;
    }

    private JsonObject facetValueObject(String group, String value) {
        JsonObject result = new JsonObject();

        result.addProperty("group", SchemaFieldNameUtils.characteristicFieldNameToDisplayName(group));
        result.addProperty("value", value);
        result.addProperty("label", StringUtils.capitalize(value));

        return result;
    }

    // Converts list of (k, clusterId) pairs into json objects
    private JsonArray convertMarkerGeneModel(List<Pair<Integer, Integer>> model) {
        JsonArray result = new JsonArray();

        model.forEach((kClusterIdPair) ->
                result.add(markerGeneObject(kClusterIdPair.getLeft(), kClusterIdPair.getRight()))
        );

        return result;
    }

    private JsonObject markerGeneObject(Integer k, Integer clusterId) {
        JsonObject result = new JsonObject();

        result.addProperty("k", k);
        result.addProperty("clusterId", clusterId);

        return result;
    }
}
