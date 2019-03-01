package uk.ac.ebi.atlas.markergenes;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HighchartsHeatmapAdapter {

    private MarkerGenesDao markerGenesDao;

    @Inject
    public HighchartsHeatmapAdapter(MarkerGenesDao markerGenesDao) {
        this.markerGenesDao = markerGenesDao;
    }

    /**
     *  Given a list of marker genes, this method returns a Highcharts data series object
     *  (https://api.highcharts.com/highcharts/series.heatmap.data), where gene IDs/symbols are
     *  the rows (y values), and the cluster IDs are the columns (x values).
     *  The cells contain the median average expression of the gene in the cluster.
     *  The rows of the heatmap are ordered by the cluster number, i.e. genes for cluster 1, 2, etc.
     *  If there are no marker genes for a cluster, then no rows will be present in the data.
     */
    List<Map<String, Object>> getMarkerGeneHeatmapData(List<MarkerGene> markerGenes) {
        List<MarkerGene> sortedMarkerGenes = markerGenes.stream()
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

        return sortedMarkerGenes
                .stream()
                .map(markerGene -> {
                    Map<String, Object> heatmapCell = new HashMap<>();
                    heatmapCell.put("x", markerGene.clusterId() - 1);
                    heatmapCell.put("y", geneIdIndices.get(markerGene.geneId()));
                    heatmapCell.put("name", symbolsForGeneIds.getOrDefault(markerGene.geneId(), markerGene.geneId()));
                    heatmapCell.put("value", markerGene.medianExpression());
                    heatmapCell.put("clusterIdWhereMarker", markerGene.clusterIdWhereMarker());
                    heatmapCell.put("pValue", markerGene.pValue());
                    return heatmapCell;
                })
                .collect(Collectors.toList());
    }
}
