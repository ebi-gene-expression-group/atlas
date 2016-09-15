package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class AssayGroup implements Iterable<String>{

    private String id;
    private Set<String> assayAccessions;
    private int replicates;

    public AssayGroup(String id, String... assayAccessions) {
        this(id, assayAccessions.length, assayAccessions);
    }

    public AssayGroup(String id, int replicates, String... assayAccessions) {
        checkArgument(StringUtils.isNotBlank(id));
        checkArgument(assayAccessions.length > 0 );

        this.id = id;
        this.replicates = replicates;
        this.assayAccessions = Sets.newHashSet(assayAccessions);
    }

    @Override
    public Iterator<String> iterator() {
        return assayAccessions.iterator();
    }

    public String getId() {
        return id;
    }

    public int getReplicates() {
        return replicates;
    }

    public String getFirstAssayAccession() {
        return assayAccessions.iterator().next();
    }

    @Override
    public int hashCode() {return Objects.hash(id, assayAccessions);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        final AssayGroup other = (AssayGroup) obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.assayAccessions, other.assayAccessions);
    }

    public JsonObject toJson(){
        JsonObject o = new JsonObject();
        o.addProperty("id", id);
        JsonArray a = new JsonArray();
        for(String assayAccession: assayAccessions){
            a.add(new JsonPrimitive(assayAccession));
        }
        o.add("assayAccessions", a);
        o.addProperty("replicates", replicates);
        return o;
    }
}
