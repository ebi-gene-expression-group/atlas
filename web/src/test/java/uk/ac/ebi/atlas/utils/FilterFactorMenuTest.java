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

package uk.ac.ebi.atlas.utils;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;

import java.util.Set;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FilterFactorMenuTest {

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final String RNA = "RNA";
    private static final String CELLULAR_COMPONENT = "CELLULAR_COMPONENT";
    private static final String ORGANISM_PART_NAME = "organism part";
    private static final String RNA_TYPE_NAME = "RNA";
    private static final String CELLULAR_COMPONENT_NAME = "cellular component";

    Factor firstFactor = new Factor(ORGANISM_PART, "liver");
    Factor secondFactor = new Factor(ORGANISM_PART, "heart");
    Factor thirdFactor = new Factor(ORGANISM_PART, "brain");
    Factor forthFactor = new Factor(RNA, "long poly a rna");
    Factor fifthFactor = new Factor(RNA, "total rna");
    Factor sixthFactor = new Factor(CELLULAR_COMPONENT, "plasma");
    Factor sevenFactor = new Factor(CELLULAR_COMPONENT, "whole cell");

    @Mock
    ExperimentalFactors experimentalFactorsMock;

    FilterFactorMenu subject;

    private SortedSet<Factor> putIntoSortedSet(Factor... factors) {
        SortedSet<Factor> result = Sets.newTreeSet();
        for (Factor factor : factors) {
            result.add(factor);
        }
        return result;
    }

    @Before
    public void setup() {
        Set<Factor> allFactors = Sets.newHashSet(firstFactor, secondFactor, thirdFactor, forthFactor, fifthFactor, sixthFactor, sevenFactor);

        // liver, long poly a rna, plasma
        when(experimentalFactorsMock.getCoOccurringFactors(firstFactor)).thenReturn(putIntoSortedSet(forthFactor, sixthFactor));

        // heart, long poly a rna, whole cell
        when(experimentalFactorsMock.getCoOccurringFactors(secondFactor)).thenReturn(putIntoSortedSet(forthFactor, sevenFactor));

        // brain, total rna, whole cell
        when(experimentalFactorsMock.getCoOccurringFactors(thirdFactor)).thenReturn(putIntoSortedSet(fifthFactor, sevenFactor));

        // long poly a rna with liver, heart, plasma, whole cell
        when(experimentalFactorsMock.getCoOccurringFactors(forthFactor)).thenReturn(putIntoSortedSet(firstFactor, secondFactor, sixthFactor, sevenFactor));

        // total rna with brain, whole cell
        when(experimentalFactorsMock.getCoOccurringFactors(fifthFactor)).thenReturn(putIntoSortedSet(thirdFactor, sevenFactor));

        // plasma with liver, long poly a rna
        when(experimentalFactorsMock.getCoOccurringFactors(sixthFactor)).thenReturn(putIntoSortedSet(firstFactor, forthFactor));

        // whole cell with, heart, brain, long poly a rna, total rna
        when(experimentalFactorsMock.getCoOccurringFactors(sevenFactor)).thenReturn(putIntoSortedSet(secondFactor, thirdFactor, forthFactor, fifthFactor));

        when(experimentalFactorsMock.getFactorName(ORGANISM_PART)).thenReturn(ORGANISM_PART_NAME);
        when(experimentalFactorsMock.getFactorName(RNA)).thenReturn(RNA_TYPE_NAME);
        when(experimentalFactorsMock.getFactorName(CELLULAR_COMPONENT)).thenReturn(CELLULAR_COMPONENT_NAME);

        subject = new FilterFactorMenu(experimentalFactorsMock, allFactors);
    }

    @Test
    public void testFirstFactorLevel() {
        assertThat(subject.getFactors(), contains(sixthFactor, sevenFactor, thirdFactor, secondFactor, firstFactor, forthFactor, fifthFactor));
    }

    @Test
    public void testFilterOutByFactor() {
        assertThat(subject.filterOutByFactor(firstFactor).getFactors(), contains(sixthFactor, forthFactor));
    }

    @Test
    public void testGetFactorsForFactorName() {
        assertThat(subject.getFactorsForFactorName(ORGANISM_PART_NAME), contains(thirdFactor, secondFactor, firstFactor));
    }

    @Test
    public void testFilterOutByFactorAndAnotherFactor() {
        assertThat(subject.filterOutByFactor(secondFactor).filterOutByFactor(forthFactor).getFactors(), contains(sevenFactor));
    }

    @Test
    public void testFilterOutByFactorAndGetFactorsForFactorName() {
        assertThat(subject.filterOutByFactor(forthFactor).getFactorsForFactorName(CELLULAR_COMPONENT_NAME), contains(sixthFactor, sevenFactor));
    }

    @Test
    public void testLastFactorLevel() {
        assertThat(subject.filterOutByFactor(thirdFactor).filterOutByFactor(fifthFactor).getFactorsForFactorName(CELLULAR_COMPONENT_NAME), contains(sevenFactor));
    }

    @Test
    public void testGetAllFactorNames() {
        assertThat(subject.getAllFactorNames(), contains(RNA_TYPE_NAME, CELLULAR_COMPONENT_NAME, ORGANISM_PART_NAME));
        assertThat(subject.filterOutByFactor(firstFactor).getAllFactorNames(), contains(RNA_TYPE_NAME, CELLULAR_COMPONENT_NAME));
        assertThat(subject.filterOutByFactor(sixthFactor).filterOutByFactor(firstFactor).getAllFactorNames(), contains(RNA_TYPE_NAME));
    }


}