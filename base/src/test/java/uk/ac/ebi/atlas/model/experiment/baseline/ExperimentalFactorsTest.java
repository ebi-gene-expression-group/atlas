
package uk.ac.ebi.atlas.model.experiment.baseline;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentalFactorsTest {

    private static final Factor FACTOR1 = new Factor("TYPE", "VALUE");
    private static final String DEFAULT_QUERY_TYPE = "DEFAULT_QUERY_TYPE";

    private static final String DEFAULT_FILTER_FACTOR_TYPE = "A filter factor type";
    private static final String G1 = "g1";
    private static final String G2 = "g2";

    @Mock
    private Factor defaultFilterFactorMock;

    private ExperimentalFactors subject;

    private Factor factorWithType1 = new Factor("TYPE1",  "VALUE1");
    private Factor factorWithType2 = new Factor("TYPE2", "VALUE2");
    private Factor factorWithType2DifferentValue = new Factor("TYPE2",  "DIFFERENT_VALUE2");
    private Factor factorWithType3 = new Factor("TYPE3",  "VALUE3");
    private Factor factorWithType3DifferentValue = new Factor("TYPE3", "DIFFERENT_VALUE3");
    private FactorGroup factorGroup1 = new FactorSet().add(factorWithType2)
            .add(factorWithType1)
            .add(factorWithType3);
    private FactorGroup factorGroup2 = new FactorSet()
            .add(factorWithType3DifferentValue)
            .add(factorWithType2DifferentValue)
            .add(factorWithType1);


    @Before
    public void initSubject() {
        when(defaultFilterFactorMock.getType()).thenReturn(DEFAULT_FILTER_FACTOR_TYPE);

        Map<String, String> factorNameByType = new HashMap<>();
        factorNameByType.put("TYPE1", "NAME1");
        factorNameByType.put("TYPE2", "NAME2");
        factorNameByType.put("TYPE3", "NAME3");

        ExperimentalFactorsBuilder builder = new ExperimentalFactorsBuilder();
        List<FactorGroup> factorGroups = Lists.newArrayList(factorGroup1, factorGroup2);
        Map<String, FactorGroup> orderedFactorGroupsByAssayGroup = ImmutableMap.of(G1, factorGroup1, G2, factorGroup2);
        subject = builder
                .withMenuFilterFactorTypes(Sets.newHashSet("TYPE1", "TYPE2"))
                .withOrderedFactorGroups(factorGroups)
                .withOrderedFactorGroupsByAssayGroupId(orderedFactorGroupsByAssayGroup)
                .withFactorNamesByType(factorNameByType)
                .withDefaultQueryType(DEFAULT_QUERY_TYPE)
                .withDefaultFilterFactors(Collections.singleton(FACTOR1))
                .create();
    }

    @Test
    public void getComplementFactors() {
        SortedSet<Factor> complement = subject.getComplementFactors(Sets.newHashSet(factorWithType1, factorWithType2));
        assertThat(complement, contains(factorWithType3));

        complement = subject.getComplementFactors(Sets.newHashSet(factorWithType1, factorWithType2DifferentValue));
        assertThat(complement, contains(factorWithType3DifferentValue));


        complement = subject.getComplementFactors(Sets.newHashSet(factorWithType3DifferentValue, factorWithType2DifferentValue));
        assertThat(complement, contains(factorWithType1));

        //ToDo: this is not valid combination: do we need to check it?!!!
        complement = subject.getComplementFactors(Sets.newHashSet(factorWithType3DifferentValue, factorWithType2));
        assertThat(complement.isEmpty(), is(true));
    }

}
