package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
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
    private final SingleCellAnalyticsDao analyticsDao;
    private final TSnePlotDao tSnePlotDao;
    private final ClustersDao clustersDao;

    @Inject
    SingleCellAnalyticsLoader(DataFileHub dataFileHub,
                              SingleCellAnalyticsDao analyticsDao,
                              TSnePlotDao tSnePlotDao,
                              ClustersDao clustersDao) {
        this.dataFileHub = dataFileHub;
        this.analyticsDao = analyticsDao;
        this.tSnePlotDao = tSnePlotDao;
        this.clustersDao = clustersDao;
    }

    @Transactional
    @Override
    public void loadAnalytics(String experimentAccession) {
        DataFileHub.SingleCellExperimentFiles files = dataFileHub.getSingleCellExperimentFiles(experimentAccession);

        try (SingleCellAnalyticsStreamer analyticsStreamer =
                     new SingleCellAnalyticsStreamer(files.tpmsMatrix, files.geneIdsTsv, files.cellIdsTsv);
             TSnePlotStreamer tSnePlotStreamer = new TSnePlotStreamer(files.tSnePlotTsvs);
             ClustersStreamer clustersStreamer = new ClustersStreamer(files.clustersTsv)) {

            analyticsDao.loadAnalytics(experimentAccession, analyticsStreamer.get());
            tSnePlotStreamer.availablePerplexities()
                    .forEach(p -> tSnePlotDao.loadTSnePlot(experimentAccession, p, tSnePlotStreamer.stream(p)));
            clustersStreamer.stream()
                    .forEach(pair ->
                            clustersDao.loadClusters(experimentAccession, pair.getLeft(), pair.getRight().stream()));

        }
    }

    @Override
    public void deleteAnalytics(String accession) {
        analyticsDao.deleteAnalytics(accession);
        tSnePlotDao.deleteTSnePlot(accession);
    }

}
