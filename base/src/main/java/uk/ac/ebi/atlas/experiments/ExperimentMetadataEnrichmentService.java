package uk.ac.ebi.atlas.experiments;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.text.MessageFormat;

public class ExperimentMetadataEnrichmentService {

    private final ExperimentTrader experimentTrader;

    public ExperimentMetadataEnrichmentService(ExperimentTrader experimentTrader){
        this.experimentTrader = experimentTrader;
    }


    String getComparisonTitle(Experiment e, String chosenContrast){
        if(e!= null && e instanceof DifferentialExperiment){
           Contrast c = ((DifferentialExperiment) e).getContrast(chosenContrast);
            if(c != null){
                return c.getDisplayName();
            }
        }
        return chosenContrast;
    }

    JsonObject experimentObject(Experiment e, String experimentAccession, String chosenContrast){
        JsonObject result = new JsonObject();
        result.addProperty("accession", experimentAccession);
        result.addProperty("name", e!= null ? e.getDisplayName() : experimentAccession);
        result.addProperty("url",
                MessageFormat.format("/gxa/experiments/{0}?queryFactorValues={1}&_specific=on",
                        experimentAccession, chosenContrast));
        return result;
    }

    Experiment getExperimentOrNull(String experimentAccession){
        try{
            return experimentTrader.getPublicExperiment(experimentAccession);
        } catch (ResourceNotFoundException e){
            return null;
        }
    }


    JsonObject enrich(JsonObject inObject){
        if(inObject.has("comparison_id") && inObject.has("experiment_accession")){
            Experiment e = getExperimentOrNull(inObject.get("experiment_accession").getAsString());
            inObject.addProperty("comparison_title",
                    getComparisonTitle(
                            e,
                            inObject.get("comparison_id").getAsString()
                    ));
            inObject.add("experiment",
                    experimentObject(
                            e,
                            inObject.get("experiment_accession").getAsString(),
                            inObject.get("comparison_id").getAsString()));
        }
        return inObject;
    }

    public JsonArray enrich(JsonArray arrayOfObjects){
        JsonArray result = new JsonArray();
        for(JsonElement e: arrayOfObjects){
            result.add(enrich(e.getAsJsonObject()));
        }
        return result;
    }
}
