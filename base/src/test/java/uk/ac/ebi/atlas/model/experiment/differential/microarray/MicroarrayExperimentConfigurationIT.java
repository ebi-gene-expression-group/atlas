package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class MicroarrayExperimentConfigurationIT {

    @Inject
    private ConfigurationTrader configurationTrader;

    private MicroarrayExperimentConfiguration subject;

    @Before
    public void setUp() throws Exception {
        subject = configurationTrader.getMicroarrayExperimentConfiguration("E-GEOD-13316");
    }

    @Test
    public void testGetArrayDesignNames() throws Exception {
        assertThat(subject.getArrayDesignAccessions().size(), greaterThan(0));
    }

    @Test
    public void testGetContrasts() throws Exception {
        List<Contrast> contrasts = subject.getContrasts();
        assertThat(contrasts.size(), greaterThan(0));
        for(Contrast contrast: contrasts){
            assertNotNull(contrast.getId());
            assertNotNull(contrast.getDisplayName());
            assertTrue(contrast.getReferenceAssayGroup().iterator().hasNext());
            assertTrue(contrast.getTestAssayGroup().iterator().hasNext());
        }
    }
}
