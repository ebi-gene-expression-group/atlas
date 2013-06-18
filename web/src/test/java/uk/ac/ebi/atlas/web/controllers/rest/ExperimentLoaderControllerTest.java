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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.configuration.ConfigurationDao;
import uk.ac.ebi.atlas.configuration.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.ExperimentType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentLoaderControllerTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String EXPERIMENT_TYPE = "BASELINE";

    @Mock
    private ConfigurationDao configurationDaoMock;

    @Mock
    private ExperimentConfiguration experimentConfigurationMock;

    private ExperimentLoaderController subject;

    @Before
    public void setUp() throws Exception {
        subject = new ExperimentLoaderController(configurationDaoMock);

        when(configurationDaoMock.getExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(null);
        when(configurationDaoMock.addExperimentConfiguration(EXPERIMENT_ACCESSION, ExperimentType.valueOf(EXPERIMENT_TYPE))).thenReturn(1);
        when(configurationDaoMock.deleteExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(1);
    }

    @Test
    public void testLoadExperiment() throws Exception {
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, EXPERIMENT_TYPE), is("Experiment " + EXPERIMENT_ACCESSION + " loaded."));
    }

    @Test
    public void testLoadExperimentEmptyAccession() throws Exception {
        assertThat(subject.loadExperiment("", EXPERIMENT_TYPE), is("Experiment accession cannot be empty."));
    }

    @Test
    public void testLoadExperimentExistingAccession() throws Exception {
        when(configurationDaoMock.getExperimentConfiguration(EXPERIMENT_ACCESSION)).thenReturn(experimentConfigurationMock);
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, EXPERIMENT_TYPE), is("Experiment with accession " + EXPERIMENT_ACCESSION + " already exists."));
    }

    @Test
    public void testLoadExperimentWrongType() throws Exception {
        assertThat(subject.loadExperiment(EXPERIMENT_ACCESSION, "NON-EXISTING-TYPE"), is("An unknown experiment type has been specified."));
    }

    @Test
    public void testDeleteExperiment() throws Exception {
        assertThat(subject.deleteExperiment(EXPERIMENT_ACCESSION), is("Experiment " + EXPERIMENT_ACCESSION + " deleted."));
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
}