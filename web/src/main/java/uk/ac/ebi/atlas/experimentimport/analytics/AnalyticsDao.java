package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsDao;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsDao;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsDao;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsDao {

    private final BaselineAnalyticsDao baselineAnalyticsDao;
    private final MicroarrayDifferentialAnalyticsDao microarrayDifferentialAnalyticsDao;
    private final RnaSeqDifferentialAnalyticsDao rnaSeqDifferentialAnalyticsDao;

    @Inject
    public AnalyticsDao(BaselineAnalyticsDao baselineAnalyticsDao, MicroarrayDifferentialAnalyticsDao microarrayDifferentialAnalyticsDao,
                        RnaSeqDifferentialAnalyticsDao rnaSeqDifferentialAnalyticsDao) {
        this.baselineAnalyticsDao = baselineAnalyticsDao;
        this.microarrayDifferentialAnalyticsDao = microarrayDifferentialAnalyticsDao;
        this.rnaSeqDifferentialAnalyticsDao = rnaSeqDifferentialAnalyticsDao;
    }

    public void deleteInactiveAnalytics() {
        baselineAnalyticsDao.deleteInactiveAnalytics();
        microarrayDifferentialAnalyticsDao.deleteInactiveAnalytics();
        rnaSeqDifferentialAnalyticsDao.deleteInactiveAnalytics();
    }

}
