package uk.ac.ebi.atlas.home;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomExperimentAccession;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LatestExperimentsServiceTest {

  @Mock
  private LatestExperimentsDao latestExperimentsDaoMock;

  @Mock
  private ExperimentTrader experimentTraderMock;

  private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();
  private LatestExperimentsService subject;

  @BeforeEach
  void setUp() {
    subject = new LatestExperimentsService(latestExperimentsDaoMock, experimentTraderMock);
  }

  @Test
  void expectedAttributesbyLatestExperimentsAccession() {
    List<String> latestExperimentsAccessionList = new ArrayList<>();
    latestExperimentsAccessionList.add(generateRandomExperimentAccession());
    long experimentCount = RNG.nextInt(1, 1000);

    when(latestExperimentsDaoMock.fetchPublicExperimentsCount())
            .thenReturn(experimentCount);
    when(latestExperimentsDaoMock.fetchLatestExperimentAccessions())
            .thenReturn(latestExperimentsAccessionList);

    Experiment experiment = mock(Experiment.class);
    when(experiment.getAccession()).thenReturn(latestExperimentsAccessionList.get(0));
    when(experimentTraderMock.getPublicExperiment(latestExperimentsAccessionList.get(0))).thenReturn(experiment);

    assertThat(subject.fetchLatestExperimentsAttributes())
            .isNotEmpty()
            .containsKeys("experimentCount", "formattedExperimentCount", "latestExperiments")
            .containsValues(experimentCount);
  }
}
