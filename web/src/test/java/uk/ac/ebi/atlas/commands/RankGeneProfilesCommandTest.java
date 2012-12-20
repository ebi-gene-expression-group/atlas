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

package uk.ac.ebi.atlas.commands;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.IndexClient;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;
import uk.ac.ebi.atlas.web.RequestPreferences;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RankGeneProfilesCommandTest {

    @Mock
    private GeneProfilesInputStream.Builder geneProfileInputStreamBuilderMock;

    @Mock
    private RequestPreferences requestPreferencesMock;

    @Mock
    private IndexClient indexClient;

    @Mock
    private ExperimentsCache experimentsCache;

    @Mock
    private Experiment experiment;

    private ObjectInputStream<GeneProfile> largeInputStream;

    private ObjectInputStream<GeneProfile> smallInputStream;

    private RankGeneProfilesCommand subject;

    public RankGeneProfilesCommandTest() {
    }

    @Before
    public void initializeSubject() throws Exception {

        // no filtering should be done here
        when(indexClient.findGeneIds(anyString(), anyString())).thenReturn(Lists.<String>newArrayList());

        when(experiment.getSpecie()).thenReturn("SPECIE");

        when(experimentsCache.getExperiment(anyString())).thenReturn(experiment);

        when(geneProfileInputStreamBuilderMock.forExperiment(anyString())).thenReturn(geneProfileInputStreamBuilderMock);

        when(geneProfileInputStreamBuilderMock.withCutoff(anyDouble())).thenReturn(geneProfileInputStreamBuilderMock);

        when(requestPreferencesMock.getHeatmapMatrixSize()).thenReturn(100);
        when(requestPreferencesMock.getCutoff()).thenReturn(0.1);
        when(requestPreferencesMock.isRankGenesExpressedOnMostFactorsLast()).thenReturn(true);

        //a stream with 5 profile of 2 expressions
        largeInputStream = new GeneProfileInputStreamMock(5);

        //a stream with 1 profile of 2 expressions
        smallInputStream = new GeneProfileInputStreamMock(1);

        when(geneProfileInputStreamBuilderMock.create()).thenReturn(largeInputStream);

        subject = new RankGeneProfilesCommand();

        subject.setGeneProfileInputStreamBuilder(geneProfileInputStreamBuilderMock);

        subject.setRequestPreferences(requestPreferencesMock);

        subject.setIndexClient(indexClient);

        subject.setExperimentsCache(experimentsCache);

    }

    @Test
    public void commandBuildsGeneProfileInputStream() {
        //when
        subject.apply("ANY_EXPERIMENT_ACCESSION");
        //then
        verify(geneProfileInputStreamBuilderMock).forExperiment("ANY_EXPERIMENT_ACCESSION");
        //verify(geneProfileInputStreamBuilderMock).withExperimentAccession("ANY_EXPERIMENT_ACCESSION");
        verify(geneProfileInputStreamBuilderMock).withCutoff(requestPreferencesMock.getCutoff());
        verify(geneProfileInputStreamBuilderMock).create();
    }


    @Test
    public void givenAStreamWithLessExpressionsThanRankSizeTheCommandShouldReturnAllTheExpressions() throws Exception {
        //given
        given(geneProfileInputStreamBuilderMock.create()).willReturn(smallInputStream);
        //when
        List<GeneProfile> top3Objects = subject.apply("ANY_ACCESSION");

        //then
        assertThat(top3Objects.size(), is(1));

    }

    @Test
    public void givenRankingSizeOf3TheCommandWillAlwaysReturnAtMax3Expressions() throws Exception {


        //given
        given(requestPreferencesMock.getHeatmapMatrixSize()).willReturn(3);
        //and
        when(geneProfileInputStreamBuilderMock.create()).thenReturn(largeInputStream);


        //when
        List<GeneProfile> top3Objects = subject.apply("AN_ACCESSION");

        //then
        assertThat(top3Objects.size(), is(3));

    }

    @Test
    public void rankedObjectsShouldBeInAscendingOrder() throws Exception {

        //given
        when(requestPreferencesMock.isRankGenesExpressedOnMostFactorsLast()).thenReturn(false);

        //when
        List<GeneProfile> top3Objects = subject.apply("ANY_ACCESSION");

        //and
        assertThat(top3Objects.get(0).getSpecificity(), is(5));
        //and
        assertThat(top3Objects.get(0).getMaxExpressionLevel(), is(5D));
        //then
        assertThat(top3Objects.get(0).getGeneId(), is("5"));

        //and
        assertThat(top3Objects.get(2).getSpecificity(), is(3));
        //and
        assertThat(top3Objects.get(2).getMaxExpressionLevel(), is(3D));
        //and
        assertThat(top3Objects.get(2).getGeneId(), is("3"));

    }

    @Test
    public void rankedObjectsShouldBeInDescendingOrder() throws Exception {

        //when
        List<GeneProfile> top3Objects = subject.apply("ANY_ACCESSION");

        //and
        assertThat(top3Objects.get(0).getSpecificity(), is(1));
        //and
        assertThat(top3Objects.get(0).getMaxExpressionLevel(), is(1D));
        //then
        assertThat(top3Objects.get(0).getGeneId(), is("1"));

        //and
        assertThat(top3Objects.get(2).getSpecificity(), is(3));
        //and
        assertThat(top3Objects.get(2).getMaxExpressionLevel(), is(3D));
        //and
        assertThat(top3Objects.get(2).getGeneId(), is("3"));

    }

}
