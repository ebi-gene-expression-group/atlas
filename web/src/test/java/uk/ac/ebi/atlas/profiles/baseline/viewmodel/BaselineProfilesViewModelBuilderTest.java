package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.springframework.util.StringUtils;
import uk.ac.ebi.atlas.model.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileDeserializer;
import uk.ac.ebi.atlas.utils.ColourGradient;
import uk.ac.ebi.atlas.utils.NumberUtils;

import java.awt.*;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BaselineProfilesViewModelBuilderTest {

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final Factor ADIPOSE = new Factor(ORGANISM_PART, "adipose", "ontologyTerm");
    private static final Factor ADRENAL = new Factor(ORGANISM_PART, "adrenal");
    private static final Factor BRAIN = new Factor(ORGANISM_PART, "brain");
    private static final Factor BREAST = new Factor(ORGANISM_PART, "breast");

    private static final AssayGroupFactor G1_ADIPOSE = new AssayGroupFactor("g1", ADIPOSE);
    private static final AssayGroupFactor G2_ADRENAL = new AssayGroupFactor("g2", ADRENAL);
    private static final AssayGroupFactor G3_BRAIN = new AssayGroupFactor("g3", BRAIN);
    private static final AssayGroupFactor G4_BREAST = new AssayGroupFactor("g4", BREAST);

    private static final ImmutableSortedSet<AssayGroupFactor> ASSAY_GROUP_FACTORS = ImmutableSortedSet.of(G1_ADIPOSE, G2_ADRENAL, G3_BRAIN, G4_BREAST);

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
    private BaselineExpressionViewModelBuilder baselineExpressionViewModelBuilder = new BaselineExpressionViewModelBuilder(colorGradient, new NumberUtils());
    private BaselineProfilesViewModelBuilder subject = new BaselineProfilesViewModelBuilder(baselineExpressionViewModelBuilder, new NumberUtils());
    private SortedSet<Factor> orderedFactors = ImmutableSortedSet.of(ADIPOSE, ADRENAL, BRAIN, BREAST);

    @Test
    public void buildProfilesViewModel() {
        BaselineProfileRowViewModel[] genes = subject.buildGenes(baselineProfiles, orderedFactors, minExpressionLevel, maxExpressionLevel);

        BaselineProfilesViewModel profiles = new BaselineProfilesViewModel<>(new NumberUtils(), 1.1, 2.2, 50, genes);

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

    @Test
    public void buildAssayGroupFactorsViewModel() {
        Gson gson = new Gson();
        String json = gson.toJson(ASSAY_GROUP_FACTORS);

        String expected = "[\n" +
                "  {\n" +
                "    \"assayGroupId\": \"g1\",\n" +
                "    \"factor\": {\n" +
                "      \"header\": \"ORGANISM_PART\",\n" +
                "      \"type\": \"ORGANISM_PART\",\n" +
                "      \"value\": \"adipose\",\n" +
                "      \"valueOntologyTermId\": \"ontologyTerm\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"assayGroupId\": \"g2\",\n" +
                "    \"factor\": {\n" +
                "      \"header\": \"ORGANISM_PART\",\n" +
                "      \"type\": \"ORGANISM_PART\",\n" +
                "      \"value\": \"adrenal\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"assayGroupId\": \"g3\",\n" +
                "    \"factor\": {\n" +
                "      \"header\": \"ORGANISM_PART\",\n" +
                "      \"type\": \"ORGANISM_PART\",\n" +
                "      \"value\": \"brain\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"assayGroupId\": \"g4\",\n" +
                "    \"factor\": {\n" +
                "      \"header\": \"ORGANISM_PART\",\n" +
                "      \"type\": \"ORGANISM_PART\",\n" +
                "      \"value\": \"breast\"\n" +
                "    }\n" +
                "  }\n" +
                "]";

        assertThat(json, is(StringUtils.trimAllWhitespace(expected)));

    }

}