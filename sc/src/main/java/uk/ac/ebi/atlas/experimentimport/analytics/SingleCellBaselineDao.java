package uk.ac.ebi.atlas.experimentimport.analytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
public class SingleCellBaselineDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleCellBaselineDao.class);

    // Based on experimentation, see https://www.ebi.ac.uk/seqdb/confluence/display/GXA/Single+Cell+Expression+data
    private static final int BATCH_SIZE = 2000;
    private static final String SC_EXPRESSION_INSERT = "INSERT INTO sca_single_cell_expression " +
            "(EXPERIMENT_ACCESSION, GENE_ID, CELL_ID, EXPRESSION_LEVEL) VALUES (?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public SingleCellBaselineDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loadAnalytics(final String experimentAccession, SingleCellBaselineInputStream singleCellInputStream) {
        LOGGER.info("loadSingleCellExpression for experiment {} begin", experimentAccession);

        SingleCellBaseline scb;
        final List<Object[]> batch = new ArrayList<>(BATCH_SIZE);

        while ((scb = singleCellInputStream.readNext()) != null) {
            // Prepare the batch
            batch.clear();
            while (batch.size() < BATCH_SIZE && scb != null) {
                batch.add(new Object[] {experimentAccession, scb.getGeneId(), scb.getCellId(), scb.getExpressionLevel()});
                scb = singleCellInputStream.readNext();
            }

            // Insert the batch
            jdbcTemplate.batchUpdate(SC_EXPRESSION_INSERT, batch);
        }

        LOGGER.info("loadSingleCellExpression for experiment {} complete", experimentAccession);
    }

    public void deleteAnalytics(String experimentAccession) {
        LOGGER.info("delete SingleCellExpression for experiment {}", experimentAccession);
        jdbcTemplate.update("DELETE FROM sca_single_cell_expression WHERE EXPERIMENT_ACCESSION = ?", experimentAccession);
    }

}
