package uk.ac.ebi.atlas.profiles.differential.viewmodel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.utils.ColourGradient;

import java.awt.*;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DifferentialProfilesViewModelBuilderTest {

    //E-MTAB-1066
    private static final AssayGroup G2 = new AssayGroup("g2", "WT3", "WT1", "WT2");
    private static final AssayGroup G1 = new AssayGroup("g1", "C3", "C1", "C2");
    private static final AssayGroup G3 = new AssayGroup("g1", "K1", "K2", "K3");
    private static final Contrast G2_G1 = new Contrast("g2_g1", "A-AFFY-35", G2, G1, "cycC");
    private static final Contrast G2_G3 = new Contrast("g2_g3", "A-AFFY-35", G2, G3, "cdk8");

    private static final ImmutableSortedSet<Contrast> CONTRASTS = ImmutableSortedSet.of(G2_G1, G2_G3);

    //Gene ID	Gene Name	Design Element	g2_g3.p-value	g2_g3.t-statistic	g2_g3.log2foldchange	g2_g1.p-value	g2_g1.t-statistic	g2_g1.log2foldchange
    //FBgn0051624	CG31624	1630811_at	0.00226603492883074	-6.44757385549733	-0.761232333333333	0.000323957214317996	-8.79117189098254	-3.36862716666667
    //FBgn0053459	CG33459	1640410_at	0.000216315773519821	10.7092831659167	1.33047243333334	0.00212698465597433	6.03548553011526	0.714417566666667
    private static final MicroarrayExpression CG31624_G2_G3 = new MicroarrayExpression(0.00226603492883074, -0.761232333333333, -6.44757385549733, G2_G3);
    private static final MicroarrayExpression CG31624_G2_G1 = new MicroarrayExpression(0.000323957214317996, -8.79117189098254, -3.36862716666667, G2_G1);

    private static final MicroarrayExpression CG33459_G2_G3 = new MicroarrayExpression(0.000216315773519821, 1.33047243333334, 10.7092831659167, G2_G3);

    private static final MicroarrayProfile CG31624 = MicroarrayProfile.create("FBgn0051624", "CG31624", "1630811_at").add(CG31624_G2_G3).add(CG31624_G2_G1);
    private static final MicroarrayProfile CG33459 = MicroarrayProfile.create("FBgn0053459", "CG33459", "1640410_at").add(CG33459_G2_G3);

    private static final DifferentialProfilesList<MicroarrayProfile> diffProfiles = new DifferentialProfilesList(ImmutableList.of(CG31624, CG33459));

    private Color startColour = Color.LIGHT_GRAY;
    private Color endColour = Color.BLUE;
    private Color blankColour = Color.WHITE;
    private double colourScale = 1;
    private ColourGradient colorGradient = new ColourGradient(startColour, endColour, blankColour, colourScale);
    private PValueFormatter pValueFormatter = new PValueFormatter();
    private DifferentialProfilesViewModelBuilder subject = new DifferentialProfilesViewModelBuilder(colorGradient, pValueFormatter);
    private SortedSet<Contrast> orderedContrasts = ImmutableSortedSet.of(G2_G1, G2_G3);

    @Test
    public void buildProfilesViewModel() {
        diffProfiles.setTotalResultCount(50);
        //DifferentialProfileRowViewModel[] genes = subject.buildGenesJson(diffProfiles, orderedContrasts);

//        DifferentialProfilesViewModel profiles = new DifferentialProfilesViewModel(diffProfiles.getMinUpRegulatedExpressionLevel(), diffProfiles.getMaxUpRegulatedExpressionLevel(), diffProfiles.getMinDownRegulatedExpressionLevel(), diffProfiles.getMaxDownRegulatedExpressionLevel(), 50, genes);

        JsonObject actual = subject.build(diffProfiles, orderedContrasts);


        String expected = "{\n" +
                "  \"minUpLevel\": 1.3,\n" +
                "  \"maxUpLevel\": 1.3,\n" +
                "  \"searchResultTotal\": 50,\n" +
                "  \"rows\": [\n" +
                "    {\n" +
                "      \"id\": \"FBgn0051624\",\n" +
                "      \"name\": \"CG31624\",\n" +
                "      \"designElement\": \"1630811_at\",\n" +
                "      \"expressions\": [\n" +
                "        {\n" +
                "          \"contrastName\": \"cdk8\",\n" +
                "          \"color\": \"#C0C0C0\",\n" +
                "          \"foldChange\": \"-0.8\",\n" +
                "          \"pValue\": \"0.002\",\n" +
                "          \"tStat\": \"-6.45\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"contrastName\": \"cycC\",\n" +
                "          \"color\": \"#0000FF\",\n" +
                "          \"foldChange\": \"-8.8\",\n" +
                "          \"pValue\": \"3.24E-4\",\n" +
                "          \"tStat\": \"-3.37\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"FBgn0053459\",\n" +
                "      \"name\": \"CG33459\",\n" +
                "      \"designElement\": \"1640410_at\",\n" +
                "      \"expressions\": [\n" +
                "        {\n" +
                "          \"contrastName\": \"cdk8\",\n" +
                "          \"color\": \"#FF0000\",\n" +
                "          \"foldChange\": \"1.3\",\n" +
                "          \"pValue\": \"2.16E-4\",\n" +
                "          \"tStat\": \"10.71\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"contrastName\": \"cycC\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"minDownLevel\": -0.8,\n" +
                "  \"maxDownLevel\": -8.8\n" +
                "}";

        assertThat(actual,is(new JsonParser().parse(expected)));

    }

    @Test
    public void buildContrastsViewModel() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(CONTRASTS);

        String expected = "[\n" +
                "  {\n" +
                "    \"id\": \"g2_g3\",\n" +
                "    \"arrayDesignAccession\": \"A-AFFY-35\",\n" +
                "    \"referenceAssayGroup\": {\n" +
                "      \"id\": \"g2\",\n" +
                "      \"assayAccessions\": [\n" +
                "        \"WT3\",\n" +
                "        \"WT1\",\n" +
                "        \"WT2\"\n" +
                "      ],\n" +
                "      \"replicates\": 3\n" +
                "    },\n" +
                "    \"testAssayGroup\": {\n" +
                "      \"id\": \"g1\",\n" +
                "      \"assayAccessions\": [\n" +
                "        \"K1\",\n" +
                "        \"K2\",\n" +
                "        \"K3\"\n" +
                "      ],\n" +
                "      \"replicates\": 3\n" +
                "    },\n" +
                "    \"displayName\": \"cdk8\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"g2_g1\",\n" +
                "    \"arrayDesignAccession\": \"A-AFFY-35\",\n" +
                "    \"referenceAssayGroup\": {\n" +
                "      \"id\": \"g2\",\n" +
                "      \"assayAccessions\": [\n" +
                "        \"WT3\",\n" +
                "        \"WT1\",\n" +
                "        \"WT2\"\n" +
                "      ],\n" +
                "      \"replicates\": 3\n" +
                "    },\n" +
                "    \"testAssayGroup\": {\n" +
                "      \"id\": \"g1\",\n" +
                "      \"assayAccessions\": [\n" +
                "        \"C3\",\n" +
                "        \"C1\",\n" +
                "        \"C2\"\n" +
                "      ],\n" +
                "      \"replicates\": 3\n" +
                "    },\n" +
                "    \"displayName\": \"cycC\"\n" +
                "  }\n" +
                "]";
        assertThat(json, is(expected));

    }

}
