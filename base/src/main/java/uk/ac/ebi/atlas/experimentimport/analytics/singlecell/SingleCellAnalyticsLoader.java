package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.analytics.AnalyticsDao;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.analytics.AnalyticsStreamer;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters.ClustersStreamer;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters.ClustersDao;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne.TSnePlotDao;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne.TSnePlotStreamer;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SingleCellAnalyticsLoader implements AnalyticsLoader {
    private final DataFileHub dataFileHub;
    private final AnalyticsDao analyticsDao;
    private final TSnePlotDao tSnePlotDao;
    private final ClustersDao clustersDao;

    @Inject
    SingleCellAnalyticsLoader(DataFileHub dataFileHub,
                              AnalyticsDao analyticsDao, TSnePlotDao tSnePlotDao, ClustersDao clustersDao) {
        this.dataFileHub = dataFileHub;
        this.analyticsDao = analyticsDao;
        this.tSnePlotDao = tSnePlotDao;
        this.clustersDao = clustersDao;
    }

    @Transactional
    @Override
    public void loadAnalytics(String experimentAccession) {
        loadExpression(experimentAccession);
        loadTSnePlots(experimentAccession);
        loadClusteringData(experimentAccession);
    }

    @Transactional
    @Override
    public void deleteAnalytics(String experimentAccession) {
        deleteExpression(experimentAccession);
        deleteTSnePlots(experimentAccession);
        deleteClusteringData(experimentAccession);
    }

    @Transactional
    public void reloadExpression(String experimentAccession) {
        deleteExpression(experimentAccession);
        loadExpression(experimentAccession);
    }

    @Transactional
    public void reloadTSnePlots(String experimentAccession) {
        deleteTSnePlots(experimentAccession);
        loadTSnePlots(experimentAccession);
    }

    @Transactional
    public void reloadClusteringData(String experimentAccession) {
        deleteClusteringData(experimentAccession);
        loadClusteringData(experimentAccession);
    }

    private void loadExpression(String experimentAccession) {
        DataFileHub.SingleCellExperimentFiles files = dataFileHub.getSingleCellExperimentFiles(experimentAccession);

        try (AnalyticsStreamer analyticsStreamer =
                     new AnalyticsStreamer(files.tpmsMatrix, files.geneIdsTsv, files.cellIdsTsv)) {

            analyticsDao.loadAnalytics(experimentAccession, analyticsStreamer.get());
        }
    }

    private void loadTSnePlots(String experimentAccession) {
        DataFileHub.SingleCellExperimentFiles files = dataFileHub.getSingleCellExperimentFiles(experimentAccession);

        try (TSnePlotStreamer tSnePlotStreamer = new TSnePlotStreamer(files.tSnePlotTsvs)) {
            tSnePlotStreamer.availablePerplexities()
                    .forEach(p -> tSnePlotDao.loadTSnePlot(experimentAccession, p, tSnePlotStreamer.stream(p)));
        }
    }

    private void loadClusteringData(String experimentAccession) {
        DataFileHub.SingleCellExperimentFiles files = dataFileHub.getSingleCellExperimentFiles(experimentAccession);

        try (ClustersStreamer clustersStreamer = new ClustersStreamer(files.clustersTsv)) {
            clustersStreamer.stream()
                    .forEach(pair ->
                            clustersDao.loadClusters(experimentAccession, pair.getLeft(), pair.getRight().stream()));
        }
    }

    private void deleteExpression(String experimentAccession) {
        analyticsDao.deleteAnalytics(experimentAccession);
    }

    private void deleteTSnePlots(String experimentAccession) {
        tSnePlotDao.deleteTSnePlot(experimentAccession);
    }

    private void deleteClusteringData(String experimentAccession) {
        clustersDao.deleteClusters(experimentAccession);
    }

}
