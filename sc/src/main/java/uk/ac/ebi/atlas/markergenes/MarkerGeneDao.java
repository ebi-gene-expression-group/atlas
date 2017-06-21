package uk.ac.ebi.atlas.markergenes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.SingleCellBaselineDao;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class MarkerGeneDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleCellBaselineDao.class);

    private static final double DEFAULT_P_THRESHOLD = 0.95;

    // Based on experimentation, see https://www.ebi.ac.uk/seqdb/confluence/display/GXA/Single+Cell+Expression+data
    private static final int BATCH_SIZE = 2000;
    private static final String MARKER_GENE_INSERT_STATEMENT =
            "INSERT INTO MARKER_GENES " +
            "(GENE_ID, EXPERIMENT_ACCESSION, PERPLEXITY, CLUSTER_ID, MARKER_PROBABILITY) VALUES (?, ?, ?, ?, ?)";
    private static final String MARKER_GENE_SELECT_STATEMENT =
            "SELECT * FROM MARKER_GENES WHERE GENE_ID='?' AND MARKER_PROBABILITY > ?";


    private final JdbcTemplate jdbcTemplate;

    @Inject
    public MarkerGeneDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loadMarkerGenes(ObjectInputStream<MarkerGene> inputStream) {
        int rowCount = 0;
        final List<Object[]> batch = new ArrayList<>(BATCH_SIZE);
        MarkerGene markerGene;

        while ((markerGene = inputStream.readNext()) != null) {
            // Prepare the batch
            batch.clear();
            while (batch.size() < BATCH_SIZE && markerGene != null) {
                batch.add(
                        new Object[] {markerGene.geneId(), markerGene.experimentAccession(), markerGene.perplexity(),
                                      markerGene.clusterId(), markerGene.p()});
                markerGene = inputStream.readNext();
            }

            // Insert the batch
            rowCount += jdbcTemplate.batchUpdate(MARKER_GENE_INSERT_STATEMENT, batch).length;
        }

        LOGGER.info("{} rows inserted", rowCount);
    }

    public List<MarkerGene> fetchMarkerGenes(String geneId) {
        return jdbcTemplate.queryForList(
                        "SELECT * FROM MARKER_GENES WHERE GENE_ID=? AND MARKER_PROBABILITY>?",
                        geneId, DEFAULT_P_THRESHOLD).stream()
                .map(rowMap ->
                        MarkerGene.create(
                                (String) rowMap.get("GENE_ID"), (String) rowMap.get("EXPERIMENT_ACCESSION"),
                                (int) rowMap.get("PERPLEXITY"), (int) rowMap.get("CLUSTER_ID"),
                                (double) rowMap.get("MARKER_PROBABILITY")))
                .collect(Collectors.toList());
    }

    public void deleteAll() {
        int rowCount = jdbcTemplate.update("DELETE FROM MARKER_GENES");
        LOGGER.info("{} rows deleted", rowCount);
    }

}
