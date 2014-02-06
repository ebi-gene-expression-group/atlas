package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsDao;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.transcript.RnaSeqBaselineTranscriptDao;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsDao;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsDao;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsDao {

    private final BaselineAnalyticsDao baselineAnalyticsDao;
    private final MicroarrayDifferentialAnalyticsDao microarrayDifferentialAnalyticsDao;
    private final RnaSeqDifferentialAnalyticsDao rnaSeqDifferentialAnalyticsDao;
    private final RnaSeqBaselineTranscriptDao rnaSeqBaselineTranscriptDao;

    @Inject
    public AnalyticsDao(BaselineAnalyticsDao baselineAnalyticsDao, MicroarrayDifferentialAnalyticsDao microarrayDifferentialAnalyticsDao,
                        RnaSeqDifferentialAnalyticsDao rnaSeqDifferentialAnalyticsDao, RnaSeqBaselineTranscriptDao rnaSeqBaselineTranscriptDao) {
        this.baselineAnalyticsDao = baselineAnalyticsDao;
        this.microarrayDifferentialAnalyticsDao = microarrayDifferentialAnalyticsDao;
        this.rnaSeqDifferentialAnalyticsDao = rnaSeqDifferentialAnalyticsDao;
        this.rnaSeqBaselineTranscriptDao = rnaSeqBaselineTranscriptDao;
    }

    public void deleteInactiveAnalytics() {
        baselineAnalyticsDao.deleteInactiveAnalytics();
        microarrayDifferentialAnalyticsDao.deleteInactiveAnalytics();
        rnaSeqDifferentialAnalyticsDao.deleteInactiveAnalytics();
        rnaSeqBaselineTranscriptDao.deleteAllInactiveTranscripts();
    }

}
