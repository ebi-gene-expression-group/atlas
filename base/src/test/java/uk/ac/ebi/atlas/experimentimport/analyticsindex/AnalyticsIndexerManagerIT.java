package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
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