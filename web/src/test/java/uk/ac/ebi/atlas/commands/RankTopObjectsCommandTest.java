package uk.ac.ebi.atlas.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.ObjectInputStream;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RankTopObjectsCommandTest {

    private static final int QUEUE_SIZE = 3;

    @Mock
    private ObjectInputStream<String> largeInputStream;

    @Mock
    private ObjectInputStream<String> smallInputStream;

    private RankTopObjectsCommand<String> subject;

    @Before
    public void initializeInputStreamMock() throws Exception {
        when(largeInputStream.readNext()).thenReturn("4")
                .thenReturn("1")
                .thenReturn("7")
                .thenReturn("0")
                .thenReturn("7")
                .thenReturn("2")
                .thenReturn("5")
                .thenReturn(null);

        when(smallInputStream.readNext()).thenReturn("0")
                .thenReturn("1")
                .thenReturn(null);

    }

    @Before
    public void initializeSubject() throws Exception {
        subject = new RankTopObjectsCommand<String>().setRankingSize(QUEUE_SIZE);
    }

    @Test
    public void givenAStreamWithLessObjectsThanRankSizeTheCommandShouldReturnAllTheObjects() throws Exception {
        //when
        List<String> top3Objects = subject.apply(smallInputStream);

        //then
        assertThat(top3Objects.size(), is(2));
        //and
        assertThat(top3Objects, contains("1", "0")); //contains also checks order

    }

    @Test
    public void givenAStreamWithManyObjectsTheCommandShouldReturnThreeObjects() throws Exception {
        //when
        List<String> top3Objects = subject.apply(largeInputStream);

        //then
        assertThat(top3Objects.size(), is(3));

    }

    @Test
    public void rankedObjectsShouldBeInDescendingOrder() throws Exception {
        //when
        List<String> top3Objects = subject.apply(largeInputStream);

        //then
        assertThat(top3Objects, contains("7", "7", "5")); //contains also checks order

    }

}
