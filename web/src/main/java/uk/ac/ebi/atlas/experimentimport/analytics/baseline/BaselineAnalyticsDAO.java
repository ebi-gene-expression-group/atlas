package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractInterruptibleBatchPreparedStatementSetter;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Named
public class BaselineAnalyticsDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineAnalyticsDAO.class);

    private static final String ANALYTICS_INSERT = "INSERT INTO RNASEQ_BSLN_EXPRESSIONS (identifier, experiment, assaygroupid, expression) VALUES (?, ?, ?, ?)";
    private static final int IDENTIFIER = 1;
    private static final int EXPERIMENT = 2;
    private static final int ASSAY_GROUP_ID = 3;
    private static final int EXPRESSION = 4;

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public BaselineAnalyticsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loadAnalytics(final String experimentAccession, ObjectInputStream<BaselineAnalytics> analyticsInputStream)  {
        LOGGER.info(String.format("loadAnalytics for experiment %s begin", experimentAccession));

        // will autoclose if DataAccessException thrown by jdbcTemplate
        try (ObjectInputStream<BaselineAnalytics> source = analyticsInputStream) {

            jdbcTemplate.batchUpdate(ANALYTICS_INSERT, new AbstractInterruptibleBatchPreparedStatementSetter() {

                @Override
                protected boolean setValuesIfAvailable(PreparedStatement ps, int i) throws SQLException {
                    BaselineAnalytics baselineAnalytics = source.readNext();

                    if (baselineAnalytics == null) {
                        return false;
                    }

                    ps.setString(IDENTIFIER, baselineAnalytics.getGeneId());
                    ps.setString(EXPERIMENT, experimentAccession);
                    ps.setString(ASSAY_GROUP_ID, baselineAnalytics.getAssayGroupId());
                    ps.setDouble(EXPRESSION, baselineAnalytics.getExpressionLevel());

                    return true;
                }
            });
        } catch (IOException e) {
            LOGGER.warn("Cannot close BaselineAnalyticsInputStream: {}", e.getMessage());
        }

        LOGGER.info("loadAnalytics for experiment {} complete", experimentAccession);
    }

    public void deleteAnalytics(String experimentAccession) {
        LOGGER.info("deleteAnalytics for experiment {}", experimentAccession);
        jdbcTemplate.update("DELETE FROM RNASEQ_BSLN_EXPRESSIONS WHERE experiment = ?", experimentAccession);
    }
}
