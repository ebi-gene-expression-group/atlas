package uk.ac.ebi.atlas.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
class AtlasResourceHubWIT {
    @Inject
    private AtlasResourceHub subject;

    @Inject
    private ExpressionAtlasExperimentTrader experimentTrader;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void rnaSeqExperiments() throws Exception {
        for (String accession : experimentTrader.getRnaSeqDifferentialExperimentAccessions()) {
            DifferentialExperiment differentialExperiment =
                    (DifferentialExperiment) experimentTrader.getPublicExperiment(accession);
            assertAboutResult(subject.contrastImages(differentialExperiment));
        }
    }

    @Test
    void microarrayExperiments() throws Exception {
        for (String accession : experimentTrader.getMicroarrayExperimentAccessions()) {
            MicroarrayExperiment differentialExperiment =
                    (MicroarrayExperiment) experimentTrader.getPublicExperiment(accession);
            assertAboutResult(subject.contrastImages(differentialExperiment));
        }
    }

    private void assertAboutResult(Map<String, JsonArray> result) throws Exception {
        for (Map.Entry<String, JsonArray> entryPerContrast : result.entrySet()) {
            for (JsonElement e : entryPerContrast.getValue()) {
                testResourceExists(e.getAsJsonObject().get("uri").getAsString());
            }
        }
    }

    private void testResourceExists(String resourceURI) throws Exception {
        this.mockMvc.perform(get("/" + resourceURI)).andExpect(status().isOk());
    }
}
