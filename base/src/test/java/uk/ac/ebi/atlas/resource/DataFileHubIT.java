package uk.ac.ebi.atlas.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class DataFileHubIT {

    @Inject
    DataFileHub subject;

    @Test
    public void testGetExperimentFiles() throws Exception {
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").main);
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").analysisMethods);
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").condensedSdrf);
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").experimentDesign);
    }

    @Test
    public void testGetDifferentialExperimentFiles() throws Exception {
        assertAtlasResourceExists(subject.getDifferentialExperimentFiles("E-GEOD-54705").analytics);
        assertAtlasResourceExists(subject.getDifferentialExperimentFiles("E-GEOD-54705").rawCounts);
    }

    void assertAtlasResourceExists(AtlasResource<?> resource){
        assertTrue(resource.toString(), resource.exists());
    }
}