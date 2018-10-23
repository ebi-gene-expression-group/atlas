package uk.ac.ebi.atlas.bioentity;

import com.google.gson.JsonArray;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import uk.ac.ebi.atlas.configuration.TestConfig;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class GeneSetPageControllerIT {
    @Inject
    private GeneSetPageController subject;

    @ParameterizedTest
    @ValueSource(strings = {"GO:0016746", "R-HSA-73887", "PO:0005052"})
    void bioentityProperties(String bioentityIdentifier) {
        Model model = new BindingAwareModelMap();
        subject.showGeneSetPage(bioentityIdentifier, "", model);
        JsonArray bioentityProperties =
                GSON.fromJson((String) model.asMap().get("bioentityProperties"), JsonArray.class);
        assertThat(bioentityProperties.size(), is(1));
    }
}
