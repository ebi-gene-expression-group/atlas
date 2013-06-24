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

package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.configuration.ConfigurationDao;
import uk.ac.ebi.atlas.configuration.ExperimentChecker;
import uk.ac.ebi.atlas.configuration.ExperimentConfiguration;
import uk.ac.ebi.atlas.configuration.ExperimentManager;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.transcript.GeneProfileDao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentLoaderControllerTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String BASELINE_TYPE = "BASELINE";
    private static final String TEST_EXCEPTION = "TEST_EXCEPTION";
    private static final String DIFFERENTIAL_TYPE = "DIFFERENTIAL";
    private static final String MICROARRAY_TYPE = "MICROARRAY";
    private static final String TWOCOLOUR_TYPE = "TWOCOLOUR";
    private static final String NON_EXISTING_TYPE = "NON-EXISTING-TYPE";

    @Mock
    private ConfigurationDao configurationDaoMock;

    @Mock
    private GeneProfileDao geneProfileDaoMock;

    @Mock
    private ExperimentChecker experimentCheckerMock;

    @Mock
    private ExperimentManager experimentManagerMock;

    private ExperimentLoaderController subject;

    @Before
    public void setUp() throws Exception {

        subject = new ExperimentLoaderController(configurationDaoMock,
                geneProfileDaoMock, experimentCheckerMock, experimentManagerMock);

        when(configurationDaoMock.getExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(null);
        when(configurationDaoMock.addExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.valueOf(BASELINE_TYPE))).thenReturn(1);
        when(configurationDaoMock.deleteExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(1);
        when(configurationDaoMock.getExperimentConfigurations()).thenReturn(
                Lists.newArrayList(new ExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.valueOf(BASELINE_TYPE))));

        when(experimentCheckerMock.checkAccessionAndType(EXPERIMENT_ACCESSION, BASELINE_TYPE)).thenReturn(ExperimentType.BASELINE);
    }

    @Test
    public void testLoadExpDesignForBaseline() throws Exception {
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, BASELINE_TYPE), is("Experiment " + EXPERIMENT_ACCESSION + " loaded."));
        verify(experimentManagerMock, times(1)).loadTranscripts(EXPERIMENT_ACCESSION);
        verify(experimentManagerMock, times(0)).loadArrayDesign(EXPERIMENT_ACCESSION);
    }

    @Test
    public void testLoadExpDesignForDifferential() throws Exception {
        when(experimentCheckerMock.checkAccessionAndType(EXPERIMENT_ACCESSION, DIFFERENTIAL_TYPE)).thenReturn(ExperimentType.DIFFERENTIAL);
        when(configurationDaoMock.addExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.valueOf(DIFFERENTIAL_TYPE))).thenReturn(1);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, DIFFERENTIAL_TYPE), is("Experiment " + EXPERIMENT_ACCESSION + " loaded."));
        verify(experimentManagerMock, times(0)).loadTranscripts(EXPERIMENT_ACCESSION);
        verify(experimentManagerMock, times(0)).loadArrayDesign(EXPERIMENT_ACCESSION);
    }

    @Test
    public void testLoadExpDesignMicroArray() throws Exception {
        when(experimentCheckerMock.checkAccessionAndType(EXPERIMENT_ACCESSION, MICROARRAY_TYPE)).thenReturn(ExperimentType.MICROARRAY);
        when(configurationDaoMock.addExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.valueOf(MICROARRAY_TYPE))).thenReturn(1);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, MICROARRAY_TYPE), is("Experiment " + EXPERIMENT_ACCESSION + " loaded."));
        verify(experimentManagerMock, times(0)).loadTranscripts(EXPERIMENT_ACCESSION);
        verify(experimentManagerMock, times(1)).loadArrayDesign(EXPERIMENT_ACCESSION);
    }

    @Test
    public void testLoadExpDesignTwoColour() throws Exception {
        when(experimentCheckerMock.checkAccessionAndType(EXPERIMENT_ACCESSION, TWOCOLOUR_TYPE)).thenReturn(ExperimentType.TWOCOLOUR);
        when(configurationDaoMock.addExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.valueOf(TWOCOLOUR_TYPE))).thenReturn(1);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, TWOCOLOUR_TYPE), is("Experiment " + EXPERIMENT_ACCESSION + " loaded."));
        verify(experimentManagerMock, times(0)).loadTranscripts(EXPERIMENT_ACCESSION);
        verify(experimentManagerMock, times(1)).loadArrayDesign(EXPERIMENT_ACCESSION);
    }

    @Test
    public void testGenerateExpDesignException() throws Exception {
        when(experimentCheckerMock.checkAccessionAndType(EXPERIMENT_ACCESSION, TWOCOLOUR_TYPE)).thenReturn(ExperimentType.TWOCOLOUR);
        Mockito.doThrow(new IllegalStateException(TEST_EXCEPTION)).when(experimentManagerMock).generateExpDesign(EXPERIMENT_ACCESSION, ExperimentType.TWOCOLOUR);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, TWOCOLOUR_TYPE), is(TEST_EXCEPTION));
    }

    @Test
    public void testTranscriptLoaderException() throws Exception {
        Mockito.doThrow(new IllegalStateException(TEST_EXCEPTION)).when(experimentManagerMock).loadTranscripts(EXPERIMENT_ACCESSION);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, BASELINE_TYPE), is(TEST_EXCEPTION));
    }

    @Test
    public void testLoadExperimentEmptyAccession() throws Exception {
        Mockito.doThrow(new IllegalStateException("Experiment accession cannot be empty.")).when(experimentCheckerMock).checkAccessionAndType("", BASELINE_TYPE);
        assertThat(subject.loadExperiment("", BASELINE_TYPE), is("Experiment accession cannot be empty."));
    }

    @Test
    public void testLoadExperimentExistingAccession() throws Exception {
        Mockito.doThrow(new IllegalStateException("Experiment with accession " + EXPERIMENT_ACCESSION + " already exists.")).when(experimentCheckerMock).checkAccessionAndType(EXPERIMENT_ACCESSION, BASELINE_TYPE);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, BASELINE_TYPE), is("Experiment with accession " + EXPERIMENT_ACCESSION + " already exists."));
    }

    @Test
    public void testLoadExperimentWrongType() throws Exception {
        Mockito.doThrow(new IllegalStateException("An unknown experiment type has been specified.")).when(experimentCheckerMock).checkAccessionAndType(EXPERIMENT_ACCESSION, NON_EXISTING_TYPE);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, NON_EXISTING_TYPE), is("An unknown experiment type has been specified."));
    }

    @Test
    public void testLoadExperimentRequiredFileMissing() throws Exception {
        Mockito.doThrow(new IllegalStateException(TEST_EXCEPTION)).when(experimentCheckerMock).checkAllFilesPresent(EXPERIMENT_ACCESSION, ExperimentType.BASELINE);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, BASELINE_TYPE), is(TEST_EXCEPTION));
    }

    @Test(expected = IllegalStateException.class)
    public void testLoadExperimentIllegalState() throws Exception {
        when(configurationDaoMock.addExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.valueOf(BASELINE_TYPE))).thenReturn(0);
        subject.loadExperiment(EXPERIMENT_ACCESSION, BASELINE_TYPE);
    }

    @Test
    public void testDeleteExperiment() throws Exception {
        assertThat(subject.deleteExperiment(EXPERIMENT_ACCESSION), is("Experiment " + EXPERIMENT_ACCESSION + " deleted."));
        verify(geneProfileDaoMock).deleteTranscriptProfilesForExperiment(EXPERIMENT_ACCESSION);
    }

    @Test
    public void testDeleteExperimentEmpty() throws Exception {
        assertThat(subject.deleteExperiment(""), is("Experiment accession cannot be empty."));
    }

    @Test
    public void testDeleteExperimentNonExisting() throws Exception {
        when(configurationDaoMock.deleteExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(0);
        assertThat(subject.deleteExperiment(EXPERIMENT_ACCESSION), is("Experiment " + EXPERIMENT_ACCESSION + " not present."));
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteExperimentIllegalState() throws Exception {
        when(configurationDaoMock.deleteExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(-1);
        subject.deleteExperiment(EXPERIMENT_ACCESSION);
    }

    @Test
    public void testListExperiments() throws Exception {
        assertThat(subject.listExperiments(), is("[{\"experimentAccession\":\"" + EXPERIMENT_ACCESSION + "\",\"experimentType\":\"" + BASELINE_TYPE + "\"}]"));
    }

}