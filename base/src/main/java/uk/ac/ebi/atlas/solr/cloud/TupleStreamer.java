package uk.ac.ebi.atlas.solr.cloud;

import org.apache.solr.client.solrj.io.Tuple;
import org.apache.solr.client.solrj.io.stream.TupleStream;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// Due to the fact that tuple.EOF signals the end of the TupleStream we can’t use ObjectInputStream.
// Remember: use with try-with-resources so that the underlying TupleStream is properly closed!

public final class TupleStreamer implements AutoCloseable, Supplier<Stream<Tuple>> {
    private final TupleStream tupleStream;
    private final Iterator<Tuple> iterator;

    private boolean isOpen = false;

    private TupleStreamer(TupleStream tupleStream) {
        this.tupleStream = tupleStream;
        iterator = new TupleStreamIterator();
    }

    public static TupleStreamer of(TupleStream tupleStream) {
        return new TupleStreamer(tupleStream);
    }

    @Override
    public Stream<Tuple> get() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    }

    @Override
    public void close() {
        try {
            tupleStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            isOpen = false;
        }
    }

    private final class TupleStreamIterator implements Iterator<Tuple> {
        private Tuple bufferedTuple;
        private Tuple currentTuple;

        private TupleStreamIterator() {
            bufferNextTuple();
        }

        @Override
        public boolean hasNext() {
            return (bufferedTuple != null && !bufferedTuple.EOF);
        }

        @Override
        public Tuple next() {
            // if (bufferedTuple.EOF) {
            //     return null;
            // }

            currentTuple = bufferedTuple;
            bufferNextTuple();
            return currentTuple;
        }

        // TODO Consider using CompletableFuture, Supplier, Consumer so we don’t block until next tuple is ready
        private void bufferNextTuple() {
            try {
                if (!isOpen) {
                    tupleStream.open();
                    isOpen = true;
                }
                bufferedTuple = tupleStream.read();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}
