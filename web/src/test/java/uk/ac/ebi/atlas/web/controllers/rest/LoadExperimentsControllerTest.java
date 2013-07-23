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
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentloader.ExperimentCRUD;
import uk.ac.ebi.atlas.experimentloader.ExperimentChecker;
import uk.ac.ebi.atlas.experimentloader.ExperimentConfiguration;
import uk.ac.ebi.atlas.experimentloader.ExperimentConfigurationDao;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.transcript.GeneProfileDao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoadExperimentsControllerTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String TEST_EXCEPTION = "TEST_EXCEPTION";

    @Mock
    private ExperimentConfigurationDao experimentConfigurationDaoMock;

    @Mock
    private GeneProfileDao geneProfileDaoMock;

    @Mock
    private ExperimentChecker experimentCheckerMock;

    @Mock
    private ExperimentCRUD experimentCRUDMock;

    private LoadExperimentsController subject;

    @Before
    public void setUp() throws Exception {

        subject = new LoadExperimentsController(experimentCheckerMock, experimentCRUDMock);

        given(experimentConfigurationDaoMock.getExperimentConfiguration(EXPERIMENT_ACCESSION)).willReturn(null);
        given(experimentConfigurationDaoMock.addExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.BASELINE)).willReturn(true);
        given(experimentConfigurationDaoMock.deleteExperimentConfiguration(EXPERIMENT_ACCESSION)).willReturn(true);
        given(experimentConfigurationDaoMock.findAllExperimentConfigurations()).willReturn(
                Lists.newArrayList(new ExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.BASELINE)));

    }

    @Test
    public void loadExperimentShouldSucceed() throws Exception {
        String responseText = subject.loadExperiment(EXPERIMENT_ACCESSION, ExperimentType.BASELINE);
        assertThat(responseText, is("Experiment " + EXPERIMENT_ACCESSION + " loaded."));
        verify(experimentCheckerMock).checkAllFilesPresent(EXPERIMENT_ACCESSION, ExperimentType.BASELINE);
        verify(experimentCRUDMock).importExperiment(EXPERIMENT_ACCESSION, ExperimentType.BASELINE);
    }

    @Test
    public void loadExperimentShouldFail() throws Exception {
        doThrow(new IllegalStateException(TEST_EXCEPTION)).when(experimentCRUDMock).importExperiment(EXPERIMENT_ACCESSION, ExperimentType.TWOCOLOUR);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, ExperimentType.TWOCOLOUR), is(TEST_EXCEPTION));
    }

    @Test
    public void deleteExperimentShouldSucceed() throws Exception {
        String responseText = subject.deleteExperiment(EXPERIMENT_ACCESSION);
        assertThat(responseText, is("Experiment " + EXPERIMENT_ACCESSION + " successfully deleted."));
    }

    @Test
    public void exceptionMessageShouldBeReturnedAsResponseText() throws Exception {
        doThrow(new IllegalStateException(TEST_EXCEPTION)).when(experimentCRUDMock).deleteExperiment(EXPERIMENT_ACCESSION);
        String responseText = subject.deleteExperiment(EXPERIMENT_ACCESSION);
        assertThat(responseText, is(TEST_EXCEPTION));
    }

    @Test
    public void testListExperiments() throws Exception {
        given(experimentCRUDMock.findAllExperiments())
                .willReturn(Lists.newArrayList(new ExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.BASELINE)));

        assertThat(subject.listExperiments(), is("[{\"experimentAccession\":\"" + EXPERIMENT_ACCESSION + "\",\"experimentType\":\"BASELINE\"}]"));
    }

}