package uk.ac.ebi.atlas.bioentity;

import com.google.gson.JsonArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import uk.ac.ebi.atlas.configuration.WebConfig;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class GeneSetPageControllerIT {

    @Inject
    private GeneSetPageController subject;

    @Test
    public void bioentityPropertiesOfVariousTypes() {
        bioentityProperties("GO:0016746");
        bioentityProperties("R-HSA-73887");
        bioentityProperties("PO:0009030");
    }

    private void bioentityProperties(String bioentityIdentifier) {
        Model model = new BindingAwareModelMap();
        subject.showGeneSetPage(bioentityIdentifier, "", model);
        JsonArray bioentityProperties =
                GSON.fromJson((String) model.asMap().get("bioentityProperties"), JsonArray.class);
        assertThat(bioentityProperties.size(), is(1));
    }

}