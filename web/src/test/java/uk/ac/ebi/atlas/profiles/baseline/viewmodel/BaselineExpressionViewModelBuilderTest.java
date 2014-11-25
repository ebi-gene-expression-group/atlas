package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import com.google.common.collect.ImmutableSortedSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.utils.ColourGradient;

import java.awt.*;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BaselineExpressionViewModelBuilderTest {

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final Factor ADIPOSE = new Factor(ORGANISM_PART, "adipose", new OntologyTerm("ontologyTerm"));
    private static final Factor ADRENAL = new Factor(ORGANISM_PART, "adrenal");
    private static final Factor BRAIN = new Factor(ORGANISM_PART, "brain");
    private static final Factor BREAST = new Factor(ORGANISM_PART, "breast");

    private static final String NT = "NT";
    private static final double minExpressionLevel = 0.3;
    private static final double maxExpressionLevel = 47;

    private Color startColour = Color.LIGHT_GRAY;
    private Color endColour = Color.BLUE;
    private Color blankColour = Color.WHITE;
    private double colourScale = 1;
    private ColourGradient colorGradient = new ColourGradient(startColour, endColour, blankColour, colourScale);

    private BaselineExpressionViewModelBuilder subject = new BaselineExpressionViewModelBuilder(colorGradient, new BaselineExpressionLevelRounder());
    private SortedSet<Factor> orderedFactors = ImmutableSortedSet.of(ADIPOSE, ADRENAL, BRAIN, BREAST);


    @Test
    public void buildExpressionViewModel () {
        BaselineProfile profile = new BaselineProfile("Factor_ID", "Factor_NAME");
        profile.add("ORGANISM_PART", new BaselineExpression(NT, new FactorSet(ADIPOSE)));
        profile.add("ORGANISM_PART", new BaselineExpression(0.3, new FactorSet(ADRENAL)));

        BaselineExpressionViewModel[] expressions = subject.buildExpressions(profile, orderedFactors, minExpressionLevel, maxExpressionLevel);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(expressions);

        String expected = "[\n"  +
            "  {\n" +
            "    \"factorName\": \"adipose\",\n" +
            "    \"color\": \"\",\n" +
            "    \"value\": \"NT\",\n" +
            "    \"svgPathId\": \"ontologyTerm\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"factorName\": \"adrenal\",\n" +
            "    \"color\": \"#C0C0C0\",\n" +
            "    \"value\": \"0.3\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"factorName\": \"brain\",\n" +
            "    \"color\": \"\",\n" +
            "    \"value\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"factorName\": \"breast\",\n" +
            "    \"color\": \"\",\n" +
            "    \"value\": \"\"\n" +
            "  }\n" +
        "]";


        assertThat(json, is(expected));
    }

}