package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.experiment.baseline.ExperimentalFactorsBuilder;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentalFactorsBuilderTest {
    private static final Factor FACTOR1 = new Factor("TYPE", "VALUE");
    private static final String DEFAULT_QUERY_TYPE = "DEFAULT_QUERY_TYPE";
    ExperimentalFactorsBuilder subject;

    private Factor factorWithType1 = new Factor("TYPE1", "VALUE1");
    private Factor factorWithType2 = new Factor("TYPE2", "VALUE2");
    private Factor factorWithType2DifferentValue = new Factor("TYPE2", "DIFFERENT_VALUE2");
    private Factor factorWithType3 = new Factor("TYPE3", "VALUE3");
    private FactorGroup factorGroup1 = new FactorSet().add(factorWithType2)
            .add(factorWithType1)
            .add(factorWithType3);
    private FactorGroup factorGroup2 = new FactorSet().add(factorWithType3)
            .add(factorWithType2DifferentValue)
            .add(factorWithType1);
    List<FactorGroup> factorGroups = Lists.newArrayList(factorGroup1, factorGroup2);

    @Before
    public void setUp() throws Exception {

        Map<String, String> factorNameByType = new HashMap<>();
        factorNameByType.put("TYPE1", "NAME1");
        factorNameByType.put("TYPE2", "NAME2");
        factorNameByType.put("TYPE3", "NAME3");

        subject = new ExperimentalFactorsBuilder();
        subject.withMenuFilterFactorTypes(Sets.newHashSet("TYPE1"))
                .withOrderedFactorGroups(factorGroups)
                .withFactorNamesByType(factorNameByType)
                .withDefaultQueryType(DEFAULT_QUERY_TYPE)
                .withDefaultFilterFactors(Collections.singleton(FACTOR1));
    }

    @Test
    public void testFactorByNameIsCreated() {
        subject.create();

        SortedSetMultimap<String, Factor> factorsByType = subject.buildFactorsByType();
        assertThat(factorsByType.keySet(), contains("TYPE1", "TYPE2", "TYPE3"));
        assertThat(factorsByType.get("TYPE2"), contains(factorWithType2DifferentValue, factorWithType2));
    }

    @Test
    public void testCoOccurringFactors() {
        subject.create();

        SetMultimap<Factor, Factor> coOccurringFactors = ExperimentalFactors.createCoOccurringFactors(factorGroups);

        assertThat(coOccurringFactors.get(factorWithType1), contains(factorWithType2DifferentValue, factorWithType2, factorWithType3));
    }

    @Test
    public void testDefaultQueryType() {
        assertThat(subject.create().getDefaultQueryFactorType(), is(DEFAULT_QUERY_TYPE));
    }

    @Test
    public void testDefaultFilterFactors() {
        assertThat(subject.create().getDefaultFilterFactors(), contains(FACTOR1));
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateWithNullMenuFilterFactorTypes() throws Exception {
        subject.withMenuFilterFactorTypes(null);
        subject.create();
    }

}
