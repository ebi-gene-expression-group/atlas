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
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class ExperimentPageService {

    protected final HeatmapDataToJsonService heatmapDataToJsonService;
    protected final ApplicationProperties applicationProperties;
    protected final Gson gson = new Gson();

    public ExperimentPageService(HeatmapDataToJsonService heatmapDataToJsonService,
                                 ApplicationProperties applicationProperties) {
        this.heatmapDataToJsonService = heatmapDataToJsonService;
        this.applicationProperties = applicationProperties;
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

        JsonObject experimentDescription = new JsonObject();
        experimentDescription.addProperty("accession", experiment.getAccession());
        experimentDescription.addProperty("type", experiment.getType().getDescription());
        experimentDescription.addProperty("relUrl",
                MessageFormat.format("experiments/{0}?geneQuery={1}", experiment.getAccession(), requestPreferences.getGeneQuery().toUrlEncodedJson() ));
        experimentDescription.addProperty("description", experiment.getDescription());
        experimentDescription.addProperty("species", experiment.getSpecies().getName());
        return experimentDescription;
    }
}
