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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ConfigurationDaoIT {

    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_MTAB_1066 = "E-MTAB-1066";
    private static final ExperimentType TYPE_BASELINE = ExperimentType.BASELINE;
    private static final ExperimentType TYPE_DIFFERENTIAL = ExperimentType.DIFFERENTIAL;
    private static final ExperimentType TYPE_MICROARRAY = ExperimentType.MICROARRAY;
    private static final ExperimentType TYPE_MICRORNA = ExperimentType.MICRORNA;
    private static final String ANOTHER_ACCESION = "ANOTHER";
    private static final String YET_ANOTHER_ACCESSION = "YET ANOTHER";
    private static final String YET_YET_ANOTHER_ACCESSION = "YET YET ANOTHER";

    @Inject
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Inject
    private ConfigurationDao subject;

    @Before
    public void setUp() throws Exception {

        subject.deleteExperimentConfiguration(E_MTAB_513);
        subject.addExperimentConfiguration(E_MTAB_513, TYPE_BASELINE);

    }

    @Test
    public void testGetExperimentConfigurations() throws Exception {
        List<ExperimentConfiguration> experimentConfigurations = subject.getExperimentConfigurations();
        assertThat(experimentConfigurations, hasItem(new ExperimentConfiguration(E_MTAB_513, TYPE_BASELINE)));
    }

    @Test
    public void testGetExperimentConfigurationsByType() throws Exception {
        subject.addExperimentConfiguration(ANOTHER_ACCESION, TYPE_DIFFERENTIAL);
        subject.addExperimentConfiguration(YET_ANOTHER_ACCESSION, TYPE_MICROARRAY);
        subject.addExperimentConfiguration(YET_YET_ANOTHER_ACCESSION, TYPE_MICRORNA);
        List<ExperimentConfiguration> experimentConfigurations = subject.getExperimentConfigurations(TYPE_BASELINE);
        assertThat(experimentConfigurations, hasItem(new ExperimentConfiguration(E_MTAB_513, TYPE_BASELINE)));
        experimentConfigurations = subject.getExperimentConfigurations(TYPE_DIFFERENTIAL);
        assertThat(experimentConfigurations, hasItem(new ExperimentConfiguration(ANOTHER_ACCESION, TYPE_DIFFERENTIAL)));
        experimentConfigurations = subject.getExperimentConfigurations(TYPE_MICROARRAY);
        assertThat(experimentConfigurations, hasItem(new ExperimentConfiguration(YET_ANOTHER_ACCESSION, TYPE_MICROARRAY)));
        experimentConfigurations = subject.getExperimentConfigurations(TYPE_MICRORNA);
        assertThat(experimentConfigurations, hasItem(new ExperimentConfiguration(YET_YET_ANOTHER_ACCESSION, TYPE_MICRORNA)));
        subject.deleteExperimentConfiguration(ANOTHER_ACCESION);
        subject.deleteExperimentConfiguration(YET_ANOTHER_ACCESSION);
        subject.deleteExperimentConfiguration(YET_YET_ANOTHER_ACCESSION);
    }

    @Test
    public void testGetExperimentConfiguration() throws Exception {
        ExperimentConfiguration experimentConfiguration = subject.getExperimentConfiguration(E_MTAB_513);
        assertThat(experimentConfiguration.getExperimentAccession(), is(E_MTAB_513));
        assertThat(experimentConfiguration.getExperimentType(), is(TYPE_BASELINE));
    }

    @Test
    public void testGetExperimentConfigurationEmpty() throws Exception {
        assertThat(subject.getExperimentConfiguration("NON-EXISTING"), is(nullValue()));
    }

    @Test
    public void testAddExperimentConfiguration() throws Exception {
        // cleanup just in case
        subject.deleteExperimentConfiguration(E_MTAB_1066);
        List<ExperimentConfiguration> experimentConfigurations = subject.getExperimentConfigurations();
        int size = experimentConfigurations.size();
        assertThat(subject.addExperimentConfiguration(E_MTAB_1066, TYPE_MICROARRAY), is(1));
        experimentConfigurations = subject.getExperimentConfigurations();
        assertThat(experimentConfigurations.size(), is(size + 1));
        assertThat(experimentConfigurations, hasItem(new ExperimentConfiguration(E_MTAB_1066, TYPE_MICROARRAY)));
    }

    @Test
    public void testDeleteExperimentConfiguration() throws Exception {
        List<ExperimentConfiguration> experimentConfigurations = subject.getExperimentConfigurations();
        int size = experimentConfigurations.size();
        assertThat(subject.deleteExperimentConfiguration(E_MTAB_513), is(1));
        assertThat(subject.getExperimentConfigurations().size(), is(size - 1));
    }
}