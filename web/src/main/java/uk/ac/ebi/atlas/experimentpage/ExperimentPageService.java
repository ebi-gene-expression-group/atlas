package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

public class ExperimentPageService {

    private static final Gson gson = new Gson();

    protected JsonElement prepareExperimentDescription(Experiment experiment, ExperimentPageRequestPreferences requestPreferences){
        return prepareExperimentDescription(experiment, requestPreferences.getGeneQuery(), requestPreferences
                .getSerializedFilterFactors());
    }

    //used when external parties include our widget and also to pass header summary to heatmap tooltips
    private JsonElement prepareExperimentDescription(Experiment experiment, SemanticQuery geneQuery, String
            serializedFilterFactors) {
        String additionalQueryOptionsString =
                "?geneQuery="+geneQuery.toUrlEncodedJson()+
                        "&serializedFilterFactors="+serializedFilterFactors;

        JsonObject experimentDescription = new JsonObject();
        experimentDescription.addProperty("URL", "/experiments/"+experiment.getAccession()+additionalQueryOptionsString);
        experimentDescription.addProperty("description", experiment.getDescription());
        experimentDescription.addProperty("species", experiment.getSpecies().originalName);
        experimentDescription.addProperty("headerSummary", gson.toJson(experiment.headerSummary()));
        return experimentDescription;
    }
}
