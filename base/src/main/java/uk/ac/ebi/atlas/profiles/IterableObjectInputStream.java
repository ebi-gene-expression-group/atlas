package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import java.util.Iterator;

// Used to make ObjectInputStream iterable
public class IterableObjectInputStream<T> implements Iterable<T> {

    private final ObjectInputStream<T> inputStream;

    public IterableObjectInputStream(ObjectInputStream<T> inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Iterator<T> iterator() {
        return new ObjectInputStreamIterator();
    }

    // buffers next object input stream result so we can provide a hasNext method
    private final class ObjectInputStreamIterator implements Iterator<T> {

        private T next;

        private ObjectInputStreamIterator() {
            storeNext();
        }

        private void storeNext() {
            next = inputStream.readNext();
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
            throw new UnsupportedOperationException();
        }
    }

}
