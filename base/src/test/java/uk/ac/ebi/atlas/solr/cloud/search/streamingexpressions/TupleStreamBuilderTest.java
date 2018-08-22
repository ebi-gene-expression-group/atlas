package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions;

import org.apache.solr.client.solrj.io.stream.StreamContext;
import org.apache.solr.client.solrj.io.stream.TupleStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TupleStreamBuilderTest {
    @Mock
    private TupleStream tupleStreamMock;

    private class DummyTupleStreamBuilder extends TupleStreamBuilder {
        @Override
        protected TupleStream getRawTupleStream() {
            return tupleStreamMock;
        }
    }

    @BeforeEach
    void setUp() {
        doNothing().when(tupleStreamMock).setStreamContext(any());
    }

    // Creating a cache each time (and not closing it) leaks a connection in ZooKeeper, so not reusing it is a bug
    @Test
    void solrClientCacheIsReusedInStreamContext() {
        new DummyTupleStreamBuilder().build();
        new DummyTupleStreamBuilder().build();
        ArgumentCaptor<StreamContext> argument = ArgumentCaptor.forClass(StreamContext.class);
        verify(tupleStreamMock, times(2)).setStreamContext(argument.capture());

        assertThat(argument.getAllValues().get(0).getSolrClientCache())
                .isSameAs(argument.getAllValues().get(1).getSolrClientCache());
    }
}
