package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.io.Tuple;
import org.apache.solr.client.solrj.io.comp.ComparatorOrder;
import org.apache.solr.client.solrj.io.comp.FieldComparator;
import org.apache.solr.client.solrj.io.comp.StreamComparator;
import org.apache.solr.client.solrj.io.stream.StreamContext;
import org.apache.solr.client.solrj.io.stream.TupleStream;
import org.apache.solr.client.solrj.io.stream.expr.Explanation;
import org.apache.solr.client.solrj.io.stream.expr.StreamFactory;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class DummyTupleStreamBuilder<T extends CollectionProxy> extends TupleStreamBuilder<T> {
    private final Collection<Tuple> tuples;
    private final String sortFieldName;
    private final boolean ascendingSort;

    private DummyTupleStreamBuilder(Collection<Tuple> tuples, String sortFieldName, boolean ascendingSort) {
        this.tuples = tuples;
        this.sortFieldName = sortFieldName;
        this.ascendingSort = ascendingSort;
    }

    @Override
    protected TupleStream getRawTupleStream() {
        return new DummyTupleStream(tuples, sortFieldName, ascendingSort);
    }

    private final static class DummyTupleStream extends TupleStream {
        private List<Tuple> data;
        private Iterator<Tuple> dataIterator;
        private final String sortFieldName;
        private boolean ascendingSort;

        DummyTupleStream(Collection<Tuple> tuples, String sortFieldName, boolean ascendingSort) {
            data = ImmutableList.<Tuple>builder()
                    .addAll(tuples)
                    .add(new Tuple(ImmutableMap.of("EOF", true)))
                    .build();
            this.sortFieldName = sortFieldName;
            this.ascendingSort = ascendingSort;
        }

        @Override
        public void setStreamContext(StreamContext context) {

        }

        @Override
        public List<TupleStream> children() {
            return null;
        }

        @Override
        public void open() {
            dataIterator = data.iterator();
        }

        @Override
        public void close() {

        }

        @Override
        public Tuple read() {
            return dataIterator.next();
        }

        @Override
        public StreamComparator getStreamSort() {
            return new FieldComparator(
                    sortFieldName,
                    ascendingSort ? ComparatorOrder.ASCENDING : ComparatorOrder.DESCENDING);
        }

        @Override
        public Explanation toExplanation(StreamFactory factory) {
            return null;
        }
    }

    public static <T extends CollectionProxy> DummyTupleStreamBuilder<T> create(int size) {
        ImmutableList<Tuple> tuples =
                IntStream.range(0, size)
                        .boxed()
                        .map(i -> new Tuple(ImmutableMap.of("field1", i, "field2", i)))
                        .collect(toImmutableList());

        return create(tuples, "field1", true);
    }

    public static <T extends CollectionProxy> DummyTupleStreamBuilder<T> create(Collection<Tuple> tuples,
                                                                                String sortFieldName,
                                                                                boolean ascending) {
        return new DummyTupleStreamBuilder<>(tuples, sortFieldName, ascending);
    }

}
