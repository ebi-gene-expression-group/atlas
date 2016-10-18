package uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

public class RnaSeqDifferentialAnalyticsLoader implements AnalyticsLoader {

    private final RnaSeqDifferentialAnalyticsDao analyticsDao;
    private final RnaSeqDifferentialAnalyticsInputStreamFactory analyticsInputStreamFactory;

    public RnaSeqDifferentialAnalyticsLoader(RnaSeqDifferentialAnalyticsDao analyticsDao,
                                             RnaSeqDifferentialAnalyticsInputStreamFactory analyticsInputStreamFactory) {
        this.analyticsDao = analyticsDao;
        this.analyticsInputStreamFactory = analyticsInputStreamFactory;
    }

    @Override
    @Transactional
    public void loadAnalytics(String accession) throws IOException {
        RnaSeqDifferentialAnalyticsInputStream analyticsInputStream = analyticsInputStreamFactory.create(accession);
        analyticsDao.loadAnalytics(accession, analyticsInputStream);
    }

    @Override
    @Transactional
    public void deleteAnalytics(String accession) {
        analyticsDao.deleteAnalytics(accession);
    }

}
