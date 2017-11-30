package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Named
public class SingleCellAnalyticsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleCellAnalyticsDao.class);

    // Based on experimentation, see https://www.ebi.ac.uk/seqdb/confluence/display/GXA/Single+Cell+Expression+data
    private static final int BATCH_SIZE = 100000;
    private static final String SC_EXPRESSION_INSERT = "INSERT INTO scxa_analytics " +
            "(EXPERIMENT_ACCESSION, GENE_ID, CELL_ID, EXPRESSION_LEVEL) VALUES (?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public SingleCellAnalyticsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loadAnalytics(final String experimentAccession, Stream<SingleCellAnalytics> singleCellAnalyticsStream) {
        LOGGER.info("loadAnalytics for experiment {} begin", experimentAccession);

        final List<Object[]> batch = new ArrayList<>(BATCH_SIZE);
        singleCellAnalyticsStream.forEach(scxa -> {
            batch.add(new Object[] {experimentAccession, scxa.geneId(), scxa.cellId(), scxa.expressionLevel()});
            if (batch.size() == BATCH_SIZE) {
                LOGGER.debug("loadAnalytics for experiment {}: inserting batch...", experimentAccession);
                jdbcTemplate.batchUpdate(SC_EXPRESSION_INSERT, batch);
                LOGGER.debug("loadAnalytics for experiment {}: batch inserted", experimentAccession);
                batch.clear();
            }
        });
        // There might me some leftovers, also, it’s safe if batch is empty
        jdbcTemplate.batchUpdate(SC_EXPRESSION_INSERT, batch);

        LOGGER.info("loadSingleCellExpression for experiment {} complete", experimentAccession);
    }

    public void deleteAnalytics(String experimentAccession) {
        LOGGER.info("delete SingleCellExpression for experiment {}", experimentAccession);
        jdbcTemplate.update("DELETE FROM scxa_analytics WHERE EXPERIMENT_ACCESSION = ?", experimentAccession);
    }

}
