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

    private static final int BATCH_SIZE = 2000;

    private static final String SC_EXPRESSION_INSERT = "INSERT INTO SINGLE_CELL_EXPRESSION " +
            "(GENE_ID, EXPERIMENT_ACCESSION, CELL_ID, EXPRESSION_LEVEL) VALUES (?, ?, ?, ?)";

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

        SingleCellBaseline scb = singleCellInputStream.readNext();
        while (scb != null) {
            final List<SingleCellBaseline> scbList = new ArrayList<>();

            int bufferRead = 0;
            while (scb != null && bufferRead < BATCH_SIZE) {
                //populate list
                scbList.add(scb);

                if (bufferRead < BATCH_SIZE) {
                    scb = singleCellInputStream.readNext();
                }

                bufferRead++;
            }

            //Add the chunk of data to db
            jdbcTemplate.batchUpdate(SC_EXPRESSION_INSERT, scbList, BATCH_SIZE, (ps, singleCellBaseline) -> {
                ps.setString(GENE_ID, singleCellBaseline.getGeneId());
                ps.setString(EXPERIMENT_ACCESSION, experimentAccession);
                ps.setString(CELL_ID, singleCellBaseline.getCellId());
                ps.setDouble(EXPRESSION_LEVEL, singleCellBaseline.getExpressionLevel());
            });
        }

        LOGGER.info("loadSingleCellExpression for experiment {} complete", experimentAccession);
    }

    public void deleteAnalytics(String experimentAccession) {
        LOGGER.info("delete SingleCellExpression for experiment {}");
        jdbcTemplate.update("DELETE FROM SINGLE_CELL_EXPRESSION WHERE EXPERIMENT_ACCESSION = ?", experimentAccession);
    }

}
