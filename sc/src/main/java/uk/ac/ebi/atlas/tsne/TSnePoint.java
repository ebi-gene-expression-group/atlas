package uk.ac.ebi.atlas.tsne;

import com.google.auto.value.AutoValue;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.Optional;

@AutoValue
public abstract class TSnePoint {
    abstract double x();
    abstract double y();
    public abstract Optional<Double> expressionLevel();
    abstract String name();

    public static TSnePoint create(double x, double y, Optional<Double> expressionLevel, String name) {
        return new AutoValue_TSnePoint(x, y, expressionLevel, name);
    }

    public static TSnePoint create(double x, double y, String name) {
        return create(x, y, Optional.empty(), name);
    }

    private static final Comparator<TSnePoint> NAME_COMPARATOR = new NameComparator();
    public static Comparator<TSnePoint> getNameComparator() {
        return NAME_COMPARATOR;
    }

    private static class NameComparator implements Comparator<TSnePoint> {
        @Override
        public int compare(TSnePoint o1, TSnePoint o2) {
            return o1.name().compareTo(o2.name());
        }
    }

    public static GsonTypeAdapter getGsonTypeAdapter() {
        return GSON_TYPE_ADAPTER;
    }

    private static class GsonTypeAdapter
            implements JsonSerializer<TSnePoint>, JsonDeserializer<TSnePoint>, InstanceCreator<TSnePoint> {
        @Override
        public JsonElement serialize(TSnePoint src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", src.name());
            jsonObject.addProperty("x", src.x());
            jsonObject.addProperty("y", src.y());
            if (src.expressionLevel().isPresent()) {
                jsonObject.addProperty("expressionLevel", src.expressionLevel().get());
            }
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
                        Optional.of(jsonObject.get("expressionLevel").getAsDouble()),
                        jsonObject.get("name").getAsString());
            } else {
                return create(
                        jsonObject.get("x").getAsDouble(),
                        jsonObject.get("y").getAsDouble(),
                        jsonObject.get("name").getAsString());
            }
        }

        @Override
        public TSnePoint createInstance(Type type) {
            return create(0.0, 0.0, "");
        }
    }
    private static final GsonTypeAdapter GSON_TYPE_ADAPTER = new GsonTypeAdapter();


    // Public static method returning a TypeAdapter<Foo> is what tells auto-value-gson to create a TypeAdapter for Foo
//    public static TypeAdapter<TSnePoint> typeAdapter(Gson gson) {
//        return new AutoValue_TSnePoint.GsonTypeAdapter(gson);
//    }

//    public static TypeAdapter<TSnePoint> typeAdapter(Gson gson) {
//        return new GsonTypeAdapter(gson);
//    }
//
//    public static final class GsonTypeAdapter extends TypeAdapter<TSnePoint> {
//        private final TypeAdapter<Double> xAdapter;
//        private final TypeAdapter<Double> yAdapter;
//        private final TypeAdapter<Double> expressionLevelAdapter;
//        private final TypeAdapter<String> nameAdapter;
//
//        public GsonTypeAdapter(Gson gson) {
//            this.xAdapter = gson.getAdapter(Double.class);
//            this.yAdapter = gson.getAdapter(Double.class);
//            this.expressionLevelAdapter = gson.getAdapter(Double.class);
//            this.nameAdapter = gson.getAdapter(String.class);
//        }
//
//        @Override
//        public void write(JsonWriter jsonWriter, TSnePoint object) throws IOException {
//            if (object == null) {
//                jsonWriter.nullValue();
//                return;
//            }
//            jsonWriter.beginObject();
//            jsonWriter.name("x");
//            xAdapter.write(jsonWriter, object.x());
//            jsonWriter.name("y");
//            yAdapter.write(jsonWriter, object.y());
//            if (object.expressionLevel().isPresent()) {
//                jsonWriter.name("expressionLevel");
//                expressionLevelAdapter.write(jsonWriter, object.expressionLevel().get());
//            }
//            jsonWriter.name("name");
//            nameAdapter.write(jsonWriter, object.name());
//            jsonWriter.endObject();
//        }
//
//        @Override
//        public TSnePoint read(JsonReader jsonReader) throws IOException {
//            if (jsonReader.peek() == JsonToken.NULL) {
//                jsonReader.nextNull();
//                return null;
//            }
//            jsonReader.beginObject();
//            double x = 0.0d;
//            double y = 0.0d;
//            Optional<Double> expressionLevel = Optional.empty();
//            String name = null;
//            while (jsonReader.hasNext()) {
//                String _name = jsonReader.nextName();
//                if (jsonReader.peek() == JsonToken.NULL) {
//                    jsonReader.nextNull();
//                    continue;
//                }
//                switch (_name) {
//                    case "x": {
//                        x = xAdapter.read(jsonReader);
//                        break;
//                    }
//                    case "y": {
//                        y = yAdapter.read(jsonReader);
//                        break;
//                    }
//                    case "expressionLevel": {
//                        expressionLevel = Optional.of(expressionLevelAdapter.read(jsonReader));
//                        break;
//                    }
//                    case "name": {
//                        name = nameAdapter.read(jsonReader);
//                        break;
//                    }
//                    default: {
//                        jsonReader.skipValue();
//                    }
//                }
//            }
//            jsonReader.endObject();
//            return new AutoValue_TSnePoint(x, y, expressionLevel, name);
//        }
//    }

}
