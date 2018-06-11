package uk.ac.ebi.atlas.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Cell;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public class JsonGeneSearchController extends JsonExceptionHandlingController {

    private GeneSearchService geneSearchService;
    private ScxaExperimentTrader experimentTrader;
    private ExperimentAttributesService experimentAttributesService;

    public JsonGeneSearchController(GeneSearchService geneSearchService,
                                    ScxaExperimentTrader experimentTrader,
                                    ExperimentAttributesService experimentAttributesService) {
        this.geneSearchService = geneSearchService;
        this.experimentTrader = experimentTrader;
        this.experimentAttributesService = experimentAttributesService;
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
            Map<String, Map<Integer, List<Integer>>> markerGeneFacets = geneSearchService.getMarkerGeneProfile(geneId);

            cellIds.forEach((experimentAccession, cells) -> {
                JsonObject resultEntry = new JsonObject();

                JsonObject experimentAttributes = getExperimentInformation(experimentAccession, geneId);
                JsonArray facets = convertFacetModel(factorFacets.getOrDefault(experimentAccession, new HashMap<>()));
                if(markerGeneFacets.containsKey(experimentAccession)) {
                    facets.add(facetValueObject("Marker genes", "Experiments with marker genes"));
                    experimentAttributes.add("markerGenes", convertMarkerGeneModel(experimentAccession, geneId, markerGeneFacets.get(experimentAccession)));
                }

                resultEntry.add("element", experimentAttributes);
                resultEntry.add("facets",  facets);

                results.add(resultEntry);
            });
        }

        resultObject.add("results", results);

        resultObject.add("checkboxFacetGroups",  GSON.toJsonTree(Arrays.asList("Marker genes", "Species")));

        return GSON.toJson(resultObject);
    }

    /** The following two methods convert this model:
     *      {
     *          "Cell type": ["stem", "enterocyte"],
     *          "Organism part": ["small intestine"]
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

        result.addProperty("group", group);
        result.addProperty("value", value);
        result.addProperty("label", StringUtils.capitalize(value));

        return result;
    }

    // Converts list of map of k and cluster IDs into JSON objects
    private JsonArray convertMarkerGeneModel(String experimentAccession, String geneId, Map<Integer, List<Integer>> model) {
        JsonArray result = new JsonArray();

        model.forEach((k, clusterIds) ->
                result.add(markerGeneObject(
                        k,
                        clusterIds,
                        createResultsPageURL(experimentAccession, geneId, k, clusterIds))));

        return result;
    }

    private JsonObject markerGeneObject(Integer k, List<Integer> clusterIds, String url) {
        JsonObject result = new JsonObject();

        result.addProperty("k", k);
        result.add("clusterIds", GSON.toJsonTree(clusterIds));
        result.addProperty("url", url);

        return result;
    }

    private String createExperimentPageURL(String experimentAccession, String geneId) {
       return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments/{experimentAccession}")
                .query("geneId={geneId}")
                .build()
                .expand(experimentAccession, geneId)
                .toString();
    }

    private String createResultsPageURL(String experimentAccession, String geneId, Integer k, List<Integer> clusterId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments/{experimentAccession}/Results")
                .query("geneId={geneId}")
                .query("k={k}")
                .query("clusterId={clusterId}")
                .build()
                .expand(experimentAccession, geneId, k, clusterId)
                .toString();
    }

    private JsonObject getExperimentInformation(String experimentAccession, String geneId) {
        Experiment<Cell> experiment = experimentTrader.getPublicExperiment(experimentAccession);

        JsonObject experimentAttributes = GSON.toJsonTree(experimentAttributesService.getAttributes(experiment)).getAsJsonObject();
        experimentAttributes.addProperty("url", createExperimentPageURL(experimentAccession, geneId));

        return experimentAttributes;
    }

}
