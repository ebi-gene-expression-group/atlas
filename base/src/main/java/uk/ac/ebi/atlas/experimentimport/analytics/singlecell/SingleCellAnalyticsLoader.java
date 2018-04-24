package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters.ClustersStreamer;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters.ClustersStreamerFactory;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters.SingleCellClustersDao;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne.TSnePlotDao;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne.TSnePlotStreamer;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne.TSnePlotStreamerFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SingleCellAnalyticsLoader implements AnalyticsLoader {
    private final SingleCellAnalyticsDao analyticsDao;
    private final SingleCellAnalyticsStreamerFactory analyticsStreamerFactory;

    private final TSnePlotDao tSnePlotDao;
    private final TSnePlotStreamerFactory tSnePlotStreamFactory;


    private final SingleCellClustersDao clustersDao;
    private final ClustersStreamerFactory clustersStreamerFactory;

    @Inject
    SingleCellAnalyticsLoader(SingleCellAnalyticsDao analyticsDao,
                              SingleCellAnalyticsStreamerFactory analyticsStreamerFactory,
                              TSnePlotDao tSnePlotDao,
                              TSnePlotStreamerFactory tSnePlotStreamFactory,
                              SingleCellClustersDao clustersDao,
                              ClustersStreamerFactory clustersStreamerFactory) {
        this.analyticsDao = analyticsDao;
        this.analyticsStreamerFactory = analyticsStreamerFactory;

        this.tSnePlotDao = tSnePlotDao;
        this.tSnePlotStreamFactory = tSnePlotStreamFactory;

        this.clustersDao = clustersDao;
        this.clustersStreamerFactory = clustersStreamerFactory;
    }

    @Override
    public void loadAnalytics(String accession) {
        try (SingleCellAnalyticsStreamer analyticsStreamer = analyticsStreamerFactory.create(accession);
             TSnePlotStreamer tSnePlotStreamer = tSnePlotStreamFactory.create(accession);
             ClustersStreamer clustersStreamer = clustersStreamerFactory.create(accession)) {
            analyticsDao.loadAnalytics(accession, analyticsStreamer.get());
            tSnePlotStreamer.availablePerplexities()
                    .forEach(p -> tSnePlotDao.loadTSnePlot(accession, p, tSnePlotStreamer.stream(p)));
            clustersStreamer.get()
                    .forEach(pair -> clustersDao.loadClusters(accession, pair.getLeft(), pair.getRight().stream()));
        }
    }

    @Override
    public void deleteAnalytics(String accession) {
        analyticsDao.deleteAnalytics(accession);
        tSnePlotDao.deleteTSnePlot(accession);
    }

}
