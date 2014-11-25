package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileDeserializer;
import uk.ac.ebi.atlas.utils.ColourGradient;

import java.awt.*;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BaselineProfilesViewModelBuilderTest {

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final Factor ADIPOSE = new Factor(ORGANISM_PART, "adipose", new OntologyTerm("ontologyTerm"));
    private static final Factor ADRENAL = new Factor(ORGANISM_PART, "adrenal");
    private static final Factor BRAIN = new Factor(ORGANISM_PART, "brain");
    private static final Factor BREAST = new Factor(ORGANISM_PART, "breast");

    private static final String factorType = "organism part";
    private static final String factorValues = "adipose, adrenal, brain, breast";
    private static final String profileLines = "ENSG00000082258\tCCNT2\t0\t9\t5\t11\n" +
            "ENSG00000047315\tPOLR2B\tFAIL\t47\t0.3\t25";
    private static final double minExpressionLevel = 0.3;
    private static final double maxExpressionLevel = 47;

    private static final ImmutableList<BaselineProfile> baselineProfiles = BaselineProfileDeserializer.buildProfiles(factorType, factorValues, profileLines, 0.5D);

    private Color startColour = Color.LIGHT_GRAY;
    private Color endColour = Color.BLUE;
    private Color blankColour = Color.WHITE;
    private double colourScale = 1;
    private ColourGradient colorGradient = new ColourGradient(startColour, endColour, blankColour, colourScale);
    private BaselineExpressionViewModelBuilder baselineExpressionViewModelBuilder = new BaselineExpressionViewModelBuilder(colorGradient, new BaselineExpressionLevelRounder());
    private BaselineProfilesViewModelBuilder subject = new BaselineProfilesViewModelBuilder(baselineExpressionViewModelBuilder, new BaselineExpressionLevelRounder());
    private SortedSet<Factor> orderedFactors = ImmutableSortedSet.of(ADIPOSE, ADRENAL, BRAIN, BREAST);

    @Test
    public void buildProfilesViewModel() {
        BaselineProfileRowViewModel[] genes = subject.buildGenes(baselineProfiles, orderedFactors, minExpressionLevel, maxExpressionLevel);

        BaselineProfilesViewModel profiles = new BaselineProfilesViewModel<>(new BaselineExpressionLevelRounder(), 1.1, 2.2, 50, genes);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(profiles);

        String expected = "{\n" +
                "  \"minExpressionLevel\": 1.0,\n" +
                "  \"maxExpressionLevel\": 2.0,\n" +
                "  \"searchResultTotal\": 50,\n" +
                "  \"rows\": [\n" +
                "    {\n" +
                "      \"id\": \"ENSG00000082258\",\n" +
                "      \"name\": \"CCNT2\",\n" +
                "      \"expressions\": [\n" +
                "        {\n" +
                "          \"factorName\": \"adipose\",\n" +
                "          \"color\": \"\",\n" +
                "          \"value\": \"\",\n" +
                "          \"svgPathId\": \"ontologyTerm\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"factorName\": \"adrenal\",\n" +
                "          \"color\": \"#9697C0\",\n" +
                "          \"value\": \"9\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"factorName\": \"brain\",\n" +
                "          \"color\": \"#A9AAC0\",\n" +
                "          \"value\": \"5\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"factorName\": \"breast\",\n" +
                "          \"color\": \"#8D8DC0\",\n" +
                "          \"value\": \"11\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ENSG00000047315\",\n" +
                "      \"name\": \"POLR2B\",\n" +
                "      \"expressions\": [\n" +
                "        {\n" +
                "          \"factorName\": \"adipose\",\n" +
                "          \"color\": \"UNKNOWN\",\n" +
                "          \"value\": \"UNKNOWN\",\n" +
                "          \"svgPathId\": \"ontologyTerm\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"factorName\": \"adrenal\",\n" +
                "          \"color\": \"#0000FF\",\n" +
                "          \"value\": \"47\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"factorName\": \"brain\",\n" +
                "          \"color\": \"\",\n" +
                "          \"value\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"factorName\": \"breast\",\n" +
                "          \"color\": \"#4A4AC0\",\n" +
                "          \"value\": \"25\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        assertThat(json, is(expected));
    }

}