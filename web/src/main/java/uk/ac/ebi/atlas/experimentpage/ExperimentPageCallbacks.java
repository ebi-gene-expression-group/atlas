package uk.ac.ebi.atlas.experimentpage;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;
import uk.ac.ebi.atlas.web.OldGeneQuery;

import java.util.Map;

public class ExperimentPageCallbacks {

    private static final String GENE_QUERY_SEPARATOR = "~";

    public  <Preferences extends ExperimentPageRequestPreferences>
    String create(Preferences preferences, Map<String, String> allParameters, String originalRequestURI) {
        allParameters.put("geneQuery", Joiner.on(GENE_QUERY_SEPARATOR).join(preferences.getGeneQuery().terms()));
        StringBuilder sourceURLBuilder = new StringBuilder(originalRequestURI.replace("/experiments",
                "/json/experiments").replaceFirst("\\??$","?"));
        for(Map.Entry<String, String> e: allParameters.entrySet()){
            sourceURLBuilder.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }
        sourceURLBuilder.deleteCharAt(sourceURLBuilder.lastIndexOf("&"));
        return sourceURLBuilder.toString();
    }

    public <Preferences extends ExperimentPageRequestPreferences>
            void adjustReceivedObjects(Preferences preferences){
        ImmutableList<String> geneQueryTerms = preferences.getGeneQuery().terms();
        if(geneQueryTerms.size()==0){
            // pass
        } else if (geneQueryTerms.size()==1) {
            preferences.setGeneQuery(OldGeneQuery.create(geneQueryTerms.get(0).split(GENE_QUERY_SEPARATOR)));
        } else {
            throw new IllegalStateException("Expecting a single synthetic gene query term in a callback, " +
                    "received:"+geneQueryTerms.toString());
        }
    }
}
