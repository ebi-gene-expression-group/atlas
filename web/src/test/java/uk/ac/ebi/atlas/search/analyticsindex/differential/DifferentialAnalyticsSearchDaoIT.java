package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.collect.Multimap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DifferentialAnalyticsSearchDaoIT {

    @Inject
    private DifferentialAnalyticsSearchDao subject;

    @Test
    public void test() {
        String stringNameValue = subject.fetchFacets(GeneQuery.create("*"));

        // TODO parseJsonResponse and assert that it has the expected fields
    }

}