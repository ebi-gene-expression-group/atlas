package uk.ac.ebi.atlas.markergenes;

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

    public ImmutableList<MarkerGeneProfile> fetchMarkerGenes(String geneId, double cutoff) {
        // TODO   We might want to do a JOIN with experiment names to get the experiment metadata in one go and
        // TODO   return something like List<Pair<MarkerGeneProfile, ExperimentStuff>>
        List<MarkerGeneDto> markerGeneDtos =
               jdbcTemplate.queryForList(MARKER_GENE_SELECT_STATEMENT, geneId, cutoff)
                        .stream()
                        .map(rowMap ->
                                MarkerGeneDto.create(
                                        (String) rowMap.get("experiment_accession"),
                                        (int) rowMap.get("k"),
                                        (int) rowMap.get("cluster_id"),
                                        (double) rowMap.get("marker_probability")))
                        .collect(toList());


        ImmutableList.Builder<MarkerGeneProfile> builder = ImmutableList.builder();

        // Trivial to extend to multiple genes if we add geneId to MarkerGeneProfile
        markerGeneDtos.stream()
                .collect(groupingBy(MarkerGeneDao.MarkerGeneDto::experimentAccession))
                .forEach((experimentAccession, mgdsByExperimentAccession) -> mgdsByExperimentAccession.stream()
                        .collect(groupingBy(MarkerGeneDao.MarkerGeneDto::k))
                        .values()
                        .forEach(mgdsByExperimentAccessionAndK ->
                                builder.add(MarkerGeneProfile.create(mgdsByExperimentAccessionAndK))));

        return builder.build();
    }

    public void deleteAll() {
        int rowCount = jdbcTemplate.update("DELETE FROM scxa_marker_genes");
        LOGGER.info("{} rows deleted", rowCount);
    }

    public void delete(String experimentAccession) {
        int rowCount = jdbcTemplate.update("DELETE FROM scxa_marker_genes WHERE experiment_accession=?", experimentAccession);
        LOGGER.info("{} rows deleted", rowCount);
    }

    @AutoValue
    static abstract class MarkerGeneDto {
        static MarkerGeneDto create(String experimentAccession, int k, int clusterId, double p) {
            return new AutoValue_MarkerGeneDao_MarkerGeneDto(experimentAccession, k, clusterId, p);
        }

        abstract String experimentAccession();
        abstract int k();
        abstract int clusterId();
        abstract double p();
    }
}
