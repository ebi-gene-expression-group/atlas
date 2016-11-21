package uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq;

import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class RnaSeqDifferentialAnalyticsLoader implements AnalyticsLoader {

    private RnaSeqDifferentialAnalyticsDao analyticsDao;
    private RnaSeqDifferentialAnalyticsInputStreamFactory analyticsInputStreamFactory;

    @Inject
    public void setAnalyticsDao(RnaSeqDifferentialAnalyticsDao analyticsDao) {
        this.analyticsDao = analyticsDao;
    }

    @Inject
    public void setAnalyticsInputStreamFactory(RnaSeqDifferentialAnalyticsInputStreamFactory analyticsInputStreamFactory) {
        this.analyticsInputStreamFactory = analyticsInputStreamFactory;
    }

    @Override
    public void loadAnalytics(String accession) throws IOException {
        RnaSeqDifferentialAnalyticsInputStream analyticsInputStream = analyticsInputStreamFactory.create(accession);
        analyticsDao.loadAnalytics(accession, analyticsInputStream);
    }

    @Override
    public void deleteAnalytics(String accession) {
        analyticsDao.deleteAnalytics(accession);
    }

}
