package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne.SingleCellTSnePlotDao;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne.TSnePlotStreamer;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne.SingleCellTSnePlotStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SingleCellAnalyticsLoader implements AnalyticsLoader {

    private final SingleCellAnalyticsDao analyticsDao;
    private final SingleCellAnalyticsStreamFactory analyticsStreamFactory;

    private final SingleCellTSnePlotDao tSnePlotDao;
    private final SingleCellTSnePlotStreamFactory tSnePlotStreamFactory;

    @Inject
    SingleCellAnalyticsLoader(SingleCellAnalyticsDao analyticsDao,
                              SingleCellAnalyticsStreamFactory analyticsStreamFactory,
                              SingleCellTSnePlotDao tSnePlotDao,
                              SingleCellTSnePlotStreamFactory tSnePlotStreamFactory) {
        this.analyticsDao = analyticsDao;
        this.analyticsStreamFactory = analyticsStreamFactory;

        this.tSnePlotDao = tSnePlotDao;
        this.tSnePlotStreamFactory = tSnePlotStreamFactory;
    }

    @Override
    public void loadAnalytics(String accession) {
        try (SingleCellAnalyticsStream analyticsStream = analyticsStreamFactory.create(accession);
             TSnePlotStreamer tSnePlotStream = tSnePlotStreamFactory.create(accession)) {
            analyticsDao.loadAnalytics(accession, analyticsStream.stream());
            tSnePlotStream.availablePerplexities()
                    .forEach(p -> tSnePlotDao.loadTSnePlot(accession, p, tSnePlotStream.stream(p)));
        }
    }

    @Override
    public void deleteAnalytics(String accession) {
        analyticsDao.deleteAnalytics(accession);
        tSnePlotDao.deleteTSnePlot(accession);
    }

}
