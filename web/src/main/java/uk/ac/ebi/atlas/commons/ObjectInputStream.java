package uk.ac.ebi.atlas.commons;

import java.io.Closeable;

public interface ObjectInputStream<E> extends Closeable {

    public E readNext();

}
