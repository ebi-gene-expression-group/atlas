package uk.ac.ebi.atlas.bioentity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class GenePageControllerIT {

    @Inject
    GenePageController subject;

    @Test
    public void bioentityProperties() throws Exception {
        bioentityProperties("ENSG00000005801", 8, true); //zinc finger
        bioentityProperties("ENSMUSG00000006386", 5, true);
        bioentityProperties("Sb01g008360");
    }

    private void bioentityProperties(String bioentityIdentifier){
        bioentityProperties(bioentityIdentifier, 3, false);
    }

    private void bioentityProperties(String bioentityIdentifier, int expectedMinimalSize, boolean expectGoTerms){
        Model model = new BindingAwareModelMap();
        subject.showGenePage(bioentityIdentifier, model);

        JsonArray bioentityProperties = new Gson().fromJson((String) model.asMap().get("bioentityProperties"), JsonArray.class);

        assertTrue(bioentityIdentifier+" should have properties" , bioentityProperties.size()>=expectedMinimalSize);

        Map<String, Integer> gotermsAndTheirRelevance = new HashMap<>();
        for(JsonElement e: bioentityProperties){
            if(e.getAsJsonObject().get("type").getAsString().equals("go")){
                for(JsonElement v: e.getAsJsonObject().get("values").getAsJsonArray()){
                    gotermsAndTheirRelevance.put(
                            v.getAsJsonObject().get("text").getAsString(),
                            v.getAsJsonObject().get("relevance").getAsInt()
                    );
                }
            }
        }
        if(expectGoTerms){
            assertThat(gotermsAndTheirRelevance.size(), Matchers.greaterThan(0));

            if(gotermsAndTheirRelevance.size()> 10){
                // not all the same relevance
                assertThat(new HashSet<>(gotermsAndTheirRelevance.values()).size(),Matchers.greaterThan(1) );
            }
        }

    }
}