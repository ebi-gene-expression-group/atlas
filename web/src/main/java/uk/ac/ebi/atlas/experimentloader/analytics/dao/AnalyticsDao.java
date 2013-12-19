package uk.ac.ebi.atlas.experimentloader.analytics.dao;

import uk.ac.ebi.atlas.experimentloader.analytics.dao.BaselineExpressionDao;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsDao {

    private final BaselineExpressionDao baselineExpressionDao;

    @Inject
    public AnalyticsDao(BaselineExpressionDao baselineExpressionDao) {
        this.baselineExpressionDao = baselineExpressionDao;
    }

    public void deleteInactiveExpressions() {
        baselineExpressionDao.deleteInactiveExpressions();
    }
}
