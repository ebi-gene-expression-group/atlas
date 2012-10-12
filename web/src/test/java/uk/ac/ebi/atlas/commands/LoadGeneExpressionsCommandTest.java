package uk.ac.ebi.atlas.commands;

import com.google.common.cache.LoadingCache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.GeneExpression;
import uk.ac.ebi.atlas.model.GeneExpressionsList;
import uk.ac.ebi.atlas.model.GeneProfile;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoadGeneExpressionsCommandTest {

    public static final String EXPERIMENT_ACCESSION = "ACCESSION_VALUE";

//    @Mock
//    private GeneProfilesInputStreamBuilder inputStreamBuilderMock;

    @Mock
    private LoadingCache<String, List<ExperimentRun>> experimentsMock;

    @Mock
    ObjectInputStream<GeneProfile> inputStream;

    @Mock
    RankBySpecificityAndExpressionLevelCommand rankBySpecificityObjectsCommand;

    private GeneExpressionsList top10ExpressionsMock;

    private LoadGeneExpressionsCommand subject;

    @Before
    public void init() throws Exception {

        top10ExpressionsMock = new GeneExpressionsList();
        top10ExpressionsMock.add(mock(GeneExpression.class));

        when(rankBySpecificityObjectsCommand.setRankingSize(anyInt())).thenReturn(rankBySpecificityObjectsCommand);
        when(rankBySpecificityObjectsCommand.apply(inputStream)).thenReturn(top10ExpressionsMock);

        subject = new LoadGeneExpressionsCommand(experimentsMock, rankBySpecificityObjectsCommand);
    }

    @Test(expected = IllegalStateException.class)
    public void whenGetFromCacheFailsCacheShallThrowIllegalStateException() throws ExecutionException {
        //given
        given(experimentsMock.get("")).willThrow(new ExecutionException(new MalformedURLException()));
        //when
        subject.getExperimentRuns("");
        //then should throw IllegalStateException

    }

/*  ToDo: this can't be tested because actually GeneProfileInputStream nor its Builder can be mocked. We should inject Builder
    @Test
    public void rankingShouldBuildAnInputStreamAndUseItWithARankCommand() throws Exception {
        //when
        List<GeneExpression> geneExpressions = subject.apply(EXPERIMENT_ACCESSION);

        //then
        verify(experimentsMock).get(EXPERIMENT_ACCESSION);
        //and
        verify(rankBySpecificityObjectsCommand).apply(any(GeneProfilesInputStream.class));

    }
*/

}
