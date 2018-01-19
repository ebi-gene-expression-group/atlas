package uk.ac.ebi.atlas.solr.cloud;

import org.apache.solr.client.solrj.io.Tuple;
import org.apache.solr.client.solrj.io.stream.TupleStream;

import java.io.IOException;
import java.util.Iterator;

// Due to the fact that tuple.EOF signals the end of the TupleStream we can’t use ObjectInputStream and related classes

public class TupleStreamAutoCloseableIterator implements Iterator<Tuple>, AutoCloseable {
    private final TupleStream tupleStream;
    private boolean isOpen = false;
    private Tuple bufferedTuple;

    public TupleStreamAutoCloseableIterator(TupleStream tupleStream) {
        this.tupleStream = tupleStream;
        bufferNextTuple();
    }

    @Override
    public boolean hasNext() {
        return (bufferedTuple != null && !bufferedTuple.EOF);
    }

    @Override
    public Tuple next() {
        Tuple currentTuple = bufferedTuple;

        if (currentTuple.EOF) {
            return null;
        }

        bufferNextTuple();
        return currentTuple;
    }

    @Override
    public void close() throws IOException {
        tupleStream.close();
        isOpen = false;
    }

    private void bufferNextTuple() {
        try {
            if (!isOpen) {
                tupleStream.open();
                isOpen = true;
            }
            bufferedTuple = tupleStream.read();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
