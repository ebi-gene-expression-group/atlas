package uk.ac.ebi.atlas.bioentity;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import uk.ac.ebi.atlas.bioentity.properties.PropertyLink;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    }

    private void bioentityProperties(String bioentityIdentifier){
        bioentityProperties(bioentityIdentifier, 3, false);
    }

    private void bioentityProperties(String bioentityIdentifier, int expectedMinimalSize, boolean expectGoTerms){
        Model model = new BindingAwareModelMap();
        subject.showGenePage(bioentityIdentifier, model);

        Map<BioentityPropertyName, List<PropertyLink>> bioentityProperties =
                (Map<BioentityPropertyName, List<PropertyLink>>) model.asMap().get("bioentityProperties");

        assertTrue(bioentityIdentifier+" should have properties" , bioentityProperties.size()>=expectedMinimalSize);

        Map<String, String> gotermsAndTheirRelevance = new HashMap<>();

        for (Map.Entry<BioentityPropertyName, List<PropertyLink>> entry : bioentityProperties.entrySet()) {

            BioentityPropertyName name = entry.getKey();
            List<PropertyLink> values = entry.getValue();

            if (name.name().equals(BioentityPropertyName.GO.name())) {
                for (PropertyLink propertyLink : values) {
                    gotermsAndTheirRelevance.put(propertyLink.getText(), propertyLink.getUrl());
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