package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.hamcrest.Matchers;
import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesignTest;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RichFactorGroupTest {

    @Test
    public void smallCase() throws Exception {


        FactorGroup fg1 = new FactorSet().add(new Factor("T1", "v1"));
        FactorGroup fg2 = new FactorSet().add(new Factor("T1", "v2"));

        assertThat(RichFactorGroup.typesWithCommonValues(ImmutableList.of(fg1)),
                is((Set<String>) ImmutableSet.of("T1")));
        assertThat(RichFactorGroup.typesWithCommonValues(ImmutableList.of(fg1, fg1)),
                is((Set<String>) ImmutableSet.of("T1")));
        assertThat(RichFactorGroup.typesWithCommonValues(ImmutableList.of(fg1, fg2)),
                is((Set<String>) ImmutableSet.<String>of()));

        assertThat(RichFactorGroup.filterOutTypesWithCommonValues(ImmutableList.of("T1"),ImmutableList.of(fg1) ),
                is((List<String>)ImmutableList.<String>of()));
        assertThat(RichFactorGroup.filterOutTypesWithCommonValues(ImmutableList.of("T1"),ImmutableList.of(fg1, fg1) ),
                is((List<String>)ImmutableList.<String>of()));
        assertThat(RichFactorGroup.filterOutTypesWithCommonValues(ImmutableList.of("T1"),ImmutableList.of(fg1, fg2) ),
                is((List<String>)ImmutableList.of("T1")));
    }

    @Test
    public void twoFactorCase() throws Exception {
        FactorGroup fg1 = new FactorSet().add(new Factor("T1", "v1")).add(new Factor("T2", "v2"));
        FactorGroup fg2 = new FactorSet().add(new Factor("T1", "v1")).add(new Factor("T2", "v3"));

        assertThat(RichFactorGroup.typesWithCommonValues(ImmutableList.of(fg1, fg2)),
                is((Set<String>) ImmutableSet.of("T1")));

        assertThat(RichFactorGroup.filterOutTypesWithCommonValues(ImmutableList.of("T1", "T2"),ImmutableList.of(fg1, fg2) ),
                is((List<String>)ImmutableList.of("T2")));
    }
}