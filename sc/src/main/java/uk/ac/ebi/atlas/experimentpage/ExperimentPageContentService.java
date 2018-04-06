package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.download.ExperimentFileLocationService;
import uk.ac.ebi.atlas.download.ExperimentFileType;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesignTable;

import java.util.Arrays;
import java.util.HashMap;

@Component
public class ExperimentPageContentService {

    private ExperimentFileLocationService experimentFileLocationService;

    public ExperimentPageContentService(ExperimentFileLocationService experimentFileLocationService) {
        this.experimentFileLocationService = experimentFileLocationService;
    }

    public JsonObject getExperimentInformation(Experiment experiment, String accessKey) {
        JsonObject result = new JsonObject();

        result.addProperty("experimentAccession", experiment.getAccession());
        result.addProperty("accessKey", accessKey);
        result.addProperty("species", experiment.getSpecies().getReferenceName());
        result.addProperty("disclaimer", experiment.getDisclaimer());

        return result;
    }

    public JsonObject getTsnePlotModel() {
        JsonObject result = new JsonObject();

        JsonArray availableClusters = new JsonArray();
        Arrays.stream(new int[] {2, 3, 4, 5, 6, 7, 8, 9, 10}).forEach(availableClusters::add);
        result.add("ks", availableClusters);

        // TODO Get available perplexities from scxa_tsne https://www.pivotaltracker.com/story/show/154898174
        JsonArray perplexityArray = new JsonArray();
        Arrays.stream(new int[] {1, 5, 10, 15, 20}).forEach(perplexityArray::add);
        result.add("perplexities", perplexityArray);

        JsonArray units = new JsonArray();
        units.add("TPM");

        result.add("units", units);

        // TODO Should this be the responsibility of the ExperimentController?
        result.addProperty("suggesterEndpoint", "json/suggestions");

        return result;
    }

    public JsonObject getExperimentDesignModel(Experiment experiment, String accessKey) {
        JsonObject result = new JsonObject();

        ExperimentDesignTable table = new ExperimentDesignTable(experiment);
        result.add("table", new ExperimentDesignTable(experiment).asJson());

        String fileUri = experimentFileLocationService.getFileUri(experiment.getAccession(), ExperimentFileType.EXPERIMENT_DESIGN, accessKey).toString();
        // Should change this key name (here and in JSON model received by the React component) to file URI. URL is created by the component.
        // Or should this service be responsible for making the full URL, and make the component dumber
        result.addProperty("downloadUrl", fileUri);

        return result;
    }

    public JsonArray getDownloadsModel(String experimentAccession, String accessKey) {
        JsonArray result = new JsonArray();

        result.add(getExperimentFileModel(ExperimentFileType.SDRF, experimentAccession, accessKey));
        result.add(getExperimentFileModel(ExperimentFileType.CLUSTERING, experimentAccession, accessKey));
        result.add(getExperimentFileModel(ExperimentFileType.EXPERIMENT_DESIGN, experimentAccession, accessKey));

        return result;
    }

    private JsonObject getExperimentFileModel(ExperimentFileType experimentFileType, String experimentAccession, String accessKey) {
        String url = experimentFileLocationService.getFileUri(experimentAccession, experimentFileType, accessKey).toString();

        JsonObject result = new JsonObject();

        result.addProperty("url", url);
        result.addProperty("type", experimentFileType.getIconType().getName());
        result.addProperty("description",  experimentFileType.getDescription());
        result.addProperty("isDownload", true);

        return result;
    }

}
