package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.tracks.GenomeBrowserController;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class ExperimentPageService {

    protected final Gson gson = new Gson();

    protected Map<String, JsonElement> payloadAttributes(Experiment experiment,
                                                         String accessKey, ExperimentPageRequestPreferences requestPreferences) {
        Map<String, JsonElement> result = new HashMap<>();

        result.put("experiment", experimentDescription(experiment, accessKey, requestPreferences));
        result.put("config", config(experiment, requestPreferences));
        return result;
    }

    private JsonElement experimentDescription(Experiment experiment,
                                              String accessKey, ExperimentPageRequestPreferences requestPreferences) {

        JsonObject experimentDescription = new JsonObject();
        experimentDescription.addProperty("accession", experiment.getAccession());
        experimentDescription.addProperty("type", experiment.getType().getDescription());

        JsonObject urls = new JsonObject();

        urls.addProperty("main_page",
                MessageFormat.format(
                        "experiments/{0}?geneQuery={1}",
                        experiment.getAccession(), requestPreferences.getGeneQuery().toUrlEncodedJson())
                        + (isBlank(accessKey) ? "" : "&accessKey=" + accessKey)
        );
        urls.addProperty("genome_browsers",
                GenomeBrowserController.redirectUrl(experiment.getAccession(), accessKey)
        );
        urls.addProperty("download",
                ExperimentDownloadController.getUrl(experiment.getAccession(), accessKey, experiment.getType(), requestPreferences)
        );
        experimentDescription.add("urls", urls);

        experimentDescription.addProperty("type", experiment.getType().getDescription());
        experimentDescription.addProperty("description", experiment.getDescription());
        experimentDescription.addProperty("species", experiment.getSpecies().getName());

        return experimentDescription;
    }

    private JsonObject config(Experiment<?> experiment, ExperimentPageRequestPreferences preferences) {
        JsonObject config = new JsonObject();
        config.addProperty("geneQuery", preferences.getGeneQuery().toUrlEncodedJson());
        config.addProperty("species", experiment.getSpecies().getName());
        config.add("genomeBrowsers", gson.toJsonTree(experiment.getGenomeBrowserNames()));
        config.addProperty("disclaimer", experiment.getDisclaimer());
        //only for the multiexperiment heatmap
        config.addProperty("columnType", "");
        config.addProperty("conditionQuery", "");
        return config;
    }
}
