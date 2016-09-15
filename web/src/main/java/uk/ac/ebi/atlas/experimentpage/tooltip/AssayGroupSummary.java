package uk.ac.ebi.atlas.experimentpage.tooltip;

import java.util.Iterator;
import java.util.SortedSet;

public class AssayGroupSummary implements Iterable<AssayProperty> {

    @SuppressWarnings("UnusedDeclaration") //serialized by Gson
    private final int replicates;
    private final SortedSet<AssayProperty> properties;

    public AssayGroupSummary(int replicates, SortedSet<AssayProperty> properties) {
        this.properties = properties;
        this.replicates = replicates;
    }

    @Override
    public Iterator<AssayProperty> iterator() {
        return properties.iterator();
    }



}
