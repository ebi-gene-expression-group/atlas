package uk.ac.ebi.atlas.tsne;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.stream.Collectors;

@Named
public class TSnePlotCollator {

    // TODO This class will change a lot (and very probably won’t be necessary) when all data is available in the DB
    // TODO and we can do a JOIN on the cell ID field

    private final TSnePlotDao tSnePlotDao;
    private final ClusterDao clusterDao;
    private final GeneExpressionDao geneExpressionDao;
    private final RandomTSnePlot randomTSnePlot = new RandomTSnePlot();

    @Inject
    public TSnePlotCollator(TSnePlotDao tSnePlotDao, ClusterDao clusterDao, GeneExpressionDao geneExpressionDao) {
        this.tSnePlotDao = tSnePlotDao;
        this.clusterDao = clusterDao;
        this.geneExpressionDao = geneExpressionDao;
    }

    public TreeMultimap<Integer, TSnePoint> getTSnePlotWithClusters(String experimentAccession, int k) {
        if ("E-RANDOM".equalsIgnoreCase(experimentAccession)) {
            return randomTSnePlot.getTSnePlotWithClusters(k);
        }

        Map<String, TSnePoint> points = tSnePlotDao.fetchTSnePlotPoints(experimentAccession);
        Multimap<Integer, String> clusters = clusterDao.fetchClusters(experimentAccession, k);

        TreeMultimap<Integer, TSnePoint> clusterPoints = TreeMultimap.create();
        clusters.entries().forEach(entry -> clusterPoints.put(entry.getKey(), points.get(entry.getValue())));

        return clusterPoints;
    }

    public ImmutableList<TSnePoint> getTSnePlotWithExpression(String experimentAccession, String geneId) {
        Map<String, TSnePoint> points = tSnePlotDao.fetchTSnePlotPoints(experimentAccession);
        Map<String, Double> expression = geneExpressionDao.fetchGeneExpression(experimentAccession, geneId);

        return ImmutableList.copyOf(
                points.values().stream()
                        .map(point ->
                                TSnePoint.create(point.x(), point.y(), expression.get(point.name()), point.name()))
                        .collect(Collectors.toList()));
    }

}
