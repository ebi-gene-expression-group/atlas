package uk.ac.ebi.atlas.markergenes;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.experimentpage.json.JsonExperimentController;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.GsonProvider;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class JsonMarkerGenesController extends JsonExperimentController {

    private MarkerGenesDao markerGenesDao;

    @Inject
    public JsonMarkerGenesController(ExperimentTrader experimentTrader, MarkerGenesDao markerGenesDao) {
        super(experimentTrader);
        this.markerGenesDao = markerGenesDao;
    }

    @GetMapping(
            value = "/json/experiments/{experimentAccession}/marker-genes/{k}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public String getMarkerGenes(
            @PathVariable String experimentAccession,
            @PathVariable int k) {
        List<MarkerGene> result = markerGenesDao.getMarkerGenesWithAveragesPerCluster(experimentAccession, k);

        List<MarkerGene> sortedMarkerGenes = result.stream()
                .parallel()
                .sorted(Comparator.comparing(MarkerGene::clusterIdWhereMarker)
                        .thenComparing(MarkerGene::pValue)
                )
                .collect(Collectors.toList());

        List<String> uniqueGeneIds = sortedMarkerGenes
                .stream()
                .map(MarkerGene::geneId)
                .distinct()
                .collect(Collectors.toList());

        Map<String, String> symbolsForGeneIds = markerGenesDao.getSymbolsForGeneIds(uniqueGeneIds);

        Map<String, Integer> geneIdIndices = uniqueGeneIds
                .stream()
                .collect(Collectors.toMap(x -> x, uniqueGeneIds::indexOf));

        List<Map<String, Object>> highchartsHeatmapData = sortedMarkerGenes
                .stream()
                .map(markerGene -> {
                    Map<String, Object> heatmapCell = new HashMap<>();
                    heatmapCell.put("x", markerGene.clusterId());
                    heatmapCell.put("y", geneIdIndices.get(markerGene.geneId()));
                    heatmapCell.put("name", symbolsForGeneIds.get(markerGene.geneId()));
                    heatmapCell.put("value", markerGene.medianExpression());
                    heatmapCell.put("clusterIdWhereMarker", markerGene.clusterIdWhereMarker());
                    heatmapCell.put("pValue", markerGene.pValue());
                    return heatmapCell;
                })
                .collect(Collectors.toList());

        return GsonProvider.GSON.toJson(highchartsHeatmapData);
    }

}
