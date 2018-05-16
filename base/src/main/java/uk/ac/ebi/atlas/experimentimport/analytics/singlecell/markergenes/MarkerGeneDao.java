package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.markergenes;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Named
public class MarkerGeneDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkerGeneDao.class);

    // Based on experimentation, see https://www.ebi.ac.uk/seqdb/confluence/display/GXA/Single+Cell+Expression+data
    private static final int BATCH_SIZE = 2000;
    private static final String MARKER_GENE_INSERT_STATEMENT =
            "INSERT INTO scxa_marker_genes " +
            "(gene_id, experiment_accession, k, cluster_id, marker_probability) VALUES (?, ?, ?, ?, ?)";
    private static final String MARKER_GENE_SELECT_STATEMENT =
            "SELECT * FROM scxa_marker_genes WHERE gene_id=? AND marker_probability>?";


    private final JdbcTemplate jdbcTemplate;

    @Inject
    public MarkerGeneDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loadMarkerGenes(ObjectInputStream<Object[]> inputStream) {
        int rowCount = 0;
        final List<Object[]> batch = new ArrayList<>(BATCH_SIZE);
        Object[] line;

        while ((line = inputStream.readNext()) != null) {
            batch.clear();
            while (batch.size() < BATCH_SIZE && line != null) {
                batch.add(line);
                line = inputStream.readNext();
            }

            rowCount += jdbcTemplate.batchUpdate(MARKER_GENE_INSERT_STATEMENT, batch).length;
        }

        LOGGER.info("{} rows inserted", rowCount);
    }

    public void deleteAll() {
        int rowCount = jdbcTemplate.update("DELETE FROM scxa_marker_genes");
        LOGGER.info("{} rows deleted", rowCount);
    }

    public void delete(String experimentAccession) {
        int rowCount = jdbcTemplate.update("DELETE FROM scxa_marker_genes WHERE experiment_accession=?", experimentAccession);
        LOGGER.info("{} rows deleted", rowCount);
    }

}
