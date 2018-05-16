package uk.ac.ebi.atlas.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;

import java.util.List;
import java.util.Map;

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

        cellIds.forEach((experimentAccession, cells) -> {
            JsonObject resultEntry = new JsonObject();

            JsonElement element = GSON.toJsonTree(geneSearchService.getExperimentInformation(experimentAccession));
            resultEntry.add("element", element);

            JsonArray facets = new JsonArray();
            cells.forEach(facets::add);
            resultEntry.add("facets", facets);

            result.add(resultEntry);
        });

        return GSON.toJson(result);
    }
}
