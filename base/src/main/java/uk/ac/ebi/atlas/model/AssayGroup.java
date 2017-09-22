package uk.ac.ebi.atlas.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

public class AssayGroup extends DescribesDataColumns {

    private final Set<BiologicalReplicate> biologicalReplicates;

    //convenience constructor for tests
    public AssayGroup(String id, String... assayAccessions) {
        this(id, Arrays.asList(assayAccessions).stream().map(a -> new BiologicalReplicate(a)).collect(Collectors.toSet()));
    }

    public AssayGroup(String id, Set<BiologicalReplicate> biologicalReplicates) {
        super(id);
        checkArgument(biologicalReplicates.size() > 0);
        this.biologicalReplicates = biologicalReplicates;
    }

    public int getReplicates() {
        return biologicalReplicates.size();
    }

    public String getFirstAssayAccession() {
        return assaysAnalyzedForThisDataColumn().iterator().next();
    }

    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        o.addProperty("id", id);
        JsonArray a = new JsonArray();
        for (String assayAccession : assaysAnalyzedForThisDataColumn()) {
            a.add(new JsonPrimitive(assayAccession));
        }
        o.add("assayAccessions", a);
        o.addProperty("replicates", getReplicates());
        return o;
    }

    @Override
    public Set<String> assaysAnalyzedForThisDataColumn() {
        return biologicalReplicates.stream().flatMap(b -> b.assaysAnalyzedForThisDataColumn().stream()).collect(Collectors.toSet());
    }

    public Set<BiologicalReplicate> biologicalReplicatesForThisDataColumn() {
        return biologicalReplicates;
    }
}
