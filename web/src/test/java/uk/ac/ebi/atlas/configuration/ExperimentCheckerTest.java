/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.configuration;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;

import java.io.File;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentCheckerTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String BASELINE_TYPE = "BASELINE";
    private static final String NON_EXISTING_TYPE = "NON-EXISTING-TYPE";
    private static final String CONFIGURATION_PROPERTY_KEY = "PROPERTY_KEY";
    private static final String TEMP_FILENAME = "UNIT_TEST";
    private static final String NON_EXISTING_PATH = "NON-EXISTING-PATH";

    @Mock
    private ConfigurationDao configurationDaoMock;

    @Mock
    private ExperimentConfiguration experimentConfigurationMock;

    @Mock
    private Properties configurationPropertiesMock;

    @Mock
    private ConfigurationTrader configurationTraderMock;

    @Mock
    private MicroarrayExperimentConfiguration microarrayExperimentConfigurationMock;

    private ExperimentChecker subject;

    @Before
    public void setUp() throws Exception {

        when(configurationDaoMock.getExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(null);
        when(configurationTraderMock.getMicroarrayExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(microarrayExperimentConfigurationMock);
        when(microarrayExperimentConfigurationMock.getArrayDesignNames()).thenReturn(Sets.newTreeSet(Sets.newHashSet("ARRAYDESIGN")));

        subject = new ExperimentChecker(configurationPropertiesMock, configurationDaoMock, configurationTraderMock);
    }

    @Test
    public void testCheckAccessionAndType() throws Exception {
        assertThat(subject.checkAccessionAndType(EXPERIMENT_ACCESSION, BASELINE_TYPE), is(ExperimentType.BASELINE));
    }

    @Test(expected = IllegalStateException.class)
    public void testCheckAccessionAndTypeEmptyAccession() throws Exception {
        subject.checkAccessionAndType("", BASELINE_TYPE);
    }

    @Test(expected = IllegalStateException.class)
    public void testCheckAccessionAndTypeUnknownType() throws Exception {
        subject.checkAccessionAndType(EXPERIMENT_ACCESSION, NON_EXISTING_TYPE);
    }

    @Test(expected = IllegalStateException.class)
    public void testCheckAccessionAndTypeExistingAccession() throws Exception {
        when(configurationDaoMock.getExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(experimentConfigurationMock);
        subject.checkAccessionAndType(EXPERIMENT_ACCESSION, NON_EXISTING_TYPE);
    }

    @Test
    public void testCheckRequiredFileCanRead() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty(CONFIGURATION_PROPERTY_KEY)).thenReturn(pathTemplate);
        subject.checkRequiredFileCanRead(CONFIGURATION_PROPERTY_KEY, EXPERIMENT_ACCESSION);
        tempFile.delete();
    }

    @Test(expected = IllegalStateException.class)
    public void testCheckRequiredFileCanReadException() throws Exception {
        when(configurationPropertiesMock.getProperty(CONFIGURATION_PROPERTY_KEY)).thenReturn(NON_EXISTING_PATH);
        subject.checkRequiredFileCanRead(CONFIGURATION_PROPERTY_KEY, EXPERIMENT_ACCESSION);
    }

    @Test
    public void testCheckBaseline() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("experiment.magetab.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.transcripts.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.factors.path.template")).thenReturn(pathTemplate);
        subject.checkBaseline(EXPERIMENT_ACCESSION);
        verify(configurationPropertiesMock, times(3)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckDifferential() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("diff.experiment.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("diff.experiment.raw-counts.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("diff.experiment.configuration.path.template")).thenReturn(pathTemplate);
        subject.checkDifferential(EXPERIMENT_ACCESSION);
        verify(configurationPropertiesMock, times(3)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckMicroarray() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("microarray.experiment.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.normalized.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("diff.experiment.configuration.path.template")).thenReturn(pathTemplate);
        subject.checkMicroarray(EXPERIMENT_ACCESSION);
        verify(configurationPropertiesMock, times(3)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckTwoColour() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("microarray.experiment.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.log-fold-changes.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("diff.experiment.configuration.path.template")).thenReturn(pathTemplate);
        subject.checkTwoColour(EXPERIMENT_ACCESSION);
        verify(configurationPropertiesMock, times(3)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckAllFilesPresentBaseline() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("experiment.analysis-method.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.magetab.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.transcripts.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("experiment.factors.path.template")).thenReturn(pathTemplate);
        subject.checkAllFilesPresent(EXPERIMENT_ACCESSION, ExperimentType.BASELINE);
        verify(configurationPropertiesMock, times(4)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckAllFilesPresentDifferential() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("experiment.analysis-method.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("diff.experiment.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("diff.experiment.raw-counts.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("diff.experiment.configuration.path.template")).thenReturn(pathTemplate);
        subject.checkAllFilesPresent(EXPERIMENT_ACCESSION, ExperimentType.DIFFERENTIAL);
        verify(configurationPropertiesMock, times(4)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckAllFilesPresentMicroarray() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("experiment.analysis-method.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.experiment.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.normalized.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("diff.experiment.configuration.path.template")).thenReturn(pathTemplate);
        subject.checkAllFilesPresent(EXPERIMENT_ACCESSION, ExperimentType.MICROARRAY);
        verify(configurationPropertiesMock, times(4)).getProperty(anyString());
        tempFile.delete();
    }

    @Test
    public void testCheckAllFilesPresentTwoColour() throws Exception {
        File tempFile = File.createTempFile(TEMP_FILENAME + EXPERIMENT_ACCESSION, ".tmp");
        String pathTemplate = tempFile.getAbsolutePath().replaceAll(EXPERIMENT_ACCESSION, "{0}");
        when(configurationPropertiesMock.getProperty("experiment.analysis-method.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.experiment.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("microarray.log-fold-changes.data.path.template")).thenReturn(pathTemplate);
        when(configurationPropertiesMock.getProperty("diff.experiment.configuration.path.template")).thenReturn(pathTemplate);
        subject.checkAllFilesPresent(EXPERIMENT_ACCESSION, ExperimentType.TWOCOLOUR);
        verify(configurationPropertiesMock, times(4)).getProperty(anyString());
        tempFile.delete();
    }
}