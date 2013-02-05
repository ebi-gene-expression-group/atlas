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

package uk.ac.ebi.atlas.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentTest {

    @Mock
    private ExperimentalFactors experimentalFactorsMock;

    private static final String SPECIE = "homo sapiens";

    private static final String RUN_ACCESSION_1 = "ENS0";
    private static final String RUN_ACCESSION_2 = "ENS1";
    private static final String DESCRIPTION = "aDescription";

    private static final String CELLULAR_COMPONENT = "CELLULAR_COMPONENT";
    private static final String ORGANISM_PART = "ORGANISM_PART";


    @Mock
    private ExperimentRun experimentRun1Mock;
    @Mock
    private ExperimentRun experimentRun2Mock;
    @Mock
    private FactorGroup factorGroupMock1;
    @Mock
    private FactorGroup factorGroupMock2;

    @Mock
    private Factor factorMock1;
    @Mock
    private Factor factorMock2;


    private Experiment subject;

    @Before
    public void initializeSubject() {

        when(experimentRun1Mock.getRunAccession()).thenReturn(RUN_ACCESSION_1);
        when(experimentRun2Mock.getRunAccession()).thenReturn(RUN_ACCESSION_2);

        when(experimentRun1Mock.getFactorGroup()).thenReturn(factorGroupMock1);
        when(experimentRun2Mock.getFactorGroup()).thenReturn(factorGroupMock2);

        subject = new ExperimentBuilder(experimentalFactorsMock)
                                         .forSpecie(SPECIE)
                                         .withDescription(DESCRIPTION)
                                         .withDefaultQueryType(ORGANISM_PART)
                                         .withDefaultFilterFactors(Collections.EMPTY_SET)
                                         .withExperimentRuns(Lists.newArrayList(experimentRun1Mock, experimentRun2Mock))
                                         .create();


    }

    @Test
    public void testExperimentRunAccessions() {
        assertThat(subject.getExperimentRunAccessions(), containsInAnyOrder(RUN_ACCESSION_1, RUN_ACCESSION_2));
    }

    @Test
    public void testDescription() {
        assertThat(subject.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void testSpecies() {
        assertThat(subject.getSpecie(), is(SPECIE));
    }

    @Test
    public void getDefaultFactorTypeShouldDelegateToExperimentalFactors() {
        //when
        subject.getDefaultQueryFactorType();
        verify(experimentalFactorsMock).getDefaultQueryFactorType();
    }

    @Test
    public void getFactorGroupShouldDelegateToExperimentalRun() {
        //when
        subject.getFactorGroup(RUN_ACCESSION_1);
        //then
        verify(experimentRun1Mock,times(2)).getFactorGroup();
    }

    @Test
    public void testFilteredFactorValues() {
        //given
        Set<Factor> filteredFactors = Sets.newHashSet(factorMock1, factorMock2);
        //when
        subject.getFilteredFactors(filteredFactors, CELLULAR_COMPONENT);

        //then
        verify(experimentalFactorsMock).getFilteredFactors(filteredFactors, CELLULAR_COMPONENT);
    }

}
