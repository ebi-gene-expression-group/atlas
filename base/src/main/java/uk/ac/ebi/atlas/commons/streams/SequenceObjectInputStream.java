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

    private Enumeration<ObjectInputStream<T>> enumeration;
    private ObjectInputStream<T> in;

    public SequenceObjectInputStream(Enumeration<ObjectInputStream<T>> enumeration) {
        this.enumeration = enumeration;
        try {
            nextStream();
        } catch (Exception e) {
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

        if (enumeration.hasMoreElements()) {
            in = enumeration.nextElement();
            if (in == null) {
                throw new NullPointerException();
            }
        } else {
            in = null;
        }

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
            } catch (Exception e) {
                throw new IllegalStateException("Next stream failed", e);
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
