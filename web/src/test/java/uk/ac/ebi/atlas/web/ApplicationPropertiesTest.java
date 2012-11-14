/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import java.util.Properties;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationPropertiesTest {

    private static final String HOMO_SAPIENS_EXPERIMENT_ACCESSION = "AN_ACCESSION_1";
    private static final String MOUSE_EXPERIMENT_ACCESSION = "AN_ACCESSION_2";

    private static final String HOMO_SAPIENS_SPECIE = "homo sapiens";
    private static final String MOUSE_SPECIE = "mousy";

    private static final String HOMO_SAPIENS_FILE_NAME = "homoSapiens";
    private static final String FEMALE_SAPIENS_FILE_NAME = "femaleSapiens";
    private static final String MOUSE_FILE_NAME = "mouseFileName";

    private static final String ORGANISM_PARTS_PROPERTY_KEY = "organism.parts";
    private static final String ANATOMOGRAM_PROPERTY_KEY = "organism.anatomogram.";

    @Mock
    private Experiment homoSapiensExperimentMock;

    @Mock
    private Experiment mouseExperimentMock;

    @Mock
    private ExperimentsCache experimentCacheMock;

    @Mock
    private Properties configurationMock;

    private ApplicationProperties subject;

    @Before
    public void setUp() throws Exception {
        when(experimentCacheMock.getExperiment(HOMO_SAPIENS_EXPERIMENT_ACCESSION)).thenReturn(homoSapiensExperimentMock);
        when(experimentCacheMock.getExperiment(MOUSE_EXPERIMENT_ACCESSION)).thenReturn(mouseExperimentMock);

        when(configurationMock.getProperty(ORGANISM_PARTS_PROPERTY_KEY)).thenReturn("heart,wind,fire");
        when(configurationMock.getProperty(ANATOMOGRAM_PROPERTY_KEY + HOMO_SAPIENS_SPECIE + ".male")).thenReturn(HOMO_SAPIENS_FILE_NAME);
        when(configurationMock.getProperty(ANATOMOGRAM_PROPERTY_KEY + HOMO_SAPIENS_SPECIE + ".female")).thenReturn(FEMALE_SAPIENS_FILE_NAME);
        when(configurationMock.getProperty(ANATOMOGRAM_PROPERTY_KEY + MOUSE_SPECIE)).thenReturn(MOUSE_FILE_NAME);

        when(homoSapiensExperimentMock.getSpecie()).thenReturn(HOMO_SAPIENS_SPECIE);
        when(mouseExperimentMock.getSpecie()).thenReturn(MOUSE_SPECIE);

        subject = new ApplicationProperties(experimentCacheMock, configurationMock);
    }

    @Test
    public void testGetAllOrganismParts() throws Exception {
        SortedSet<String> allOrganismParts = subject.getAllOrganismParts();
        assertThat(allOrganismParts, hasItem("heart"));
    }

    @Test
    public void testGetAnatomogramFileName() throws Exception {
        String fileNameMale = subject.getAnatomogramFileName(HOMO_SAPIENS_EXPERIMENT_ACCESSION, true);
        String fileNameFemale = subject.getAnatomogramFileName(HOMO_SAPIENS_EXPERIMENT_ACCESSION, false);
        String fileNameMouseMale = subject.getAnatomogramFileName(MOUSE_EXPERIMENT_ACCESSION, true);
        String fileNameMouseFemale = subject.getAnatomogramFileName(MOUSE_EXPERIMENT_ACCESSION, false);

        assertThat(fileNameMale, is(HOMO_SAPIENS_FILE_NAME));
        assertThat(fileNameFemale, is(FEMALE_SAPIENS_FILE_NAME));
        assertThat(fileNameMouseMale, is(MOUSE_FILE_NAME));
        assertThat(fileNameMouseFemale, is(MOUSE_FILE_NAME));

    }
}
