package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.net.URI;
import java.text.MessageFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class ExternallyAvailableContentControllerEIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternallyAvailableContentControllerEIT.class);
    @Inject
    ExpressionAtlasExperimentTrader experimentTrader;

    private EndPoint endPointForExperiment(String accession){
        return new EndPoint(MessageFormat.format("/gxa/json/experiments/{0}/resources", accession));
    }

    void testAllResourcesAreNonempty(String accession) throws Exception {
        JsonArray response = endPointForExperiment(accession).getJsonResponse().getAsJsonArray();

        assertThat(response.size(), greaterThan(0));

        for(JsonElement e: response){
            String uri = e.getAsJsonObject().get("uri").getAsString();

            if(!uri.contains("www.ebi.ac.uk")) {
                LOGGER.info(uri);
                Response r = RestAssured.get(URI.create(uri).toURL());
                assertThat(uri, r.getStatusCode(), is(200));
            }

        }
    }

    @Test
    public void shouldReturnSomeResourcesForEachExperiment() throws Exception {
        for(String accession : experimentTrader.getPublicExperimentAccessions(
                ExperimentType.RNASEQ_MRNA_BASELINE, ExperimentType.PROTEOMICS_BASELINE,
                ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, ExperimentType.MICROARRAY_ANY)){
            testAllResourcesAreNonempty(accession);
        }
    }

}