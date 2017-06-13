package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesignTest;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialExperimentTest {

    private static final String PUBMEDID = "PUBMEDID";

    private DifferentialExperiment subject;

    AssayGroup referenceAssay1 = new AssayGroup("g1", "assay1");
    AssayGroup testAssay1 = new AssayGroup("g2", "assay2");
    AssayGroup referenceAssay2 = new AssayGroup("g3", "assay3");
    AssayGroup testAssay2 = new AssayGroup("g4", "assay41", "assay42");

    Contrast c1 = new Contrast("g1_g2", null, referenceAssay1, testAssay1, "first contrast");
    Contrast c2 = new Contrast("g3_g4", null, referenceAssay2, testAssay2, "second contrast");

    public static DifferentialExperiment mockExperiment(String accession, List<Contrast> contrasts){
        return mockExperiment(accession, contrasts, ExperimentDesignTest.mockExperimentDesign(FluentIterable.from(contrasts).transformAndConcat(new Function<Contrast, Iterable<AssayGroup>>() {
            @Nullable
            @Override
            public Iterable<AssayGroup> apply(@Nullable Contrast contrast) {
                return ImmutableList.of(contrast.getTestAssayGroup(), contrast.getReferenceAssayGroup());
            }
        }).toList()));
    }

    static DifferentialExperiment mockExperiment(String accession, List<Contrast> contrasts, ExperimentDesign experimentDesign){
        return new DifferentialExperiment(accession, new Date(), FluentIterable.from(contrasts).transform(new Function<Contrast, Pair<Contrast,Boolean>>() {
            @Override
            public Pair<Contrast, Boolean> apply(Contrast contrast) {
                return Pair.of(contrast, true);
            }
        }).toList(),
                "description", new Species("species", SpeciesProperties.UNKNOWN), Sets.newHashSet(PUBMEDID),
                experimentDesign);
    }

    @Before
    public void setUp() throws Exception {
        subject = mockExperiment("accession",ImmutableList.of(c1, c2));
    }

    @Test
    public void testGetContrasts() throws Exception {
        assertThat(subject.getDataColumnDescriptors(), hasItems(c1, c2));
    }

    @Test
    public void testGetContrast() throws Exception {
        assertThat(subject.getDataColumnDescriptor("g1_g2"), is(c1));
        assertThat(subject.getDataColumnDescriptor("g3_g4"), is(c2));
    }

    @Test
    public void testGetAssayAccessions() throws Exception {
        assertThat(subject.getAnalysedRowsAccessions(), hasItems("assay1", "assay2", "assay3","assay41", "assay42"));
    }

    @Test
    public void testGetPubMedIds() throws Exception {
        assertThat((Iterable<String>) subject.getAttributes().get("pubMedIds"), contains(PUBMEDID));
    }

    @Test
    public void groupingsForHeatmapIncludeComparisonName(){
        DifferentialExperiment experiment = mockExperiment("accession", ImmutableList.of(c1), ExperimentDesignTest.mockExperimentDesign(ImmutableList.of(referenceAssay1, testAssay1)));
        JsonArray result = experiment.groupingsForHeatmap();

        assertThat(result.size(), greaterThan(0));
        assertThat(result.get(0).getAsJsonObject().get("name").getAsString(), equalToIgnoringCase("comparison_name"));
    }

    @Test
    public void sampleCharacteristicsThatArePerWholeContrastShowUpAsTestThenReference(){
        ExperimentDesign experimentDesign = new ExperimentDesign();

        experimentDesign.putFactor(referenceAssay1.getFirstAssayAccession(), "infect", "totally_normal");
        experimentDesign.putFactor(testAssay1.getFirstAssayAccession(), "infect", "very_disease");

        DifferentialExperiment experiment = mockExperiment("accession", ImmutableList.of(c1), experimentDesign);
        JsonArray result = experiment.groupingsForHeatmap();

        assertThat(result.size(), is(2));

        String stringDump = new Gson().toJson(result);

        assertThat(stringDump.indexOf("very_disease"), lessThan(stringDump.indexOf("totally_normal")));
    }

}