package uk.ac.ebi.atlas.commons.streams;

import java.io.IOException;
import java.util.Enumeration;

/**
 * Concatenates multiple ObjectInputStreams. When one stream ends, continues
 * from the next stream.
 *
 * (This code was adapted from Java’s SequenceInputStream class).
 *
 * @param <T>
 */
public class SequenceObjectInputStream<T> implements ObjectInputStream<T> {

    private Enumeration<ObjectInputStream<T>> e;
    private ObjectInputStream<T> in;

    public SequenceObjectInputStream(Enumeration<ObjectInputStream<T>> e) {
        this.e = e;
        try {
            nextStream();
        } catch (Exception ex) {
            // This should never happen
            throw new Error("panic");
        }
    }

    /**
     * Continues reading in the next stream if an EOF is reached.
     */
    private void nextStream() throws IOException {
        if (in != null) {
            in.close();
        }

        if (e.hasMoreElements()) {
            in = e.nextElement();
            if (in == null)
                throw new NullPointerException();
        } else in = null;

    }

    @Override
    public T readNext() {
        if (in == null) {
            return null;
        }
        T c = in.readNext();
        if (c == null) {
            try {
                nextStream();
            } catch (Exception ex) {
                throw new RuntimeException("Next stream failed.", ex);
            }
            return readNext();
        }
        return c;
    }

    @Override
    public void close() throws IOException {
        do {
            nextStream();
        } while (in != null);
    }
}