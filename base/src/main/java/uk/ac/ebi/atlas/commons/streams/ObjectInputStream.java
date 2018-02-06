package uk.ac.ebi.atlas.commons.streams;

import java.io.IOException;

/*
 * Essentially a closeable iterator, however we don't use the Iterator interface because
 * the semantics of this class is different. See discussion here:
 * https://code.google.com/p/guava-libraries/issues/detail?id=973
 *
 * and comments here about Iterator not supporting IOException semantics
 * http://stackoverflow.com/a/10811303/149412
 */
public interface ObjectInputStream<T> extends AutoCloseable {
    // returns null when stream is empty
    T readNext();
    void close() throws IOException;
}
