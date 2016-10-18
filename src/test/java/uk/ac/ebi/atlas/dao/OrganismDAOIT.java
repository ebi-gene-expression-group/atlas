package uk.ac.ebi.atlas.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experiments.NumberOfExperiments;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
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
        assertThat(organisms.size(), is(NumberOfExperiments.DISTINCT_ORGANISMS));
    }
}