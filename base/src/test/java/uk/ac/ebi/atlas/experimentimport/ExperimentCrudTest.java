package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParserOutput;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentCrudTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String EXPERIMENT_ASSAY = "EXPERIMENT_ASSAY";

    private static final String EFO_0000761 = "EFO_0000761";

    private ExperimentCrud subject;

    @Mock
    private ExperimentDAO experimentDAOMock;

    @Mock
    private ExperimentTrader experimentTraderMock;

    @Mock
    private DifferentialExperiment differentialExperimentMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Mock
    private ExperimentConfiguration experimentConfigurationMock;

    @Mock
    private CondensedSdrfParser condensedSdrfParserMock;

    @Mock
    private CondensedSdrfParserOutput condensedSdrfParserOutputMock;

    @Mock
    private ExperimentDTO experimentDTOMock;

    @Mock
    ExperimentDesignFileWriterService experimentDesignFileWriterService;

    @Mock
    ExperimentChecker experimentChecker;

    @Mock
    AnalyticsLoaderFactory analyticsLoaderFactory;

    @Mock
    AnalyticsLoader analyticsLoader;

    @Mock
    ConfigurationTrader configurationTrader;

    private ImmutableSetMultimap<String,String> allOntologyTermIdsByAssayAccession = ImmutableSetMultimap.of
            (EXPERIMENT_ASSAY,EFO_0000761);

    private ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_BASELINE;

    private String accessKey = "accessKey";

    @Before
    public void setUp() throws Exception {
        when(analyticsLoaderFactory.getLoader(experimentType)).thenReturn(analyticsLoader);

        when(configurationTrader.getExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn
                (experimentConfigurationMock);

        when(experimentConfigurationMock.getExperimentType()).thenReturn(experimentType);

        given(experimentTraderMock.getPublicExperiment(EXPERIMENT_ACCESSION)).willReturn(differentialExperimentMock);

        when(experimentDAOMock.findExperiment(anyString(), anyBoolean())).thenReturn(experimentDTOMock);

        given(experimentDTOMock.getExperimentAccession()).willReturn(EXPERIMENT_ACCESSION);
        given(experimentDTOMock.getExperimentType()).willReturn(experimentType);
        given(experimentDTOMock.getAccessKey()).willReturn(accessKey);

        given(condensedSdrfParserMock.parse(anyString(), any(ExperimentType.class))).willReturn(condensedSdrfParserOutputMock);
        given(condensedSdrfParserOutputMock.getExperimentDesign()).willReturn(experimentDesignMock);

        given(condensedSdrfParserOutputMock.getExperimentAccession()).willReturn(EXPERIMENT_ACCESSION);
        given(condensedSdrfParserOutputMock.getExperimentType()).willReturn(ExperimentType.RNASEQ_MRNA_BASELINE);
        given(condensedSdrfParserOutputMock.getPubmedIds()).willReturn(new ImmutableSet.Builder<String>().build());
        given(condensedSdrfParserOutputMock.getTitle()).willReturn("");

        when(experimentDesignMock.getAllOntologyTermIdsByAssayAccession()).thenReturn(allOntologyTermIdsByAssayAccession);

        subject = new ExperimentCrud(condensedSdrfParserMock,
                experimentDesignFileWriterService,
                experimentDAOMock,
                experimentChecker,
                analyticsLoaderFactory,configurationTrader );
    }

    @Test
    public void updateExperimentToPrivateShouldDelegateToDAO() throws Exception {
        ExperimentDTO publicMock = mock(ExperimentDTO.class);
        ExperimentDTO privateMock = mock(ExperimentDTO.class);
        when(publicMock.isPrivate()).thenReturn(false);
        when(privateMock.isPrivate()).thenReturn(true);
        given(experimentDAOMock.findExperiment(EXPERIMENT_ACCESSION, false)).willReturn(publicMock);
        given(experimentDAOMock.findExperiment(EXPERIMENT_ACCESSION, true)).willReturn(privateMock);
        subject.makeExperimentPrivate(EXPERIMENT_ACCESSION);
        verify(experimentDAOMock).updateExperiment(EXPERIMENT_ACCESSION, true);
        verify(experimentDAOMock, times(0)).updateExperiment(EXPERIMENT_ACCESSION, false);
    }

    @Test
    public void updateExperimentToPublicShouldDelegateToDAO() throws Exception {
        ExperimentDTO publicMock = mock(ExperimentDTO.class);
        ExperimentDTO privateMock = mock(ExperimentDTO.class);
        when(publicMock.isPrivate()).thenReturn(false);
        when(privateMock.isPrivate()).thenReturn(true);
        given(experimentDAOMock.findExperiment(EXPERIMENT_ACCESSION, false)).willReturn(publicMock);
        given(experimentDAOMock.findExperiment(EXPERIMENT_ACCESSION, true)).willReturn(privateMock);

        subject.makeExperimentPublic(EXPERIMENT_ACCESSION);
        verify(experimentDAOMock).updateExperiment(EXPERIMENT_ACCESSION, false);
        verify(experimentDAOMock, times(0)).updateExperiment(EXPERIMENT_ACCESSION, true);

    }

    @Test
    public void importExperimentParsesDesignFileOnce() throws Exception {
        subject.importExperiment(EXPERIMENT_ACCESSION, false);
        verify(condensedSdrfParserMock).parse(EXPERIMENT_ACCESSION, experimentType);
    }

}