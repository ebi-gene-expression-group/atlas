package uk.ac.ebi.atlas.experimentpage;

import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import java.util.Map;

public class ExperimentPageCallbacks {

    public  <Preferences extends ExperimentPageRequestPreferences> String
    create(Preferences preferences, Map<String, String> allParameters, String originalRequestURI) {
        allParameters.put("geneQuery", preferences.getGeneQuery().toUrlEncodedJson());
        StringBuilder sourceURLBuilder = new StringBuilder(originalRequestURI.replace("/experiments", "/json/experiments").replaceFirst("\\??$","?"));
        for(Map.Entry<String, String> e: allParameters.entrySet()){
            sourceURLBuilder.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }
        sourceURLBuilder.deleteCharAt(sourceURLBuilder.lastIndexOf("&"));
        return sourceURLBuilder.toString();
    }
}
