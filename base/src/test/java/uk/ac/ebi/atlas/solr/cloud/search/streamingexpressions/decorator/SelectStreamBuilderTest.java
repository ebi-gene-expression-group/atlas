package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator;

import com.google.common.collect.ImmutableMap;
import org.assertj.core.api.Condition;
import org.junit.Test;
import uk.ac.ebi.atlas.solr.cloud.TupleStreamer;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.DummyTupleStreamBuilder;

import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class SelectStreamBuilderTest {
    @Test
    public void fieldsAreRenamed() {
        int size = ThreadLocalRandom.current().nextInt(1, 1000);
        DummyTupleStreamBuilder<AnalyticsCollectionProxy> tupleStreamBuilderMock =
                DummyTupleStreamBuilder.create(size);

        SelectStreamBuilder<AnalyticsCollectionProxy> subject =
                new SelectStreamBuilder<>(tupleStreamBuilderMock)
                        .addFieldMapping(ImmutableMap.of("field1", "fieldA", "field2", "fieldB"));

        assertThat(TupleStreamer.of(subject.build()).get().collect(toList()))
                .hasSize(size)
                .allMatch(
                        tuple ->
                                tuple.getMap().keySet().contains("fieldA") &&
                                tuple.getMap().keySet().contains("fieldB"))
                .areNot(
                        new Condition<>(
                                tuple ->
                                        tuple.getMap().keySet().contains("field1") ||
                                        tuple.getMap().keySet().contains("field2"),
                                "Does not contain field1 or field2"));
    }

    @Test
    public void byDefaultNoFieldsArePreserved() {
        int size = ThreadLocalRandom.current().nextInt(1, 1000);
        DummyTupleStreamBuilder<AnalyticsCollectionProxy> tupleStreamBuilderMock =
                DummyTupleStreamBuilder.create(size);

        SelectStreamBuilder<AnalyticsCollectionProxy> subject = new SelectStreamBuilder<>(tupleStreamBuilderMock);

        assertThat(TupleStreamer.of(subject.build()).get().collect(toList()))
                .hasSize(size)
                .allMatch(tuple -> tuple.getMap().keySet().size() == 0);
    }
}