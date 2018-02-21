package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions;

import org.junit.Test;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.DummyTupleStream.TUPLE_STREAM_SOURCE_SIZE;

public class TopStreamBuilderTest {
    public static final int N = 20;

    @Test
    public void keepsOnlyNElements() {
        TupleStreamBuilder<AnalyticsCollectionProxy> tupleStreamBuilderMock = DummyTupleStream::new;

        TopStreamBuilder<AnalyticsCollectionProxy> subject =
                new TopStreamBuilder<>(tupleStreamBuilderMock, N, "field1");

        assertThat(TupleStreamer.of(subject.build()).get().collect(toList()))
                .hasSize(20)
                .first().matches(tuple -> tuple.getLong("field1") == TUPLE_STREAM_SOURCE_SIZE - 1);
    }
}