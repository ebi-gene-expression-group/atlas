package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableMap;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.model.Publication;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.testutils.MockExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.EuropePmcClient;

import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomExperimentAccession;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentAttributesServiceTest {

    private static final String[] BASELINE_EXPERIMENT_ATTRIBUTES = {
            "experimentAccession", "experimentDescription", "type", "pubMedIds", "dois", "disclaimer",
            "pageKeywords", "pageDescription", "dataProviderURL", "dataProviderDescription", "alternativeViews",
            "alternativeViewDescriptions"
    };

    private static final String[] DIFFERENTIAL_EXPERIMENT_ATTRIBUTES = {"regulationValues", "contrasts"};

    private static final String[] MICROARRAY_EXPERIMENT_ATTRIBUTES = {"arrayDesignAccessions", "arrayDesignNames"};

    @Mock
    private EuropePmcClient europePmcClientMock;
    @Mock
    private IdfParser idfParser;
    @Mock
    private ExperimentTrader experimentTrader;

    @InjectMocks
    private ExperimentAttributesService subject;


    @Test
    public void returnsCellIdsPerExperiment() {

        String experimentAccession = generateRandomExperimentAccession();

        Experiment experimentRandom = MockExperiment.createBaselineExperiment();

        when(experimentTrader.getPublicExperiment(experimentAccession)).thenReturn(experimentRandom);

        ImmutableMap<String, Object> result = subject.fetchSpecificExperimentsAttributes(experimentAccession);

        assertThat(result)
                .containsOnlyKeys("specificExperimentInfo");
    }

    @Test
    public void getAttributesForBaselineExperimentWithNoPublications() {
        when(europePmcClientMock.getPublicationByIdentifier(anyString())).thenReturn(Optional.empty());
        when(idfParser.parse(any()))
                .thenReturn(new IdfParserOutput("title", "description", Lists.emptyList(), 0, Lists.emptyList()));

        BaselineExperiment experiment = MockExperiment.createBaselineExperiment("FOOBAR");
        Map<String, Object> result = subject.getAttributes(experiment);

        assertThat(result)
                .containsKeys(BASELINE_EXPERIMENT_ATTRIBUTES)
                .doesNotContainKeys(DIFFERENTIAL_EXPERIMENT_ATTRIBUTES)
                .doesNotContainKeys(MICROARRAY_EXPERIMENT_ATTRIBUTES)
                .extracting("experimentAccession", "type", "publications")
                .contains("FOOBAR", ExperimentType.RNASEQ_MRNA_BASELINE.getHumanDescription(), Lists.emptyList());
    }

    @Test
    public void getAttributesForBaselineExperimentWithPublicationsFromDois() {
        List<String> dois = Arrays.asList("100.100/doi", "999.100/another-doi");

        when(europePmcClientMock.getPublicationByIdentifier("100.100/doi"))
                .thenReturn(Optional.of(new Publication("", "100.100/doi", "Publication 1")));
        when(europePmcClientMock.getPublicationByIdentifier("999.100/another-doi"))
                .thenReturn(Optional.of(new Publication("", "999.100/another-doi", "Publication 2")));
        when(idfParser.parse(any()))
                .thenReturn(new IdfParserOutput("title", "description", Lists.emptyList(), 0, Lists.emptyList()));

        BaselineExperiment experiment = MockExperiment.createBaselineExperiment(Lists.emptyList(), dois);

        Map<String, Object> result = subject.getAttributes(experiment);

        assertThat(result).extracting("publications").isNotEmpty();
    }

    @Test
    public void getAttributesForBaselineExperimentWithPublicationsFromPubmedIds() {
        List<String> pubmedIds = Arrays.asList("1123", "1235");

        when(europePmcClientMock.getPublicationByIdentifier("1123"))
                .thenReturn(Optional.of(new Publication("1123", "100.100/doi", "Publication 1")));
        when(europePmcClientMock.getPublicationByIdentifier("1235"))
                .thenReturn(Optional.of(new Publication("1235", "999.100/another-doi", "Publication 2")));
        when(idfParser.parse(any()))
                .thenReturn(new IdfParserOutput("title", "description", Lists.emptyList(), 0, Lists.emptyList()));

        BaselineExperiment experiment = MockExperiment.createBaselineExperiment(pubmedIds, Lists.emptyList());

        Map<String, Object> result = subject.getAttributes(experiment);

        assertThat(result).extracting("publications").isNotEmpty();
    }

    @Test
    public void getAttributesForDifferentialExperiment() {
        DifferentialExperiment experiment = MockExperiment.createDifferentialExperiment();
        when(idfParser.parse(any()))
                .thenReturn(new IdfParserOutput("title", "description", Lists.emptyList(), 0, Lists.emptyList()));

        Map<String, Object> result = subject.getAttributes(experiment);

        assertThat(result)
                .containsKeys(BASELINE_EXPERIMENT_ATTRIBUTES)
                .containsKeys(DIFFERENTIAL_EXPERIMENT_ATTRIBUTES)
                .doesNotContainKeys(MICROARRAY_EXPERIMENT_ATTRIBUTES);
    }

    @Test
    public void getAttributesForMicroarrayExperiment() {
        MicroarrayExperiment experiment = MockExperiment.createMicroarrayExperiment();
        when(idfParser.parse(any()))
                .thenReturn(new IdfParserOutput("title", "description", Lists.emptyList(), 0, Lists.emptyList()));

        Map<String, Object> result = subject.getAttributes(experiment);

        assertThat(result)
                .containsKeys(BASELINE_EXPERIMENT_ATTRIBUTES)
                .containsKeys(MICROARRAY_EXPERIMENT_ATTRIBUTES)
                .doesNotContainKeys(DIFFERENTIAL_EXPERIMENT_ATTRIBUTES);
    }
}
