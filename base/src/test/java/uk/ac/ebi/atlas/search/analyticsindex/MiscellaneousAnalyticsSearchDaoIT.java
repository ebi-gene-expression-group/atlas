package uk.ac.ebi.atlas.search.analyticsindex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/dbContext.xml"})
public class MiscellaneousAnalyticsSearchDaoIT {

    @Inject
    MiscellaneousAnalyticsSearchDao subject;

    @Test
    public void getSpecies() {
        String species = subject.getSpecies(SemanticQuery.create("zinc finger"), SemanticQuery.create(""));

        System.out.println(species);
    }
}