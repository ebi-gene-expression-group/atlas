package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.SemanticQueryTerm;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ExperimentPageCallbacks {

    public  <Preferences extends ExperimentPageRequestPreferences> String
    create(Preferences preferences, Map<String, String> allParameters, String originalRequestURI)
    throws UnsupportedEncodingException {
        allParameters.put("geneQuery", preferences.getGeneQuery().toUrlEncodedJson());
        StringBuilder sourceURLBuilder = new StringBuilder(originalRequestURI.replace("/experiments", "/json/experiments").replaceFirst("\\??$","?"));
        for(Map.Entry<String, String> e: allParameters.entrySet()){
            sourceURLBuilder.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }
        sourceURLBuilder.deleteCharAt(sourceURLBuilder.lastIndexOf("&"));
        return sourceURLBuilder.toString();
    }

//    public <Preferences extends ExperimentPageRequestPreferences> void
//    adjustReceivedObjects(Preferences preferences) {
//        preferences.setGeneQuery(GeneQuery.fromUrlEncodedJson(preferences.getGeneQuery()));
//    }
}
