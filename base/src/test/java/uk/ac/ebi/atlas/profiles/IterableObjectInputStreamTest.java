package uk.ac.ebi.atlas.profiles;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IterableObjectInputStreamTest {
    private static final int MAX_STREAM_SIZE = 10000;

    private int streamSize;
    private DummyObjectInputStream spyInputStream;

    private IterableObjectInputStream<Object> subject;

    @Before
    public void setUp() throws Exception {
        streamSize = ThreadLocalRandom.current().nextInt(0, MAX_STREAM_SIZE);
        spyInputStream = Mockito.spy(new DummyObjectInputStream(streamSize));
        subject = new IterableObjectInputStream<>(spyInputStream);
    }

    @Test
    public void nextClosesTheStreamWhenExhausted() {
        Iterator<Object> subjectIterator = subject.iterator();
        for (int i = 0; i < streamSize; i++) {
            assertThat(subjectIterator.next(), is(not(nullValue())));
        }
        assertThat(subjectIterator.next(), is(nullValue()));
        verify(spyInputStream, atLeastOnce()).close();
    }

    @Test
    public void callToIteratorConsumesObjectFromStream() {
        for (int i = 0; i < streamSize; i++) {
            assertThat(subject.iterator().hasNext(), is(true));
        }
        assertThat(subject.iterator().hasNext(), is(false));
    }

    class DummyObjectInputStream implements ObjectInputStream<Object> {
        private int count;

        DummyObjectInputStream(int elements) {
            count = elements;
        }

        @Override
        public Object readNext() {
            if (count > 0) {
                count = count - 1;
                return new Object();
            } else {
                return null;
            }
        }

        @Override
        public void close() {}
    }
}
