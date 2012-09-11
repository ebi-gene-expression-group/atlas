package uk.ac.ebi.atlas.commands;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.TranscriptExpression;
import uk.ac.ebi.atlas.model.TranscriptProfile;
import uk.ac.ebi.atlas.streams.TranscriptProfilesInputStreamBuilder;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoadExpressionLevelsCommandTest {

    public static final String EXPERIMENT_ACCESSION = "ACCESSION_VALUE";

    @Mock
    TranscriptProfilesInputStreamBuilder inputStreamBuilder;

    @Mock
    ObjectInputStream<TranscriptProfile> inputStream;

    @Mock
    RankAndConvertTopObjectsCommand rankObjectsCommand;

    private List<TranscriptExpression> top10LevelsMock = Lists.newArrayList(mock(TranscriptExpression.class));

    private LoadExpressionLevelsCommand subject;

    @Before
    public void init() throws Exception {

        when(inputStreamBuilder.createFor(EXPERIMENT_ACCESSION)).thenReturn(inputStream);

        when(rankObjectsCommand.setRankingSize(anyInt())).thenReturn(rankObjectsCommand);
        when(rankObjectsCommand.apply(inputStream)).thenReturn(top10LevelsMock);

        subject = new LoadExpressionLevelsCommand(inputStreamBuilder, rankObjectsCommand);
    }

    @Test
    public void rankingShouldBuildAnObjectInputStreamAndUseItWithARankObjectsCommand() throws Exception {
        //when
        List<TranscriptExpression> transcriptExpressionLevels = subject.apply(EXPERIMENT_ACCESSION);

        //then
        verify(inputStreamBuilder).createFor(EXPERIMENT_ACCESSION);
        //and
        verify(rankObjectsCommand).apply(inputStream);
        //and
        assertThat(transcriptExpressionLevels, is(top10LevelsMock));

    }

    @Test
    public void testSetRankingSize() throws Exception {

    }
}
