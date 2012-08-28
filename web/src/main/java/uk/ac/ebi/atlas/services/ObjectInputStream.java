package uk.ac.ebi.atlas.services;

import java.io.Closeable;

public interface ObjectInputStream<E> extends Closeable {

    public E readNext();

}
