package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
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

    @Inject
    SingleCellAnalyticsLoader(SingleCellAnalyticsDao analyticsDao,
                              SingleCellAnalyticsStreamerFactory analyticsStreamerFactory,
                              TSnePlotDao tSnePlotDao,
                              TSnePlotStreamerFactory tSnePlotStreamFactory) {
        this.analyticsDao = analyticsDao;
        this.analyticsStreamerFactory = analyticsStreamerFactory;

        this.tSnePlotDao = tSnePlotDao;
        this.tSnePlotStreamFactory = tSnePlotStreamFactory;
    }

    @Override
    public void loadAnalytics(String accession) {
        try (SingleCellAnalyticsStreamer analyticsStreamer = analyticsStreamerFactory.create(accession);
             TSnePlotStreamer tSnePlotStreamer = tSnePlotStreamFactory.create(accession)) {
            analyticsDao.loadAnalytics(accession, analyticsStreamer.get());
            tSnePlotStreamer.availablePerplexities()
                    .forEach(p -> tSnePlotDao.loadTSnePlot(accession, p, tSnePlotStreamer.stream(p)));
        }
    }

    @Override
    public void deleteAnalytics(String accession) {
        analyticsDao.deleteAnalytics(accession);
        tSnePlotDao.deleteTSnePlot(accession);
    }

}
