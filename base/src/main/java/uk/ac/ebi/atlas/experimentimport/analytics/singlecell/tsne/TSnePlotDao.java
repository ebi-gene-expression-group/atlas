package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.experimentpage.baseline.tsne.TSnePoint;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Named
public class TSnePlotDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TSnePlotDao.class);

    // Based on experimentation, see https://www.ebi.ac.uk/seqdb/confluence/display/GXA/Single+Cell+Expression+data
    static final int BATCH_SIZE = 10000;

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public TSnePlotDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_T_SNE_PLOT_POINT_STATEMENT =
            "INSERT INTO scxa_tsne (experiment_accession, perplexity, cell_id, x, y) VALUES (?, ?, ?, ?, ?)";
    public void loadTSnePlot(final String experimentAccession, int perplexity, Stream<TSnePoint> tSnePointStream) {
        LOGGER.info("loadTSnePlot for experiment {} perplexity={} begin", experimentAccession, perplexity);

        final List<Object[]> batch = new ArrayList<>(BATCH_SIZE);
        tSnePointStream.forEach(tSnePoint -> {
            batch.add(new Object[] {experimentAccession, perplexity, tSnePoint.name(), tSnePoint.x(), tSnePoint.y()});
            if (batch.size() == BATCH_SIZE) {
                LOGGER.debug("loadTSnePlot for experiment {}: inserting batch...", experimentAccession);
                jdbcTemplate.batchUpdate(INSERT_T_SNE_PLOT_POINT_STATEMENT, batch);
                LOGGER.debug("loadTSnePlot for experiment {}: batch inserted", experimentAccession);
                batch.clear();
            }
        });
        // There might me some leftovers if stream size % BATCH_SIZE != 0 (it’s safe if batch is empty)
        jdbcTemplate.batchUpdate(INSERT_T_SNE_PLOT_POINT_STATEMENT, batch);

        LOGGER.info("loadTSnePlot for experiment {} complete", experimentAccession);
    }

    private static final String DELETE_T_SNE_PLOT_STATEMENT = "DELETE FROM scxa_tsne WHERE EXPERIMENT_ACCESSION = ?";
    public void deleteTSnePlot(String experimentAccession) {
        LOGGER.info("deleteTSnePlot for experiment {}", experimentAccession);
        jdbcTemplate.update(DELETE_T_SNE_PLOT_STATEMENT, experimentAccession);
    }
}
