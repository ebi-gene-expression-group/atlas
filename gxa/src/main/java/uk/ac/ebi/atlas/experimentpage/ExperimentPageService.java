package uk.ac.ebi.atlas.experimentpage;

import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.utils.HeatmapDataToJsonService;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ExperimentPageService {

    private final AtlasResourceHub atlasResourceHub;
    protected final HeatmapDataToJsonService heatmapDataToJsonService;
    protected final ApplicationProperties applicationProperties;
    protected final Gson gson = new Gson();

    public ExperimentPageService(AtlasResourceHub atlasResourceHub,
                                 HeatmapDataToJsonService heatmapDataToJsonService,
                                 ApplicationProperties applicationProperties) {
        this.atlasResourceHub = atlasResourceHub;
        this.heatmapDataToJsonService = heatmapDataToJsonService;
        this.applicationProperties = applicationProperties;
    }

    protected Map<String, ?> headerAttributes(Experiment experiment) {
        Map<String, Object> result = new HashMap<>();
        result.put("hasExtraInfo", atlasResourceHub.hasExtraInfo(experiment));
        return result;
    }

    protected Map<String, JsonElement> payloadAttributes(Experiment experiment,
                                               ExperimentPageRequestPreferences requestPreferences) {
        Map<String, JsonElement> result = new HashMap<>();

        result.put("experiment", prepareExperimentDescription(experiment, requestPreferences));
        return result;
    }

    protected String downloadURL(SemanticQuery geneQuery, HttpServletRequest request) {
        return applicationProperties.buildDownloadURL(geneQuery, request);
    }

    private JsonElement prepareExperimentDescription(Experiment experiment,
                                                     ExperimentPageRequestPreferences requestPreferences) {
        return prepareExperimentDescription(
                experiment, requestPreferences.getGeneQuery(), requestPreferences.getSerializedFilterFactors());
    }

    //used when external parties include our widget and also to pass header summary to heatmap tooltips
    private JsonElement prepareExperimentDescription(Experiment experiment, SemanticQuery geneQuery,
                                                     String serializedFilterFactors) {
        String additionalQueryOptionsString =
                "?geneQuery=" + geneQuery.toUrlEncodedJson() + "&serializedFilterFactors=" + serializedFilterFactors;

        JsonObject experimentDescription = new JsonObject();
        experimentDescription.addProperty("accession", experiment.getAccession());
        experimentDescription.addProperty("type", experiment.getType().getDescription());
        experimentDescription.addProperty(
                "relUrl", "experiments/" + experiment.getAccession()+additionalQueryOptionsString);
        experimentDescription.addProperty("description", experiment.getDescription());
        experimentDescription.addProperty("species", experiment.getSpecies().getName());
        return experimentDescription;
    }
}
