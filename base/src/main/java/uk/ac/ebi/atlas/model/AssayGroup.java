package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.google.common.base.Preconditions.checkArgument;

public class AssayGroup extends DescribesDataColumns {

    private final Set<String> assayAccessions;
    private final int replicates;

    public AssayGroup(String id, String... assayAccessions) {
        this(id, assayAccessions.length, assayAccessions);
    }

    public AssayGroup(String id, int replicates, String... assayAccessions) {
        super(id);
        checkArgument(assayAccessions.length > 0 );

        this.replicates = replicates;
        this.assayAccessions = Sets.newHashSet(assayAccessions);
    }

    public AssayGroup(String id, BiologicalReplicate... biologicalReplicates){
        super(id);
        checkArgument(biologicalReplicates.length > 0 );
        this.assayAccessions = Arrays.asList(biologicalReplicates).stream().flatMap(b -> b.assaysAnalyzedForThisDataColumn().stream()).collect(Collectors.toSet());
        this.replicates = biologicalReplicates.length;
    }

    public int getReplicates() {
        return replicates;
    }

    public String getFirstAssayAccession() {
        return assayAccessions.iterator().next();
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

    @Override
    public Set<String> assaysAnalyzedForThisDataColumn() {
        return assayAccessions;
    }
}
