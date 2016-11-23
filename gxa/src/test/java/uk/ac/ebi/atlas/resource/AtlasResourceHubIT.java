package uk.ac.ebi.atlas.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.resource.ResourceType;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class AtlasResourceHubIT {

    @Inject
    AtlasResourceHub subject;


    @Inject
    ExpressionAtlasExperimentTrader experimentTrader;


    @Test
    public void dataHasRightFormatForRnaSeqExperiments(){
        for(String accession : experimentTrader.getRnaSeqDifferentialExperimentAccessions()){
            DifferentialExperiment differentialExperiment = (DifferentialExperiment) experimentTrader.getPublicExperiment(accession);

            assertAboutResult(subject.contrastImages(differentialExperiment));
        }
    }

    @Test
    public void dataHasRightFormatForMicroarrayExperiments(){
        for(String accession : experimentTrader.getMicroarrayExperimentAccessions()){
            MicroarrayExperiment differentialExperiment = (MicroarrayExperiment) experimentTrader.getPublicExperiment(accession);

            assertAboutResult(subject.contrastImages(differentialExperiment));
        }
    }

    @Test
    public void weSometimesHaveExtraInfoAndSometimesWeDoNot(){
        int countPositives = 0;
        int countNegatives = 0;
        for(String accession: experimentTrader.getAllBaselineExperimentAccessions()){
            Experiment experiment = experimentTrader.getPublicExperiment(accession);
            if(subject.hasExtraInfo(experiment)){
                countPositives++;
            } else {
                countNegatives++;
            }
        }
        for(String accession: experimentTrader.getRnaSeqDifferentialExperimentAccessions()){
            Experiment experiment = experimentTrader.getPublicExperiment(accession);
            if(subject.hasExtraInfo(experiment)){
                countPositives++;
            } else {
                countNegatives++;
            }
        }
        for(String accession: experimentTrader.getMicroarrayExperimentAccessions()){
            Experiment experiment = experimentTrader.getPublicExperiment(accession);
            if(subject.hasExtraInfo(experiment)){
                countPositives++;
            } else {
                countNegatives++;
            }
        }
        assertTrue(countPositives >0);
        assertTrue(countNegatives >0);
    }

     void assertAboutResult(Map<String, JsonArray> result){
        for(Map.Entry<String,JsonArray> e : result.entrySet()){
            assertTrue("Contrast: "+e.getKey(), e.getKey().matches("g\\d+_g\\d+"));

            for(JsonElement el : e.getValue().getAsJsonArray()){
                assertTrue(el.getAsJsonObject().has("type"));
                assertTrue(el.getAsJsonObject().has("uri"));
                try{
                    ResourceType.forFileName(el.getAsJsonObject().get("type").getAsString());
                }catch(Exception exc){
                    fail(exc.getMessage());
                }
            }
        }
    }
}