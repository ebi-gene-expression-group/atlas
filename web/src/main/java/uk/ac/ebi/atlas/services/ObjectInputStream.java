package uk.ac.ebi.atlas.services;

import uk.ac.ebi.atlas.model.ExpressionLevel;

import java.io.Closeable;

public interface ObjectInputStream<E> extends Closeable {

    public E readNext();

}
