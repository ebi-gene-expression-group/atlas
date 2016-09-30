package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.resource.ExternalImage;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import java.util.HashMap;
import java.util.Map;

public class ExperimentPageService {

    private final AtlasResourceHub atlasResourceHub;
    protected final Gson gson = new Gson();

    public ExperimentPageService(AtlasResourceHub atlasResourceHub){
        this.atlasResourceHub = atlasResourceHub;
    }

    protected Map<String, ?> headerAttributes(Experiment experiment,
                                               ExperimentPageRequestPreferences requestPreferences) {
        Map<String, Object> result = new HashMap<>();
        result.put("hasExtraInfo", atlasResourceHub.hasExtraInfo(experiment));
        return result;
    }
    protected Map<String, ?> payloadAttributes(Experiment experiment,
                                               ExperimentPageRequestPreferences requestPreferences){
        Map<String, Object> result = new HashMap<>();

        result.put("jsonExperiment", gson.toJson(prepareExperimentDescription(experiment, requestPreferences)));
        return result;
    }

    private JsonElement prepareExperimentDescription(Experiment experiment, ExperimentPageRequestPreferences
            requestPreferences){
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
        return experimentDescription;
    }
}
