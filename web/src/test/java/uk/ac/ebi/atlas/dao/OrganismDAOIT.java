package uk.ac.ebi.atlas.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class OrganismDAOIT {

    @Inject
    private OrganismDAO subject;

    @Test
    public void testGetOrganisms() throws Exception {
        List<String> organisms = subject.getOrganisms();
        assertThat(organisms, hasItem("Mus musculus"));
    }

    @Test
    public void testGetOrganismsSize() throws Exception {
        List<String> organisms = subject.getOrganisms();
        assertThat(organisms.size(), is(16));
    }
}