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

    @Test
    void solrClientCacheIsSetInStreamContext() {
        new DummyTupleStreamBuilder().build();
        ArgumentCaptor<StreamContext> argument = ArgumentCaptor.forClass(StreamContext.class);
        verify(tupleStreamMock).setStreamContext(argument.capture());
        assertThat(argument.getValue().getSolrClientCache()).isNotNull();
    }
}