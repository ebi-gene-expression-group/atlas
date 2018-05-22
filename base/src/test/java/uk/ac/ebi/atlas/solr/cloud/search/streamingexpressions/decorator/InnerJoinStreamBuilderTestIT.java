package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.io.Tuple;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.DummyTupleStreamBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.TupleStreamBuilder;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class InnerJoinStreamBuilderTestIT {
    private class DummyCollectionProxy extends CollectionProxy {
        protected DummyCollectionProxy(SolrClient solrClient, String nameOrAlias) {
            super(solrClient, nameOrAlias);
        }
    }

    private final static String SORT_FIELD = "id";
    private final static String STREAM_A_FIELD = "fieldA";
    private final static String STREAM_B_FIELD = "fieldB";
    private final static String STREAM_C_FIELD = "fieldC";
    private final static String STREAM_D_FIELD = "fieldD";
    
    private List<Map<String, String>> streamA = ImmutableList.of(
            ImmutableMap.of(SORT_FIELD, "a", STREAM_A_FIELD, "x"),
            ImmutableMap.of(SORT_FIELD, "b", STREAM_A_FIELD, "y"),
            ImmutableMap.of(SORT_FIELD, "c", STREAM_A_FIELD, "z"));
    private List<Map<String, String>> streamB = ImmutableList.of(
            ImmutableMap.of(SORT_FIELD, "a", STREAM_B_FIELD, "u"),
            ImmutableMap.of(SORT_FIELD, "b", STREAM_B_FIELD, "v"),
            ImmutableMap.of(SORT_FIELD, "c", STREAM_B_FIELD, "w"));
    private List<Map<String, String>> streamC = ImmutableList.of(
            ImmutableMap.of(SORT_FIELD, "b", STREAM_C_FIELD, "s"),
            ImmutableMap.of(SORT_FIELD, "d", STREAM_C_FIELD, "t"),
            ImmutableMap.of(SORT_FIELD, "e", STREAM_C_FIELD, "u"));
    private List<Map<String, String>> streamD = ImmutableList.of(
            ImmutableMap.of(SORT_FIELD, "d", STREAM_D_FIELD, "p"),
            ImmutableMap.of(SORT_FIELD, "e", STREAM_D_FIELD, "q"),
            ImmutableMap.of(SORT_FIELD, "f", STREAM_D_FIELD, "r"));


    private TupleStreamBuilder<DummyCollectionProxy> tupleStreamBuilderA =
            DummyTupleStreamBuilder.create(streamA.stream().map(Tuple::new).collect(toList()), SORT_FIELD, true);
    private TupleStreamBuilder<DummyCollectionProxy> tupleStreamBuilderB =
            DummyTupleStreamBuilder.create(streamB.stream().map(Tuple::new).collect(toList()), SORT_FIELD, true);
    private TupleStreamBuilder<DummyCollectionProxy> tupleStreamBuilderC =
            DummyTupleStreamBuilder.create(streamC.stream().map(Tuple::new).collect(toList()), SORT_FIELD, true);
    private TupleStreamBuilder<DummyCollectionProxy> tupleStreamBuilderD =
            DummyTupleStreamBuilder.create(streamD.stream().map(Tuple::new).collect(toList()), SORT_FIELD, true);

    @Test
    void innerJoinOnSelfReturnsSelf() {
        assertAboutInnerJoinStreamBuilder(
                tupleStreamBuilderA,
                tupleStreamBuilderA,
                (tupleStreamer) ->
                        assertThat(tupleStreamer.get())
                                .extracting(tuple -> ImmutableMap.of(SORT_FIELD, tuple.getString(SORT_FIELD),
                                                                     STREAM_A_FIELD, tuple.getString(STREAM_A_FIELD)))
                                .allMatch(map -> map.keySet().size() == 2)
                                .extracting(SORT_FIELD, STREAM_A_FIELD)
                                .containsExactly(tuple("a", "x"), tuple("b", "y"), tuple("c", "z")));

        assertAboutInnerJoinStreamBuilder(
                tupleStreamBuilderB,
                tupleStreamBuilderB,
                (tupleStreamer) ->
                        assertThat(tupleStreamer.get())
                                .extracting(tuple -> ImmutableMap.of(SORT_FIELD, tuple.getString(SORT_FIELD),
                                                                     STREAM_B_FIELD, tuple.getString(STREAM_B_FIELD)))
                                .allMatch(map -> map.keySet().size() == 2)
                                .extracting(SORT_FIELD, STREAM_B_FIELD)
                                .containsExactly(tuple("a", "u"), tuple("b", "v"), tuple("c", "w")));
    }

    @Test
    void mergesOnlyAttributesOfMatchingTuples() {
        assertAboutInnerJoinStreamBuilder(
                tupleStreamBuilderA,
                tupleStreamBuilderB,
                (tupleStreamer) ->
                        assertThat(tupleStreamer.get())
                                .extracting(tuple -> ImmutableMap.of(SORT_FIELD, tuple.getString(SORT_FIELD),
                                                                     STREAM_A_FIELD, tuple.getString(STREAM_A_FIELD),
                                                                     STREAM_B_FIELD, tuple.getString(STREAM_B_FIELD)))
                                .allMatch(map -> map.keySet().size() == 3)
                                .extracting(SORT_FIELD, STREAM_A_FIELD, STREAM_B_FIELD)
                                .containsExactly(tuple("a", "x", "u"), tuple("b", "y", "v"), tuple("c", "z", "w")));

        assertAboutInnerJoinStreamBuilder(
                tupleStreamBuilderA,
                tupleStreamBuilderC,
                (tupleStreamer) ->
                        assertThat(tupleStreamer.get())
                                .extracting(tuple -> ImmutableMap.of(SORT_FIELD, tuple.getString(SORT_FIELD),
                                                                     STREAM_A_FIELD, tuple.getString(STREAM_A_FIELD),
                                                                     STREAM_C_FIELD, tuple.getString(STREAM_C_FIELD)))
                                .allMatch(map -> map.keySet().size() == 3)
                                .extracting(SORT_FIELD, STREAM_A_FIELD, STREAM_C_FIELD)
                                .containsExactly(tuple("b", "y", "s")));

        assertAboutInnerJoinStreamBuilder(
                tupleStreamBuilderC,
                tupleStreamBuilderD,
                (tupleStreamer) ->
                        assertThat(tupleStreamer.get())
                                .extracting(tuple -> ImmutableMap.of(SORT_FIELD, tuple.getString(SORT_FIELD),
                                                                     STREAM_C_FIELD, tuple.getString(STREAM_C_FIELD),
                                                                     STREAM_D_FIELD, tuple.getString(STREAM_D_FIELD)))
                                .allMatch(map -> map.keySet().size() == 3)
                                .extracting(SORT_FIELD, STREAM_C_FIELD, STREAM_D_FIELD)
                                .containsExactly(tuple("d", "t", "p"), tuple("e", "u", "q")));

    }

    @Test
    void innerJoinOnNonMatchingFieldsIsEmpty() {
        assertAboutInnerJoinStreamBuilder(
                tupleStreamBuilderA,
                tupleStreamBuilderD,
                (tupleStreamer) -> assertThat(tupleStreamer.get()).isEmpty());

        assertAboutInnerJoinStreamBuilder(
                tupleStreamBuilderB,
                tupleStreamBuilderD,
                (tupleStreamer) -> assertThat(tupleStreamer.get()).isEmpty());
    }

    // A way to make assertions with try-with-resources
    private static <T extends CollectionProxy> void assertAboutInnerJoinStreamBuilder(
            TupleStreamBuilder<T> tupleStreamBuilder1,
            TupleStreamBuilder<T> tupleStreamBuilder2,
            Consumer<TupleStreamer> assertionOverTupleStreamer) {

        try (TupleStreamer tupleStreamer =
                     TupleStreamer.of(
                             new InnerJoinStreamBuilder<>(tupleStreamBuilder1, tupleStreamBuilder2, SORT_FIELD)
                                     .build())) {
            assertionOverTupleStreamer.accept(tupleStreamer);
        }

    }
}