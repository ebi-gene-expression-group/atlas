package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.experimentpage.tsne.TSnePoint;
import uk.ac.ebi.atlas.tsne.TSnePlotService;
import uk.ac.ebi.atlas.experimentpage.json.JsonExperimentController;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public class JsonExperimentTSnePlotController extends JsonExperimentController {
    private final TSnePlotService tSnePlotService;

    @Inject
    public JsonExperimentTSnePlotController(ScxaExperimentTrader experimentTrader, TSnePlotService tSnePlotService) {
        super(experimentTrader);
        this.tSnePlotService = tSnePlotService;
    }

    @RequestMapping(
            value = "/json/experiments/{experimentAccession}/tsneplot/{perplexity}/clusters/k/{k}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String tSnePlotWithClusters(
            @PathVariable String experimentAccession,
            @PathVariable int perplexity,
            @PathVariable int k,
            @RequestParam(defaultValue = "") String accessKey) {
        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);
        return GSON.toJson(
                ImmutableMap.of(
                        "series",
                        modelForHighcharts(
                                "Cluster ",
                                tSnePlotService.fetchTSnePlotWithClusters(experiment.getAccession(), perplexity, k))));
    }


    @RequestMapping(
            value = "/json/experiments/{experimentAccession}/tsneplot/{perplexity}/clusters/k/{k}/expression/{geneId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String tSnePlotExpressionsWithClusters(
            @PathVariable String experimentAccession,
            @PathVariable int perplexity,
            @PathVariable int k,
            @PathVariable String geneId,
            @RequestParam(defaultValue = "") String accessKey) {
        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        Map<Integer, Set<TSnePoint>> pointsWithExpressionAndClusters =
                tSnePlotService.fetchTSnePlotWithExpressionAndClusters(experiment.getAccession(), perplexity, geneId, k);

        OptionalDouble max = pointsWithExpressionAndClusters.values().stream().flatMap(Set::stream)
                .map(TSnePoint::expressionLevel)
                .filter(Optional::isPresent)
                .mapToDouble(Optional::get)
                .filter(d -> d > 0)
                .max();

        OptionalDouble min = pointsWithExpressionAndClusters.values().stream().flatMap(Set::stream)
                .map(TSnePoint::expressionLevel)
                .filter(Optional::isPresent)
                .mapToDouble(Optional::get)
                .filter(d -> d > 0)
                .min();

        String unit = "TPM"; // Get units from experiment, or from request parameter if more than one is available

        Map<String, Object> model = new HashMap<>();
        model.put("series", modelForHighcharts("Cluster ", pointsWithExpressionAndClusters));
        model.put("unit", unit);
        max.ifPresent(n -> model.put("max", n));
        min.ifPresent(n -> model.put("min", n));

        return GSON.toJson(model);
    }

    @RequestMapping(
            value = "/json/experiments/{experimentAccession}/tsneplot/{perplexity}/clusters/k/{k}/expression",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String tSnePlotExpressionsWithClusters(
            @PathVariable String experimentAccession,
            @PathVariable int perplexity,
            @PathVariable int k,
            @RequestParam(defaultValue = "") String accessKey) {
        return tSnePlotExpressionsWithClusters(experimentAccession, perplexity, k, "", accessKey);
    }

    @RequestMapping(
            value = "/json/experiments/{experimentAccession}/tsneplot/{perplexity}/metadata/{metadata}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String tSnePlotWithMetadata(
            @PathVariable String experimentAccession,
            @PathVariable int perplexity,
            @PathVariable String metadata,
            @RequestParam(defaultValue = "") String accessKey) {
        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        return GSON.toJson(
                ImmutableMap.of(
                        "series",
                        modelForHighcharts(
                                "",
                                new TreeMap<>(
                                        tSnePlotService.fetchTSnePlotWithMetadata(
                                                experiment.getAccession(), perplexity, metadata)))));
    }
    
    @RequestMapping(
            value = "/json/experiments/{experimentAccession}/tsneplot/{perplexity}/metadata/{metadata}/expression/{geneId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String tSnePlotExpressionsWithMetadata(
            @PathVariable String experimentAccession,
            @PathVariable int perplexity,
            @PathVariable String metadata,
            @PathVariable String geneId,
            @RequestParam(defaultValue = "") String accessKey) {
        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        Set<TSnePoint> pointsWithExpression =
                tSnePlotService.fetchTSnePlotWithExpression(experiment.getAccession(), perplexity, geneId);

        OptionalDouble max = pointsWithExpression.stream()
                .map(TSnePoint::expressionLevel)
                .filter(Optional::isPresent)
                .mapToDouble(Optional::get)
                .filter(d -> d > 0)
                .max();

        OptionalDouble min = pointsWithExpression.stream()
                .map(TSnePoint::expressionLevel)
                .filter(Optional::isPresent)
                .mapToDouble(Optional::get)
                .filter(d -> d > 0)
                .min();

        String unit = "TPM"; // Get units from experiment, or from request parameter if more than one is available

        Map<String, Object> model = new HashMap<>();
        model.put("series", modelForHighcharts(
                "",
                new TreeMap<>(
                        tSnePlotService.fetchTSnePlotWithExpressionAndMetadata(
                                experiment.getAccession(), perplexity, geneId,  metadata))));
        model.put("unit", unit);
        max.ifPresent(n -> model.put("max", n));
        min.ifPresent(n -> model.put("min", n));

        return GSON.toJson(model);
    }

    @RequestMapping(
            value =  "/json/experiments/{experimentAccession}/tsneplot/{perplexity}/metadata/{metadata}/expression",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String tSnePlotExpressionsWithMetadata(
            @PathVariable String experimentAccession,
            @PathVariable int perplexity,
            @PathVariable String metadata,
            @RequestParam(defaultValue = "") String accessKey) {
        return tSnePlotExpressionsWithMetadata(experimentAccession, perplexity, metadata, "", "accessKey");
    }

    private List<Map<String, Object>> modelForHighcharts(String seriesNamePrefix, Map<?, Set<TSnePoint>> points) {
        return points.entrySet().stream()
                .map(entry -> ImmutableMap.of(
                        "name", seriesNamePrefix + entry.getKey().toString(),
                        "data", entry.getValue()))
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> modelForHighcharts(String seriesName, Set<TSnePoint> points) {
        return ImmutableList.of(ImmutableMap.of("name", seriesName, "data", points));
    }
}
