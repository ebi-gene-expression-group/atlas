package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Named
public class TSnePlotDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TSnePlotDao.class);

    // Based on experimentation, see https://www.ebi.ac.uk/seqdb/confluence/display/GXA/Single+Cell+Expression+data
    private static final String INSERT_EXPRESSION = "INSERT INTO scxa_tsne " +
            "(experiment_accession, perplexity, cell_id, x, y) VALUES (?, ?, ?, ?, ?)";
//    private static final String SELECT_PLOT = "SELECT cell_id, x, y FROM scxa_tsne WHERE experiment_accession=? AND perplexity=?";
    private static final String SELECT_PLOT_WITH_EXPRESSION =
            "SELECT tsne.cell_id, tsne.x, tsne.y, analytics.expression_level " +
            "FROM scxa_tsne AS tsne " +
            "LEFT JOIN " +
                "(SELECT * FROM scxa_analytics WHERE experiment_accession=:experiment_accession AND gene_id=:gene_id) " +
                "AS analytics " +
            "ON analytics.cell_id=tsne.cell_id " +
            "WHERE perplexity=:perplexity AND tsne.experiment_accession=:experiment_accession";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final int BATCH_SIZE = 10000;

    @Inject
    public TSnePlotDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void loadTSnePlot(final String experimentAccession, int perplexity, Stream<TSnePoint> tSnePointStream) {
        LOGGER.info("loadTSnePlot for experiment {} perplexity={} begin", experimentAccession, perplexity);

        final List<Object[]> batch = new ArrayList<>(BATCH_SIZE);
        tSnePointStream.forEach(tSnePoint -> {
            batch.add(new Object[] {experimentAccession, perplexity, tSnePoint.name(), tSnePoint.x(), tSnePoint.y()});
            if (batch.size() == BATCH_SIZE) {
                LOGGER.debug("loadTSnePlot for experiment {}: inserting batch...", experimentAccession);
                jdbcTemplate.batchUpdate(INSERT_EXPRESSION, batch);
                LOGGER.debug("loadTSnePlot for experiment {}: batch inserted", experimentAccession);
                batch.clear();
            }
        });
        // There might me some leftovers if stream size % BATCH_SIZE != 0 (it’s safe if batch is empty)
        jdbcTemplate.batchUpdate(INSERT_EXPRESSION, batch);

        LOGGER.info("loadTSnePlot for experiment {} complete", experimentAccession);
    }

    public void deleteTSnePlot(String experimentAccession) {
        LOGGER.info("deleteTSnePlot for experiment {}", experimentAccession);
        jdbcTemplate.update("DELETE FROM scxa_tsne WHERE EXPERIMENT_ACCESSION = ?", experimentAccession);
    }

    // Consider mov annotation to a service, see comment in JsonSingleCellExperimentController
    @Transactional(readOnly = true)
    public List<TSnePoint> fetchTSnePlot(String experimentAccession, int perplexity, String geneId) {
        Map<String, Object> namedParameters =
                ImmutableMap.of(
                        "experiment_accession", experimentAccession,
                        "perplexity", perplexity,
                        "gene_id", geneId);
        return namedParameterJdbcTemplate.query(SELECT_PLOT_WITH_EXPRESSION, namedParameters, new TSnePointMapper());
    }

    private static final class TSnePointMapper implements RowMapper<TSnePoint> {
        public TSnePoint mapRow(ResultSet rs, int rowNum) throws SQLException {
            double expressionLevel = rs.getDouble("expression_level");
            return TSnePoint.create(
                    rs.getDouble("x"), rs.getDouble("y"), expressionLevel, rs.getString("cell_id"));
        }
    }
}
