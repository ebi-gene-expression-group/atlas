package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator;

import org.apache.solr.client.solrj.io.comp.ComparatorOrder;
import org.apache.solr.client.solrj.io.comp.FieldComparator;
import org.apache.solr.client.solrj.io.stream.SortStream;
import org.apache.solr.client.solrj.io.stream.TupleStream;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.TupleStreamBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;

public class SortStreamBuilder extends TupleStreamBuilder {
    private final TupleStreamBuilder tupleStreamBuilder;
    private final String fieldName;
    private final boolean descending;

    public SortStreamBuilder(TupleStreamBuilder tupleStreamBuilder, String fieldName) {
        this.tupleStreamBuilder = tupleStreamBuilder;
        this.fieldName = fieldName;
        this.descending = false;
    }

    @Override
    protected TupleStream getRawTupleStream() {
        try {
            return new SortStream(
                    tupleStreamBuilder.build(),
                    new FieldComparator(
                            fieldName,
                            descending ? ComparatorOrder.DESCENDING : ComparatorOrder.ASCENDING));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
