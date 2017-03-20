package uk.ac.ebi.atlas.experiments;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.annotation.Nullable;
import java.text.MessageFormat;

public class ExperimentMetadataEnrichmentService {

    private final ExperimentTrader experimentTrader;

    public ExperimentMetadataEnrichmentService(ExperimentTrader experimentTrader){
        this.experimentTrader = experimentTrader;
    }


    String getComparisonTitle(Experiment e, String chosenContrast){
        if(e!= null && e instanceof DifferentialExperiment){
           Contrast c = ((DifferentialExperiment) e).getDataColumnDescriptor(chosenContrast);
            if(c != null){
                return c.getDisplayName();
            }
        }
        return chosenContrast;
    }

    String experimentName(@Nullable Experiment e, String experimentAccession){
        return  e!= null ? e.getDisplayName() : experimentAccession;
    }

    JsonObject comparisonObject(Experiment e, String experimentAccession, String chosenContrast){
        JsonObject result = new JsonObject();
        result.addProperty("name",getComparisonTitle(e, chosenContrast));
        result.addProperty("url",
                MessageFormat.format("/gxa/experiments/{0}?selectedColumnIds={1}",
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
            inObject.add("comparison_title",
                    comparisonObject(e,inObject.get("experiment_accession").getAsString(), inObject.get("comparison_id").getAsString())
                   );
            inObject.addProperty("experiment",
                    experimentName(e, inObject.get("experiment_accession").getAsString())
                    );
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
