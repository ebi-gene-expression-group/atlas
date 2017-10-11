package uk.ac.ebi.atlas.tsne;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

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

    public TreeMultimap<Integer, TSnePoint> getTsnePlotWithClustersAndExpression(String experimentAccession,
                                                                                 int k,
                                                                                 String geneId) {
        TreeMultimap<Integer, TSnePoint> clusters = this.getTSnePlotWithClusters(experimentAccession, k);
        Map<String, Double> expression = geneExpressionDao.fetchGeneExpression(experimentAccession, geneId);

        TreeMultimap<Integer, TSnePoint> clusterPointsWithExpression = TreeMultimap.create();
        clusters.entries().forEach(
                entry ->
                        clusterPointsWithExpression.put(
                                entry.getKey(),
                                TSnePoint.create(
                                        entry.getValue().x(),
                                        entry.getValue().y(),
                                        expression.get(entry.getValue().name()),
                                        entry.getValue().name()
                                )
                        )
        );

        return clusterPointsWithExpression;
    }

    private TreeMultimap<Integer, TSnePoint> getTSnePlotWithClusters(String experimentAccession, int k) {
        if ("E-RANDOM".equalsIgnoreCase(experimentAccession)) {
            return randomTSnePlot.getTSnePlotWithClusters(k);
        }

        Map<String, TSnePoint> points = tSnePlotDao.fetchTSnePlotPoints(experimentAccession);
        Multimap<Integer, String> clusters = clusterDao.fetchClusters(experimentAccession, k);

        TreeMultimap<Integer, TSnePoint> clusterPoints = TreeMultimap.create();
        clusters.entries().forEach(entry -> clusterPoints.put(entry.getKey(), points.get(entry.getValue())));

        return clusterPoints;
    }
}
