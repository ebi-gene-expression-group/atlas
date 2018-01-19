package uk.ac.ebi.atlas.solr.cloud;

import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.io.Tuple;
import org.apache.solr.client.solrj.io.stream.TupleStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TupleStreamAutoCloseableIteratorTest {
    private static final int MAX_NUM_DOCS = 10000;

    @Mock
    private TupleStream tupleStreamMock;

    private List<Tuple> tuples;

    @Before
    public void setUp() throws IOException {
        doNothing().when(tupleStreamMock).open();
        doNothing().when(tupleStreamMock).close();

        tuples =
                IntStream.rangeClosed(1, ThreadLocalRandom.current().nextInt(0, MAX_NUM_DOCS))
                        .boxed()
                        .map(i -> new Tuple(ImmutableMap.of("field", "value" + Integer.toString(i))))
                        .collect(Collectors.toList());

        Tuple tupleEof = new Tuple();
        tupleEof.EOF = true;
        tuples.add(tupleEof);

        when(tupleStreamMock.read()).thenReturn(tuples.get(0), tuples.subList(1, tuples.size()).toArray(new Tuple[0]));
    }

    @Test
    public void underlyingTupleStreamIsAutoClosed() throws IOException {
        try (TupleStreamAutoCloseableIterator subject = new TupleStreamAutoCloseableIterator(tupleStreamMock)) {
            Iterable<Tuple> iterable = () -> subject;
            Stream<Tuple> stream = StreamSupport.stream(iterable.spliterator(), false);
            assertThat(stream.collect(Collectors.toList())).isNotEmpty();
        }

        verify(tupleStreamMock).close();
    }

    @Test
    public void eofTupleIsExcluded() throws IOException {
        try (TupleStreamAutoCloseableIterator subject = new TupleStreamAutoCloseableIterator(tupleStreamMock)) {
            Iterable<Tuple> iterable = () -> subject;
            Stream<Tuple> stream = StreamSupport.stream(iterable.spliterator(), false);
            assertThat(stream.collect(Collectors.toList()))
                    .hasSize(tuples.size() - 1)
                    .allSatisfy(tuple -> assertThat(tuple.EOF).isEqualTo(false));
        }
    }

    @Test(expected=IllegalStateException.class)
    public void openIOExceptionIsWrapped() throws IOException {
        doThrow(new IOException()).when(tupleStreamMock).open();
        new TupleStreamAutoCloseableIterator(tupleStreamMock);
    }

    @Test(expected=IllegalStateException.class)
    public void readIOExceptionIsWrapped() throws IOException {
        when(tupleStreamMock.read()).thenReturn(tuples.get(0)).thenThrow(new IOException());
        try (TupleStreamAutoCloseableIterator subject = new TupleStreamAutoCloseableIterator(tupleStreamMock)) {
            Iterable<Tuple> iterable = () -> subject;
            Stream<Tuple> stream = StreamSupport.stream(iterable.spliterator(), false);
            stream.collect(Collectors.toList());
        }
    }
}