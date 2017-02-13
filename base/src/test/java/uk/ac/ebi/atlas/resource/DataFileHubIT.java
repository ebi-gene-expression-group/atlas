package uk.ac.ebi.atlas.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class DataFileHubIT {

    @Inject
    private DataFileHub subject;

    @Test
    public void testGetExperimentFiles() throws Exception {
        assertAtlasResourceExists(subject.getBaselineExperimentFiles("E-MTAB-513").main);
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").analysisMethods);
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").condensedSdrf);
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").experimentDesign);
    }

    @Test
    public void testGetDifferentialExperimentFiles() throws Exception {
        assertAtlasResourceExists(subject.getDifferentialExperimentFiles("E-GEOD-54705").analytics);
        assertAtlasResourceExists(subject.getDifferentialExperimentFiles("E-GEOD-54705").rawCounts);
    }

    private void assertAtlasResourceExists(AtlasResource<?> resource){
        assertThat(resource.exists(), is(true));
    }
}
