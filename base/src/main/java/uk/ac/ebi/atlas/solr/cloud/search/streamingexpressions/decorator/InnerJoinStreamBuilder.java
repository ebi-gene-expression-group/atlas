package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.solr.client.solrj.io.eq.FieldEqualitor;
import org.apache.solr.client.solrj.io.eq.StreamEqualitor;
import org.apache.solr.client.solrj.io.stream.InnerJoinStream;
import org.apache.solr.client.solrj.io.stream.TupleStream;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.TupleStreamBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;

// Implementation of innerJoin streaming expression:
// https://lucene.apache.org/solr/guide/7_1/stream-decorator-reference.html#innerjoin
// Uncomment commented out blocks if you need to support joins by multiple/different fields and add constructors.
public class InnerJoinStreamBuilder extends TupleStreamBuilder {
    private final TupleStreamBuilder tupleStreamBuilderLeft;
    private final TupleStreamBuilder tupleStreamBuilderRight;
    private final ImmutableSet.Builder<Pair<String, String>> onFieldsBuilder = ImmutableSet.builder();

    // We don’t use SchemaField<T> because field names may have been renamed by a previous select clause
    public InnerJoinStreamBuilder(TupleStreamBuilder tupleStreamBuilderLeft,
                                  TupleStreamBuilder tupleStreamBuilderRight,
                                  String fieldName) {
        this.tupleStreamBuilderLeft = tupleStreamBuilderLeft;
        this.tupleStreamBuilderRight = tupleStreamBuilderRight;
        onFieldsBuilder.add(Pair.of(fieldName, fieldName));
    }

    @Override
    protected TupleStream getRawTupleStream() {
        try {
            ImmutableSet<Pair<String, String>> onFields = onFieldsBuilder.build();
            // Uncomment if you need to support multiple fields (see private method below)
            // StreamEqualitor streamEqualitor = onFields.size() == 1 ?
            //         createFieldEqualitor(onFields.iterator().next()) :
            //         createMultipleFieldEqualitor(onFields);
            StreamEqualitor streamEqualitor = createFieldEqualitor(onFields.iterator().next());

            return new InnerJoinStream(tupleStreamBuilderLeft.build(),
                                       tupleStreamBuilderRight.build(),
                                       streamEqualitor);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static StreamEqualitor createFieldEqualitor(Pair<String, String> fieldNames) {
        return new FieldEqualitor(fieldNames.getLeft());
        // Uncomment if you need to match different field names
        // if (fieldNames.getLeft().contentEquals(fieldNames.getRight())) {
        //     return new FieldEqualitor(fieldNames.getLeft());
        // }
        // return new FieldEqualitor(fieldNames.getLeft(), fieldNames.getRight());
    }

    // Uncomment if you need to support multiple fields
    // private static StreamEqualitor createMultipleFieldEqualitor(Collection<Pair<String, String>> fieldNames) {
    //     return new MultipleFieldEqualitor(
    //             fieldNames.stream()
    //                     .map(InnerJoinStreamBuilder::createFieldEqualitor).toArray(StreamEqualitor[]::new));
    // }
}
