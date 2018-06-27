package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator;

import org.junit.Test;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.DummyTupleStreamBuilder;

import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class TopStreamBuilderTest {
    public static final int N = 20;

    @Test
    public void keepsOnlyNElements() {
        int size = ThreadLocalRandom.current().nextInt(1, 1000);
        DummyTupleStreamBuilder<AnalyticsCollectionProxy> tupleStreamBuilderMock =
                DummyTupleStreamBuilder.create(size);

        int topN = ThreadLocalRandom.current().nextInt(1, 1000);
        TopStreamBuilder subject = new TopStreamBuilder(tupleStreamBuilderMock, topN, "field1");

        assertThat(TupleStreamer.of(subject.build()).get().collect(toList()))
                .hasSize(Math.min(size, topN))
                .first().matches(tuple -> tuple.getLong("field1") == size - 1);
    }
}