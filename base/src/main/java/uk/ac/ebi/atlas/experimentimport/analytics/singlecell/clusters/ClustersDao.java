package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Responsible for inserting a clusters TSV file contents into the DB
@Component
public class ClustersDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClustersDao.class);

    // Based on experimentation, see https://www.ebi.ac.uk/seqdb/confluence/display/GXA/Single+Cell+Expression+data
    private static final int BATCH_SIZE = 100000;

    private final JdbcTemplate jdbcTemplate;

    public ClustersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SCXA_CELL_CLUSTERS_INSERT_STATEMENT =
            "INSERT INTO scxa_cell_clusters (experiment_accession, k, cell_id, cluster_id) VALUES (?, ?, ?, ?)";
    public void loadClusters(final String experimentAccession, int k, Stream<Pair<String, Integer>> clustersStream) {
        LOGGER.info("loadClusters for experiment {}/{} begin", experimentAccession, k);

        final List<Object[]> batch = new ArrayList<>(BATCH_SIZE);
        clustersStream.forEach(cluster -> {
            batch.add(new Object[] {experimentAccession, k, cluster.getLeft(), cluster.getRight()});
            if (batch.size() == BATCH_SIZE) {
                LOGGER.debug("loadClusters for experiment {}/{}: inserting batch...", experimentAccession, k);
                jdbcTemplate.batchUpdate(SCXA_CELL_CLUSTERS_INSERT_STATEMENT, batch);
                LOGGER.debug("loadClusters for experiment {}/{}: batch inserted", experimentAccession, k);
                batch.clear();
            }
        });
        // There might me some leftovers; it’s safe if batch is empty
        jdbcTemplate.batchUpdate(SCXA_CELL_CLUSTERS_INSERT_STATEMENT, batch);

        LOGGER.info("loadClusters for experiment {}/{} complete", experimentAccession, k);
    }

    private static final String SCXA_CELL_CLUSTERS_DELETE_STATEMENT =
            "DELETE FROM scxa_cell_clusters WHERE EXPERIMENT_ACCESSION = ?";
    public void deleteClusters(String experimentAccession) {
        LOGGER.info("delete clusters for experiment {}", experimentAccession);
        jdbcTemplate.update(SCXA_CELL_CLUSTERS_DELETE_STATEMENT, experimentAccession);
    }
}
