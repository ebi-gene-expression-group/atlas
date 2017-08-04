package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.text.MessageFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class ExternallyAvailableContentControllerWIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternallyAvailableContentControllerWIT.class);

    @Inject
    ExpressionAtlasExperimentTrader experimentTrader;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    private EndPoint endPointForExperiment(String accession, ExternallyAvailableContent.ContentType contentType){
        return new EndPoint(MessageFormat.format("/gxa/json/experiments/{0}/resources/{1}", accession, contentType));
    }

    void testAllResourcesAreNonemptyAndContainValidLinks(String accession, ExternallyAvailableContent.ContentType contentType, boolean expectNonempty) throws Exception {
        JsonArray response = endPointForExperiment(accession, contentType).getJsonResponse().getAsJsonArray();

        if(expectNonempty){
            assertThat(response.size(), greaterThan(0));
        }


        for(JsonElement e: response){
            String uri = e.getAsJsonObject().get("url").getAsString();

            if(!uri.contains("www.ebi.ac.uk")) {
                LOGGER.info(uri);
                this.mockMvc.perform(get("/" + uri)).andExpect(status().isOk());
            }
        }
    }

    @Test
    public void shouldReturnSomeResourcesForEachExperiment() throws Exception {
        for(String accession : experimentTrader.getPublicExperimentAccessions(
                ExperimentType.RNASEQ_MRNA_BASELINE, ExperimentType.PROTEOMICS_BASELINE,
                ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, ExperimentType.MICROARRAY_ANY)){
            testAllResourcesAreNonemptyAndContainValidLinks(accession, ExternallyAvailableContent.ContentType.DATA, true);
            testAllResourcesAreNonemptyAndContainValidLinks(accession, ExternallyAvailableContent.ContentType.SUPPLEMENTARY_INFORMATION, true);
        }
        for(String accession : experimentTrader.getPublicExperimentAccessions(
                ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, ExperimentType.MICROARRAY_ANY)){
            testAllResourcesAreNonemptyAndContainValidLinks(accession, ExternallyAvailableContent.ContentType.PLOTS, false);
        }
    }

}