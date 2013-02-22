//ToDo ... this is impossible to test without bootstrapping spring, as most other IT tests


package uk.ac.ebi.atlas.model.caches;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.model.FactorGroup;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ExperimentMetadataLoaderIT {

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-30352";
    private static final String RUN_ACCESSION = "SRR306774";

    @Inject
    private ExperimentMetadataLoader subject;

    @Before
    public void initSubject() throws IOException, ParseException {
    }

    @Test
    public void firstRunIsCorrect() throws Exception {
        //given
        Experiment experiment = subject.load(EXPERIMENT_ACCESSION);
        FactorGroup experimentRun = experiment.getFactorGroup(RUN_ACCESSION);
        //then
        assertThat(experimentRun.getFactorByType("ORGANISM_PART").getValue(), is(equalTo("liver")));
        Set<String> species = experiment.getSpecies();
        assertThat(species, hasItems("Monodelphis domestica", "Gallus gallus", "Homo sapiens"));
        assertThat(species, not(hasItem("Ornithorhynchus anatinus")));
    }

    @Test
    public void experimentRunShouldContainTheRightFactorsTest() throws Exception {
        Factor organismFactor = new Factor("ORGANISM","organism","Mus musculus");
        Factor organismPartFactor = new Factor("ORGANISM_PART","organism part","liver");
        //given
        Experiment experiment = subject.load(EXPERIMENT_ACCESSION);
        //when
        FactorGroup factorGroup = experiment.getFactorGroup(RUN_ACCESSION);
        //then
        assertThat(factorGroup.containsAll(Sets.newHashSet(organismFactor, organismPartFactor)), is(true));

    }

    @Test
    public void illuminaBodymapExperimentRunShouldContainTheRightFactorsTest() throws Exception {
        Factor organismPartFactor = new Factor("ORGANISM_PART","organism part","liver");
        //given
        Experiment experiment = subject.load("E-MTAB-513");
        //when
        FactorGroup factorGroup = experiment.getFactorGroup("ERR030887");
        //then
        assertThat(factorGroup.getFactorByType("PHENOTYPE"), is(nullValue()));
        assertThat(factorGroup.getFactorByType("PROTOCOL"), is(nullValue()));
        assertThat(factorGroup.getFactorByType("ORGANISM_PART"), is(organismPartFactor));

    }

    @Test
    public void ExperimentShouldOnlyContainRunsFromDataFile() throws IOException, ParseException {
        Experiment experiment = subject.load("E-MTAB-513");

        assertThat(experiment.getExperimentRunAccessions(), hasItems(
            "ERR030872", "ERR030873", "ERR030874", "ERR030875",
            "ERR030876", "ERR030877", "ERR030878", "ERR030879",
            "ERR030880", "ERR030881", "ERR030882", "ERR030883",
            "ERR030884", "ERR030885", "ERR030886", "ERR030887"
        ));
    }


}
