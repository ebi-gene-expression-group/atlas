package uk.ac.ebi.atlas.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
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
        JsonArray result = new JsonArray();

        Map<String, List<String>> cellIds = geneSearchService.getCellIdsInExperiments(geneId);

        List<String> allCellIds = cellIds.values().stream().flatMap(List::stream).collect(Collectors.toList());

        if(!cellIds.isEmpty()) {
            cellIds.forEach((experimentAccession, cells) -> {
                JsonObject resultEntry = new JsonObject();

                resultEntry.add("element", GSON.toJsonTree(geneSearchService.getExperimentInformation(experimentAccession)));

                resultEntry.add("facets",  convertFacetModel(geneSearchService.getFacets(allCellIds).get(experimentAccession)));

                result.add(resultEntry);
            });
        }

        return GSON.toJson(result);
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
                        result.add(facetValueObject(facetGroup, facetValue, true)))
        );

        return result;
    }

    private JsonObject facetValueObject(String group, String value, boolean hasLabel) {
        JsonObject result = new JsonObject();

        result.addProperty("group", SchemaFieldNameUtils.characteristicFieldNameToDisplayName(group));
        result.addProperty("value", value);

        if(hasLabel) {
            result.addProperty("label", StringUtils.capitalize(value));
        }

        return result;
    }
}
