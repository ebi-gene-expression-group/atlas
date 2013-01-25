package uk.ac.ebi.atlas.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FactorValueSet implements Set<FactorValue> {

    private Set<FactorValue> factorValues = new HashSet<>();

    public FactorValueSet() {
    }

    public FactorValueSet(Set<FactorValue> factorValues) {
        this.factorValues = factorValues;
    }

    @Override
    public int size() {return factorValues.size();}

    @Override
    public boolean isEmpty() {return factorValues.isEmpty();}

    @Override
    public boolean contains(Object o) {return factorValues.contains(o);}

    @Override
    public Iterator<FactorValue> iterator() {return factorValues.iterator();}

    @Override
    public Object[] toArray() {return factorValues.toArray();}

    @Override
    public <T> T[] toArray(T[] a) {return factorValues.toArray(a);}

    @Override
    public boolean add(FactorValue factorValue) {
        return factorValues.add(factorValue);
    }

    @Override
    public boolean remove(Object o) {return factorValues.remove(o);}

    @Override
    public boolean containsAll(Collection<?> c) {return factorValues.containsAll(c);}

    @Override
    public boolean addAll(Collection<? extends FactorValue> c) {return factorValues.addAll(c);}

    @Override
    public boolean retainAll(Collection<?> c) {return factorValues.retainAll(c);}

    @Override
    public boolean removeAll(Collection<?> c) {return factorValues.removeAll(c);}

    @Override
    public void clear() {factorValues.clear();}

    public FactorValue getFactorValue(String type) {
        for (FactorValue factorValue : factorValues) {
            if (factorValue.getType().equals(type)) {
                return factorValue;
            }
        }
        throw new IllegalStateException("Expression doesn't contain factor value for a given type");
    }

    @Override
    public boolean equals(Object o) {return factorValues.equals(o);}

    @Override
    public int hashCode() {return factorValues.hashCode();}
}
