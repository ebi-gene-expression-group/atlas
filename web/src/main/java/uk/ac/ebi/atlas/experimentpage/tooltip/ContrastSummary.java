
package uk.ac.ebi.atlas.experimentpage.tooltip;

import java.util.Iterator;
import java.util.SortedSet;

@SuppressWarnings("UnusedDeclaration")  //fields serialized by Gson
public class ContrastSummary implements Iterable<AssayProperty> {

    private final SortedSet<AssayProperty> properties;
    private final String experimentDescription;
    private final String contrastDescription;
    private final int testReplicates;
    private final int referenceReplicates;

    public ContrastSummary(String experimentDescription, String contrastDescription, int testReplicates, int referenceReplicates, SortedSet<AssayProperty> properties) {
        this.testReplicates = testReplicates;
        this.referenceReplicates = referenceReplicates;
        this.properties = properties;
        this.experimentDescription = experimentDescription;
        this.contrastDescription = contrastDescription;
    }

    @Override
    public Iterator<AssayProperty> iterator() {
        return properties.iterator();
    }
}