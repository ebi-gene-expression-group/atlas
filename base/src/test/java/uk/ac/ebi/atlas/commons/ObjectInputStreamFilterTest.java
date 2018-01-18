package uk.ac.ebi.atlas.commons;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStreamFilter;

import java.io.IOException;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ObjectInputStreamFilterTest {

    @Mock
    private ObjectInputStream<Object> inputStreamMock;

    @Mock
    private Predicate<Object> predicateMock;

    private Object object1 = new Object();
    private Object object2 = new Object();
    private Object object3 = new Object();


    private ObjectInputStreamFilter<Object> subject;

    @Before
    public void initSubject() {
        subject = new ObjectInputStreamFilter<Object>(inputStreamMock){

            @Override
            protected Predicate<Object> getAcceptanceCriteria() {
                return predicateMock;
            }
        };
    }

    @Test
    public void testReadNext() {
        //given
        given(predicateMock.test(any())).willReturn(false)
                                                .willReturn(true)
                                                .willReturn(false);
        //and
        given(inputStreamMock.readNext()).willReturn(object1)
                                         .willReturn(object2)
                                         .willReturn(object3)
                                         .willReturn(null);

        //then
        assertThat(subject.readNext(), is(object2));

        //and reading again
        assertThat(subject.readNext(), is(nullValue()));

    }

    @Test
    public void closeShouldCloseTheWrappedInputStream() throws Exception {
        //when
        subject.close();

        verify(inputStreamMock).close();
    }
}
