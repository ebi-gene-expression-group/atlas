package uk.ac.ebi.atlas.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.impl.FactorSet;

import java.util.Collection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentalFactorsBuilderTest {
    ExperimentalFactorsBuilder subject;

    @Mock
    private ExperimentRun experimentRun1Mock;
    @Mock
    private ExperimentRun experimentRun2Mock;
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


    private Collection<ExperimentRun> experimentRunMocks;

    @Before
    public void setUp() throws Exception {
        experimentRunMocks = Lists.newArrayList(experimentRun1Mock, experimentRun2Mock);
        when(experimentRun1Mock.getFactorGroup()).thenReturn(factorGroup1);
        when(experimentRun2Mock.getFactorGroup()).thenReturn(factorGroup2);


        subject = new ExperimentalFactorsBuilder();
        subject.withExperimentRuns(experimentRunMocks).withMenuFilterFactorTypes(Sets.newHashSet("TYPE1"));
    }


    @Test
    public void testExtractFactorGroups() throws Exception {

        //then
        assertThat(subject.extractFactorGroups(), contains(factorGroup1, factorGroup2));
    }

    @Test
    public void testFactorByNameIsCreated() {
        subject.create();

        SortedSetMultimap<String, Factor> factorsByName = subject.getFactorsByType();
        assertThat(factorsByName.keySet(), contains("TYPE1", "TYPE2", "TYPE3"));
        assertThat(factorsByName.get("TYPE2"), contains(factorWithType2DifferentValue, factorWithType2));
    }

    @Test
    public void testFactorNamesByType() {
        subject.create();
        Map<String, String> factorNamesByType = subject.getFactorNamesByType();

        assertThat(factorNamesByType.keySet(), hasItems("TYPE1", "TYPE2", "TYPE3"));
        assertThat(factorNamesByType.get("TYPE1"), is("NAME1"));
    }

    @Test
    public void testCoOccurringFactors() {
        subject.create();

        SortedSetMultimap<Factor, Factor> coOccurringFactors = subject.getCoOccurringFactors();

        assertThat(coOccurringFactors.get(factorWithType1), contains(factorWithType2DifferentValue, factorWithType2, factorWithType3));
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateWithEmptyRun() throws Exception {
        subject.withExperimentRuns(null);
        subject.withMenuFilterFactorTypes(Sets.newHashSet("TYPE1"));
        subject.create();
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateWithNullMenuFilterFactorTypes() throws Exception {
        subject.withExperimentRuns(experimentRunMocks);
        subject.withMenuFilterFactorTypes(null);
        subject.create();
    }
}
