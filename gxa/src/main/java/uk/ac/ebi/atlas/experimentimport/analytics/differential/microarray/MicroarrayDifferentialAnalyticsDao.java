package uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractInterruptibleBatchPreparedStatementSetter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Named
public class MicroarrayDifferentialAnalyticsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicroarrayDifferentialAnalyticsDao.class);

    private static final String ANALYTICS_INSERT = "INSERT INTO MICROARRAY_DIFF_ANALYTICS " +
            "(designelement, experiment, arraydesign, contrastid, pval, log2fold, tstat) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final int DESIGNELEMENT = 1;
    private static final int EXPERIMENT = 2;
    private static final int ARRAYDESIGN = 3;
    private static final int CONTRAST_ID = 4;
    private static final int PVAL = 5;
    private static final int LOG2FOLD = 6;
    private static final int TSTAT = 7;

    //The database can't handle a small value than 1E-125, so we need to treat it as 0D.
    private static final double SMALL_PVALUE_ALLOWED = 1E-125;

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public MicroarrayDifferentialAnalyticsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loadAnalytics(final String experimentAccession, final String arrayDesign, MicroarrayDifferentialAnalyticsInputStream analyticsInputStream)  {
        LOGGER.info("loadAnalytics for experiment {} begin", experimentAccession);

        // will autoclose if DataAccessException thrown by jdbcTemplate
        try (MicroarrayDifferentialAnalyticsInputStream source = analyticsInputStream) {

            jdbcTemplate.batchUpdate(ANALYTICS_INSERT, new AbstractInterruptibleBatchPreparedStatementSetter() {

                @Override
                protected boolean setValuesIfAvailable(PreparedStatement ps, int i) throws SQLException {
                    MicroarrayDifferentialAnalytics analytics = source.readNext();

                    if (analytics == null) {
                        return false;
                    }

                    ps.setString(DESIGNELEMENT, analytics.getDesignElement());
                    ps.setString(EXPERIMENT, experimentAccession);
                    ps.setString(ARRAYDESIGN, arrayDesign);
                    ps.setString(CONTRAST_ID, analytics.getContrastId());

                    Double pValue = (analytics.getpValue() <  SMALL_PVALUE_ALLOWED) ? 0D : analytics.getpValue();

                    ps.setDouble(PVAL, pValue);
                    ps.setDouble(LOG2FOLD, analytics.getFoldChange());
                    ps.setDouble(TSTAT, analytics.getTStatistic());

                    return true;
                }
            });
        } catch (IOException e) {
            LOGGER.warn("Cannot close MicroarrayDifferentialAnalyticsInputStream: {}", e.getMessage());
        }

        LOGGER.info("loadAnalytics for experiment {} complete", experimentAccession);
    }

    public void deleteAnalytics(String experimentAccession) {
        LOGGER.info("deleteAnalytics for experiment {}", experimentAccession);
        jdbcTemplate.update("DELETE FROM MICROARRAY_DIFF_ANALYTICS WHERE experiment = ?", experimentAccession);
    }
}
