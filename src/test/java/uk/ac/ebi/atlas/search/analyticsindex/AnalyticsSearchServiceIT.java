package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.ImmutableSet;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.SpeciesTest;

import javax.inject.Inject;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class AnalyticsSearchServiceIT {

    @Inject
    AnalyticsSearchService subject;

    @Test
    public void testGetBioentityIdentifiersForSpecies() throws Exception {
        Collection<String> response = subject.getBioentityIdentifiersForSpecies(SpeciesTest.HUMAN);

        assertThat(response.size(), Matchers.greaterThan(100));
    }
}