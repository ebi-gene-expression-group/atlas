package uk.ac.ebi.atlas.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.TranscriptExpression;
import uk.ac.ebi.atlas.model.TranscriptProfile;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class RankBySpecificityAndRpkmCommandTest {

    private static final int QUEUE_SIZE = 3;

    private ObjectInputStream<TranscriptProfile> largeInputStream;

    private ObjectInputStream<TranscriptProfile> smallInputStream;

    private RankBySpecificityAndRpkmCommand subject;

    public RankBySpecificityAndRpkmCommandTest() {
    }

    @Before
    public void initializeSubject() throws Exception {

        //a stream with 5 profile of 2 expression levels
        largeInputStream = new TranscriptProfileInputStreamMock(5);

        //a stream with 1 profile of 2 expression levels
        smallInputStream = new TranscriptProfileInputStreamMock(1);


        subject = new RankBySpecificityAndRpkmCommand().setRankingSize(QUEUE_SIZE);
    }

    @Test
    public void givenAStreamWithLessLevelsThanRankSizeTheCommandShouldReturnAllTheLevels() throws Exception {
        //when
        List<TranscriptExpression> top3Objects = subject.apply(smallInputStream);

        //then
        assertThat(top3Objects.size(), is(1));

    }

    @Test
    public void givenAStreamWithManyLevelsTheCommandShouldReturnThreeExpressionLevels() throws Exception {
        //when
        List<TranscriptExpression> top3Objects = subject.apply(largeInputStream);

        //then
        assertThat(top3Objects.size(), is(3));

    }

    @Test
    public void rankedObjectsShouldBeInDescendingOrder() throws Exception {
        //when
        List<TranscriptExpression> top3Objects = subject.apply(largeInputStream);

        //and
        assertThat(top3Objects.get(0).getSpecificity(), is(1));
        //and
        assertThat(top3Objects.get(0).getRpkm(), is(1D));
        //then
        assertThat(top3Objects.get(0).getTranscriptId(), is("1"));

        //and
        assertThat(top3Objects.get(2).getSpecificity(), is(2));
        //and
        assertThat(top3Objects.get(2).getRpkm(), is(1D));
        //and
        assertThat(top3Objects.get(2).getTranscriptId(), is("2"));

    }

}
