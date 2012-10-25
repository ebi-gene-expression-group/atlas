//package uk.ac.ebi.atlas.commands;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import uk.ac.ebi.atlas.commons.ObjectInputStream;
//import uk.ac.ebi.atlas.model.GeneExpression;
//import uk.ac.ebi.atlas.model.GeneProfile;
//import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;
//import uk.ac.ebi.atlas.web.controllers.RequestPreferences;
//
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Matchers.anyDouble;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class RankBySpecificityAndExpressionLevelCommandTest {
//
//    private static final String DATA_FILE_URL = "ANY_URL";
//
//    @Mock
//    private GeneProfilesInputStream.Builder geneProfileInputStreamBuilderMock;
//
//    @Mock
//    private RequestPreferences requestPreferencesMock;
//
//    private ObjectInputStream<GeneProfile> largeInputStream;
//
//    private ObjectInputStream<GeneProfile> smallInputStream;
//
//    private RankBySpecificityAndExpressionLevelCommand subject;
//
//    public RankBySpecificityAndExpressionLevelCommandTest() {
//    }
//
//    @Before
//    public void initializeSubject() throws Exception {
//
//        when(geneProfileInputStreamBuilderMock.forDataFileURL(anyString())).thenReturn(geneProfileInputStreamBuilderMock);
//        when(geneProfileInputStreamBuilderMock.withExperimentAccession(anyString())).thenReturn(geneProfileInputStreamBuilderMock);
//        when(geneProfileInputStreamBuilderMock.withCutoff(anyDouble())).thenReturn(geneProfileInputStreamBuilderMock);
//
//        when(requestPreferencesMock.getHeatmapMatrixSize()).thenReturn(100);
//        when(requestPreferencesMock.getCutoff()).thenReturn(0.1);
//
//        //a stream with 5 profile of 2 expressions
//        largeInputStream = new GeneProfileInputStreamMock(5);
//
//        //a stream with 1 profile of 2 expressions
//        smallInputStream = new GeneProfileInputStreamMock(1);
//
//        when(geneProfileInputStreamBuilderMock.create()).thenReturn(largeInputStream);
//
//        subject = new RankBySpecificityAndExpressionLevelCommand(geneProfileInputStreamBuilderMock, DATA_FILE_URL, null);
//
//        subject.setRequestPreferences(requestPreferencesMock);
//    }
//
//    @Test
//    public void commandBuildsGeneProfileInputStream() {
//        //when
//        subject.apply("ANY_EXPERIMENT_ACCESSION");
//        //then
//        verify(geneProfileInputStreamBuilderMock).forDataFileURL(DATA_FILE_URL);
//        verify(geneProfileInputStreamBuilderMock).withExperimentAccession("ANY_EXPERIMENT_ACCESSION");
//        verify(geneProfileInputStreamBuilderMock).withCutoff(requestPreferencesMock.getCutoff());
//        verify(geneProfileInputStreamBuilderMock).create();
//    }
//
//
//    @Test
//    public void givenAStreamWithLessExpressionsThanRankSizeTheCommandShouldReturnAllTheExpressions() throws Exception {
//        //given
//        given(geneProfileInputStreamBuilderMock.create()).willReturn(smallInputStream);
//        //when
//        List<GeneExpression> top3Objects = subject.apply("ANY_ACCESSION");
//
//        //then
//        assertThat(top3Objects.size(), is(1));
//
//    }
//
//    @Test
//    public void givenRankingSizeOf3TheCommandWillAlwaysReturnAtMax3Expressions() throws Exception {
//
//
//        //given
//        given(requestPreferencesMock.getHeatmapMatrixSize()).willReturn(3);
//        //and
//        when(geneProfileInputStreamBuilderMock.create()).thenReturn(largeInputStream);
//
//
//        //when
//        List<GeneExpression> top3Objects = subject.apply("AN_ACCESSION");
//
//        //then
//        assertThat(top3Objects.size(), is(3));
//
//    }
//
//    @Test
//    public void rankedObjectsShouldBeInDescendingOrder() throws Exception {
//        //when
//        List<GeneExpression> top3Objects = subject.apply("ANY_ACCESSION");
//
//        //and
//        assertThat(top3Objects.get(0).getSpecificity(), is(1));
//        //and
//        assertThat(top3Objects.get(0).getLevel(), is(1D));
//        //then
//        assertThat(top3Objects.get(0).getGeneId(), is("1"));
//
//        //and
//        assertThat(top3Objects.get(2).getSpecificity(), is(2));
//        //and
//        assertThat(top3Objects.get(2).getLevel(), is(1D));
//        //and
//        assertThat(top3Objects.get(2).getGeneId(), is("2"));
//
//    }
//
//}
