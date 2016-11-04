package uk.ac.ebi.atlas.utils;

public interface Visitor<T> {

    void visit(T value);
}
