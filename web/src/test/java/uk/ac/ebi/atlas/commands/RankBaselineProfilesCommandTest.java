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

package uk.ac.ebi.atlas.commands;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RankBaselineProfilesCommandTest {

    private static final String SPECIES = "Species 1";

    @Mock
    private BaselineRequestContext requestContextMock;

    @Mock
    private InputStreamFactory inputStreamFactoryMock;

    @Mock
    private GeneSetProfilesBuilder geneSetProfilesBuilderMock;

    private ObjectInputStream<BaselineProfile> largeInputStream;

    private ObjectInputStream<BaselineProfile> smallInputStream;

    private RankBaselineProfilesCommand subject;

    public RankBaselineProfilesCommandTest() {
    }

    //ToDo: better to do verifications on real values that on anyX(), using anyX() could hide bugs
    @Before
    public void initializeSubject() throws Exception {

        when(requestContextMock.getGeneQuery()).thenReturn("");


        when(requestContextMock.getFilteredBySpecies()).thenReturn(SPECIES);


        when(requestContextMock.getHeatmapMatrixSize()).thenReturn(100);
        when(requestContextMock.isSpecific()).thenReturn(true);

        //a stream with 5 profile of 2 expressions
        largeInputStream = new GeneProfileInputStreamMock(5);

        //a stream with 1 profile of 2 expressions
        smallInputStream = new GeneProfileInputStreamMock(1);

        subject = new RankBaselineProfilesCommand(requestContextMock, inputStreamFactoryMock, geneSetProfilesBuilderMock);

    }

    @Test
    public void givenAStreamWithLessExpressionsThanRankSizeTheCommandShouldReturnAllTheExpressions() throws Exception {
        //when
        List<BaselineProfile> top3Objects = subject.execute(smallInputStream, requestContextMock);

        //then
        assertThat(top3Objects.size(), is(1));

    }

    @Test
    public void givenRankingSizeOf3TheCommandWillAlwaysReturnAtMax3Expressions() throws Exception {

        //given
        given(requestContextMock.getHeatmapMatrixSize()).willReturn(3);

        //when
        List<BaselineProfile> top3Objects = subject.execute(largeInputStream, requestContextMock);

        //then
        assertThat(top3Objects.size(), is(3));

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenEmptyConditions() throws Exception {
        //given
        when(requestContextMock.isSpecific()).thenReturn(false);
        //when
        List<BaselineProfile> top3Objects = subject.execute(largeInputStream, requestContextMock);
    }

        @Test
    public void rankedObjectsShouldBeInAscendingOrder() throws Exception {

        //given
        given(requestContextMock.isSpecific()).willReturn(false);
        //and
        given(requestContextMock.getSelectedQueryFactors()).willReturn(Sets.newHashSet(mock(Factor.class)));


        //when
        List<BaselineProfile> top3Objects = subject.execute(largeInputStream, requestContextMock);

        //and
        assertThat(top3Objects.get(0).getSpecificity(), is(5));
        //and
        assertThat(top3Objects.get(0).getMaxExpressionLevel(), is(5D));
        //then
        assertThat(top3Objects.get(0).getId(), is("5"));

        //and
        assertThat(top3Objects.get(2).getSpecificity(), is(3));
        //and
        assertThat(top3Objects.get(2).getMaxExpressionLevel(), is(3D));
        //and
        assertThat(top3Objects.get(2).getId(), is("3"));

    }

    @Test
    public void rankedObjectsShouldBeInDescendingOrder() throws Exception {

        //when
        List<BaselineProfile> top3Objects = subject.execute(largeInputStream, requestContextMock);

        //and
        assertThat(top3Objects.get(0).getSpecificity(), is(1));
        //and
        assertThat(top3Objects.get(0).getMaxExpressionLevel(), is(1D));
        //then
        assertThat(top3Objects.get(0).getId(), is("1"));

        //and
        assertThat(top3Objects.get(2).getSpecificity(), is(3));
        //and
        assertThat(top3Objects.get(2).getMaxExpressionLevel(), is(3D));
        //and
        assertThat(top3Objects.get(2).getId(), is("3"));

    }

}
