package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.testutils.MockExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomExperimentAccession;

@ExtendWith(MockitoExtension.class)
class ExperimentInfoServiceTest {
    @Mock
    private ExperimentTrader experimentTrader;

    private ExperimentInfoService subject;

    @BeforeEach
    void setUp() {
        subject = new ExperimentInfoService(experimentTrader);
    }

    @Test
    void returnsCellIdsPerExperiment() {

        String experimentAccession = generateRandomExperimentAccession();

        Experiment experimentRandom = MockExperiment.createBaselineExperiment();

        when(experimentTrader.getPublicExperiment(experimentAccession)).thenReturn(experimentRandom);

        ImmutableMap<String, Object> result = subject.fetchSpecificExperimentsAttributes(experimentAccession);

        assertThat(result)
                .containsOnlyKeys("specificExperimentInfo");
    }
}
