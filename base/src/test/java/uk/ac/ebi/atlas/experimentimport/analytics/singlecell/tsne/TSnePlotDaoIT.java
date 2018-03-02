package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne.TSnePlotDao.BATCH_SIZE;
import static uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne.TSnePlotStreamerTest.randomTSnePoints;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TSnePlotDaoIT {
    @Inject
    private TSnePlotDao subject;

    // The test leaves the DB in its initial state iff it’s successful. With @Transactional we ensure this is the case
    // even if it fails before reaching the delete operation
    @Transactional
    @Test
    public void load() {
        // n in 4,569,303,024 chance of clashing for n experiments in the test database
        String experimentAccession = "E-" + randomAlphabetic(4).toUpperCase() + "-" + randomNumeric(1, 4);
        List<TSnePoint> tSnePoints =
                randomTSnePoints(
                        ThreadLocalRandom.current().nextInt(2, 5) * BATCH_SIZE +
                        ThreadLocalRandom.current().nextInt(1, BATCH_SIZE));
        int perplexity = ThreadLocalRandom.current().nextInt(1, 100);

        assertThat(subject.fetchTSnePlot(experimentAccession, perplexity, "")).isEmpty();

        subject.loadTSnePlot(experimentAccession, perplexity, tSnePoints.stream());
        assertThat(subject.fetchTSnePlot(experimentAccession, perplexity, ""))
                .containsExactlyInAnyOrder(
                        tSnePoints.stream()
                                .map(
                                        tSnePoint ->
                                                TSnePoint.create(tSnePoint.x(), tSnePoint.y(), 0.0, tSnePoint.name()))
                                .toArray(TSnePoint[]::new));

        subject.deleteTSnePlot(experimentAccession);
        assertThat(subject.fetchTSnePlot(experimentAccession, perplexity, "")).isEmpty();
    }

}