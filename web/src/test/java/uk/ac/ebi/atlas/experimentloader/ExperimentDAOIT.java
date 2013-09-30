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

package uk.ac.ebi.atlas.experimentloader;

import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:testDBContext.xml"})
public class ExperimentDAOIT {

    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_MTAB_1066 = "E-MTAB-1066";
    private static final ExperimentType TYPE_BASELINE = ExperimentType.BASELINE;
    private static final ExperimentType TYPE_DIFFERENTIAL = ExperimentType.DIFFERENTIAL;
    private static final ExperimentType TYPE_MICROARRAY = ExperimentType.MICROARRAY;
    private static final ExperimentType TYPE_MICRORNA = ExperimentType.MICRORNA;
    private static final String DIFFERENTIAL_ACCESION = "DIFFERENTIAL_ACCESSION";
    private static final String MICROARRAY_ACCESSION = "MICROARRAY_ACCESSION";
    private static final String MICRORNA_ACCESSION = "MICRORNA_ACCESSION";
    private static final String ACCESS_KEY = "AN_UUID";

    @Inject
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Inject
    private ExperimentDAO subject;

    @Before
    public void setUp() throws Exception {
        ExperimentDTO mtab513 = new ExperimentDTO(E_MTAB_513, TYPE_BASELINE, Sets.newHashSet("human", "mouse"),
                Sets.newHashSet("1", "2"), "Illumina", false);
        ExperimentDTO mtab1066 = new ExperimentDTO(E_MTAB_1066, TYPE_MICROARRAY, Sets.newHashSet("bird"),
                Sets.newHashSet("1", "2"), "diff", false);

        ExperimentDTO mtabPrivate = new ExperimentDTO("Secret_111", TYPE_MICROARRAY, Sets.newHashSet("cow"),
                Sets.newHashSet("1"), "diff", true);

        subject.addExperiment(mtab513);
        subject.addExperiment(mtab1066);
        subject.addExperiment(mtabPrivate);
    }

    @After
    public void tearDown() throws Exception {
        try {
            subject.deleteExperiment(E_MTAB_513);
        } catch (Exception e) {
        }
        try {
            subject.deleteExperiment(E_MTAB_1066);
        } catch (Exception e) {
        }
        try {
            subject.deleteExperiment("Secret_111");
        } catch (Exception e) {

        }
    }

    @Test
    public void testFindExperiments() throws Exception {
        List<ExperimentDTO> experimentDTOs = subject.findAllExperiments();
        assertThat(experimentDTOs, hasSize(3));
        assertThat(experimentDTOs, hasItem(new ExperimentDTO(E_MTAB_513, TYPE_BASELINE, Sets.newHashSet(""), Sets.newHashSet(""), "", false)));
    }

    @Test
    public void testFindExperimentByType() throws Exception {
        Set<String> experimentAccessions = subject.findPublicExperimentAccessions(TYPE_BASELINE);
        assertThat(experimentAccessions, hasItem(E_MTAB_513));
        experimentAccessions = subject.findPublicExperimentAccessions(TYPE_MICROARRAY);
        assertThat(experimentAccessions, hasItem(E_MTAB_1066));

    }

    @Test
    public void findExperimentShouldSucceed() throws Exception {
        ExperimentDTO experimentDTO = subject.findPublicExperiment(E_MTAB_513);
        assertThat(experimentDTO.getExperimentAccession(), is(E_MTAB_513));
        assertThat(experimentDTO.getExperimentType(), is(TYPE_BASELINE));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findExperimentShouldFailForUnknownExperiment() throws Exception {
        subject.findPublicExperiment("UNKNOWN");
    }

    @Test
    public void testAddExperiment() throws Exception {
        List<ExperimentDTO> experimentDTOs = subject.findAllExperiments();
        int size = experimentDTOs.size();
        ExperimentDTO mtabNew = new ExperimentDTO("new", TYPE_MICROARRAY, Sets.newHashSet("cow"),
                        Sets.newHashSet("1"), "diff", true);
        subject.addExperiment(mtabNew);
        experimentDTOs = subject.findAllExperiments();
        assertThat(experimentDTOs.size(), is(size + 1));
        assertThat(experimentDTOs, hasItem(mtabNew));
        subject.deleteExperiment("new");
    }

    @Test
    public void testDeleteExperiment() throws Exception {
        List<ExperimentDTO> experimentDTOs = subject.findAllExperiments();
        int size = experimentDTOs.size();
        subject.deleteExperiment(E_MTAB_513);
        assertThat(subject.findAllExperiments().size(), is(size - 1));
    }

    @Test
    public void updateExperimentShouldChangePrivateState() throws Exception {
        assertThat(subject.findPublicExperiment(E_MTAB_513), is(notNullValue()));
        subject.updateExperiment(E_MTAB_513, true);
        assertThat(subject.findExperiment(E_MTAB_513, true).isPrivate(), is(true));
        subject.updateExperiment(E_MTAB_513, false);
        assertThat(subject.findPublicExperiment(E_MTAB_513), is(notNullValue()));
    }

    @Test
    public void isImportedShouldReturnImportState() throws Exception {
        assertThat(subject.isImported(E_MTAB_513), is(true));
        assertThat(subject.isImported("NEW"), is(false));
    }

}