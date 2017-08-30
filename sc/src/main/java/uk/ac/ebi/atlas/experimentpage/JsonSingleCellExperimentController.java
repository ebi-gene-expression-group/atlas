package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.experimentpage.json.JsonExperimentController;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class JsonSingleCellExperimentController extends JsonExperimentController {

    private final Gson gson = new Gson();

    @Inject
    public JsonSingleCellExperimentController(ExperimentTrader experimentTrader) {
        super(experimentTrader);
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}/tsneplot/reference/{k}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String tSnePlotReference(
            @PathVariable String experimentAccession,
            @PathVariable int k,
            @RequestParam(defaultValue = "") String accessKey) throws IOException {
        experimentTrader.getExperiment(experimentAccession, accessKey);

        Optional<JsonArray> maybeClustersArray = hardCodedClusters(k);
        if (maybeClustersArray.isPresent()) {
            JsonObject payload = new JsonObject();
            payload.add("coordinates", hardCodedTSneCoordinates());
            payload.add("clusters", maybeClustersArray.get());
            return gson.toJson(payload);
        } else {
            throw new ResourceNotFoundException(String.format("Cluster data not found for k=%d", k));
        }
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}/tsneplot/expression/{geneId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String tSnePlotExpression(
            @PathVariable String experimentAccession,
            @PathVariable String geneId,
            @RequestParam(defaultValue = "") String accessKey) throws IOException {
        // Return hard-coded t-SNE plot data: coordinates
        experimentTrader.getExperiment(experimentAccession, accessKey);
        return "";
    }

    private JsonArray hardCodedTSneCoordinates() throws IOException {
        String json = IOUtils.readLines(
                JsonSingleCellExperimentController.class.getResourceAsStream("tsne-coordinates.json"),
                StandardCharsets.UTF_8)
        .stream().collect(Collectors.joining(""));

        return new JsonParser().parse(json).getAsJsonArray();
    }

    private Optional<JsonArray> hardCodedClusters(int k) throws IOException {
        String json = IOUtils.readLines(
                JsonSingleCellExperimentController.class.getResourceAsStream("tsne-clusters.json"),
                StandardCharsets.UTF_8)
                .stream().collect(Collectors.joining(""));

        JsonObject jsonObject =  new JsonParser().parse(json).getAsJsonObject();

        return Optional.ofNullable(jsonObject.getAsJsonArray(Integer.toString(k)));
    }

}
