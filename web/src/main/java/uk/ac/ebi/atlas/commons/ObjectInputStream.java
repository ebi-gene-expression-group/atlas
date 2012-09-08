package uk.ac.ebi.atlas.commons;

import java.io.Closeable;

public interface ObjectInputStream<T> extends Closeable {

    public T readNext();

}
