package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions;

import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.io.Tuple;
import org.apache.solr.client.solrj.io.comp.StreamComparator;
import org.apache.solr.client.solrj.io.stream.StreamContext;
import org.apache.solr.client.solrj.io.stream.TupleStream;
import org.apache.solr.client.solrj.io.stream.expr.Explanation;
import org.apache.solr.client.solrj.io.stream.expr.StreamFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DummyTupleStream extends TupleStream {
    static final int TUPLE_STREAM_SOURCE_SIZE = 1000;

    private List<Tuple> data;
    private Iterator<Tuple> dataIterator;

    public DummyTupleStream() {
        data = new ArrayList<>(TUPLE_STREAM_SOURCE_SIZE + 1);
        for (int i = 0 ; i < TUPLE_STREAM_SOURCE_SIZE ; i++) {
            data.add(new Tuple(ImmutableMap.of("field1", i, "field2", i)));
        }
        data.add(new Tuple(ImmutableMap.of("EOF", true)));
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
        return null;
    }

    @Override
    public Explanation toExplanation(StreamFactory factory) {
        return null;
    }
}

