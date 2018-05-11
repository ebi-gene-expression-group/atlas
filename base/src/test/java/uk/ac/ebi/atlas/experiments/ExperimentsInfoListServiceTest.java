package uk.ac.ebi.atlas.experiments;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.testutils.MockExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentsInfoListServiceTest {

    private static final String EXPERIMENT_ACCESSION = "E-EXPS-001";

    @Mock
    private ExperimentTrader experimentTrader;

    private ExperimentInfoListService subject;

    @Before
    public void setUp() throws Exception {
        when(experimentTrader.getPublicExperiments(ExperimentType.RNASEQ_MRNA_BASELINE))
                .thenReturn(ImmutableSet.of(MockExperiment.createBaselineExperiment(EXPERIMENT_ACCESSION)));

        subject =
                new ExperimentInfoListService(experimentTrader, ImmutableSet.of(ExperimentType.RNASEQ_MRNA_BASELINE));
    }

    @Test
    public void sizeIsRight() {
        JsonArray result = subject.getExperimentsJson().get("aaData").getAsJsonArray();
        assertThat(result).hasSize(1);
    }
    
    @Test
    public void formatIsInSyncWithWhatWeExpectAndTheDataOfMockBaselineExperiment() {
        JsonObject result = subject.getExperimentsJson().get("aaData").getAsJsonArray().get(0).getAsJsonObject();

        assertThat(result.has("experimentType")).isTrue();
        assertThat(result.get("experimentType").getAsString()).isEqualToIgnoringCase(ExperimentType.RNASEQ_MRNA_BASELINE.getDescription());

        assertThat(result.has("experimentAccession")).isTrue();
        assertThat(result.get("experimentAccession").getAsString()).isEqualToIgnoringCase(EXPERIMENT_ACCESSION);

        assertThat(result.has("experimentDescription")).isTrue();
        assertThat(result.get("experimentDescription").getAsString()).isNotEmpty();

        assertThat(result.has("lastUpdate")).isTrue();
        assertThat(result.get("lastUpdate").getAsString()).isNotEmpty();

        assertThat(result.has("numberOfAssays")).isTrue();
        assertThat(result.get("numberOfAssays").getAsInt()).isGreaterThan(0);

        assertThat(result.has("numberOfContrasts")).isTrue();

        assertThat(result.has("species")).isTrue();
        assertThat(result.get("species").getAsString()).isNotEmpty();

        assertThat(result.has("kingdom")).isTrue();
        assertThat(result.get("kingdom").getAsString()).isNotEmpty();

        assertThat(result.has("experimentalFactors")).isTrue();
        assertThat(result.has("arrayDesigns")).isTrue();
        assertThat(result.has("arrayDesignNames")).isTrue();
    }
}