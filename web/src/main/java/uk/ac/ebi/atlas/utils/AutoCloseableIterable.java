package uk.ac.ebi.atlas.utils;

import java.io.IOException;

public interface AutoCloseableIterable<E> extends Iterable<E>, AutoCloseable {

    @Override
    void close() throws IOException;

}
