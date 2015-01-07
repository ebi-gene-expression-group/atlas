package uk.ac.ebi.atlas.experimentimport.analytics.index;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class AnalyticsIndexerServiceIT {

    @Inject
    private AnalyticsIndexerService subject;

    @Test
    public void indexBaselineExperimentAnalytics() {
        subject.deleteExperimentFromIndex("E-MTAB-2039");
        subject.indexBaselineExperimentAnalytics("E-MTAB-2039");
    }


}