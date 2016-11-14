
package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;

import java.io.File;
import java.util.Properties;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
/*
TODO think how this test should work. Maybe it should go away?
 */
@RunWith(MockitoJUnitRunner.class)
public class ExperimentCheckerTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String CONFIGURATION_PROPERTY_KEY = "PROPERTY_KEY";
    private static final String TEMP_FILENAME = "UNIT_TEST";
    private static final String NON_EXISTING_PATH = "NON-EXISTING-PATH";

    @Mock
    private ExperimentDTO experimentDTOMock;

    @Mock
    private Properties configurationPropertiesMock;

    @Mock
    private ConfigurationTrader configurationTraderMock;

    @Mock
    private MicroarrayExperimentConfiguration microarrayExperimentConfigurationMock;

    DataFileHub dataFileHub = MockDataFileHub.get();

    private ExperimentChecker subject;

    @Before
    public void setUp() throws Exception {

        when(configurationTraderMock.getMicroarrayExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(microarrayExperimentConfigurationMock);
        when(microarrayExperimentConfigurationMock.getArrayDesignAccessions()).thenReturn(Sets.newTreeSet(Sets.newHashSet("ARRAYDESIGN")));

        subject = new ExperimentChecker(configurationPropertiesMock, dataFileHub,configurationTraderMock);
    }

    @Test
    public void testCheckRequiredFileCanRead() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty(CONFIGURATION_PROPERTY_KEY)).thenReturn(pathTemplate);
        subject.checkFileExistsAndIsReadable(CONFIGURATION_PROPERTY_KEY, EXPERIMENT_ACCESSION);
        tempFile.delete();
    }

    @Test(expected = IllegalStateException.class)
    public void testCheckRequiredFileCanReadException() throws Exception {
        when(configurationPropertiesMock.getProperty(CONFIGURATION_PROPERTY_KEY)).thenReturn(NON_EXISTING_PATH);
        subject.checkFileExistsAndIsReadable(CONFIGURATION_PROPERTY_KEY, EXPERIMENT_ACCESSION);
    }

    @Test
    public void testCheckBaseline() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        //when(configurationPropertiesMock.getProperty("experiment.magetab.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.factors.path.template")).thenReturn(pathTemplate);
        subject.checkBaselineFiles(EXPERIMENT_ACCESSION);
        verify(configurationPropertiesMock, times(2)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckDifferential() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("diff.experiment.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("diff.experiment.raw-counts.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.configuration.path.template")).thenReturn(pathTemplate);
        subject.checkDifferentialFiles(EXPERIMENT_ACCESSION);
        verify(configurationPropertiesMock, times(2)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckMicroarray() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("microarray.experiment.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.normalized.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.configuration.path.template")).thenReturn(pathTemplate);
        subject.checkMicroarrayFiles(EXPERIMENT_ACCESSION);
        verify(configurationPropertiesMock, times(2)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckTwoColour() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("microarray.experiment.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.log-fold-changes.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.configuration.path.template")).thenReturn(pathTemplate);
        subject.checkTwoColourFiles(EXPERIMENT_ACCESSION);
        verify(configurationPropertiesMock, times(2)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckAllFilesPresentBaseline() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("experiment.analysis-method.path.template")).thenReturn(pathTemplate);
       // when(configurationPropertiesMock.getProperty("experiment.magetab.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.factors.path.template")).thenReturn(pathTemplate);
        subject.checkAllFiles(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_BASELINE);
        verify(configurationPropertiesMock, times(3)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckAllFilesPresentDifferential() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("experiment.analysis-method.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("diff.experiment.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("diff.experiment.raw-counts.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.configuration.path.template")).thenReturn(pathTemplate);
        subject.checkAllFiles(EXPERIMENT_ACCESSION, ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);
        verify(configurationPropertiesMock, times(3)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckAllFilesPresentMicroarray() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("experiment.analysis-method.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.experiment.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.normalized.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.configuration.path.template")).thenReturn(pathTemplate);
        subject.checkAllFiles(EXPERIMENT_ACCESSION, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL);
        verify(configurationPropertiesMock, times(3)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckAllFilesPresentTwoColour() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("experiment.analysis-method.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.experiment.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.log-fold-changes.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.configuration.path.template")).thenReturn(pathTemplate);
        subject.checkAllFiles(EXPERIMENT_ACCESSION, ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL);
        verify(configurationPropertiesMock, times(3)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckAllFilesPresentMicroRNA() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("experiment.analysis-method.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.experiment.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.normalized.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.configuration.path.template")).thenReturn(pathTemplate);
        subject.checkAllFiles(EXPERIMENT_ACCESSION, ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL);
        verify(configurationPropertiesMock, times(3)).getProperty(anyString());
        tempFile.delete();
    }
}