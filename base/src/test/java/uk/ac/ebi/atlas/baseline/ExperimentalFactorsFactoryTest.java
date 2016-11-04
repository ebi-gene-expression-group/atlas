package uk.ac.ebi.atlas.baseline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentalFactorsFactoryTest {

    public static final String SPECIES = "species";
    public static final String DEFAULT_QUERY_FACTOR_TYPE = "defaultQueryFactorType";
    public static final String FACTOR_TYPE = "factorType";


    ExperimentalFactorsFactory subject = new ExperimentalFactorsFactory();

    @Mock
    private Factor factorMock;

    @Test
    public void testGetRequiredFactorTypes() throws Exception {

        Set<Factor> defaultFilterFactors = new HashSet<>();

        Set<String> requiredFactorTypes = subject.getRequiredFactorTypes(DEFAULT_QUERY_FACTOR_TYPE, defaultFilterFactors);
        assertThat(requiredFactorTypes, contains(DEFAULT_QUERY_FACTOR_TYPE));

        when(factorMock.getType()).thenReturn(FACTOR_TYPE);
        defaultFilterFactors.add(factorMock);
        requiredFactorTypes = subject.getRequiredFactorTypes(DEFAULT_QUERY_FACTOR_TYPE, defaultFilterFactors);
        assertThat(requiredFactorTypes, containsInAnyOrder(DEFAULT_QUERY_FACTOR_TYPE, FACTOR_TYPE));
    }

}