package uk.ac.ebi.atlas.experimentloader.analytics;

import uk.ac.ebi.atlas.experimentloader.analytics.baseline.BaselineAnalyticsDao;
import uk.ac.ebi.atlas.experimentloader.analytics.differential.microarray.MicroarrayDifferentialAnalyticsDao;
import uk.ac.ebi.atlas.experimentloader.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsDao;
import uk.ac.ebi.atlas.transcript.TranscriptProfileDao;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsDao {

    private final BaselineAnalyticsDao baselineAnalyticsDao;
    private final MicroarrayDifferentialAnalyticsDao microarrayDifferentialAnalyticsDao;
    private final RnaSeqDifferentialAnalyticsDao rnaSeqDifferentialAnalyticsDao;
    private final TranscriptProfileDao transcriptProfileDao;

    @Inject
    public AnalyticsDao(BaselineAnalyticsDao baselineAnalyticsDao, MicroarrayDifferentialAnalyticsDao microarrayDifferentialAnalyticsDao,
                        RnaSeqDifferentialAnalyticsDao rnaSeqDifferentialAnalyticsDao, TranscriptProfileDao transcriptProfileDao) {
        this.baselineAnalyticsDao = baselineAnalyticsDao;
        this.microarrayDifferentialAnalyticsDao = microarrayDifferentialAnalyticsDao;
        this.rnaSeqDifferentialAnalyticsDao = rnaSeqDifferentialAnalyticsDao;
        this.transcriptProfileDao = transcriptProfileDao;
    }

    public void deleteInactiveAnalytics() {
        baselineAnalyticsDao.deleteInactiveAnalytics();
        microarrayDifferentialAnalyticsDao.deleteInactiveAnalytics();
        rnaSeqDifferentialAnalyticsDao.deleteInactiveAnalytics();
        transcriptProfileDao.deleteInactiveTranscriptProfiles();
    }

}
