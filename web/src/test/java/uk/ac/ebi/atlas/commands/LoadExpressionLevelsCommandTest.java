package uk.ac.ebi.atlas.commands;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.TranscriptExpressionLevel;
import uk.ac.ebi.atlas.streams.ExpressionLevelInputStreamBuilder;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoadExpressionLevelsCommandTest {

    public static final String EXPERIMENT_ACCESSION = "ACCESSION_VALUE";

    @Mock
    ExpressionLevelInputStreamBuilder inputStreamBuilder;

    @Mock
    ObjectInputStream<TranscriptExpressionLevel> inputStream;

    @Mock
    RankTopObjectsCommand<TranscriptExpressionLevel> rankObjectsCommandCommand;

    private List<TranscriptExpressionLevel> top10LevelsMock = Lists.newArrayList(mock(TranscriptExpressionLevel.class));

    private LoadExpressionLevelsCommand subject;

    @Before
    public void init() throws Exception {
        when(inputStreamBuilder.createFor(EXPERIMENT_ACCESSION)).thenReturn(inputStream);
        when(rankObjectsCommandCommand.setRankingSize(anyInt())).thenReturn(rankObjectsCommandCommand);
        when(rankObjectsCommandCommand.apply(inputStream)).thenReturn(top10LevelsMock);

        subject = new LoadExpressionLevelsCommand(inputStreamBuilder, rankObjectsCommandCommand);
    }

    @Test
    public void rankingShouldBuildAnObjectInputStreamAndUseItWithARankObjectsCommand() throws Exception {
        //when
        List<TranscriptExpressionLevel> transcriptExpressionLevels = subject.apply(EXPERIMENT_ACCESSION);

        //then
        verify(inputStreamBuilder).createFor(EXPERIMENT_ACCESSION);
        //and
        verify(rankObjectsCommandCommand).apply(inputStream);
        //and
        assertThat(transcriptExpressionLevels, is(top10LevelsMock));

    }

    @Test
    public void testSetRankingSize() throws Exception {

    }
}
