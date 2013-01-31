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
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.GeneProfileInputStreamMock;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamBuilder;
import uk.ac.ebi.atlas.streams.RankingParameters;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RankGeneProfilesCommandTest {

    @Mock
    private GeneProfileInputStreamBuilder geneProfileInputStreamBuilderMock;

    @Mock
    private SolrClient solrClientMock;

    @Mock
    private ExperimentsCache experimentsCacheMock;

    @Mock
    private Experiment experimentMock;

    @Mock
    private FilterParameters filterParameters;

    @Mock
    private RankingParameters rankingParametersMock;

    private ObjectInputStream<GeneProfile> largeInputStream;

    private ObjectInputStream<GeneProfile> smallInputStream;

    private RankGeneProfilesCommand subject;

    public RankGeneProfilesCommandTest() {
    }

    @Before
    public void initializeSubject() throws Exception {

        // no filtering should be done here
        when(solrClientMock.findGeneIds(anyString(), anyString())).thenReturn(Lists.<String>newArrayList());

        when(experimentMock.getSpecie()).thenReturn("SPECIE");

        when(experimentsCacheMock.getExperiment(anyString())).thenReturn(experimentMock);

        when(geneProfileInputStreamBuilderMock.forExperiment(anyString())).thenReturn(geneProfileInputStreamBuilderMock);

        when(rankingParametersMock.getHeatmapMatrixSize()).thenReturn(100);
        when(filterParameters.getCutoff()).thenReturn(0.1);
        when(rankingParametersMock.isSpecific()).thenReturn(true);

        //a stream with 5 profile of 2 expressions
        largeInputStream = new GeneProfileInputStreamMock(5);

        //a stream with 1 profile of 2 expressions
        smallInputStream = new GeneProfileInputStreamMock(1);

        when(geneProfileInputStreamBuilderMock.createGeneProfileInputStream()).thenReturn(largeInputStream);

        subject = new RankGeneProfilesCommand();

        subject.setGeneProfileInputStreamBuilder(geneProfileInputStreamBuilderMock);

        subject.setFilteredParameters(filterParameters);
        subject.setRankingParameters(rankingParametersMock);

        subject.setExperimentsCache(experimentsCacheMock);

    }

    @Test
    public void commandBuildsGeneProfileInputStream() {
        //when
        subject.apply("ANY_EXPERIMENT_ACCESSION");
        //then
        verify(geneProfileInputStreamBuilderMock).forExperiment("ANY_EXPERIMENT_ACCESSION");
        verify(geneProfileInputStreamBuilderMock).createGeneProfileInputStream();
    }


    @Test
    public void givenAStreamWithLessExpressionsThanRankSizeTheCommandShouldReturnAllTheExpressions() throws Exception {
        //given
        given(geneProfileInputStreamBuilderMock.createGeneProfileInputStream()).willReturn(smallInputStream);
        //when
        List<GeneProfile> top3Objects = subject.apply("ANY_ACCESSION");

        //then
        assertThat(top3Objects.size(), is(1));

    }

    @Test
    public void givenRankingSizeOf3TheCommandWillAlwaysReturnAtMax3Expressions() throws Exception {


        //given
        given(rankingParametersMock.getHeatmapMatrixSize()).willReturn(3);
        //and
        when(geneProfileInputStreamBuilderMock.createGeneProfileInputStream()).thenReturn(largeInputStream);


        //when
        List<GeneProfile> top3Objects = subject.apply("AN_ACCESSION");

        //then
        assertThat(top3Objects.size(), is(3));

    }

    @Test
    public void rankedObjectsShouldBeInAscendingOrder() throws Exception {

        //given
        when(rankingParametersMock.isSpecific()).thenReturn(false);

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
