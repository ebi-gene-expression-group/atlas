
package uk.ac.ebi.atlas.experimentpage.tooltip;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

    public JsonObject toJson(){
        JsonObject o = new JsonObject();
        JsonArray a = new JsonArray();
        for(AssayProperty assayProperty: properties){
            a.add(assayProperty.toJson());
        }
        o.add("properties",a);
        o.addProperty("experimentDescription",experimentDescription);
        o.addProperty("contrastDescription", contrastDescription);
        o.addProperty("testReplicates", testReplicates);
        o.addProperty("referenceReplicates",referenceReplicates);
        return o;
    }
}