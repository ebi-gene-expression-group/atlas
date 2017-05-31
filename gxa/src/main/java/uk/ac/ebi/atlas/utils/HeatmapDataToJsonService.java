package uk.ac.ebi.atlas.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
public class HeatmapDataToJsonService {

    private static final Gson gson = new Gson();

    @Inject
    public HeatmapDataToJsonService() {
    }

    public JsonObject configAsJsonObject(Map<String, Object> model) {
        JsonObject config = new JsonObject();
        config.addProperty("geneQuery", getOrDefault(model, "query", get(model, "geneQuery")));
        config.addProperty("conditionQuery", get(model, "conditionQuery"));
        config.addProperty("species", get(model, "species"));
        config.add("resources", getAsJsonSerializable(model, "resources", new JsonObject()));
        config.addProperty("columnType", get(model, "queryFactorName").toLowerCase());
        config.addProperty("disclaimer", get(model, "disclaimer"));
        return config;
    }

    private JsonElement getAsJsonSerializable(Map<String, Object> model, String key, JsonElement defaultValue) {
        if(model.containsKey(key)) {
            return gson.toJsonTree(model.get(key));
        } else {
            return defaultValue;
        }
    }

    private String getOrDefault(Map<String, Object> model, String key, String defaultValue) {
        if (model.containsKey(key)) {
            return model.get(key).toString();
        } else {
            return defaultValue;
        }
    }
    private String get(Map<String, Object> model, String key){
        return getOrDefault(model, key, "");
    }

}