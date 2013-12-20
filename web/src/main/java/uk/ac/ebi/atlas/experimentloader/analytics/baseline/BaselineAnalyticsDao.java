package uk.ac.ebi.atlas.experimentloader.analytics.baseline;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractInterruptibleBatchPreparedStatementSetter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Named
public class BaselineAnalyticsDao {

    private static final Logger LOGGER = Logger.getLogger(BaselineAnalyticsDao.class);

    private static final String TRANSCRIPT_PROFILE_INSERT = "INSERT INTO RNASEQ_BSLN_EXPRESSIONS " +
            "(identifier, experiment, assaygroupid, isactive, expression) VALUES (?, ?, ?, ?, ?)";
    private static final int IDENTIFIER = 1;
    private static final int EXPERIMENT = 2;
    private static final int ASSAY_GROUP_ID = 3;
    private static final int IS_ACTIVE = 4;
    private static final int EXPRESSION = 5;

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public BaselineAnalyticsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loadBaselineAnalytics(final String experimentAccession, BaselineAnalyticsInputStream baselineExpressionDtos)  {
        LOGGER.info(String.format("loadBaselineAnalytics for experiment %s begin", experimentAccession));

        // will autoclose if DataAccessException thrown by jdbcTemplate
        try (BaselineAnalyticsInputStream source = baselineExpressionDtos) {

            jdbcTemplate.batchUpdate(TRANSCRIPT_PROFILE_INSERT, new AbstractInterruptibleBatchPreparedStatementSetter() {

                @Override
                protected boolean setValuesIfAvailable(PreparedStatement ps, int i) throws SQLException {
                    BaselineAnalytics baselineAnalytics = source.readNext();

                    if (baselineAnalytics == null) {
                        return false;
                    }

                    ps.setString(IDENTIFIER, baselineAnalytics.getGeneId());
                    ps.setString(EXPERIMENT, experimentAccession);
                    ps.setString(ASSAY_GROUP_ID, baselineAnalytics.getAssayGroupId());
                    ps.setString(IS_ACTIVE, "T");
                    ps.setDouble(EXPRESSION, baselineAnalytics.getExpressionLevel());

                    return true;
                }
            });
        } catch (IOException e) {
            LOGGER.warn(String.format("Cannot close BaselineAnalyticsInputStream: %s", e.getMessage()));
        }

        LOGGER.info(String.format("loadBaselineAnalytics for experiment %s complete", experimentAccession));
    }

    public void deleteBaselineExpressions(String experimentAccession) {
        LOGGER.info("deleteBaselineExpressions for experiment " + experimentAccession);
        jdbcTemplate.update("UPDATE RNASEQ_BSLN_EXPRESSIONS SET ISACTIVE='F' WHERE experiment = ?",
                experimentAccession);
    }

    public void deleteInactiveExpressions() {
        int count = jdbcTemplate.update("delete from RNASEQ_BSLN_EXPRESSIONS WHERE ISACTIVE = 'F'");
        LOGGER.info(String.format("deleteInactiveBaselineExpressions %s rows deleted",count));
    }

}
