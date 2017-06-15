package uk.ac.ebi.atlas.experimentimport.analytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        final int bufferSize = 2000;
        try (SingleCellBaselineInputStream source = singleCellInputStream) {

            SingleCellBaseline scb = source.readNext();
            while (scb != null) {
                final List<SingleCellBaseline> scbList = new ArrayList<>();

                int bufferRead = 0;
                while (scb != null && bufferRead < bufferSize) {
                    //populate list
                    scbList.add(scb);

                    if (bufferRead < bufferSize) {
                        scb = source.readNext();
                    }

                    bufferRead++;
                }

                //Add the chunk of data to db
                jdbcTemplate.batchUpdate(SCEXPRESSION_INSERT, scbList, bufferSize, (ps, singleCellBaseline) -> {
                    ps.setString(GENE_ID, singleCellBaseline.getGeneId());
                    ps.setString(EXPERIMENT_ACCESSION, experimentAccession);
                    ps.setString(CELL_ID, singleCellBaseline.getCellId());
                    ps.setDouble(EXPRESSION_LEVEL, singleCellBaseline.getExpressionLevel());
                });
            }

            source.close();

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
