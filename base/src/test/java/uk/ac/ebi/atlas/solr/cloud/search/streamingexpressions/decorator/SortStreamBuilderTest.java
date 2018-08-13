package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.io.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.DummyTupleStreamBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.TupleStreamBuilder;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static org.assertj.core.api.Assertions.assertThat;

class SortStreamBuilderTest {
    private class DummyCollectionProxy extends CollectionProxy {
        protected DummyCollectionProxy(SolrClient solrClient, String nameOrAlias) {
            super(solrClient, nameOrAlias);
        }
    }

    private static final int TEST_STREAM_MAX_SIZE = 10000;
    private static final int TEST_STREAM_MAX_VALUE = TEST_STREAM_MAX_SIZE * 10;

    private ImmutableList<Tuple> streamContents;
    private DummyTupleStreamBuilder<DummyCollectionProxy> tupleStreamBuilderMock;
    private Function<Tuple, Long> field2Mapper = tuple -> tuple.getLong("field2");

    @BeforeEach
    void setUp() {
        streamContents =
                IntStream.range(0, ThreadLocalRandom.current().nextInt(1, TEST_STREAM_MAX_SIZE))
                        .boxed()
                        .map(i ->
                                new Tuple(ImmutableMap.of(
                                        "field1", i,
                                        "field2", ThreadLocalRandom.current().nextInt(0, TEST_STREAM_MAX_VALUE))))
                        .collect(toImmutableList());
        tupleStreamBuilderMock = DummyTupleStreamBuilder.create(streamContents, "field1", true);
    }


    @Test
    void sortsValues() {
        assertAboutSortStreamBuilder(
                tupleStreamBuilderMock, "field2",
                (tupleStreamer) ->
                    assertThat(tupleStreamer.get().map(field2Mapper))
                            .containsExactly(streamContents.stream().map(field2Mapper).sorted().toArray(Long[]::new)));
    }

    @Test
    void sortingOnNonExistingFieldLeavesStreamUnmodified() {
        assertAboutSortStreamBuilder(
                tupleStreamBuilderMock, "field3",
                (tupleStreamer) ->
                    assertThat(tupleStreamer.get().map(field2Mapper))
                            .containsExactly(streamContents.stream().map(field2Mapper).toArray(Long[]::new)));
    }

    // A way to make assertions with try-with-resources
    private static <T extends CollectionProxy> void assertAboutSortStreamBuilder(
            TupleStreamBuilder<T> tupleStreamBuilder1,
            String fieldName,
            Consumer<TupleStreamer> assertionOverTupleStreamer) {

        try (TupleStreamer tupleStreamer =
                     TupleStreamer.of(new SortStreamBuilder(tupleStreamBuilder1, fieldName).build())) {
            assertionOverTupleStreamer.accept(tupleStreamer);
        }

    }
}