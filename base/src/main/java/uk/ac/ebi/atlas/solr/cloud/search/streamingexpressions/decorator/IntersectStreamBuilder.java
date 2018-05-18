package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.solr.client.solrj.io.eq.FieldEqualitor;
import org.apache.solr.client.solrj.io.eq.MultipleFieldEqualitor;
import org.apache.solr.client.solrj.io.eq.StreamEqualitor;
import org.apache.solr.client.solrj.io.stream.IntersectStream;
import org.apache.solr.client.solrj.io.stream.TupleStream;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.TupleStreamBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;

public class IntersectStreamBuilder<T extends CollectionProxy> extends TupleStreamBuilder<T> {
    private final TupleStreamBuilder<T> tupleStreamBuilderA;
    private final TupleStreamBuilder<T> tupleStreamBuilderB;
    private final ImmutableSet.Builder<Pair<String, String>> onFieldsBuilder = ImmutableSet.builder();

    public IntersectStreamBuilder(TupleStreamBuilder<T> tupleStreamBuilderA,
                                  TupleStreamBuilder<T> tupleStreamBuilderB) {
        this.tupleStreamBuilderA = tupleStreamBuilderA;
        this.tupleStreamBuilderB = tupleStreamBuilderB;
    }

    // We don’t use SchemaField<T> as keys because field names may have been renamed by a previous select clause or it
    // may be a field with a stream evaluator
    public IntersectStreamBuilder<T> onField(String fieldName) {
        onFieldsBuilder.add(Pair.of(fieldName, fieldName));
        return this;
    }

    public IntersectStreamBuilder<T> onField(String leftFieldName, String rightFieldName) {
        onFieldsBuilder.add(Pair.of(leftFieldName, rightFieldName));
        return this;
    }

    @Override
    protected TupleStream getRawTupleStream() {
        try {
            ImmutableSet<Pair<String, String>> onFields = onFieldsBuilder.build();
            StreamEqualitor streamEqualitor = onFields.size() == 1 ?
                    createFieldEqualitor(onFields.iterator().next()) :
                    createMultipleFieldEqualitor(onFields);

            return new IntersectStream(tupleStreamBuilderA.build(), tupleStreamBuilderB.build(), streamEqualitor);
        } catch (IOException e) {
            // The truth is, this constructor can’t throw an IOException, but the class declaration does... lame
            throw new UncheckedIOException(e);
        }
    }

    private static StreamEqualitor createFieldEqualitor(Pair<String, String> fieldNames) {
        if (fieldNames.getLeft().contentEquals(fieldNames.getRight())) {
            return new FieldEqualitor(fieldNames.getLeft());
        }
        return new FieldEqualitor(fieldNames.getLeft(), fieldNames.getRight());
    }

    private static StreamEqualitor createMultipleFieldEqualitor(Collection<Pair<String, String>> fieldNames) {
        return new MultipleFieldEqualitor(
                fieldNames.stream()
                        .map(IntersectStreamBuilder::createFieldEqualitor).toArray(StreamEqualitor[]::new));
    }
}
