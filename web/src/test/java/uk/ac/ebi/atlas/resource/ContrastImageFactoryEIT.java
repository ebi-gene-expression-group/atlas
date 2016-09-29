package uk.ac.ebi.atlas.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;
import uk.ac.ebi.atlas.acceptance.utils.URLBuilder;
import uk.ac.ebi.atlas.experimentpage.differential.GseaPlotsBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class ContrastImageFactoryEIT {

    @Inject
    ContrastImageFactory subject;

    @Inject
    GseaPlotsBuilder oldImpl;

    @Inject
    ExperimentTrader experimentTrader;


    @Test
    public void allLinksAreValid(){
        for(String accession : experimentTrader.getRnaSeqDifferentialExperimentAccessions()){
            DifferentialExperiment differentialExperiment = (DifferentialExperiment) experimentTrader.getPublicExperiment(accession);


            JsonObject result = subject.resourcesPerContrast(differentialExperiment);

            for(Map.Entry<String, JsonElement> entryPerContrast: result.entrySet()){
                for(JsonElement e: entryPerContrast.getValue().getAsJsonArray()){
                    testResourceExists(e.getAsJsonObject().get("uri").getAsString());
                }
            }
        }

    }



    public void testResourceExists(String resourceURI) {

       Response response =  new EndPoint("/gxa" + resourceURI).getResponse();


        assertThat(response.getStatusCode(), is(200));


    }


}
