package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class RichFactorGroupTest {

    @Test
    public void smallCase()  {
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
    public void twoFactorCase()  {
        FactorGroup fg1 = new FactorSet().add(new Factor("T1", "v1")).add(new Factor("T2", "v2"));
        FactorGroup fg2 = new FactorSet().add(new Factor("T1", "v1")).add(new Factor("T2", "v3"));

        assertThat(RichFactorGroup.typesWithCommonValues(ImmutableList.of(fg1, fg2)),
                is((Set<String>) ImmutableSet.of("T1")));

        assertThat(RichFactorGroup.filterOutTypesWithCommonValues(ImmutableList.of("T1", "T2"),ImmutableList.of(fg1, fg2) ),
                is((List<String>)ImmutableList.of("T2")));
    }

    @Test
    public void factorTypesMustBeNormalized()  {
        FactorGroup fg1 = new FactorSet().add(new Factor("type 1", "v1")).add(new Factor("type 2", "v2"));
        FactorGroup fg2 = new FactorSet().add(new Factor("type 1", "v1")).add(new Factor("type 2", "v3"));

        assertThat(RichFactorGroup.filterOutTypesWithCommonValues(ImmutableList.of("type 1", "type 2"),ImmutableList.of(fg1, fg2) ),
                is(not((List<String>)ImmutableList.of(Factor.normalize("type 2")))));

        assertThat(RichFactorGroup.filterOutTypesWithCommonValues(
                ImmutableList.of(Factor.normalize("type 1"), Factor.normalize("type 2")), ImmutableList.of(fg1, fg2)),
                is((List<String>)ImmutableList.of(Factor.normalize("type 2"))));

    }
    
    
    @Test
    public void isSubgroup() {
        FactorGroup generalGroup = new FactorSet().add(new Factor("T1", "v1"));
        FactorGroup moreSpecificGroup = new FactorSet().add(new Factor("T1", "v1")).add(new Factor("T2", "v2"));
        FactorGroup differentSpecificGroup = new FactorSet().add(new Factor("T1", "different value")).add(new Factor("T2", "v2"));

        assertThat(RichFactorGroup.isSubgroup(moreSpecificGroup, generalGroup), is(true));
        assertThat(RichFactorGroup.isSubgroup(differentSpecificGroup, generalGroup), is(false));

        assertThat(RichFactorGroup.isSubgroup(generalGroup, generalGroup), is(true));
        assertThat(RichFactorGroup.isSubgroup(moreSpecificGroup, new FactorSet()), is(true));
        assertThat(RichFactorGroup.isSubgroup(differentSpecificGroup, new FactorSet()), is(true));

    }

    @Test
    public void asJson() {
        assertThat(new RichFactorGroup(new FactorSet().add(new Factor("header", "value"))).asJson(), is(new Gson().fromJson("{\"HEADER\":[\"value\"]}", JsonObject.class)));
    }
}