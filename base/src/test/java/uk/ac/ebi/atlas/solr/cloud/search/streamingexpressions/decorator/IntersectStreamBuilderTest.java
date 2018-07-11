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
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class IntersectStreamBuilderTest {
    private class DummyCollectionProxy extends CollectionProxy {
        protected DummyCollectionProxy(SolrClient solrClient, String nameOrAlias) {
            super(solrClient, nameOrAlias);
        }
    }

    private final static String SORT_FIELD = "field1";
    
    private List<Tuple> streamA = ImmutableList.of(
            new Tuple(ImmutableMap.of(SORT_FIELD, "a", "field2", "x")),
            new Tuple(ImmutableMap.of(SORT_FIELD, "b", "field2", "y")),
            new Tuple(ImmutableMap.of(SORT_FIELD, "c", "field2", "z")));
    private List<Tuple> streamB = ImmutableList.of(
            new Tuple(ImmutableMap.of(SORT_FIELD, "a", "field3", "u")),
            new Tuple(ImmutableMap.of(SORT_FIELD, "b", "field3", "v")),
            new Tuple(ImmutableMap.of(SORT_FIELD, "c", "field3", "w")));
    private List<Tuple> streamC = ImmutableList.of(
            new Tuple(ImmutableMap.of(SORT_FIELD, "d", "field3", "u")),
            new Tuple(ImmutableMap.of(SORT_FIELD, "e", "field3", "v")),
            new Tuple(ImmutableMap.of(SORT_FIELD, "f", "field3", "w")));
    private List<Tuple> streamD = ImmutableList.of(
            new Tuple(ImmutableMap.of(SORT_FIELD, "a", "field3", "u")),
            new Tuple(ImmutableMap.of(SORT_FIELD, "b", "field3", "v")),
            new Tuple(ImmutableMap.of(SORT_FIELD, "d", "field3", "w")));


    private TupleStreamBuilder<DummyCollectionProxy> tupleStreamBuilderA = DummyTupleStreamBuilder.create(streamA, SORT_FIELD, true);
    private TupleStreamBuilder<DummyCollectionProxy> tupleStreamBuilderB = DummyTupleStreamBuilder.create(streamB, SORT_FIELD, true);
    private TupleStreamBuilder<DummyCollectionProxy> tupleStreamBuilderC = DummyTupleStreamBuilder.create(streamC, SORT_FIELD, true);
    private TupleStreamBuilder<DummyCollectionProxy> tupleStreamBuilderD = DummyTupleStreamBuilder.create(streamD, SORT_FIELD, true);
    
    @Test
    void intersectOnSelfReturnsSelf() {
        assertAboutIntersectStreamBuilder(
                tupleStreamBuilderA,
                tupleStreamBuilderA,
                (tupleStreamer) -> assertThat(tupleStreamer.get()).containsExactly(streamA.toArray(new Tuple[0])));

        assertAboutIntersectStreamBuilder(
                tupleStreamBuilderB,
                tupleStreamBuilderB,
                (tupleStreamer) -> assertThat(tupleStreamer.get()).containsExactly(streamB.toArray(new Tuple[0])));

        assertAboutIntersectStreamBuilder(
                tupleStreamBuilderC,
                tupleStreamBuilderC,
                (tupleStreamer) -> assertThat(tupleStreamer.get()).containsExactly(streamC.toArray(new Tuple[0])));
    }

    @Test
    void intersectWithExactlySameValuesReturnsFirstStream() {
        assertAboutIntersectStreamBuilder(
                tupleStreamBuilderA,
                tupleStreamBuilderB,
                (tupleStreamer) -> assertThat(tupleStreamer.get()).containsExactly(streamA.toArray(new Tuple[0])));
    }

    @Test
    void intersectOnNonMatchingFieldsIsEmpty() {
        assertAboutIntersectStreamBuilder(
                tupleStreamBuilderA,
                tupleStreamBuilderC,
                (tupleStreamer) -> assertThat(tupleStreamer.get()).isEmpty());

        assertAboutIntersectStreamBuilder(
                tupleStreamBuilderB,
                tupleStreamBuilderC,
                (tupleStreamer) -> assertThat(tupleStreamer.get()).isEmpty());
    }

    @Test
    void intersectWithSubset() {
        assertAboutIntersectStreamBuilder(
                tupleStreamBuilderA,
                tupleStreamBuilderD,
                (tupleStreamer) -> assertThat(tupleStreamer.get()).hasSize(2));
        assertAboutIntersectStreamBuilder(
                tupleStreamBuilderC,
                tupleStreamBuilderD,
                (tupleStreamer) -> assertThat(tupleStreamer.get()).hasSize(1));
    }

    // A way to make assertions with try-with-resources
    private static <T extends CollectionProxy> void assertAboutIntersectStreamBuilder(
            TupleStreamBuilder<T> tupleStreamBuilder1,
            TupleStreamBuilder<T> tupleStreamBuilder2,
            Consumer<TupleStreamer> assertionOverTupleStreamer) {

        try (TupleStreamer tupleStreamer =
                     TupleStreamer.of(
                             new IntersectStreamBuilder<>(tupleStreamBuilder1, tupleStreamBuilder2, SORT_FIELD)
                                     .build())) {
            assertionOverTupleStreamer.accept(tupleStreamer);
        }

    }
}