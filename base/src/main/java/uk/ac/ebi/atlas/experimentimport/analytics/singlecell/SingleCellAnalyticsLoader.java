package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsDao;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.stream.Stream;

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
        analyticsDao.loadAnalytics(accession, analyticsStreamFactory.create(accession));
    }

    @Override
    public void deleteAnalytics(String accession) {
        analyticsDao.deleteAnalytics(accession);
    }

}
