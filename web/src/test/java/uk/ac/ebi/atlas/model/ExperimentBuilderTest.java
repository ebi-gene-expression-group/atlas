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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ExperimentBuilderTest {

    private static final String DEFAULT_FILTER_FACTOR_TYPE = "A_TYPE";
    private static final String DEFAULT_QUERY_TYPE = "A_QUERY_TYPE";
    private static final String SPECIE = "A_SPECIE";
    private static final String DESCRIPTION = "A_DESCRIPTION";

    private ExperimentBuilder subject;

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

    @Mock
    private ExperimentalFactorsBuilder experimentalFactorsBuilderMock;

    private Collection<ExperimentRun> experimentRunMocks;
    private Set<Factor> defaultFilterFactors;
    private Set<String> menuFilterFactorTypes;

    @Before
    public void setUp() throws Exception {
        when(defaultFilterFactorMock.getType()).thenReturn(DEFAULT_FILTER_FACTOR_TYPE);
        defaultFilterFactors = Sets.newHashSet(defaultFilterFactorMock);
        menuFilterFactorTypes = Sets.newHashSet(DEFAULT_FILTER_FACTOR_TYPE);
        experimentRunMocks = Lists.newArrayList(experimentRun1Mock, experimentRun2Mock);
        when(experimentRun1Mock.getFactorGroup()).thenReturn(factorGroup1Mock);
        when(experimentRun2Mock.getFactorGroup()).thenReturn(factorGroup2Mock);
        when(factorGroup1Mock.iterator()).thenReturn(IteratorUtils.EMPTY_ITERATOR);
        when(factorGroup2Mock.iterator()).thenReturn(IteratorUtils.EMPTY_ITERATOR);

        when(experimentalFactorsBuilderMock.withExperimentRuns(experimentRunMocks)).thenReturn(experimentalFactorsBuilderMock);
        when(experimentalFactorsBuilderMock.create()).thenReturn(experimentalFactorsMock);
        subject = new ExperimentBuilder(experimentalFactorsBuilderMock);

        subject.forSpecies(Sets.newHashSet(SPECIE))
                .withDescription(DESCRIPTION)
                .withDefaultQueryType(DEFAULT_QUERY_TYPE)
                .withDefaultFilterFactors(defaultFilterFactors)
                .withMenuFilterFactorTypes(menuFilterFactorTypes)
                .withExperimentRuns(experimentRunMocks);

    }


    @Test
    public void testCreate() throws Exception {
        //when
        Experiment experiment = subject.create();

        assertThat(experiment.getDefaultQueryFactorType(), is(DEFAULT_QUERY_TYPE));
        //and
        verify(experimentalFactorsMock).getFactorName(DEFAULT_FILTER_FACTOR_TYPE);
        //and
        assertThat(experiment.getMenuFilterFactorTypes(), contains(DEFAULT_FILTER_FACTOR_TYPE));
    }

}
