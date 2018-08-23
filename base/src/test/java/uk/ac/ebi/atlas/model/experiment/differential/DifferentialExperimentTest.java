package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesignTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static uk.ac.ebi.atlas.testutils.MockExperiment.createDifferentialExperiment;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialExperimentTest {
    private static final String PUBMEDID = "PUBMEDID";
    private static final String DOI = "100.100/doi";

    private AssayGroup referenceAssay1 = new AssayGroup("g1", "assay1");
    private AssayGroup testAssay1 = new AssayGroup("g2", "assay2");
    private AssayGroup referenceAssay2 = new AssayGroup("g3", "assay3");
    private AssayGroup testAssay2 = new AssayGroup("g4", "assay41", "assay42");

    private Contrast c1 = new Contrast("g1_g2", null, referenceAssay1, testAssay1, "first contrast");
    private Contrast c2 = new Contrast("g3_g4", null, referenceAssay2, testAssay2, "second contrast");

    private DifferentialExperiment subject;

    @Before
    public void setUp() throws Exception {
        subject = createDifferentialExperiment("accession", ImmutableList.of(c1, c2));
    }

    @Test
    public void testGetContrasts() {
        assertThat(subject.getDataColumnDescriptors(), hasItems(c1, c2));
    }

    @Test
    public void testGetContrast() {
        assertThat(subject.getDataColumnDescriptor("g1_g2"), is(c1));
        assertThat(subject.getDataColumnDescriptor("g3_g4"), is(c2));
    }

    @Test
    public void testGetAssayAccessions() {
        assertThat(subject.getAnalysedAssays(), hasItems("assay1", "assay2", "assay3", "assay41", "assay42"));
    }

    @Test
    public void testGetPubMedIds() {
        assertThat(subject.getPubMedIds(), contains(PUBMEDID));
    }

    @Test
    public void testGetDois() {
        assertThat(subject.getDois(), contains(DOI));
    }

    @Test
    public void groupingsForHeatmapIncludeComparisonName() {
        DifferentialExperiment experiment =
                createDifferentialExperiment(
                        "accession",
                        ImmutableList.of(c1),
                        ExperimentDesignTest.mockExperimentDesign(ImmutableList.of(referenceAssay1, testAssay1)));
        JsonArray result = experiment.groupingsForHeatmap();

        assertThat(result.size(), greaterThan(0));
        assertThat(result.get(0).getAsJsonObject().get("name").getAsString(), equalToIgnoringCase("comparison_name"));
    }

    @Test
    public void sampleCharacteristicsThatArePerWholeContrastShowUpAsTestThenReference() {
        ExperimentDesign experimentDesign = new ExperimentDesign();

        experimentDesign.putFactor(referenceAssay1.getFirstAssayAccession(), "infect", "totally_normal");
        experimentDesign.putFactor(testAssay1.getFirstAssayAccession(), "infect", "very_disease");

        DifferentialExperiment experiment =
                createDifferentialExperiment("accession", ImmutableList.of(c1), experimentDesign);
        JsonArray result = experiment.groupingsForHeatmap();

        assertThat(result.size(), is(2));

        String stringDump = GSON.toJson(result);

        assertThat(stringDump.indexOf("very_disease"), lessThan(stringDump.indexOf("totally_normal")));
    }

    @Test
    public void blah() {
        AssayGroup referenceAssayGroup = new AssayGroup("g1", "assay1_1");
        AssayGroup testAssayGroup1 = new AssayGroup("g2", "assay2_1", "assay2_2");
        AssayGroup testAssayGroup2 = new AssayGroup("g3", "assay3_1", "assay3_2");

        Contrast c1 = new Contrast("g1_g2", null, referenceAssayGroup, testAssayGroup1, "'Reference' vs 'Test1'");
        Contrast c2 = new Contrast("g1_g3", null, referenceAssayGroup, testAssayGroup2, "'Reference' vs 'Test2'");

        subject = createDifferentialExperiment("accession", ImmutableList.of(c1, c2));

        JsonObject expectedResults1 = new JsonObject();
        expectedResults1.addProperty("contrastName", "'Reference' vs 'Test1'");
        expectedResults1.addProperty("referenceOrTest", "reference");

        JsonObject expectedResults2 = new JsonObject();
        expectedResults2.addProperty("contrastName", "'Reference' vs 'Test2'");
        expectedResults2.addProperty("referenceOrTest", "reference");


        assertThat(subject.propertiesForAssayV2("assay1_1").get(0), is(equalTo(expectedResults1)));
        assertThat(subject.propertiesForAssayV2("assay1_1").get(1), is(equalTo(expectedResults2)));
        assertThat(subject.propertiesForAssayV2("assay1_1").size(), is(equalTo(2)));
    }
}
