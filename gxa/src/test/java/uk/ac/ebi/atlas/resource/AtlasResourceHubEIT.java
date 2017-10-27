package uk.ac.ebi.atlas.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class AtlasResourceHubEIT {

    @Inject
    AtlasResourceHub subject;

    @Inject
    ExpressionAtlasExperimentTrader experimentTrader;

    @Test
    public void rnaSeqExperiments(){
        for(String accession : experimentTrader.getRnaSeqDifferentialExperimentAccessions()){
            DifferentialExperiment differentialExperiment = (DifferentialExperiment) experimentTrader.getPublicExperiment(accession);

            assertAboutResult(subject.contrastImages(differentialExperiment));
        }
    }

    @Test
    public void microarrayExperiments(){
        for(String accession : experimentTrader.getMicroarrayExperimentAccessions()){
            MicroarrayExperiment differentialExperiment = (MicroarrayExperiment) experimentTrader.getPublicExperiment(accession);

            assertAboutResult(subject.contrastImages(differentialExperiment));
        }
    }

    private void assertAboutResult(Map<String,JsonArray> result){
        for(Map.Entry<String, JsonArray> entryPerContrast: result.entrySet()){
            for(JsonElement e: entryPerContrast.getValue()){
                testResourceExists(e.getAsJsonObject().get("uri").getAsString());
            }
        }
    }

    private void testResourceExists(String resourceURI) {
       Response response =  new EndPoint("/gxa/" + resourceURI).getResponse();
       assertThat(response.getStatusCode(), is(200));
    }

}
