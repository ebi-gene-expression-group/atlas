package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.controllers.ReturnsJsonErrors;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class ExperimentPageService extends ReturnsJsonErrors {

    protected final Gson gson = new Gson();

    protected Map<String, JsonElement> payloadAttributes(Experiment experiment,
                                               ExperimentPageRequestPreferences requestPreferences) {
        Map<String, JsonElement> result = new HashMap<>();

        result.put("experiment", experimentDescription(experiment, requestPreferences));
        result.put("config", config(experiment, requestPreferences));
        return result;
    }

    private JsonElement experimentDescription(Experiment experiment,
                                              ExperimentPageRequestPreferences requestPreferences) {

        JsonObject experimentDescription = new JsonObject();
        experimentDescription.addProperty("accession", experiment.getAccession());
        experimentDescription.addProperty("type", experiment.getType().getDescription());
        experimentDescription.addProperty("relUrl",
                MessageFormat.format("experiments/{0}?geneQuery={1}", experiment.getAccession(), requestPreferences.getGeneQuery().toUrlEncodedJson() ));
        experimentDescription.addProperty("description", experiment.getDescription());
        experimentDescription.addProperty("species", experiment.getSpecies().getName());
        return experimentDescription;
    }

    public JsonObject config(Experiment<?> experiment, ExperimentPageRequestPreferences preferences){
        JsonObject config = new JsonObject();
        config.addProperty("geneQuery",preferences.getGeneQuery().toUrlEncodedJson());
        config.addProperty("species", experiment.getSpecies().getName());
        config.add("resources", experiment.getSpecies().getResources());
        config.addProperty("disclaimer", experiment.getDisclaimer());
        config.addProperty("expressionUnit", preferences.getUnit().toString());
        //only for the multiexperiment heatmap
        config.addProperty("columnType", "");
        config.addProperty("conditionQuery", "");
        return config;
    }
}
