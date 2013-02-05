package uk.ac.ebi.atlas.model;


import com.google.common.collect.Sets;
import org.apache.commons.collections.IteratorUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentalFactorsTest {

    private static final String DEFAULT_QUERY_FACTOR_TYPE = "A query factor type";
    private static final String DEFAULT_FILTER_FACTOR_TYPE = "A filter factor type";

    @Mock
    private FactorGroup factorGroupMock;
    @Mock
    private Factor defaultFilterFactorMock;

    private ExperimentalFactors subject;

    @Before
    public void initSubject(){
        when(defaultFilterFactorMock.getType()).thenReturn(DEFAULT_FILTER_FACTOR_TYPE);

        when(factorGroupMock.iterator()).thenReturn(IteratorUtils.EMPTY_ITERATOR);

        subject = new ExperimentalFactors()
                    .setDefaultQueryFactorType(DEFAULT_QUERY_FACTOR_TYPE)
                    .addFactorGroup(factorGroupMock)
                    .addDefaultFilterFactor(defaultFilterFactorMock);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void filteredFactorValuesShouldThrowExceptionIfNoFactorHasQueryFilterType() {
        subject.getFilteredFactors(Sets.newHashSet(defaultFilterFactorMock), "BLA");
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void filteredFactorValuesShouldThrowExceptionIfQueryFilterTypeIsTheSameAsAnyFilterFactorType() {
        subject.getFilteredFactors(Sets.newHashSet(defaultFilterFactorMock), DEFAULT_FILTER_FACTOR_TYPE);
    }

}
