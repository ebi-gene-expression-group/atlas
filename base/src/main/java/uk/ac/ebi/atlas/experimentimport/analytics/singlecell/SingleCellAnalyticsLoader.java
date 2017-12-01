package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class SingleCellAnalyticsLoader implements AnalyticsLoader {

    private final SingleCellAnalyticsDao analyticsDao;
    private final SingleCellAnalyticsStreamFactory analyticsStreamFactory;

    @Inject
    SingleCellAnalyticsLoader(SingleCellAnalyticsDao analyticsDao,
                              SingleCellAnalyticsStreamFactory analyticsStreamFactory) {
        this.analyticsDao = analyticsDao;
        this.analyticsStreamFactory = analyticsStreamFactory;
    }

    @Override
    public void loadAnalytics(String accession) throws IOException {
        try (SingleCellAnalyticsStream analyticsStream = analyticsStreamFactory.create(accession)) {
            analyticsDao.loadAnalytics(accession, analyticsStream.stream());
        }
    }

    @Override
    public void deleteAnalytics(String accession) {
        analyticsDao.deleteAnalytics(accession);
    }

}
