package uk.ac.ebi.atlas.experimentimport.analytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractInterruptibleBatchPreparedStatementSetter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by barrera on 12/05/2017.
 *
 */
@Named
public class SingleCellBaselineDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleCellBaselineDao.class);

    private static final String SCEXPRESSION_INSERT = "INSERT INTO SINGLE_CELL_EXPRESSION " +
            "(geneId, experimentaccession, cellid, expressionlevel) VALUES (?, ?, ?, ?)";

    private static final int GENE_ID = 1;
    private static final int EXPERIMENT_ACCESSION = 2;
    private static final int CELL_ID = 3;
    private static final int EXPRESSION_LEVEL = 4;

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public SingleCellBaselineDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loadAnalytics(final String experimentAccession, SingleCellBaselineInputStream singleCellInputStream) {
        LOGGER.info("loadSingleCellExpression for experiment {} begin", experimentAccession);

        // will autoclose if DataAccessException thrown by jdbcTemplate
        try (SingleCellBaselineInputStream source = singleCellInputStream) {

            jdbcTemplate.batchUpdate(SCEXPRESSION_INSERT, new AbstractInterruptibleBatchPreparedStatementSetter() {

                @Override
                protected boolean setValuesIfAvailable(PreparedStatement ps, int i) throws SQLException {
                    SingleCellBaseline scb = source.readNext();

                    if (scb == null) {
                        return false;
                    }

                    ps.setString(GENE_ID, scb.getGeneId());
                    ps.setString(EXPERIMENT_ACCESSION, experimentAccession);
                    ps.setString(CELL_ID, scb.getCellId());
                    ps.setDouble(EXPRESSION_LEVEL, scb.getExpressionLevel());

                    return true;
                }
            });
        } catch (IOException e) {
            LOGGER.warn("Cannot close SingleCellBaselineInputStream: {}", e.getMessage());
        }

        LOGGER.info("loadSingleCellExpression for experiment {} complete", experimentAccession);

    }

    public void deleteAnalytics(String experimentAccession) {
        LOGGER.info("delete SingleCellExpression for experiment {}");
        jdbcTemplate.update("DELETE FROM single_cell_expression WHERE experimentaccession = ?", experimentAccession);
    }

}
