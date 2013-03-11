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

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.GeneProfile;
import uk.ac.ebi.atlas.model.baseline.GeneProfileInputStreamMock;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RankGeneProfilesCommandTest {

    private static final String SPECIES = "Species 1";
    private static final String EXPERIMENT_ACCESSION = "ANY_EXPERIMENT_ACCESSION";
    private static final String GENE_QUERY = "A GENE QUERY";

    @Mock
    private InputStreamFactory inputStreamFactoryMock;

    @Mock
    private SolrClient solrClientMock;

    @Mock
    private BaselineExperiment experimentMock;

    @Mock
    private ExperimentalFactors experimentalFactors;

    @Mock
    private RequestContext requestContextMock;

    private ObjectInputStream<GeneProfile> largeInputStream;

    private ObjectInputStream<GeneProfile> smallInputStream;

    private RankGeneProfilesCommand subject;

    public RankGeneProfilesCommandTest() {
    }

    //ToDo: better to do verifications on real values that on anyX(), using anyX() could hide bugs
    @Before
    public void initializeSubject() throws Exception {

        when(requestContextMock.getGeneQuery()).thenReturn("");

        // no filtering should be done here
        when(solrClientMock.findGeneIds(GENE_QUERY, SPECIES)).thenReturn(Sets.<String>newHashSet("A GENE IDENTIFIER"));

        when(requestContextMock.getFilteredBySpecies()).thenReturn(SPECIES);

        when(experimentMock.getExperimentalFactors()).thenReturn(experimentalFactors);
        when(experimentMock.getExperimentAccession()).thenReturn(EXPERIMENT_ACCESSION);

        when(requestContextMock.getHeatmapMatrixSize()).thenReturn(100);
        when(requestContextMock.isSpecific()).thenReturn(true);

        //a stream with 5 profile of 2 expressions
        largeInputStream = new GeneProfileInputStreamMock(5);

        //a stream with 1 profile of 2 expressions
        smallInputStream = new GeneProfileInputStreamMock(1);

        when(inputStreamFactoryMock.createGeneProfileInputStream(EXPERIMENT_ACCESSION)).thenReturn(largeInputStream);

        subject = new RankGeneProfilesCommand();
        subject.setSolrClient(solrClientMock);
        subject.setInputStreamFactory(inputStreamFactoryMock);

        subject.setRequestContext(requestContextMock);

    }

    @Test
    public void commandBuildsGeneProfileInputStream() throws GeneNotFoundException{
        //when
        subject.apply(experimentMock);
        //then
        verify(inputStreamFactoryMock).createGeneProfileInputStream(EXPERIMENT_ACCESSION);
    }


    @Test
    public void givenAStreamWithLessExpressionsThanRankSizeTheCommandShouldReturnAllTheExpressions() throws Exception {
        //given
        given(inputStreamFactoryMock.createGeneProfileInputStream(EXPERIMENT_ACCESSION)).willReturn(smallInputStream);
        //when
        List<GeneProfile> top3Objects = subject.apply(experimentMock);

        //then
        assertThat(top3Objects.size(), is(1));

    }

    @Test
    public void givenRankingSizeOf3TheCommandWillAlwaysReturnAtMax3Expressions() throws Exception {


        //given
        given(requestContextMock.getHeatmapMatrixSize()).willReturn(3);
        //and
        when(inputStreamFactoryMock.createGeneProfileInputStream(EXPERIMENT_ACCESSION)).thenReturn(largeInputStream);


        //when
        List<GeneProfile> top3Objects = subject.apply(experimentMock);

        //then
        assertThat(top3Objects.size(), is(3));

    }

    @Test
    public void givenEmptyGeneQuerySolrClientFindGeneIdsShouldNotBeInvoked() throws GeneNotFoundException{
        when(requestContextMock.getGeneQuery()).thenReturn("");
        subject.apply(experimentMock);
        verify(solrClientMock, times(0)).findGeneIds(GENE_QUERY, SPECIES);
    }

    @Test
    public void givenEmptyFilterFactorSpeciesShouldBeTakenFromExperiment() {

        when(requestContextMock.getGeneQuery()).thenReturn(GENE_QUERY);
        subject.searchForGeneIds();
        verify(solrClientMock).findGeneIds(GENE_QUERY, SPECIES);

    }

    @Test
    public void givenAFilterFactorHasTypeOrganismSpeciesShouldBeTakenFromTheFilterFactor() {


        when(requestContextMock.getGeneQuery()).thenReturn(GENE_QUERY);
        subject.searchForGeneIds();
        verify(solrClientMock).findGeneIds(GENE_QUERY, SPECIES);

    }

    @Test
    public void rankedObjectsShouldBeInAscendingOrder() throws Exception {

        //given
        when(requestContextMock.isSpecific()).thenReturn(false);

        //when
        List<GeneProfile> top3Objects = subject.apply(experimentMock);

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
        List<GeneProfile> top3Objects = subject.apply(experimentMock);

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
