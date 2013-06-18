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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ConfigurationDaoIT {

    private static final String EXPERIMENT_ACCESSION = "experiment_accession";
    private static final ExperimentType EXPERIMENT_TYPE = ExperimentType.BASELINE;
    private static final String E_MTAB_1066 = "E-MTAB-1066";
    private static final ExperimentType TYPE_MICROARRAY = ExperimentType.MICROARRAY;
    private static final String ANOTHER_ACCESION = "ANOTHER";
    private static final String YET_ANOTHER_ACCESSION = "YETANOTHER";

    @Inject
    private DataSource dataSource;

    @Inject
    private ConfigurationDao subject;

    @Before
    public void setUp() throws Exception {

        subject.deleteExperimentConfiguration(EXPERIMENT_ACCESSION);
        subject.addExperimentConfiguration(EXPERIMENT_ACCESSION, EXPERIMENT_TYPE);

    }

    @Test
    public void testGetExperimentConfigurations() throws Exception {
        List<ExperimentConfiguration> experimentConfigurations = subject.getExperimentConfigurations();
        ExperimentConfiguration experimentConfiguration = experimentConfigurations.get(0);
        assertThat(experimentConfiguration.getExperimentAccession(), is(EXPERIMENT_ACCESSION));
        assertThat(experimentConfiguration.getExperimentType(), is(EXPERIMENT_TYPE));
    }

    @Test
    public void testGetExperimentConfigurationsByType() throws Exception {
        subject.addExperimentConfiguration(ANOTHER_ACCESION, ExperimentType.DIFFERENTIAL);
        subject.addExperimentConfiguration(YET_ANOTHER_ACCESSION, ExperimentType.MICROARRAY);
        List<ExperimentConfiguration> experimentConfigurations = subject.getExperimentConfigurations(EXPERIMENT_TYPE);
        ExperimentConfiguration experimentConfiguration = experimentConfigurations.get(0);
        assertThat(experimentConfiguration.getExperimentAccession(), is(EXPERIMENT_ACCESSION));
        assertThat(experimentConfiguration.getExperimentType(), is(EXPERIMENT_TYPE));
        experimentConfigurations = subject.getExperimentConfigurations(ExperimentType.DIFFERENTIAL);
        experimentConfiguration = experimentConfigurations.get(0);
        assertThat(experimentConfiguration.getExperimentAccession(), is(ANOTHER_ACCESION));
        assertThat(experimentConfiguration.getExperimentType(), is(ExperimentType.DIFFERENTIAL));
        experimentConfigurations = subject.getExperimentConfigurations(ExperimentType.MICROARRAY);
        experimentConfiguration = experimentConfigurations.get(0);
        assertThat(experimentConfiguration.getExperimentAccession(), is(YET_ANOTHER_ACCESSION));
        assertThat(experimentConfiguration.getExperimentType(), is(ExperimentType.MICROARRAY));
        subject.deleteExperimentConfiguration(ANOTHER_ACCESION);
        subject.deleteExperimentConfiguration(YET_ANOTHER_ACCESSION);
    }

    @Test
    public void testGetExperimentConfiguration() throws Exception {
        ExperimentConfiguration experimentConfiguration = subject.getExperimentConfiguration(EXPERIMENT_ACCESSION);
        assertThat(experimentConfiguration.getExperimentAccession(), is(EXPERIMENT_ACCESSION));
        assertThat(experimentConfiguration.getExperimentType(), is(EXPERIMENT_TYPE));
    }

    @Test
    public void testGetExperimentConfigurationEmpty() throws Exception {
        assertThat(subject.getExperimentConfiguration("NON-EXISTING"), is(nullValue()));
    }

    @Test
    public void testAddExperimentConfiguration() throws Exception {
        assertThat(subject.addExperimentConfiguration(E_MTAB_1066, TYPE_MICROARRAY), is(1));
        List<ExperimentConfiguration> experimentConfigurations = subject.getExperimentConfigurations();
        assertThat(experimentConfigurations.size(), is(2));
        ExperimentConfiguration experimentConfiguration = experimentConfigurations.get(1);
        assertThat(experimentConfiguration.getExperimentAccession(), is(E_MTAB_1066));
        assertThat(experimentConfiguration.getExperimentType(), is(TYPE_MICROARRAY));
    }

    @Test
    public void testDeleteExperimentConfiguration() throws Exception {
        assertThat(subject.deleteExperimentConfiguration(EXPERIMENT_ACCESSION), is(1));
        assertThat(subject.getExperimentConfigurations().size(), is(0));
    }
}