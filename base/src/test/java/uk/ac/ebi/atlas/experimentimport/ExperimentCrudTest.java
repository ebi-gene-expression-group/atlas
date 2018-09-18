package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParserOutput;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.testutils.AssayGroupFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentCrudTest {
    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";

    private ExperimentCrud subject;

    @Mock
    private ExperimentDao experimentDaoMock;

    @Mock
    private ExperimentConfiguration experimentConfigurationMock;

    @Mock
    private CondensedSdrfParser condensedSdrfParserMock;

    @Mock
    private IdfParser idfParserMock;

    @Mock
    private IdfParserOutput idfParserOutputMock;

    @Mock
    private CondensedSdrfParserOutput condensedSdrfParserOutputMock;

    @Mock
    private ExperimentDTO experimentDTOMock;

    @Mock
    private ExperimentDesignFileWriterService experimentDesignFileWriterService;

    @Mock
    private ExperimentChecker experimentChecker;

    @Mock
    private ConfigurationTrader configurationTrader;

    private ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_BASELINE;

    private String accessKey = UUID.randomUUID().toString();

    @Before
    public void setUp() {
        AssayGroup assayGroup = AssayGroupFactory.create("g1", "run_1");
        ExperimentDesign experimentDesign = new ExperimentDesign();
        experimentDesign.putSampleCharacteristic("run_1", "type", "value");
        experimentDesign.putFactor("run_1", "type", "value");

        when(configurationTrader.getExperimentConfiguration(EXPERIMENT_ACCESSION))
                .thenReturn(experimentConfigurationMock);

        when(experimentConfigurationMock.getExperimentType()).thenReturn(experimentType);
        when(experimentConfigurationMock.getAssayGroups()).thenReturn(ImmutableList.of(assayGroup));

        when(experimentDaoMock.getExperimentAsAdmin(anyString())).thenReturn(experimentDTOMock);

        given(experimentDTOMock.getExperimentAccession()).willReturn(EXPERIMENT_ACCESSION);
        given(experimentDTOMock.getExperimentType()).willReturn(experimentType);
        given(experimentDTOMock.getAccessKey()).willReturn(accessKey);

        given(condensedSdrfParserMock.parse(anyString(), any(ExperimentType.class)))
                .willReturn(condensedSdrfParserOutputMock);
        given(condensedSdrfParserOutputMock.getExperimentDesign()).willReturn(experimentDesign);

        given(condensedSdrfParserOutputMock.getExperimentAccession()).willReturn(EXPERIMENT_ACCESSION);
        given(condensedSdrfParserOutputMock.getExperimentType()).willReturn(ExperimentType.RNASEQ_MRNA_BASELINE);

        given(idfParserMock.parse(anyString()))
                .willReturn(idfParserOutputMock);
        given(idfParserOutputMock.getTitle()).willReturn("");

        ExperimentCrudFactory experimentCrudFactory =
                new ExperimentCrudFactory(
                        condensedSdrfParserMock, idfParserMock, experimentDesignFileWriterService, configurationTrader);

        subject = experimentCrudFactory.create(experimentDaoMock, experimentChecker);
    }

    @Test(expected = IllegalStateException.class)
    public void failImportOnValidationWhenExperimentDesignDoesNotMatchAssayGroups() throws IOException {
        given(condensedSdrfParserOutputMock.getExperimentDesign()).willReturn(new ExperimentDesign());
        subject.importExperiment(EXPERIMENT_ACCESSION, false);
    }

    @Test(expected = IllegalStateException.class)
    public void failImportOnValidationWhenExperimentDesignDoesNotMatchAssayGroups2() throws IOException {
        when(experimentConfigurationMock.getAssayGroups())
                .thenReturn(ImmutableList.of(AssayGroupFactory.create("different assay", "different run")));
        subject.importExperiment(EXPERIMENT_ACCESSION, false);
    }

    @Test
    public void updateExperimentToPrivateShouldDelegateToDAO() {
        ExperimentDTO privateMock = mock(ExperimentDTO.class);
        when(privateMock.isPrivate()).thenReturn(true);
        given(experimentDaoMock.getExperimentAsAdmin(EXPERIMENT_ACCESSION)).willReturn(privateMock);
        subject.makeExperimentPrivate(EXPERIMENT_ACCESSION);
        verify(experimentDaoMock).setExperimentPrivacyStatus(EXPERIMENT_ACCESSION, true);
        verify(experimentDaoMock, times(0)).setExperimentPrivacyStatus(EXPERIMENT_ACCESSION, false);
    }

    @Test
    public void updateExperimentToPublicShouldDelegateToDAO() {
        ExperimentDTO publicMock = mock(ExperimentDTO.class);
        when(publicMock.isPrivate()).thenReturn(false);
        given(experimentDaoMock.getExperimentAsAdmin(EXPERIMENT_ACCESSION)).willReturn(publicMock);

        subject.makeExperimentPublic(EXPERIMENT_ACCESSION);
        verify(experimentDaoMock).setExperimentPrivacyStatus(EXPERIMENT_ACCESSION, false);
        verify(experimentDaoMock, times(0)).setExperimentPrivacyStatus(EXPERIMENT_ACCESSION, true);

    }

    @Test
    public void importExperimentParsesDesignFileOnce() throws IOException {
        subject.importExperiment(EXPERIMENT_ACCESSION, false);
        verify(condensedSdrfParserMock).parse(EXPERIMENT_ACCESSION, experimentType);
    }

    @Test(expected = UncheckedIOException.class)
    public void ioExceptionsAreWrapped() throws IOException {
        doThrow(new IOException())
                .when(experimentDesignFileWriterService)
                .writeExperimentDesignFile(
                        eq(EXPERIMENT_ACCESSION),
                        eq(ExperimentType.RNASEQ_MRNA_BASELINE),
                        any(ExperimentDesign.class));

        subject.importExperiment(EXPERIMENT_ACCESSION, false);
    }

    @Test
    public void updateExperimentDesign() throws IOException {
        subject.importExperiment(EXPERIMENT_ACCESSION, false);
        subject.updateExperimentDesign(EXPERIMENT_ACCESSION);

        // First time for import, second for update
        verify(experimentDesignFileWriterService, times(2))
                .writeExperimentDesignFile(
                        eq(EXPERIMENT_ACCESSION),
                        eq(ExperimentType.RNASEQ_MRNA_BASELINE),
                        any(ExperimentDesign.class));
    }
}
