package uk.ac.ebi.atlas.dao;

import uk.ac.ebi.atlas.experiments.NumberOfExperiments;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
public class OrganismDAOIT {

    @Inject
    private OrganismDAO subject;

    @Test
    public void testGetOrganisms() throws Exception {
        List<String> organisms = subject.getOrganisms();
        assertThat(organisms, hasItem("Mus musculus"));
    }

    // check for 2 word species because differential search requires bioentity species (ie: species with 2 words only)
    @Test
    public void containsOryzaSativaShortened() throws Exception {
        List<String> organisms = subject.getOrganisms();
        assertThat(organisms, hasItem("Oryza sativa"));
    }

    @Test
    public void doesNotContainLongSpeciesNames() throws Exception {
        List<String> organisms = subject.getOrganisms();
        assertThat(organisms, not(hasItem("Oryza sativa Japonica Group")));
    }

    @Test
    public void testGetOrganismsSize() throws Exception {
        List<String> organisms = subject.getOrganisms();
        assertThat(organisms.size(), Matchers.is(NumberOfExperiments.DISTINCT_ORGANISMS));
    }

}