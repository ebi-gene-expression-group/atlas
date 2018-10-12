package uk.ac.ebi.atlas.commons.streams;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ObjectInputStreamsTest {
    @Mock
    private ObjectInputStream<Integer> inputStreamMock;

    private ObjectInputStream<Integer> oneTwoThreeNull() {
        return inputStreamMock;
    }

    @Before
    public void setUp() throws Exception {

        when(inputStreamMock.readNext()).thenReturn(1, 2, 3, null);
    }

    @Test
    public void alwaysTrue() {
        ObjectInputStream<Integer> out = ObjectInputStreams.filter(oneTwoThreeNull(), x -> true);
        assertThat(out.readNext(), is(1));
        assertThat(out.readNext(), is(2));
        assertThat(out.readNext(), is(3));
        assertThat(out.readNext(), is(nullValue()));
    }

    @Test
    public void alwaysFalse() {
        ObjectInputStream<Integer> out = ObjectInputStreams.filter(oneTwoThreeNull(), x -> false);
        assertThat(out.readNext(), is(nullValue()));
    }

    @Test
    public void keepOne() {
        int toKeep = ThreadLocalRandom.current().nextInt(1, 4);
        ObjectInputStream<Integer> out = ObjectInputStreams.filter(oneTwoThreeNull(), x -> x.equals(toKeep));
        assertThat(out.readNext(), is(toKeep));
        assertThat(out.readNext(), is(nullValue()));
    }
}
