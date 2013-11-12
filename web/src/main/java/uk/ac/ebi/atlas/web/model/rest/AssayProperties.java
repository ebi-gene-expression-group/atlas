package uk.ac.ebi.atlas.web.model.rest;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class AssayProperties implements Iterable<AssayProperty> {

    private SortedSet<AssayProperty> properties = new TreeSet<>();


    @Override
    public Iterator<AssayProperty> iterator() {
        return properties.iterator();
    }

    public void add(AssayProperty property) {
        properties.add(property);
    }

    public void addAll(Collection<AssayProperty> assayProperties) {
        properties.addAll(assayProperties);
    }
}
