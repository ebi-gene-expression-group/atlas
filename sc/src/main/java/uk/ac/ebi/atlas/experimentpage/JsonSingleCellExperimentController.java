package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.TreeMultimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.experimentpage.json.JsonExperimentController;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.tsne.TSnePlotCollator;
import uk.ac.ebi.atlas.tsne.TSnePoint;

import javax.inject.Inject;
import java.io.IOException;
import java.util.stream.Collectors;

@RestController
public class JsonSingleCellExperimentController extends JsonExperimentController {

    private Gson gson = new GsonBuilder().registerTypeAdapter(TSnePoint.class, TSnePoint.typeAdapter(new Gson())).create();
    private TSnePlotCollator tSnePlotCollator;

    @Inject
    public JsonSingleCellExperimentController(ExperimentTrader experimentTrader, TSnePlotCollator tSnePlotCollator) {
        super(experimentTrader);
        this.tSnePlotCollator = tSnePlotCollator;
    }

    @RequestMapping(
            value = "/json/experiments/{experimentAccession}/tsneplot/clusters",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String tSnePlotDefaultClusters(@PathVariable String experimentAccession,
                                      @RequestParam(defaultValue = "") String accessKey) throws IOException {
        return tSnePlotClusters(experimentAccession, 2, accessKey);
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}/tsneplot/clusters/{k}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String tSnePlotClusters(
            @PathVariable String experimentAccession,
            @PathVariable int k,
            @RequestParam(defaultValue = "") String accessKey) throws IOException {

        if (!"E-RANDOM".equalsIgnoreCase(experimentAccession)) {
            experimentTrader.getExperiment(experimentAccession, accessKey);
        }

        TreeMultimap<Integer, TSnePoint> clusterPoints =
                tSnePlotCollator.getTSnePlotWithClusters(experimentAccession, k);

        return gson.toJson(
                clusterPoints.keySet().stream()
                        .map(key -> ImmutableMap.of("name", Integer.toString(key), "data", clusterPoints.get(key)))
                        .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}/tsneplot/expression",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String tSnePlotDefaultExpression(
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) throws IOException {
        return tSnePlotExpression(experimentAccession, "", accessKey);
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}/tsneplot/expression/{geneId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String tSnePlotExpression(
            @PathVariable String experimentAccession,
            @PathVariable String geneId,
            @RequestParam(defaultValue = "") String accessKey) throws IOException {
        // Return hard-coded t-SNE plot data: coordinates
        experimentTrader.getExperiment(experimentAccession, accessKey);

        // Wrapped in a list to have a one-element array (required by Highcharts)
        return gson.toJson(
                ImmutableList.of(ImmutableMap.of("data", tSnePlotCollator.getTSnePlotWithExpression(experimentAccession, geneId))));
    }

}
