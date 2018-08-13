package uk.ac.ebi.atlas.experimentpage.tsne;

import com.google.auto.value.AutoValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Optional;

// A point in the t-SNE plot with some smarts about JSON serialization
@AutoValue
public abstract class TSnePoint {
    // Just a bag of data to ease reading from the DB with JOINs and draw a boundary between the t-SNE service DAO
    @AutoValue
    public static abstract class Dto {
        public abstract double x();
        public abstract double y();
        public abstract double expressionLevel();
        public abstract int clusterId();
        public abstract String name();

        public static TSnePoint.Dto create(double x, double y, String name) {
            return new AutoValue_TSnePoint_Dto(x, y, 0, 0, name);
        }

        public static TSnePoint.Dto create(double x, double y, double expressionLevel, String name) {
            return new AutoValue_TSnePoint_Dto(x, y, expressionLevel, 0, name);
        }

        public static TSnePoint.Dto create(double x, double y, int clusterId, String name) {
            return new AutoValue_TSnePoint_Dto(x, y, 0, clusterId, name);
        }

        public static TSnePoint.Dto create(double x, double y, double expressionLevel, int clusterId, String name) {
            return new AutoValue_TSnePoint_Dto(x, y, expressionLevel, clusterId, name);
        }
    }

    public abstract double x();
    public abstract double y();
    public abstract Optional<Double> expressionLevel();
    public abstract String name();
    public abstract String metadata();

    public static TSnePoint create(double x, double y, double expressionLevel, String name) {
        return new AutoValue_TSnePoint(x, y, Optional.of(expressionLevel), name, "");
    }

    public static TSnePoint create(double x, double y, String name) {
        return new AutoValue_TSnePoint(x, y, Optional.empty(), name, "");
    }

    public static TSnePoint create(double x, double y, String name, String metadata) {
        return new AutoValue_TSnePoint(x, y, Optional.empty(),name, metadata);
    }
    public static GsonTypeAdapter getGsonTypeAdapter() {
        return GSON_TYPE_ADAPTER;
    }

    private static class GsonTypeAdapter implements JsonSerializer<TSnePoint>, JsonDeserializer<TSnePoint> {
        @Override
        public JsonElement serialize(TSnePoint src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", src.name());
            jsonObject.addProperty("x", src.x());
            jsonObject.addProperty("y", src.y());
            src.expressionLevel()
                    .ifPresent(expressionLevel -> jsonObject.addProperty("expressionLevel", expressionLevel));
            return jsonObject;
        }

        @Override
        public TSnePoint deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject jsonObject = json.getAsJsonObject();

            if (jsonObject.has("expressionLevel")) {
                return create(
                        jsonObject.get("x").getAsDouble(),
                        jsonObject.get("y").getAsDouble(),
                        jsonObject.get("expressionLevel").getAsDouble(),
                        jsonObject.get("name").getAsString());
            }
            if (jsonObject.has("metadata")) {
                return create(
                        jsonObject.get("x").getAsDouble(),
                        jsonObject.get("y").getAsDouble(),
                        jsonObject.get("metadata").getAsString(),
                        jsonObject.get("name").getAsString());
            } else {
                return create(
                        jsonObject.get("x").getAsDouble(),
                        jsonObject.get("y").getAsDouble(),
                        jsonObject.get("name").getAsString());
            }
        }

        // Uncomment if GsonTypeAdapter also implements InstanceCreator<TSnePoint>
        // @Override
        // public TSnePoint createInstance(Type type) {
        //     return create(0.0, 0.0, "");
        // }
    }
    private static final GsonTypeAdapter GSON_TYPE_ADAPTER = new GsonTypeAdapter();

}
