package uk.ac.ebi.atlas.utils;

import java.io.IOException;
import java.util.Iterator;

public interface AutoCloseableIterator<E> extends Iterator<E>, AutoCloseable {

    @Override
    void close() throws IOException;

}
