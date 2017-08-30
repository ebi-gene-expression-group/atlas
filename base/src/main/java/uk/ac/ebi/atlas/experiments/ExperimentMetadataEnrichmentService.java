package uk.ac.ebi.atlas.experiments;

import com.google.common.base.Optional;
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


    private String comparisonTitle(Experiment e, String chosenContrast){
        if(e!= null && e instanceof DifferentialExperiment){
           Contrast c = ((DifferentialExperiment) e).getDataColumnDescriptor(chosenContrast);
            if(c != null){
                return c.getDisplayName();
            }
        }
        return chosenContrast;
    }

    private String experimentName(@Nullable Experiment experiment, String experimentAccession){
        return Optional.fromNullable(experiment).transform(e -> e.getDescription()).or(experimentAccession);
    }

    private JsonObject nameWithUrl(String name, String url){
        JsonObject result = new JsonObject();
        result.addProperty("name", name);
        result.addProperty("url",url);
        return result;
    }

    private JsonObject experimentObject(Experiment e, String experimentAccession){
        return nameWithUrl(
                experimentName(e, experimentAccession),
                MessageFormat.format("/gxa/experiments/{0}", experimentAccession));
    }

    private JsonObject comparisonObject(Experiment e, String experimentAccession, String chosenContrast){
        return nameWithUrl(
                comparisonTitle(e, chosenContrast),
                MessageFormat.format("/gxa/experiments/{0}?selectedColumnIds={1}",
                        experimentAccession, chosenContrast));
    }

    private Experiment getExperimentOrNull(String experimentAccession){
        try{
            return experimentTrader.getPublicExperiment(experimentAccession);
        } catch (ResourceNotFoundException e){
            return null;
        }
    }

    private JsonObject enrich(JsonObject inObject){
        if(inObject.has("comparison_id") && inObject.has("experiment_accession")){
            Experiment e = getExperimentOrNull(inObject.get("experiment_accession").getAsString());
            inObject.add("comparison_title",
                    comparisonObject(
                            e,
                            inObject.get("experiment_accession").getAsString(),
                            inObject.get("comparison_id").getAsString()));
            inObject.add("experiment",
                    experimentObject(e, inObject.get("experiment_accession").getAsString()));
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
