package uk.ac.ebi.atlas.commons.streams;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Vector;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SequenceObjectInputStreamTest {
    private static final Object RETURN_VALUE1 = new Object();
    private static final Object RETURN_VALUE2 = new Object();

    @Mock
    private ObjectInputStream<Object> objectInputStreamMock1;

    @Mock
    private ObjectInputStream<Object> objectInputStreamMock2;

    private SequenceObjectInputStream<Object> subject;

    @Before
    public void setUp() throws Exception {
        Vector<ObjectInputStream<Object>> inputStreamVector = new Vector<>();
        inputStreamVector.add(objectInputStreamMock1);
        inputStreamVector.add(objectInputStreamMock2);
        subject = new SequenceObjectInputStream<>(inputStreamVector.elements());
    }

    @Test
    public void testReadNext() throws Exception {
        when(objectInputStreamMock1.readNext()).thenReturn(RETURN_VALUE1);
        assertThat(subject.readNext(), is(RETURN_VALUE1));
        verify(objectInputStreamMock1).readNext();
        when(objectInputStreamMock1.readNext()).thenReturn(null);

        when(objectInputStreamMock2.readNext()).thenReturn(RETURN_VALUE2);
        assertThat(subject.readNext(), is(RETURN_VALUE2));
        verify(objectInputStreamMock2).readNext();
        verify(objectInputStreamMock1).close();
        when(objectInputStreamMock2.readNext()).thenReturn(null);

        assertThat(subject.readNext(), is(nullValue()));
        verify(objectInputStreamMock2).close();
    }

    @Test
    public void testClose() throws Exception {
        subject.close();
        verify(objectInputStreamMock1).close();
        verify(objectInputStreamMock2).close();
    }
}
