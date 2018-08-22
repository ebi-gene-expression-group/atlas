package uk.ac.ebi.atlas.model.experiment.summary;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Iterator;
import java.util.SortedSet;

public class AssayGroupSummary implements Iterable<AssayProperty> {

    //Also serialized through reflection
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


    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        o.addProperty("replicates", replicates);
        JsonArray a = new JsonArray();
        for (AssayProperty assayProperty: properties) {
            a.add(assayProperty.toJson());
        }
        o.add("properties", a);
        return o;
    }
}
