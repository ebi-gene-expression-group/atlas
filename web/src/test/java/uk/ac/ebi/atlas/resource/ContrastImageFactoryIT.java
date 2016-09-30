package uk.ac.ebi.atlas.resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.resource.ResourceType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class ContrastImageFactoryIT {

    @Inject
    ContrastImageFactory subject;


    @Inject
    ExperimentTrader experimentTrader;


    @Test
    public void dataHasRightFormatForRnaSeqExperiments(){
        for(String accession : experimentTrader.getRnaSeqDifferentialExperimentAccessions()){
            DifferentialExperiment differentialExperiment = (DifferentialExperiment) experimentTrader.getPublicExperiment(accession);

            JsonObject result = subject.resourcesPerContrast(differentialExperiment);
            assertAboutResult(result);
        }
    }

    @Test
    public void dataHasRightFormatForMicroarrayExperiments(){
        for(String accession : experimentTrader.getMicroarrayExperimentAccessions()){
            MicroarrayExperiment differentialExperiment = (MicroarrayExperiment) experimentTrader.getPublicExperiment(accession);

            JsonObject result = subject.resourcesPerContrast(differentialExperiment);
            assertAboutResult(result);
        }
    }

     void assertAboutResult(JsonObject result){
        for(Map.Entry<String,JsonElement> e : result.entrySet()){
            assertTrue("Contrast: "+e.getKey(), e.getKey().matches("g\\d+_g\\d+"));

            for(JsonElement el : e.getValue().getAsJsonArray()){
                assertTrue(el.getAsJsonObject().has("type"));
                assertTrue(el.getAsJsonObject().has("uri"));
                try{
                    ResourceType.valueOf(el.getAsJsonObject().get("type").getAsString());
                }catch(Exception exc){
                    fail(exc.getMessage());
                }
            }
        }
    }
}