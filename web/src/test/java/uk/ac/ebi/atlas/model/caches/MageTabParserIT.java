//ToDo ... this is impossible to test without bootstrapping spring, as most other IT tests


package uk.ac.ebi.atlas.model.caches;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.FactorGroup;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MageTabParserIT {

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
    }

}
