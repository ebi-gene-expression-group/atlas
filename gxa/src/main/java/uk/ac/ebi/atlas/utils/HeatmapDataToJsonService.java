package uk.ac.ebi.atlas.utils;

import com.google.common.base.Strings;
import com.google.gson.*;
import uk.ac.ebi.atlas.search.SemanticQuery;
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

    public JsonObject configAsJsonObject(HttpServletRequest request, SemanticQuery geneQuery, SemanticQuery conditionQuery,
                                         Map<String, Object> model){
        JsonObject config = new JsonObject();
        config.addProperty("atlasHost", applicationProperties.buildAtlasHostURL(request));
        config.addProperty("contextRoot", request.getContextPath());
        config.addProperty("experimentAccession", get(model, "experimentAccession"));
        config.addProperty("geneQuery", geneQuery.toUrlEncodedJson());
        config.addProperty("conditionQuery", conditionQuery.toUrlEncodedJson());
        config.addProperty("accessKey", request.getParameter("accessKey"));
        config.addProperty("species", get(model, "species"));
        config.addProperty("ensemblDB", get(model, "ensemblDB"));
        config.addProperty("columnType", get(model, "queryFactorName").toLowerCase());
        config.addProperty("enableEnsemblLauncher",
                model.containsKey("enableEnsemblLauncher")
                        ? Boolean.parseBoolean(model.get("enableEnsemblLauncher").toString()) : false);
        config.addProperty("showMaPlotButton", true);
        config.addProperty("downloadProfilesURL",
                Strings.isNullOrEmpty((String) model.get("downloadURL"))
                        ? applicationProperties.buildDownloadURL(geneQuery,request)
                        : get(model, "downloadURL"));
        config.addProperty("disclaimer", get(model, "disclaimer"));
        return config;
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