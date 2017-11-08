package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParserOutput;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentCrudTest {
    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";

    private ExperimentCrud subject;

    @Mock
    private ExperimentDAO experimentDAOMock;

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
    ConfigurationTrader configurationTrader;

    private ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_BASELINE;

    private String accessKey = "accessKey";

    @Before
    public void setUp() throws Exception {
        when(configurationTrader.getExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn
                (experimentConfigurationMock);

        when(experimentConfigurationMock.getExperimentType()).thenReturn(experimentType);

        when(experimentDAOMock.getExperimentAsAdmin(anyString())).thenReturn(experimentDTOMock);

        given(experimentDTOMock.getExperimentAccession()).willReturn(EXPERIMENT_ACCESSION);
        given(experimentDTOMock.getExperimentType()).willReturn(experimentType);
        given(experimentDTOMock.getAccessKey()).willReturn(accessKey);

        given(condensedSdrfParserMock.parse(anyString(), any(ExperimentType.class))).willReturn(condensedSdrfParserOutputMock);
        given(condensedSdrfParserOutputMock.getExperimentDesign()).willReturn(experimentDesignMock);

        given(condensedSdrfParserOutputMock.getExperimentAccession()).willReturn(EXPERIMENT_ACCESSION);
        given(condensedSdrfParserOutputMock.getExperimentType()).willReturn(ExperimentType.RNASEQ_MRNA_BASELINE);
        given(condensedSdrfParserOutputMock.getPubmedIds()).willReturn(new ImmutableSet.Builder<String>().build());
        given(condensedSdrfParserOutputMock.getTitle()).willReturn("");

        subject = new ExperimentCrud(condensedSdrfParserMock,
                experimentDesignFileWriterService,
                experimentDAOMock,
                experimentChecker,
                analyticsLoaderFactory,configurationTrader );
    }

    @Test
    public void updateExperimentToPrivateShouldDelegateToDAO() throws Exception {
        ExperimentDTO privateMock = mock(ExperimentDTO.class);
        when(privateMock.isPrivate()).thenReturn(true);
        given(experimentDAOMock.getExperimentAsAdmin(EXPERIMENT_ACCESSION)).willReturn(privateMock);
        subject.makeExperimentPrivate(EXPERIMENT_ACCESSION);
        verify(experimentDAOMock).setExperimentPrivacyStatus(EXPERIMENT_ACCESSION, true);
        verify(experimentDAOMock, times(0)).setExperimentPrivacyStatus(EXPERIMENT_ACCESSION, false);
    }

    @Test
    public void updateExperimentToPublicShouldDelegateToDAO() throws Exception {
        ExperimentDTO publicMock = mock(ExperimentDTO.class);
        when(publicMock.isPrivate()).thenReturn(false);
        given(experimentDAOMock.getExperimentAsAdmin(EXPERIMENT_ACCESSION)).willReturn(publicMock);

        subject.makeExperimentPublic(EXPERIMENT_ACCESSION);
        verify(experimentDAOMock).setExperimentPrivacyStatus(EXPERIMENT_ACCESSION, false);
        verify(experimentDAOMock, times(0)).setExperimentPrivacyStatus(EXPERIMENT_ACCESSION, true);

    }

    @Test
    public void importExperimentParsesDesignFileOnce() throws Exception {
        subject.importExperiment(EXPERIMENT_ACCESSION, false);
        verify(condensedSdrfParserMock).parse(EXPERIMENT_ACCESSION, experimentType);
    }

}