package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalyticsDAO;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsDao;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsDao;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsDAO {

    private final BaselineAnalyticsDAO baselineAnalyticsDAO;
    private final MicroarrayDifferentialAnalyticsDao microarrayDifferentialAnalyticsDao;
    private final RnaSeqDifferentialAnalyticsDao rnaSeqDifferentialAnalyticsDao;

    @Inject
    public AnalyticsDAO(BaselineAnalyticsDAO baselineAnalyticsDAO,
                        MicroarrayDifferentialAnalyticsDao microarrayDifferentialAnalyticsDao,
                        RnaSeqDifferentialAnalyticsDao rnaSeqDifferentialAnalyticsDao) {
        this.baselineAnalyticsDAO = baselineAnalyticsDAO;
        this.microarrayDifferentialAnalyticsDao = microarrayDifferentialAnalyticsDao;
        this.rnaSeqDifferentialAnalyticsDao = rnaSeqDifferentialAnalyticsDao;
    }

    public void deleteInactiveAnalytics() {
        baselineAnalyticsDAO.deleteInactiveAnalytics();
        microarrayDifferentialAnalyticsDao.deleteInactiveAnalytics();
        rnaSeqDifferentialAnalyticsDao.deleteInactiveAnalytics();
    }

}
