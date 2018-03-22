package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class AnalyticsIndexerManagerIT {
    @Inject
    private AnalyticsIndexerManager subject;

    @Test
    public void indexProteomicsExperiment() {
        subject.addToAnalyticsIndex("E-PROT-1");
    }
}