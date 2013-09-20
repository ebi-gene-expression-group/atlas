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
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentTest {

    private static final String DISPLAY_NAME = "DISPLAY NAME";
    private static final String PUBMEDID = "PUBMEDID";
    private static final String SPECIE = "homo sapiens";
    private static final String RUN_ACCESSION_1 = "ENS0";
    private static final String RUN_ACCESSION_2 = "ENS1";
    private static final String DESCRIPTION = "aDescription";
    private static final String CELLULAR_COMPONENT = "CELLULAR_COMPONENT";
    private static final String ORGANISM_PART = "ORGANISM_PART";

    @Mock
    private ExperimentalFactors experimentalFactorsMock;
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

    @Mock
    private ExperimentalFactorsBuilder experimentalFactorsBuilderMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    private BaselineExperiment subject;

    @Before
    public void initializeSubject() {

        when(experimentRun1Mock.getAccession()).thenReturn(RUN_ACCESSION_1);
        when(experimentRun2Mock.getAccession()).thenReturn(RUN_ACCESSION_2);

        when(experimentRun1Mock.getFactorGroup()).thenReturn(factorGroupMock1);
        when(experimentRun2Mock.getFactorGroup()).thenReturn(factorGroupMock2);

        Map<String, ExperimentRun> experimentRunsMock = Maps.newHashMap();
        experimentRunsMock.put(experimentRun1Mock.getAccession(), experimentRun1Mock);
        experimentRunsMock.put(experimentRun2Mock.getAccession(), experimentRun2Mock);

        List<FactorGroup> orderedFactorGroups = Lists.newArrayList(factorGroupMock1, factorGroupMock2);

        when(experimentalFactorsBuilderMock.withExperimentRuns(experimentRunsMock.values()))
                .thenReturn(experimentalFactorsBuilderMock);
        when(experimentalFactorsBuilderMock.withMenuFilterFactorTypes(anySet())).thenReturn(experimentalFactorsBuilderMock);
        when(experimentalFactorsBuilderMock.withFactorNamesByType(anyMap())).thenReturn(experimentalFactorsBuilderMock);
        when(experimentalFactorsBuilderMock.withOrderedFactorGroups(orderedFactorGroups)).thenReturn(experimentalFactorsBuilderMock);
        when(experimentalFactorsBuilderMock.create()).thenReturn(experimentalFactorsMock);


        subject = new BaselineExperimentBuilder(experimentalFactorsBuilderMock)
                .forSpecies(Sets.newHashSet(SPECIE))
                .withDescription(DESCRIPTION)
                .withDefaultQueryType(ORGANISM_PART)
                .withOrderedFactorGroups(orderedFactorGroups)
                .withDefaultFilterFactors(Collections.EMPTY_SET)
                .withMenuFilterFactorTypes(Collections.EMPTY_SET)
                .withFactorNamesByType(Collections.EMPTY_MAP)
                .withExperimentRuns(experimentRunsMock)
                .withSpeciesMapping(Collections.EMPTY_MAP)
                .withDisplayName(DISPLAY_NAME)
                .withPubMedIds(Lists.newArrayList(PUBMEDID))
                .withExperimentDesign(experimentDesignMock)
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
        assertThat(subject.getFirstSpecies(), is(SPECIE));
    }

    @Test
    public void testPubMedIds() {
        assertThat(subject.getPubMedIds(), hasItem(PUBMEDID));
    }

    @Test
    public void testGetExperimentDesign() {
        assertThat(subject.getExperimentDesign(), is(experimentDesignMock));
    }

    @Test
    public void testSpeciesMapping() {
        assertThat(subject.getSpeciesMapping(), is(Collections.EMPTY_MAP));
    }

    @Test
    public void getDefaultFactorTypeShouldDelegateToExperimentalFactors() {
        //when
        subject.getDefaultQueryFactorType();

        assertThat(subject.getDefaultFilterFactors(), is(Collections.EMPTY_SET));
    }

    @Test
    public void testFilteredFactorValues() {
        //given
        Set<Factor> filteredFactors = Sets.newHashSet(factorMock1, factorMock2);
        //when
        ExperimentalFactors experimentalFactors = subject.getExperimentalFactors();
        experimentalFactors.getFilteredFactors(filteredFactors);

        //then
        verify(experimentalFactorsMock).getFilteredFactors(filteredFactors);
        assertThat(subject.getExperimentalFactors().getMenuFilterFactorNames(), is(Collections.EMPTY_SET));
    }

    @Test
    public void testGetDisplayName() {
        assertThat(subject.getDisplayName(), is(DISPLAY_NAME));
    }

}
