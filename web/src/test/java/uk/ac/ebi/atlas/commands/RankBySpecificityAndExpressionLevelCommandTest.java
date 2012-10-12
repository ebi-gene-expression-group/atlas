package uk.ac.ebi.atlas.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneExpression;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;
import uk.ac.ebi.atlas.web.controllers.RequestPreferences;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RankBySpecificityAndExpressionLevelCommandTest {

    @Mock
    private GeneProfilesInputStream.Builder geneProfileInputStreamBuilderMock;

    @Mock
    private RequestPreferences requestPreferencesMock;

    private ObjectInputStream<GeneProfile> largeInputStream;

    private ObjectInputStream<GeneProfile> smallInputStream;

    private RankBySpecificityAndExpressionLevelCommand subject;

    public RankBySpecificityAndExpressionLevelCommandTest() {
    }

    @Before
    public void initializeSubject() throws Exception {

        when(geneProfileInputStreamBuilderMock.forDataFileURL(anyString())).thenReturn(geneProfileInputStreamBuilderMock);
        when(geneProfileInputStreamBuilderMock.withExperimentAccession(anyString())).thenReturn(geneProfileInputStreamBuilderMock);
        when(geneProfileInputStreamBuilderMock.withCutoff(anyDouble())).thenReturn(geneProfileInputStreamBuilderMock);


        //a stream with 5 profile of 2 expressions
        largeInputStream = new GeneProfileInputStreamMock(5);

        //a stream with 1 profile of 2 expressions
        smallInputStream = new GeneProfileInputStreamMock(1);

        subject = new RankBySpecificityAndExpressionLevelCommand(geneProfileInputStreamBuilderMock);

        subject.setRequestPreferences(requestPreferencesMock);
    }

    @Test
    public void givenAStreamWithLessExpressionsThanRankSizeTheCommandShouldReturnAllTheExpressions() throws Exception {
        //when
//        List<GeneExpression> top3Objects = subject.apply(smallInputStream);

        //then
//        assertThat(top3Objects.size(), is(1));

    }

    @Test
    public void givenRankingSizeOf3TheCommandWillAlwaysReturnAtMax3Expressions() throws Exception {


        //given
        given(requestPreferencesMock.getRankingSize()).willReturn(3);
        //and
        when(geneProfileInputStreamBuilderMock.create()).thenReturn(largeInputStream);


        //when
        List<GeneExpression> top3Objects = subject.apply("AN_ACCESSION");

        //then
        assertThat(top3Objects.size(), is(3));

    }
/*
    @Test
    public void rankedObjectsShouldBeInDescendingOrder() throws Exception {
        //when
        List<GeneExpression> top3Objects = subject.apply(largeInputStream);

        //and
        assertThat(top3Objects.get(0).getSpecificity(), is(1));
        //and
        assertThat(top3Objects.get(0).getLevel(), is(1D));
        //then
        assertThat(top3Objects.get(0).getGeneId(), is("1"));

        //and
        assertThat(top3Objects.get(2).getSpecificity(), is(2));
        //and
        assertThat(top3Objects.get(2).getLevel(), is(1D));
        //and
        assertThat(top3Objects.get(2).getGeneId(), is("2"));

    }

    @Test
    public void givenFilterByFirstOrganism() throws Exception {
        //when
        subject.setOrganismParts(Sets.newHashSet("org1"));
        List<GeneExpression> top3Objects = subject.apply(largeInputStream);

        //then
        assertThat(top3Objects.size(), is(3));

    }

    @Test
    public void givenFilterByLastOrganism() throws Exception {
        //when
        subject.setOrganismParts(Sets.newHashSet("org5"));
        List<GeneExpression> top3Objects = subject.apply(largeInputStream);

        //then
        assertThat(top3Objects.size(), is(1));

    }
*/
/* ToDo: this stuff is terrible
        @Test
        public void givenFilterByNotOverlappingOrganismAndGeneNameShouldReturnNoResult() throws
                Exception {
            //when
            subject.setOrganismParts(Sets.newHashSet("org5"));
            //subject.setGeneIDs(Sets.newHashSet("1"));
            List<GeneExpression> top3Objects = subject.apply(largeInputStream);

            //then
            assertThat(top3Objects.size(), is(0));
        }
    */

    /*
    @Test
    public void givenFilterByLastOrganismAndLastGenes() throws Exception {
        //when
        subject.setOrganismParts(Sets.newHashSet("org4"));
        //subject.setGeneIDs(Sets.newHashSet("4", "5"));

        List<GeneExpression> top3Objects = subject.apply(largeInputStream);

        //then
        assertThat(top3Objects.size(), is(2));

    }

    */
/* ToDo: this stuff is terrible
    @Test
    public void givenFilterByTwoOrganismAndTwoGenes() throws Exception {
        //when
        subject.setOrganismParts(Sets.newHashSet("org3", "org4"));
        //subject.setGeneIDs(Sets.newHashSet("3", "5"));
        subject.setRankingSize(5);

        List<GeneExpression> top3Objects = subject.apply(largeInputStream);

        //then
        assertThat(top3Objects.size(), is(3));

        //and
        assertThat(top3Objects.get(0).getSpecificity(), is(3));
        //and
        assertThat(top3Objects.get(0).getLevel(), is(3D));
        //then
        assertThat(top3Objects.get(0).getGeneId(), is("3"));

        //and
        assertThat(top3Objects.get(1).getSpecificity(), is(5));
        //and
        assertThat(top3Objects.get(1).getLevel(), is(4D));
        //and
        assertThat(top3Objects.get(1).getGeneId(), is("5"));

        //and
        assertThat(top3Objects.get(2).getSpecificity(), is(5));
        //and
        assertThat(top3Objects.get(2).getLevel(), is(3D));
        //and
        assertThat(top3Objects.get(2).getGeneId(), is("5"));

    }
*/
}
