package uk.ac.ebi.atlas.dto.tooltip;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class AssayGroupSummary implements Iterable<AssayProperty> {

    public AssayGroupSummary(SortedSet<AssayProperty> properties) {
        this.properties = properties;
    }


    private SortedSet<AssayProperty> properties = new TreeSet<>();

    @Override
    public Iterator<AssayProperty> iterator() {
        return properties.iterator();
    }

}
