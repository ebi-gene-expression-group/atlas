package uk.ac.ebi.atlas.bioentity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import uk.ac.ebi.atlas.configuration.TestConfig;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class GenePageControllerIT {

    @Inject
    private GenePageController subject;

    private static final String BIOENTITY_IDENTIFIER = "ENSG00000005801"; // zinc finger
    private static final String BIOENTITY_IDENTIFIER_WITHOUT_GO = "ENSG00000253210";

    @Test
    public void bioentityProperties() {
        Model model = new BindingAwareModelMap();
        subject.showGenePage(BIOENTITY_IDENTIFIER, model);

        JsonArray bioentityProperties =
                GSON.fromJson((String) model.asMap().get("bioentityProperties"), JsonArray.class);

        assertTrue(
                BIOENTITY_IDENTIFIER + " should have properties",
                bioentityProperties.size() > 0);

        Map<String, Integer> gotermsAndTheirRelevance = new HashMap<>();
        for (JsonElement e: bioentityProperties) {
            if (e.getAsJsonObject().get("type").getAsString().equals("go")) {
                for (JsonElement v: e.getAsJsonObject().get("values").getAsJsonArray()) {
                    gotermsAndTheirRelevance.put(
                            v.getAsJsonObject().get("text").getAsString(),
                            v.getAsJsonObject().get("relevance").getAsInt()
                    );
                }
            }
        }

        assertThat(gotermsAndTheirRelevance.size(), greaterThan(0));

        if (gotermsAndTheirRelevance.size() > 10) {
            // not all the same relevance
            assertThat(new HashSet<>(gotermsAndTheirRelevance.values()).size(), greaterThan(1));
        }
    }

    @Test
    public void bioentitiyProertiesForGeneWithoutGoTerms() {
        Model model = new BindingAwareModelMap();
        subject.showGenePage(BIOENTITY_IDENTIFIER_WITHOUT_GO, model);

        JsonArray bioentityProperties =
                GSON.fromJson((String) model.asMap().get("bioentityProperties"), JsonArray.class);

        assertTrue(
                BIOENTITY_IDENTIFIER_WITHOUT_GO + " should have properties",
                bioentityProperties.size() > 0);
    }
}
