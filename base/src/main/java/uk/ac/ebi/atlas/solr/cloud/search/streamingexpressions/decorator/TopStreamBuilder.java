package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.decorator;

import org.apache.solr.client.solrj.io.comp.ComparatorOrder;
import org.apache.solr.client.solrj.io.comp.FieldComparator;
import org.apache.solr.client.solrj.io.comp.StreamComparator;
import org.apache.solr.client.solrj.io.stream.RankStream;
import org.apache.solr.client.solrj.io.stream.TupleStream;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.TupleStreamBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;

public class TopStreamBuilder<T extends CollectionProxy> extends TupleStreamBuilder<T> {
    private final TupleStreamBuilder<T> tupleStreamBuilder;
    private final int n;
    private final StreamComparator streamComparator;

    // We don’t use SchemaField<T> for the field comparator because  names may have been renamed by a previous select
    // clause or it may be a field with a stream evaluator
    public TopStreamBuilder(TupleStreamBuilder<T> tupleStreamBuilder, int n, String fieldName) {
        this.tupleStreamBuilder = tupleStreamBuilder;
        this.n = n;
        streamComparator = new FieldComparator(fieldName, ComparatorOrder.DESCENDING);
    }

    @Override
    public TupleStream getRawTupleStream() {
        try {
            return new RankStream(tupleStreamBuilder.build(), n, streamComparator);
        } catch (IOException e) {
            // The truth is, this constructor can’t throw an IOException, but the class declaration does... lame
            throw new UncheckedIOException(e);
        }
    }
}
