package uk.ac.ebi.atlas.experimentimport.tsne;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.testutils.RandomDataTestUtils;
import uk.ac.ebi.atlas.experimentpage.tsne.TSnePoint;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.experimentimport.tsne.TSnePlotDao.BATCH_SIZE;
import static uk.ac.ebi.atlas.experimentimport.tsne.TSnePlotStreamerTest.randomTSnePoints;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TSnePlotDaoIT {
    private static final String SELECT_STATEMENT = "SELECT * FROM scxa_tsne WHERE experiment_accession=?";
    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private TSnePlotDao subject;

    // The test code leaves the DB in its initial state iff it’s successful. With @Transactional we ensure this is the
    // case even if it fails before reaching the delete operation
    @Transactional
    @Test
    @DisplayName("Load and delete a randomized t-SNE plot for a random experiment accession")
    void testLoadDelete() {
        String experimentAccession = RandomDataTestUtils.getRandomExperimentAccession();
        List<TSnePoint> tSnePoints =
                randomTSnePoints(
                        ThreadLocalRandom.current().nextInt(2, 5) * BATCH_SIZE +
                        ThreadLocalRandom.current().nextInt(1, BATCH_SIZE));
        int perplexity = ThreadLocalRandom.current().nextInt(1, 100);

        subject.loadTSnePlot(experimentAccession, perplexity, tSnePoints.stream());

        assertThat(fetchTSnePoints(experimentAccession))
                .containsExactlyInAnyOrder(tSnePoints.toArray(new TSnePoint[0]));

        subject.deleteTSnePlot(experimentAccession);
        assertThat(fetchTSnePoints(experimentAccession))
                .isEmpty();
    }

    private List<TSnePoint> fetchTSnePoints(String experimentAccession) {
        return jdbcTemplate.query(
                SELECT_STATEMENT,
                (rs, rowNum) -> TSnePoint.create(rs.getDouble("x"), rs.getDouble("y"), rs.getString("cell_id")),
                experimentAccession);
    }
}