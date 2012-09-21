package uk.ac.ebi.atlas.commands;

import com.google.common.cache.LoadingCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.TranscriptExpression;
import uk.ac.ebi.atlas.model.TranscriptExpressionsList;
import uk.ac.ebi.atlas.model.TranscriptProfile;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoadTranscriptExpressionsCommandTest {

    public static final String EXPERIMENT_ACCESSION = "ACCESSION_VALUE";

//    @Mock
//    private TranscriptProfilesInputStreamBuilder inputStreamBuilderMock;

    @Mock
    private LoadingCache<String, List<ExperimentRun>> experimentsMock;

    @Mock
    ObjectInputStream<TranscriptProfile> inputStream;

    @Mock
    RankBySpecificityAndExpressionLevelCommand rankBySpecificityObjectsCommand;

    private TranscriptExpressionsList top10ExpressionsMock;

    private LoadTranscriptExpressionsCommand subject;

    @Before
    public void init() throws Exception {

        top10ExpressionsMock = new TranscriptExpressionsList();
        top10ExpressionsMock.add(mock(TranscriptExpression.class));

        when(rankBySpecificityObjectsCommand.setRankingSize(anyInt())).thenReturn(rankBySpecificityObjectsCommand);
        when(rankBySpecificityObjectsCommand.apply(inputStream)).thenReturn(top10ExpressionsMock);

        subject = new LoadTranscriptExpressionsCommand(experimentsMock, rankBySpecificityObjectsCommand);
    }

    @Test(expected = IllegalStateException.class)
    public void whenGetFromCacheFailsCacheShallThrowIllegalStateException() throws ExecutionException {
        //given
        given(experimentsMock.get("")).willThrow(new ExecutionException(new MalformedURLException()));
        //when
        subject.getExperimentRuns("");
        //then should throw IllegalStateException

    }

/*  ToDo: this can't be tested because actually TranscriptProfileInputStream nor its Builder can be mocked. We should inject Builder
    @Test
    public void rankingShouldBuildAnInputStreamAndUseItWithARankCommand() throws Exception {
        //when
        List<TranscriptExpression> transcriptExpressions = subject.apply(EXPERIMENT_ACCESSION);

        //then
        verify(experimentsMock).get(EXPERIMENT_ACCESSION);
        //and
        verify(rankBySpecificityObjectsCommand).apply(any(TranscriptProfilesInputStream.class));

    }
*/

    @Test
    public void testSetRankingSize() throws Exception {
        //when
        subject.setRankingSize(1);
        //then
        verify(rankBySpecificityObjectsCommand).setRankingSize(1);
    }


}
