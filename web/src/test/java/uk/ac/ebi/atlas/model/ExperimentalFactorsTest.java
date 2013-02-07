/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.model;


import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.impl.FactorSet;

import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentalFactorsTest {

    private static final String DEFAULT_QUERY_FACTOR_TYPE = "A query factor type";
    private static final String DEFAULT_FILTER_FACTOR_TYPE = "A filter factor type";

    @Mock
    private Factor defaultFilterFactorMock;

    private ExperimentalFactors subject;

    private Factor factorWithType1 = new Factor("TYPE1", "NAME1", "VALUE1");
    private Factor factorWithType2 = new Factor("TYPE2", "NAME2", "VALUE2");
    private Factor factorWithType2DifferentValue = new Factor("TYPE2", "NAME2", "DIFFERENT_VALUE2");
    private Factor factorWithType3 = new Factor("TYPE3", "NAME3", "VALUE3");
    private FactorGroup factorGroup1 = new FactorSet(Sets.newHashSet(factorWithType2, factorWithType1, factorWithType3));
    private FactorGroup factorGroup2 = new FactorSet(Sets.newHashSet(factorWithType3, factorWithType2DifferentValue, factorWithType1));


    @Before
    public void initSubject() {
        when(defaultFilterFactorMock.getType()).thenReturn(DEFAULT_FILTER_FACTOR_TYPE);

        subject = new ExperimentalFactors()
                .addFactorGroup(factorGroup1)
                .addFactorGroup(factorGroup2);
    }

    @Test
    public void getFactorNameShouldSucceedForValidType() {
        //when
        String factorName = subject.getFactorName("TYPE2");
        //then
        assertThat(factorName, is("NAME2"));
    }


    @Test
    public void getAllFactorNamesShouldReturnAllNamesComingFromFactorGroups() {
        //when
        SortedSet<String> allFactorNames = subject.getAllFactorNames();
        //then
        assertThat(allFactorNames, contains("NAME1", "NAME2", "NAME3"));
    }

    @Test
    public void getFactorsByName() {
        //when
        SortedSet<Factor> factors = subject.getFactorsByName("NAME2");
        //then
        assertThat(factors, contains(factorWithType2DifferentValue, factorWithType2));
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
        assertThat(factors, contains(factorWithType2DifferentValue, factorWithType2, factorWithType3));

        //when
        factors = subject.getCoOccurringFactors(factorWithType3);
        //then
        assertThat(factors, contains(factorWithType1, factorWithType2DifferentValue, factorWithType2));

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
