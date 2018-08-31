package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import java.util.Collections;
import java.util.Iterator;

/* Used to make ObjectInputStream iterable

 Unsafe - does not close the underlying stream - unless you iterate until the end!

 Okay:
 for (T t = new IterableObjectInputStream<>(inputStream) {
    doStuff(t);
 }
 Dangerous:
 T t = new IterableObjectInputStream<>(inputStream).iterator().next();
*/
public class IterableObjectInputStream<T> implements Iterable<T> {

    private final ObjectInputStream<T> inputStream;

    public IterableObjectInputStream(ObjectInputStream<T> inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Iterator<T> iterator() {
        return inputStream == null ? Collections.emptyIterator() : new ObjectInputStreamIterator();
    }

    // buffers next object from inputStream result so we can provide a hasNext method
    private final class ObjectInputStreamIterator implements Iterator<T> {

        private T next;

        private ObjectInputStreamIterator() {
            storeNext();
        }

        private void storeNext() {
            next = inputStream.readNext();
            if (next == null) {
                close();
            }
        }

        @Override
        public boolean hasNext() {
            return (next != null);
        }

        @Override
        public T next() {
            T result = next;
            storeNext();
            return result;
        }

        @Override
        public void remove() {
            close();
            throw new UnsupportedOperationException();
        }

        private void close() {
            try {
                inputStream.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
