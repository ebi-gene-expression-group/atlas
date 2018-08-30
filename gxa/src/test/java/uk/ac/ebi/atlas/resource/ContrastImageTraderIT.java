package uk.ac.ebi.atlas.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;
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
@ContextConfiguration(classes = WebConfig.class)
public class ContrastImageTraderIT {
    @Inject
    private ContrastImageTrader subject;

    @Inject
    private ExpressionAtlasExperimentTrader experimentTrader;

    @Test
    public void dataHasRightFormatForRnaSeqExperiments() {
        for (String accession : experimentTrader.getRnaSeqDifferentialExperimentAccessions()) {
            DifferentialExperiment differentialExperiment =
                    (DifferentialExperiment) experimentTrader.getPublicExperiment(accession);

            assertAboutResult(subject.contrastImages(differentialExperiment));
        }
    }

    @Test
    public void dataHasRightFormatForMicroarrayExperiments() {
        for (String accession : experimentTrader.getMicroarrayExperimentAccessions()) {
            MicroarrayExperiment differentialExperiment =
                    (MicroarrayExperiment) experimentTrader.getPublicExperiment(accession);

            assertAboutResult(subject.contrastImages(differentialExperiment));
        }
    }

    private void assertAboutResult(Map<String, JsonArray> result) {
        for (Map.Entry<String, JsonArray> entry : result.entrySet()) {
            assertTrue("Contrast: " + entry.getKey(), entry.getKey().matches("g\\d+_g\\d+"));

            for (JsonElement element : entry.getValue().getAsJsonArray()) {
                assertTrue(element.getAsJsonObject().has("type"));
                assertTrue(element.getAsJsonObject().has("uri"));
                try {
                    ResourceType.forFileName(element.getAsJsonObject().get("type").getAsString());
                } catch (Exception e) {
                    fail(e.getMessage());
                }
            }
        }
    }
}
