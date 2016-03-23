package uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq;

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
public class RnaSeqDifferentialAnalyticsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RnaSeqDifferentialAnalyticsDao.class);

    private static final String ANALYTICS_INSERT = "INSERT INTO RNASEQ_DIFF_ANALYTICS " +
            "(identifier, experiment, contrastid, isactive, pval, log2fold) VALUES (?, ?, ?, ?, ?, ?)";
    private static final int IDENTIFIER = 1;
    private static final int EXPERIMENT = 2;
    private static final int CONTRAST_ID = 3;
    private static final int IS_ACTIVE = 4;
    private static final int PVAL = 5;
    private static final int LOG2FOLD = 6;

    //The database can't handle a small value than 1E-125, so we need to treat it as 0D.
    private static final double SMALL_PVALUE_ALLOWED = 1E-125;

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public RnaSeqDifferentialAnalyticsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loadAnalytics(final String experimentAccession, RnaSeqDifferentialAnalyticsInputStream analyticsInputStream)  {
        LOGGER.info("loadAnalytics for experiment {} begin", experimentAccession);

        // will autoclose if DataAccessException thrown by jdbcTemplate
        try (RnaSeqDifferentialAnalyticsInputStream source = analyticsInputStream) {

            jdbcTemplate.batchUpdate(ANALYTICS_INSERT, new AbstractInterruptibleBatchPreparedStatementSetter() {

                @Override
                protected boolean setValuesIfAvailable(PreparedStatement ps, int i) throws SQLException {
                    RnaSeqDifferentialAnalytics analytics = source.readNext();

                    if (analytics == null) {
                        return false;
                    }

                    ps.setString(IDENTIFIER, analytics.getGeneId());
                    ps.setString(EXPERIMENT, experimentAccession);
                    ps.setString(CONTRAST_ID, analytics.getContrastId());
                    ps.setString(IS_ACTIVE, "T");

                    Double pValue = (analytics.getpValue() <  SMALL_PVALUE_ALLOWED) ? 0D : analytics.getpValue();

                    ps.setDouble(PVAL, pValue);
                    ps.setDouble(LOG2FOLD, analytics.getFoldChange());

                    return true;
                }
            });
        } catch (IOException e) {
            LOGGER.warn("Cannot close RnaSeqDifferentialAnalyticsInputStream: {}", e.getMessage());
        }

        LOGGER.info("loadAnalytics for experiment {} complete", experimentAccession);
    }

    public void deleteAnalytics(String experimentAccession) {
        LOGGER.info("deleteAnalytics for experiment {}");
        jdbcTemplate.update("DELETE FROM RNASEQ_DIFF_ANALYTICS WHERE experiment = ?",
                experimentAccession);
    }
}
