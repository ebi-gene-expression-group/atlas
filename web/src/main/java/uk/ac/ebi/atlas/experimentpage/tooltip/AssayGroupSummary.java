package uk.ac.ebi.atlas.experimentpage.tooltip;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class AssayGroupSummary implements Iterable<AssayProperty> {

    private SortedSet<AssayProperty> properties = new TreeSet<>();

    public AssayGroupSummary(SortedSet<AssayProperty> properties) {
        this.properties = properties;
    }

    @Override
    public Iterator<AssayProperty> iterator() {
        return properties.iterator();
    }

}
