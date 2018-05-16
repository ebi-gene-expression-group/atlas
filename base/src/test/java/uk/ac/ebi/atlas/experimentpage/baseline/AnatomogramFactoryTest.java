package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.testutils.MockExperiment;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AnatomogramFactoryTest {

    @Test
    public void anatomogramShowsUpWhenThereAreOrganismParts() {
        ExperimentDesign experimentDesign = new ExperimentDesign();
        AssayGroup g1 = new AssayGroup("g1", "r1");
        AssayGroup g2 = new AssayGroup("g2", "r2");
        AssayGroup g3 = new AssayGroup("g3", "r3");
        AssayGroup g4 = new AssayGroup("g4", "r4");
        List<AssayGroup> assayGroups = ImmutableList.of(g1, g2, g3, g4);

        experimentDesign.putFactor("r1", AnatomogramFactory.factorTypeWithAnatomogram, "liver");
        experimentDesign.putFactor("r2", AnatomogramFactory.factorTypeWithAnatomogram, "liver");
        experimentDesign.putFactor("r3", AnatomogramFactory.factorTypeWithAnatomogram, "heart");
        experimentDesign.putFactor("r1", "other_type", "a");
        experimentDesign.putFactor("r2", "other_type", "b");
        experimentDesign.putFactor("r3", "other_type", "c");
        experimentDesign.putFactor("r4", "other_type", "d");

        BaselineExperiment experiment = MockExperiment.createBaselineExperiment(experimentDesign, assayGroups);

        assertThat(new AnatomogramFactory().get(ImmutableList.of(g1, g2, g3), experiment).isPresent(), is(true));
        assertThat(new AnatomogramFactory().get(ImmutableList.of(g1, g3), experiment).isPresent(), is(true));
        assertThat(new AnatomogramFactory().get(ImmutableList.of(g1, g2), experiment).isPresent(), is(true));
        assertThat(new AnatomogramFactory().get(ImmutableList.of(g1), experiment).isPresent(), is(true));
        assertThat(new AnatomogramFactory().get(ImmutableList.of(g4), experiment).isPresent(), is(false));
        assertThat(new AnatomogramFactory().get(ImmutableList.of(), experiment).isPresent(), is(false));
    }
}