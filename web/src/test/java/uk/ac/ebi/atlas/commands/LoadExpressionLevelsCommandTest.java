package uk.ac.ebi.atlas.commands;

import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.TranscriptExpression;
import uk.ac.ebi.atlas.model.TranscriptProfile;

import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoadExpressionLevelsCommandTest {

    public static final String EXPERIMENT_ACCESSION = "ACCESSION_VALUE";

//    @Mock
//    private TranscriptProfilesInputStreamBuilder inputStreamBuilderMock;

    @Mock
    private LoadingCache<String, List<ExperimentRun>> experimentRunsMock;

    @Mock
    ObjectInputStream<TranscriptProfile> inputStream;

    @Mock
    RankAndConvertTopObjectsCommand rankObjectsCommand;

    private List<TranscriptExpression> top10LevelsMock = Lists.newArrayList(mock(TranscriptExpression.class));

    private LoadExpressionLevelsCommand subject;

    @Before
    public void init() throws Exception {

        when(rankObjectsCommand.setRankingSize(anyInt())).thenReturn(rankObjectsCommand);
        when(rankObjectsCommand.apply(inputStream)).thenReturn(top10LevelsMock);

        subject = new LoadExpressionLevelsCommand(experimentRunsMock, rankObjectsCommand);
    }
/*  ToDo
    @Test
    public void rankingShouldBuildAnObjectInputStreamAndUseItWithARankObjectsCommand() throws Exception {
        //when
        List<TranscriptExpression> transcriptExpressionLevels = subject.apply(EXPERIMENT_ACCESSION);

        //and
        verify(rankObjectsCommand).apply(inputStream);
        //and
        assertThat(transcriptExpressionLevels, is(top10LevelsMock));

    }
*/
    @Test
    public void testSetRankingSize() throws Exception {

    }
}
