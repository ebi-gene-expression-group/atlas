package uk.ac.ebi.atlas.experimentimport.analyticsindex.conditions;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.testutils.RandomDataTestUtils;

import java.util.stream.Stream;

import static org.assertj.guava.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ConditionsLookupServiceTest {
    @Mock
    private BaselineExperiment baselineExperimentMock;

    @Mock
    private DifferentialExperiment differentialExperimentMock;

    @Mock
    private ConditionsCollector conditionsCollector;

    private ConditionsLookupService subject;

    @BeforeEach
    void setUp() {
        subject = new ConditionsLookupService(conditionsCollector);
    }

    @Test
    void whenAssayGroupsHaveNoConditionsResultIsEmpty() {
        when(conditionsCollector.getConditions(any(BaselineExperiment.class)))
                .thenReturn(Stream.empty());

        assertThat(subject.conditionsPerDataColumnDescriptor(baselineExperimentMock))
                .isEmpty();
    }

    @Test
    void whenContrastsHaveNoConditionsResultIsEmpty() {
        when(conditionsCollector.getConditions(any(DifferentialExperiment.class)))
                .thenReturn(Stream.empty());

        assertThat(subject.conditionsPerDataColumnDescriptor(differentialExperimentMock))
                .isEmpty();
    }

    @Test
    void baselineConditions() {
        String experimentAccession = RandomDataTestUtils.generateRandomExperimentAccession();

        when(conditionsCollector.getConditions(any(BaselineExperiment.class)))
                .thenReturn(Stream.of(
                        new Condition(experimentAccession, "g1", ImmutableSet.of("val1", "val2")),
                        new Condition(experimentAccession, "g2", ImmutableSet.of("val2", "val3")),
                        new Condition(experimentAccession, "g3", ImmutableSet.of("val1", "val2", "val3"))));

        assertThat(subject.conditionsPerDataColumnDescriptor(baselineExperimentMock))
                .hasSameEntriesAs(
                        ImmutableMultimap.<String, String>builder()
                                .putAll("g1", "val1", "val2")
                                .putAll("g2", "val2", "val3")
                                .putAll("g3", "val1", "val2", "val3")
                                .build());
    }

    @Test
    void differentialConditions() {
        String experimentAccession = RandomDataTestUtils.generateRandomExperimentAccession();

        when(conditionsCollector.getConditions(any(DifferentialExperiment.class)))
                .thenReturn(Stream.of(
                        new DifferentialCondition(experimentAccession, "g1", "g2_g1", ImmutableSet.of("val1", "val2")),
                        new DifferentialCondition(experimentAccession, "g2", "g2_g1", ImmutableSet.of("val3")),
                        new DifferentialCondition(experimentAccession, "g1", "g3_g1", ImmutableSet.of("val1", "val2")),
                        new DifferentialCondition(experimentAccession, "g3", "g3_g1", ImmutableSet.of("val4"))));

        assertThat(subject.conditionsPerDataColumnDescriptor(differentialExperimentMock))
                .hasSameEntriesAs(
                        ImmutableMultimap.<String, String>builder()
                                .putAll("g2_g1", "val1", "val2", "val3")
                                .putAll("g3_g1", "val1", "val2", "val4")
                                .build());
    }
}
