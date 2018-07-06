package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.testutils.RandomDataTestUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class ClustersDaoIT {
    private static final int NUMBER_OF_CELLS = 1000;
    private static final int FROM_K = 5;
    private static final int TO_K = 10;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private ClustersDao subject;

    // The test code leaves the DB in its initial state iff it’s successful. With @Transactional we ensure this is the
    // case even if it fails before reaching the delete operation
    @Transactional
    @Test
    @DisplayName("Load and delete a randomized clustering data for a random experiment accession")
    void testLoadDelete() {
        String experimentAccession = RandomDataTestUtils.getRandomExperimentAccession();
        Set<String> cellIds = RandomDataTestUtils.randomSingleCellRnaSeqRunIds(NUMBER_OF_CELLS);

        // Notice that creating a Map of streams instead of lists will break the test, because the streams will be
        // exhausted when they are loaded to the DB in the forEach line
        Map<Integer, List<Pair<String, Integer>>> clusteringData = IntStream.rangeClosed(FROM_K, TO_K)
                .boxed()
                .collect(toMap(
                        k -> k,
                        k -> cellIds.stream()
                                .map(cellId -> Pair.of(cellId, ThreadLocalRandom.current().nextInt(1, k + 1)))
                                .collect(toList())));

        clusteringData.forEach((key, value) -> subject.loadClusters(experimentAccession, key, value.stream()));

        assertThat(fetchClusters(experimentAccession))
                .extracting("middle")
                .containsOnlyElementsOf(clusteringData.keySet());

        assertThat(fetchClusters(experimentAccession))
                .extracting(triple -> Pair.of(triple.getLeft(), triple.getRight()))
                .containsAll(clusteringData.values().stream().flatMap(List::stream).collect(toList()))
                .hasSize(NUMBER_OF_CELLS * (TO_K - FROM_K + 1));

        subject.deleteClusters(experimentAccession);
        assertThat(fetchClusters(experimentAccession))
                .isEmpty();
    }

    private static final String SELECT_STATEMENT = "SELECT * FROM scxa_cell_clusters WHERE experiment_accession=?";
    private List<Triple<String, Integer, Integer>> fetchClusters(String experimentAccession) {
        return jdbcTemplate.query(
                SELECT_STATEMENT,
                (rs, rowNum) -> Triple.of(rs.getString("cell_id"), rs.getInt("k"), rs.getInt("cluster_id")),
                experimentAccession);
    }
}