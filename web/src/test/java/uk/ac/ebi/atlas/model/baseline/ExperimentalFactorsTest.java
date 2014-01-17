/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.model.baseline;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentalFactorsTest {

    private static final Factor FACTOR1 = new Factor("TYPE", "VALUE");
    private static final String DEFAULT_QUERY_TYPE = "DEFAULT_QUERY_TYPE";

    private static final String DEFAULT_FILTER_FACTOR_TYPE = "A filter factor type";

    @Mock
    private Factor defaultFilterFactorMock;

    @Mock
    private ExperimentRun experimentRun1Mock;
    @Mock
    private ExperimentRun experimentRun2Mock;

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

        when(experimentRun1Mock.getFactorGroup()).thenReturn(factorGroup1);
        when(experimentRun2Mock.getFactorGroup()).thenReturn(factorGroup2);

        Map<String, String> factorNameByType = new HashMap<>();
        factorNameByType.put("TYPE1", "NAME1");
        factorNameByType.put("TYPE2", "NAME2");
        factorNameByType.put("TYPE3", "NAME3");

        ExperimentalFactorsBuilder builder = new ExperimentalFactorsBuilder();
        List<FactorGroup> factorGroups = Lists.newArrayList(factorGroup1, factorGroup2);
        subject = builder
                .withMenuFilterFactorTypes(Sets.newHashSet("TYPE1", "TYPE2"))
                .withOrderedFactorGroups(factorGroups)
                .withFactorNamesByType(factorNameByType)
                .withDefaultQueryType(DEFAULT_QUERY_TYPE)
                .withDefaultFilterFactors(Collections.singleton(FACTOR1))
                .create();
    }

    @Test
    public void getFactorNameShouldSucceedForValidType() {
        //when
        String factorName = subject.getFactorName("TYPE2");
        //then
        assertThat(factorName, is("NAME2"));
    }

    @Test
    public void getFactorsWithGivenNameCooccurringWithGivenFactorTest() {
        //when
        SortedSet<Factor> factors = subject.getCoOccurringFactors(factorWithType2);
        //then
        assertThat(factors, contains(factorWithType1, factorWithType3));

        //when
        factors = subject.getCoOccurringFactors(factorWithType1);
        //then
        assertThat(factors, contains(factorWithType2DifferentValue, factorWithType2, factorWithType3DifferentValue, factorWithType3));

        //when
        factors = subject.getCoOccurringFactors(factorWithType3);
        //then
        assertThat(factors, contains(factorWithType1, factorWithType2));

    }

    @Test
    public void getMenuFactorNames() {
        assertThat(subject.getMenuFilterFactorNames(), contains("NAME1", "NAME2"));
    }


    @Test
    public void testGetFilteredFactors() {
        SortedSet<Factor> filteredFactors = subject.getFilteredFactors(Sets.newHashSet(factorWithType1, factorWithType2));
        assertThat(filteredFactors, contains(factorWithType3));

        filteredFactors = subject.getFilteredFactors(Sets.newHashSet(factorWithType1, factorWithType2DifferentValue));
        assertThat(filteredFactors, contains(factorWithType3DifferentValue));


        filteredFactors = subject.getFilteredFactors(Sets.newHashSet(factorWithType3DifferentValue, factorWithType2DifferentValue));
        assertThat(filteredFactors, contains(factorWithType1));

        //ToDo: this is not valid combination: do we need to check it?!!!
        filteredFactors = subject.getFilteredFactors(Sets.newHashSet(factorWithType3DifferentValue, factorWithType2));
        assertThat(filteredFactors.isEmpty(), is(true));
    }


}
