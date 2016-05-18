
package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.GeneQuery;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineRequestContextBuilderTest {

    public static final String QUERY_FACTOR1 = "a";
    public static final String QUERY_FACTOR2 = "b";
    public static final String QUERY_FACTOR3 = "c";
    public static final String FACTOR_TYPE = "organism";
    public static final String FACTOR_VALUE = "homo sapiens";
    public static final String SERIALIZED_FACTORS = "serialized";

    BaselineRequestContextBuilder subject;

    @Mock
    FilterFactorsConverter filterFactorsConverterMock;

    @Mock
    BaselineExperiment experimentMock;

    @Mock
    BaselineRequestPreferences preferencesMock;

    @Mock
    Factor factorMock;

    @Mock
    ExperimentalFactors experimentalFactorsMock;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineRequestContextBuilder(filterFactorsConverterMock);

        when(factorMock.getType()).thenReturn(FACTOR_TYPE);
        when(factorMock.getValue()).thenReturn(FACTOR_VALUE);
        when(filterFactorsConverterMock.deserialize(SERIALIZED_FACTORS)).thenReturn(ImmutableSortedSet.of(factorMock));
        when(preferencesMock.getSerializedFilterFactors()).thenReturn(SERIALIZED_FACTORS);
        when(preferencesMock.getQueryFactorValues()).thenReturn(Sets.newTreeSet(Sets.newHashSet(QUERY_FACTOR1, QUERY_FACTOR2, QUERY_FACTOR3)));
        when(preferencesMock.getQueryFactorType()).thenReturn(FACTOR_TYPE);
        when(preferencesMock.getGeneQuery()).thenReturn(GeneQuery.EMPTY);
        when(experimentMock.getExperimentalFactors()).thenReturn(experimentalFactorsMock);
        when(experimentalFactorsMock.getComplementFactors(anySet())).thenReturn(Sets.newTreeSet(Sets.newHashSet(new Factor(FACTOR_TYPE, FACTOR_VALUE))));
    }

    @Test
    public void testBuild() throws Exception {
        BaselineRequestContext context = subject.forExperiment(experimentMock).withPreferences(preferencesMock).build();
        assertThat(context.getSelectedFilterFactors(), hasItem(factorMock));
        assertThat(context.getFilteredBySpecies(), is(FACTOR_VALUE));
        Factor factor1 = new Factor(FACTOR_TYPE, QUERY_FACTOR1);
        Factor factor2 = new Factor(FACTOR_TYPE, QUERY_FACTOR2);
        Factor factor3 = new Factor(FACTOR_TYPE, QUERY_FACTOR3);
        assertThat(context.getSelectedQueryFactors(), hasItems(factor1, factor2, factor3));
        assertThat(context.getAllQueryFactors(), hasItem(new Factor(FACTOR_TYPE, FACTOR_VALUE)));
    }
}