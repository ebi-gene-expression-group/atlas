package uk.ac.ebi.atlas.search;

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

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@AutoValue
public abstract class SemanticQueryTerm {
    public abstract String value();
    public abstract Optional<String> category();

    public static SemanticQueryTerm create(String value) {
        return create(value, "");
    }

    public static SemanticQueryTerm create(String value, String category) {
        return new AutoValue_SemanticQueryTerm(value, isNotBlank(category) ? Optional.of(category) : Optional.empty());
    }

    public boolean hasValue() {
        return isNotBlank(value());
    }

    public String description() {
        return category()
                .map(category -> String.format("%s (%s)", value(), category))
                .orElse(value());
    }

    private static final SemanticQueryTerm.GsonTypeAdapter GSON_TYPE_ADAPTER = new SemanticQueryTerm.GsonTypeAdapter();
    public static SemanticQueryTerm.GsonTypeAdapter getGsonTypeAdapter() {
        return GSON_TYPE_ADAPTER;
    }

    private static class GsonTypeAdapter
            implements JsonSerializer<SemanticQueryTerm>, JsonDeserializer<SemanticQueryTerm> {
        @Override
        public JsonElement serialize(SemanticQueryTerm src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("value", src.value());
            src.category().ifPresent(category -> jsonObject.addProperty("category", category));
            return jsonObject;
        }

        @Override
        public SemanticQueryTerm deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject jsonObject = json.getAsJsonObject();

            if (jsonObject.has("category")) {
                return create(
                        jsonObject.get("value").getAsString(),
                        jsonObject.get("category").getAsString());
            } else {
                return create(
                        jsonObject.get("value").getAsString());
            }
        }

        // Uncomment if GsonTypeAdapter also implements InstanceCreator<SemanticQueryTerm>
        // @Override
        // public SemanticQueryTerm createInstance(Type type) {
        //     return create("");
        // }
    }
}
