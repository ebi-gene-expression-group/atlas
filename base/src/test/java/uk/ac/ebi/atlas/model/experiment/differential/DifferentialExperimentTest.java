package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesignTest;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialExperimentTest {
    private static final String PUBMEDID = "PUBMEDID";
    private static final String DOI = "100.100/doi";

    private DifferentialExperiment subject;

    AssayGroup referenceAssay1 = new AssayGroup("g1", "assay1");
    AssayGroup testAssay1 = new AssayGroup("g2", "assay2");
    AssayGroup referenceAssay2 = new AssayGroup("g3", "assay3");
    AssayGroup testAssay2 = new AssayGroup("g4", "assay41", "assay42");

    Contrast c1 = new Contrast("g1_g2", null, referenceAssay1, testAssay1, "first contrast");
    Contrast c2 = new Contrast("g3_g4", null, referenceAssay2, testAssay2, "second contrast");

    public static DifferentialExperiment mockExperiment(String accession, List<Contrast> contrasts) {
        return mockExperiment(
                accession,
                contrasts,
                ExperimentDesignTest.mockExperimentDesign(
                        contrasts.stream()
                                 .flatMap(contrast ->
                                          Stream.of(contrast.getTestAssayGroup(), contrast.getReferenceAssayGroup()))
                                 .collect(toList())));
    }

    public static DifferentialExperiment mockExperiment(String accession, List<Contrast> contrasts, ExperimentDesign experimentDesign){
        return new DifferentialExperiment(
                accession,
                new Date(),
                contrasts.stream().map(contrast -> Pair.of(contrast, true)).collect(toList()),
                "description", new Species("species", SpeciesProperties.UNKNOWN), Sets.newHashSet(PUBMEDID),
                Sets.newHashSet(DOI), experimentDesign);
    }

    @Before
    public void setUp() throws Exception {
        subject = mockExperiment("accession",ImmutableList.of(c1, c2));
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
        assertThat(subject.getAnalysedAssays(), hasItems("assay1", "assay2", "assay3","assay41", "assay42"));
    }

    @Test
    public void testGetPubMedIds() {
        assertThat((Iterable<String>) subject.getPubMedIds(), contains(PUBMEDID));
    }

    @Test
    public void testGetDois() {
        assertThat(subject.getDois(), contains(DOI));
    }

    @Test
    public void groupingsForHeatmapIncludeComparisonName() {
        DifferentialExperiment experiment = mockExperiment("accession", ImmutableList.of(c1), ExperimentDesignTest.mockExperimentDesign(ImmutableList.of(referenceAssay1, testAssay1)));
        JsonArray result = experiment.groupingsForHeatmap();

        assertThat(result.size(), greaterThan(0));
        assertThat(result.get(0).getAsJsonObject().get("name").getAsString(), equalToIgnoringCase("comparison_name"));
    }

    @Test
    public void sampleCharacteristicsThatArePerWholeContrastShowUpAsTestThenReference() {
        ExperimentDesign experimentDesign = new ExperimentDesign();

        experimentDesign.putFactor(referenceAssay1.getFirstAssayAccession(), "infect", "totally_normal");
        experimentDesign.putFactor(testAssay1.getFirstAssayAccession(), "infect", "very_disease");

        DifferentialExperiment experiment = mockExperiment("accession", ImmutableList.of(c1), experimentDesign);
        JsonArray result = experiment.groupingsForHeatmap();

        assertThat(result.size(), is(2));

        String stringDump = GSON.toJson(result);

        assertThat(stringDump.indexOf("very_disease"), lessThan(stringDump.indexOf("totally_normal")));
    }

}