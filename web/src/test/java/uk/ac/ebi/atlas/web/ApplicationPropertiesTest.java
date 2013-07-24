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

package uk.ac.ebi.atlas.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentloader.ExperimentDAO;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationPropertiesTest {
    private static final String HOMO_SAPIENS_SPECIE = "homo sapiens";
    private static final String MOUSE_SPECIE = "mousy";

    private static final String HOMO_SAPIENS_FILE_NAME = "homoSapiens";
    private static final String FEMALE_SAPIENS_FILE_NAME = "femaleSapiens";
    private static final String MOUSE_FILE_NAME = "mouseFileName";

    private static final String ORGANISM_PARTS_PROPERTY_KEY = "organism.parts";
    private static final String ANATOMOGRAM_PROPERTY_KEY = "organism.anatomogram.";
    private static final String FEEDBACK_EMAIL_PROPERTY_KEY = "feedback.email";
    private static final String FEEDBACK_EMAIL_VALUE = "abc@abc.com";
    private static final String ARRAYEXPRESS_URL = "http://www.ebi.ac.uk/arrayexpress/";
    private static final String EXPERIMENT_ARRAYEXPRESS_URL_TEMPLATE = "experiment.arrayexpress.url.template";
    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final String ARRAYEXPRESS_REST_URL = "http://www.ebi.ac.uk/arrayexpressrest/";
    private static final String EXPERIMENT_ARRAYEXPRESS_REST_URL_TEMPLATE = "experiment.arrayexpress.rest.url.template";
    private static final String LIST_SEPARATOR = ",";
    private static final String A_AFFY_35 = "A-AFFY-35";
    private static final String EXPERIMENT_ARRAYEXPRESS_ARRAYS_URL_TEMPLATE = "experiment.arrayexpress.arrays.url.template";
    private static final String ARRAYEXPRESS_ARRAYS_URL = "http://www.ebi.ac.uk/arrayexpress/arrays/";
    private static final String EXPERIMENT_PUBMED_URL_TEMPLATE = "experiment.pubmed.url.template";
    private static final String PUBMED_URL = "http://www.ncbi.nlm.nih.gov/pubmed/";
    private static final String EXPERIMENT_ATLAS_URL_TEMPLATE = "experiment.atlas.url.template";
    private static final String ATLAS_URL = "http://www-test.ebi.ac.uk/gxa/experiments/";
    private static final String PUB_MED_ID = "123456";

    @Mock
    private BaselineExperiment homoSapiensExperimentMock;

    @Mock
    private BaselineExperiment mouseExperimentMock;

    @Mock
    private BaselineExperimentsCache experimentCacheMock;

    @Mock
    private Properties configurationPropertiesMock;

    @Mock
    private ExperimentDAO experimentDAOMock;

    @Mock
    private Properties speciesToExperimentPropertiesMock;

    private ApplicationProperties subject;

    @Before
    public void setUp() throws Exception {
        when(configurationPropertiesMock.getProperty(ORGANISM_PARTS_PROPERTY_KEY)).thenReturn("heart" + LIST_SEPARATOR + "wind" + LIST_SEPARATOR + "fire");
        when(configurationPropertiesMock.getProperty(ANATOMOGRAM_PROPERTY_KEY + HOMO_SAPIENS_SPECIE + ".male")).thenReturn(HOMO_SAPIENS_FILE_NAME);
        when(configurationPropertiesMock.getProperty(ANATOMOGRAM_PROPERTY_KEY + HOMO_SAPIENS_SPECIE + ".female")).thenReturn(FEMALE_SAPIENS_FILE_NAME);
        when(configurationPropertiesMock.getProperty(ANATOMOGRAM_PROPERTY_KEY + MOUSE_SPECIE)).thenReturn(MOUSE_FILE_NAME);

        when(configurationPropertiesMock.getProperty(EXPERIMENT_ARRAYEXPRESS_URL_TEMPLATE)).thenReturn(ARRAYEXPRESS_URL + "{0}");
        when(configurationPropertiesMock.getProperty(EXPERIMENT_ARRAYEXPRESS_REST_URL_TEMPLATE)).thenReturn(ARRAYEXPRESS_REST_URL + "{0}");
        when(configurationPropertiesMock.getProperty(FEEDBACK_EMAIL_PROPERTY_KEY)).thenReturn(FEEDBACK_EMAIL_VALUE);

        when(configurationPropertiesMock.getProperty(EXPERIMENT_ARRAYEXPRESS_ARRAYS_URL_TEMPLATE)).thenReturn(ARRAYEXPRESS_ARRAYS_URL + "{0}");
        when(configurationPropertiesMock.getProperty(EXPERIMENT_PUBMED_URL_TEMPLATE)).thenReturn(PUBMED_URL + "{0}");
        when(configurationPropertiesMock.getProperty(EXPERIMENT_ATLAS_URL_TEMPLATE)).thenReturn(ATLAS_URL + "{0}");

        when(configurationPropertiesMock.getProperty("integration.experiment.identifiers")).thenReturn("");

        when(homoSapiensExperimentMock.getFirstSpecies()).thenReturn(HOMO_SAPIENS_SPECIE);
        when(mouseExperimentMock.getFirstSpecies()).thenReturn(MOUSE_SPECIE);

        subject = new ApplicationProperties(configurationPropertiesMock, speciesToExperimentPropertiesMock, experimentDAOMock);
    }

    @Test
    public void testGetAnatomogramFileName() throws Exception {
        String fileNameMale = subject.getAnatomogramFileName(HOMO_SAPIENS_SPECIE, true);
        String fileNameFemale = subject.getAnatomogramFileName(HOMO_SAPIENS_SPECIE, false);
        String fileNameMouseMale = subject.getAnatomogramFileName(MOUSE_SPECIE, true);
        String fileNameMouseFemale = subject.getAnatomogramFileName(MOUSE_SPECIE, false);

        assertThat(fileNameMale, is(HOMO_SAPIENS_FILE_NAME));
        assertThat(fileNameFemale, is(FEMALE_SAPIENS_FILE_NAME));
        assertThat(fileNameMouseMale, is(MOUSE_FILE_NAME));
        assertThat(fileNameMouseFemale, is(MOUSE_FILE_NAME));
    }

    @Test
    public void testGetArrayExpressURL() throws Exception {
        assertThat(subject.getArrayExpressURL(EXPERIMENT_ACCESSION), is(ARRAYEXPRESS_URL + EXPERIMENT_ACCESSION));
    }

    @Test
    public void testGetArrayExpressRestURL() throws Exception {
        assertThat(subject.getArrayExpressRestURL(EXPERIMENT_ACCESSION), is(ARRAYEXPRESS_REST_URL + EXPERIMENT_ACCESSION));
    }

    @Test
    public void testGetArrayExpressArrayURL() throws Exception {
        assertThat(subject.getArrayExpressArrayURL(A_AFFY_35), is(ARRAYEXPRESS_ARRAYS_URL + A_AFFY_35));
    }

    @Test
    public void testGetPubMedURL() throws Exception {
        assertThat(subject.getPubMedURL(PUB_MED_ID), is(PUBMED_URL + PUB_MED_ID));
    }

    @Test
    public void testGetAtlasURL() throws Exception {
        assertThat(subject.getAtlasURL(EXPERIMENT_ACCESSION), is(ATLAS_URL + EXPERIMENT_ACCESSION));
    }

    @Test
    public void testGetFeedbackEmailAddress() throws Exception {
        assertThat(subject.getFeedbackEmailAddress(), is(FEEDBACK_EMAIL_VALUE));
    }


}
