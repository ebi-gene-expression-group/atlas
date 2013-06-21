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

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.configuration.ConfigurationDao;
import uk.ac.ebi.atlas.configuration.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
    private static final String BASELINE_PROPERTY_KEY = "baseline.experiment.identifiers";
    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_MTAB_599 = "E-MTAB-599";
    private static final String LIST_SEPARATOR = ",";
    private static final String E_MTAB_1066 = "E-MTAB-1066";
    private static final String E_GEOD_43049 = "E-GEOD-43049";
    private static final String DIFFERENTIAL_PROPERTY_KEY = "differential.experiment.identifiers";
    private static final String E_GEOD_22351 = "E-GEOD-22351";
    private static final String E_GEOD_38400 = "E-GEOD-38400";
    private static final String E_GEOD_21860 = "E-GEOD-21860";
    private static final String MICROARRAY_PROPERTY_KEY = "microarray.experiment.identifiers";
    private static final String TWOCOLOUR_PROPERTY_KEY = "twocolour.experiment.identifiers";
    private static final String BIOMART_DATASET_NAMES = "biomart.dataset.names";
    private static final String A_AFFY_35 = "A-AFFY-35";
    private static final String A_AGIL_28 = "A-AGIL-28";
    private static final String ARRAYDESIGN_PROPERTY_KEY = "arraydesign.accessions";
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
    private Properties configurationMock;

    @Mock
    private ConfigurationDao configurationDaoMock;

    private ApplicationProperties subject;

    @Before
    public void setUp() throws Exception {
        when(configurationMock.getProperty(ORGANISM_PARTS_PROPERTY_KEY)).thenReturn("heart" + LIST_SEPARATOR + "wind" + LIST_SEPARATOR + "fire");
        when(configurationMock.getProperty(ANATOMOGRAM_PROPERTY_KEY + HOMO_SAPIENS_SPECIE + ".male")).thenReturn(HOMO_SAPIENS_FILE_NAME);
        when(configurationMock.getProperty(ANATOMOGRAM_PROPERTY_KEY + HOMO_SAPIENS_SPECIE + ".female")).thenReturn(FEMALE_SAPIENS_FILE_NAME);
        when(configurationMock.getProperty(ANATOMOGRAM_PROPERTY_KEY + MOUSE_SPECIE)).thenReturn(MOUSE_FILE_NAME);

        when(configurationMock.getProperty(EXPERIMENT_ARRAYEXPRESS_URL_TEMPLATE)).thenReturn(ARRAYEXPRESS_URL + "{0}");
        when(configurationMock.getProperty(EXPERIMENT_ARRAYEXPRESS_REST_URL_TEMPLATE)).thenReturn(ARRAYEXPRESS_REST_URL + "{0}");
        when(configurationMock.getProperty(FEEDBACK_EMAIL_PROPERTY_KEY)).thenReturn(FEEDBACK_EMAIL_VALUE);

        when(configurationMock.getProperty(TWOCOLOUR_PROPERTY_KEY)).thenReturn(E_GEOD_43049);
        when(configurationMock.getProperty(BIOMART_DATASET_NAMES)).thenReturn(HOMO_SAPIENS_SPECIE + LIST_SEPARATOR + MOUSE_SPECIE);
        when(configurationMock.getProperty(ARRAYDESIGN_PROPERTY_KEY)).thenReturn(A_AFFY_35 + LIST_SEPARATOR + A_AGIL_28);
        when(configurationMock.getProperty(EXPERIMENT_ARRAYEXPRESS_ARRAYS_URL_TEMPLATE)).thenReturn(ARRAYEXPRESS_ARRAYS_URL + "{0}");
        when(configurationMock.getProperty(EXPERIMENT_PUBMED_URL_TEMPLATE)).thenReturn(PUBMED_URL + "{0}");
        when(configurationMock.getProperty(EXPERIMENT_ATLAS_URL_TEMPLATE)).thenReturn(ATLAS_URL + "{0}");

        when(configurationMock.getProperty("integration.experiment.identifiers")).thenReturn("");

        when(homoSapiensExperimentMock.getFirstSpecies()).thenReturn(HOMO_SAPIENS_SPECIE);
        when(mouseExperimentMock.getFirstSpecies()).thenReturn(MOUSE_SPECIE);

        ExperimentConfiguration emtab513 = new ExperimentConfiguration(E_MTAB_513, ExperimentType.BASELINE);
        ExperimentConfiguration emtab599 = new ExperimentConfiguration(E_MTAB_599, ExperimentType.BASELINE);
        when(configurationDaoMock.getExperimentConfigurations(ExperimentType.BASELINE)).thenReturn(Lists.newArrayList(emtab513, emtab599));

        ExperimentConfiguration egeod22351 = new ExperimentConfiguration(E_GEOD_22351, ExperimentType.DIFFERENTIAL);
        ExperimentConfiguration egeod38400 = new ExperimentConfiguration(E_GEOD_38400, ExperimentType.DIFFERENTIAL);
        ExperimentConfiguration egeod21860 = new ExperimentConfiguration(E_GEOD_21860, ExperimentType.DIFFERENTIAL);
        when(configurationDaoMock.getExperimentConfigurations(ExperimentType.DIFFERENTIAL)).thenReturn(Lists.newArrayList(egeod21860, egeod22351, egeod38400));

        ExperimentConfiguration emtab1066 = new ExperimentConfiguration(E_MTAB_1066, ExperimentType.MICROARRAY);
        when(configurationDaoMock.getExperimentConfigurations(ExperimentType.MICROARRAY)).thenReturn(Lists.newArrayList(emtab1066));

        ExperimentConfiguration egeod43049 = new ExperimentConfiguration(E_GEOD_43049, ExperimentType.TWOCOLOUR);
        when(configurationDaoMock.getExperimentConfigurations(ExperimentType.TWOCOLOUR)).thenReturn(Lists.newArrayList(egeod43049));

        subject = new ApplicationProperties(configurationMock, configurationDaoMock);
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

    @Test
    public void testGetBaselineExperimentsIdentifiers() throws Exception {
        assertThat(subject.getBaselineExperimentsIdentifiers(), containsInAnyOrder(E_MTAB_513, E_MTAB_599));
    }

    @Test
    public void testGetDifferentialExperimentsIdentifiers() throws Exception {
        assertThat(subject.getDifferentialExperimentsIdentifiers(), containsInAnyOrder(E_GEOD_22351, E_GEOD_38400, E_GEOD_21860));
    }

    @Test
    public void testGetMicroarrayExperimentsIdentifiers() throws Exception {
        assertThat(subject.getMicroarrayExperimentsIdentifiers(), containsInAnyOrder(E_MTAB_1066, E_GEOD_43049));
    }

    @Test
    public void testGetTwoColourExperimentsIdentifiers() throws Exception {
        assertThat(subject.getTwoColourExperimentsIdentifiers(), containsInAnyOrder(E_GEOD_43049));
    }

    @Test
    public void testGetBiomartDatasetIdentifiers() throws Exception {
        assertThat(subject.getBiomartDatasetIdentifiers(), containsInAnyOrder(HOMO_SAPIENS_SPECIE, MOUSE_SPECIE));
    }

    @Test
    public void testGetArrayDesignAccessions() throws Exception {
        assertThat(subject.getArrayDesignAccessions(), containsInAnyOrder(A_AFFY_35, A_AGIL_28));
    }
}
