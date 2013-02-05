package uk.ac.ebi.atlas.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.IteratorUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ExperimentBuilderTest {

    private ExperimentBuilder subject;

    private static final String DEFAULT_QUERY_TYPE = "A_QUERY_TYPE";
    @Mock
    private ExperimentRun experimentRun1Mock;
    @Mock
    private ExperimentRun experimentRun2Mock;
    @Mock
    private FactorGroup factorGroup1Mock;
    @Mock
    private FactorGroup factorGroup2Mock;
    @Mock
    private Factor defaultFilterFactorMock;
    @Mock
    private ExperimentalFactors experimentalFactorsMock;


    private Collection<ExperimentRun> experimentRunMocks;
    private Set<Factor> defaultFilterFactors;

    @Before
    public void setUp() throws Exception {
        subject = new ExperimentBuilder(experimentalFactorsMock);

        defaultFilterFactors = Sets.newHashSet(defaultFilterFactorMock);
        experimentRunMocks = Lists.newArrayList(experimentRun1Mock, experimentRun2Mock);
        when(experimentRun1Mock.getFactorGroup()).thenReturn(factorGroup1Mock);
        when(experimentRun2Mock.getFactorGroup()).thenReturn(factorGroup2Mock);
        when(factorGroup1Mock.iterator()).thenReturn(IteratorUtils.EMPTY_ITERATOR);
        when(factorGroup2Mock.iterator()).thenReturn(IteratorUtils.EMPTY_ITERATOR);
    }

    @Test
    public void testExtractFactorGroups() throws Exception {
        //given
        subject.withExperimentRuns(experimentRunMocks);
        //then
        assertThat(subject.extractFactorGroups(), contains(factorGroup1Mock, factorGroup2Mock));
    }

    @Test
    public void testCreate() throws Exception {

    }

    @Test
    public void testBuildExperimentalFactors() throws Exception {
        //given
        subject.withDefaultQueryType(DEFAULT_QUERY_TYPE)
               .withDefaultFilterFactors(defaultFilterFactors)
               .withExperimentRuns(experimentRunMocks);

        //when
        subject.buildExperimentalFactors();

        //then
        verify(experimentalFactorsMock).setDefaultQueryFactorType(DEFAULT_QUERY_TYPE);
        //and
        verify(experimentalFactorsMock).addDefaultFilterFactor(defaultFilterFactorMock);
        //and
        verify(experimentalFactorsMock).addFactorGroup(factorGroup1Mock);
        verify(experimentalFactorsMock).addFactorGroup(factorGroup2Mock);

    }
}
