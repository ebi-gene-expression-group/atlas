package uk.ac.ebi.atlas.markergenes;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.experimentpage.json.JsonExperimentController;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.GsonProvider;

import javax.inject.Inject;
import java.util.List;

@RestController
public class JsonMarkerGenesController extends JsonExperimentController {

    private MarkerGenesDao markerGenesDao;
    private HighchartsHeatmapAdapter highchartsHeatmapAdapter;

    @Inject
    public JsonMarkerGenesController(ExperimentTrader experimentTrader,
                                     MarkerGenesDao markerGenesDao,
                                     HighchartsHeatmapAdapter highchartsHeatmapAdapter) {
        super(experimentTrader);
        this.markerGenesDao = markerGenesDao;
        this.highchartsHeatmapAdapter = highchartsHeatmapAdapter;
    }

    @GetMapping(
            value = "/json/experiments/{experimentAccession}/marker-genes/{k}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public String getMarkerGenes(
            @PathVariable String experimentAccession,
            @PathVariable int k) {
        List<MarkerGene> result = markerGenesDao.getMarkerGenesWithAveragesPerCluster(experimentAccession, k);

        if (!result.isEmpty()) {
            return GsonProvider.GSON.toJson(highchartsHeatmapAdapter.getMarkerGeneHeatmapData(result));
        } else {
            return GsonProvider.GSON.toJson(result);
        }
    }
}
