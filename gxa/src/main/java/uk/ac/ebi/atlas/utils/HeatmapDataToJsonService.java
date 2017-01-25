package uk.ac.ebi.atlas.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/*
I used to be "heatmap-data.jsp" but it got complicated when we split into base and gxa
 */
@Named
public class HeatmapDataToJsonService {

    private static final Gson gson = new Gson();

    private final ApplicationProperties applicationProperties;
    @Inject
    public HeatmapDataToJsonService(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    public JsonObject jsonError(String message){
        JsonObject result = new JsonObject();
        result.addProperty("error", message);
            return result;
    }

    public JsonObject configAsJsonObject(HttpServletRequest request,Map<String, Object> model){
        JsonObject config = new JsonObject();
        config.addProperty("atlasHost", applicationProperties.buildAtlasHostURL(request));
        config.addProperty("contextRoot", request.getContextPath());
        config.addProperty("experimentAccession", get(model, "experimentAccession"));
        config.addProperty("geneQuery", getOrDefault(model, "query", get(model, "geneQuery")));
        config.addProperty("conditionQuery", get(model, "conditionQuery"));
        config.addProperty("accessKey", request.getParameter("accessKey"));
        config.addProperty("species", get(model, "species"));
        config.add("resources", getAsJsonSerializable(model, "resources", new JsonArray()));
        config.addProperty("columnType", get(model, "queryFactorName").toLowerCase());
        config.addProperty("enableEnsemblLauncher",
                model.containsKey("enableEnsemblLauncher") && Boolean.parseBoolean(model.get("enableEnsemblLauncher").toString()));
        config.addProperty("downloadProfilesURL", get(model, "downloadProfilesURL"));

        config.addProperty("disclaimer", get(model, "disclaimer"));
        return config;
    }

    private JsonElement getAsJsonSerializable(Map<String, Object> model, String key, JsonElement defaultValue){
        if(model.containsKey(key)){
            return gson.toJsonTree(model.get(key));
        } else {
            return defaultValue;
        }
    }

    private String getOrDefault(Map<String, Object> model, String key, String defaultValue){
        if(model.containsKey(key)){
            return model.get(key).toString();
        } else {
            return defaultValue;
        }
    }
    private String get(Map<String, Object> model, String key){
        return getOrDefault(model, key, "");
    }

}