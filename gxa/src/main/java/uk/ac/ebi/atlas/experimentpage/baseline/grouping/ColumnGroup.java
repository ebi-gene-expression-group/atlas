package uk.ac.ebi.atlas.experimentpage.baseline.grouping;

import com.google.auto.value.AutoValue;
import com.google.gson.JsonObject;

@AutoValue
public abstract class ColumnGroup {

    abstract String id();
    abstract String name();


    public static ColumnGroup create(String id, String name){
        return new AutoValue_ColumnGroup(id,name);
    }

    public JsonObject toJson(){
        JsonObject result = new JsonObject();
        result.addProperty("id", id());
        result.addProperty("name", name());
        return result;
    }
}
