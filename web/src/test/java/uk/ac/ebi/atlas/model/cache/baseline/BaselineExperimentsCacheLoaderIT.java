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

//ToDo ... this is impossible to test without bootstrapping spring, as most other IT tests


package uk.ac.ebi.atlas.model.cache.baseline;

import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.experimentloader.ExperimentDAO;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml"})
public class BaselineExperimentsCacheLoaderIT {

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-30352";
    private static final String ILLUMINA_EXPERIMENT_ACCESSION = "E-MTAB-513";
    private static final String RUN_ACCESSION = "SRR306774";

    @Inject
    private BaselineExperimentsCacheLoader subject;

    @Inject
    private ExperimentDAO experimentDAO;

    @Before
    public void setUp() throws Exception {

        experimentDAO.addExperiment(EXPERIMENT_ACCESSION, ExperimentType.BASELINE, false);
        experimentDAO.addExperiment(ILLUMINA_EXPERIMENT_ACCESSION, ExperimentType.BASELINE, false);

    }

    @After
    public void tearDown() throws Exception {
        experimentDAO.deleteExperiment(EXPERIMENT_ACCESSION);
        experimentDAO.deleteExperiment(ILLUMINA_EXPERIMENT_ACCESSION);
    }

    @Test
    public void firstRunIsCorrect() throws Exception {
        //given
        BaselineExperiment experiment = subject.load(EXPERIMENT_ACCESSION);
        FactorGroup experimentRun = experiment.getFactorGroup(RUN_ACCESSION);
        //then
        assertThat(experimentRun.getFactorByType("ORGANISM_PART").getValue(), is(equalTo("liver")));
        Set<String> species = experiment.getSpecies();
        assertThat(species, hasItems("Monodelphis domestica", "Gallus gallus", "Homo sapiens"));
        assertThat(species, not(hasItem("Ornithorhynchus anatinus")));
    }

    @Test
    public void experimentRunShouldContainTheRightFactorsTest() throws Exception {
        Factor organismFactor = new Factor("ORGANISM","Mus musculus");
        Factor organismPartFactor = new Factor("ORGANISM_PART","liver");
        //given
        BaselineExperiment experiment = subject.load(EXPERIMENT_ACCESSION);
        //when
        FactorGroup factorGroup = experiment.getFactorGroup(RUN_ACCESSION);
        //then
        assertThat(factorGroup.containsAll(Sets.newHashSet(organismFactor, organismPartFactor)), is(true));

    }

    @Test
    public void illuminaBodymapExperimentRunShouldContainTheRightFactorsTest() throws Exception {
        Factor organismPartFactor = new Factor("ORGANISM_PART","liver");
        //given
        BaselineExperiment experiment = subject.load(ILLUMINA_EXPERIMENT_ACCESSION);
        //when
        FactorGroup factorGroup = experiment.getFactorGroup("ERR030887");
        //then
        assertThat(factorGroup.getFactorByType("PHENOTYPE"), is(nullValue()));
        assertThat(factorGroup.getFactorByType("PROTOCOL"), is(nullValue()));
        assertThat(factorGroup.getFactorByType("ORGANISM_PART"), is(organismPartFactor));

    }

    @Test
    public void ExperimentShouldOnlyContainRunsFromDataFile() throws IOException, ParseException {
        BaselineExperiment experiment = subject.load("E-MTAB-513");

        assertThat(experiment.getExperimentRunAccessions(), hasItems(
            "ERR030872", "ERR030873", "ERR030874", "ERR030875",
            "ERR030876", "ERR030877", "ERR030878", "ERR030879",
            "ERR030880", "ERR030881", "ERR030882", "ERR030883",
            "ERR030884", "ERR030885", "ERR030886", "ERR030887"
        ));
    }


}
