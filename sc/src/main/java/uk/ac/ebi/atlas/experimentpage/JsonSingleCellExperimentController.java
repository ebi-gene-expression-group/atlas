package uk.ac.ebi.atlas.experimentpage;

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
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.tsne.TSnePlotCollator;
import uk.ac.ebi.atlas.tsne.TSnePoint;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.OptionalDouble;
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
            value = "/json/experiments/{experimentAccession}/tsneplot/{perplexity}/clusters/{k}/expression",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String tSnePlotClustersWithDefaultExpression(
            @PathVariable String experimentAccession,
            @PathVariable int perplexity,
            @PathVariable int k,
            @RequestParam(defaultValue = "") String accessKey) throws IOException, InterruptedException {
        return tSnePlotClustersWithExpression(experimentAccession, perplexity, k, "", accessKey);
    }

    @RequestMapping(
            value = "/json/experiments/{experimentAccession}/tsneplot/{perplexity}/clusters/{k}/expression/{geneId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String tSnePlotClustersWithExpression(
            @PathVariable String experimentAccession,
            @PathVariable int perplexity,
            @PathVariable int k,
            @PathVariable String geneId,
            @RequestParam(defaultValue = "") String accessKey) throws IOException, InterruptedException {

        // Fail if experiment can’t be found
        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        // Artificial delay to show the funky loading animation in the front end
        // Thread.sleep(3000);

        TreeMultimap<Integer, TSnePoint> clusterPointsWithExpression =
                tSnePlotCollator.getTsnePlotWithClustersAndExpression(experimentAccession, k, geneId);

        OptionalDouble max =
                clusterPointsWithExpression.values().stream().mapToDouble(TSnePoint::expressionLevel).max();
        OptionalDouble min =
                clusterPointsWithExpression.values().stream().mapToDouble(TSnePoint::expressionLevel).min();
        String unit = "TPM"; // Get units from experiment, or from request parameter if more than one is available

        List<ImmutableMap<String, Object>> series =
                clusterPointsWithExpression.keySet().stream()
                .map(key -> ImmutableMap.of(
                        "name", Integer.toString(key),
                        "data", clusterPointsWithExpression.get(key))
                )
                .collect(Collectors.toList());

        return gson.toJson(
                ImmutableMap.of("series", series, "max", max.orElse(0.0), "min", min.orElse(0.0), "unit", unit)
        );
    }

}
