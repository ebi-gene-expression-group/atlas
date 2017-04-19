package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.junit.Assert.fail;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/dbContext.xml"})
public class AnalyticsIndexerManagerIT {

    @Inject
    AnalyticsIndexerManager subject;

    @Test
    public void testIndexAllPublicExperiments() throws Exception {
        try {
            subject.addToAnalyticsIndex("E-PROT-1");
        } catch(Exception e){
            fail(e.getMessage());
        }
    }
}