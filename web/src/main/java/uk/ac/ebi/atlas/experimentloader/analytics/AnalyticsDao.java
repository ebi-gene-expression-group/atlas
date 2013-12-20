package uk.ac.ebi.atlas.experimentloader.analytics;

import uk.ac.ebi.atlas.experimentloader.analytics.baseline.BaselineAnalyticsDao;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsDao {

    private final BaselineAnalyticsDao baselineAnalyticsDao;

    @Inject
    public AnalyticsDao(BaselineAnalyticsDao baselineAnalyticsDao) {
        this.baselineAnalyticsDao = baselineAnalyticsDao;
    }

    public void deleteInactiveExpressions() {
        baselineAnalyticsDao.deleteInactiveExpressions();
    }
}
