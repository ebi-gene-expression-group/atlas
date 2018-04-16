package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.download.ExperimentFileLocationService;
import uk.ac.ebi.atlas.download.ExperimentFileType;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class ExperimentPageContentService {

    private final ExperimentFileLocationService experimentFileLocationService;
    private final DataFileHub dataFileHub;

    private static final Gson gson = new Gson();

    public ExperimentPageContentService(ExperimentFileLocationService experimentFileLocationService,
                                        DataFileHub dataFileHub) {
        this.experimentFileLocationService = experimentFileLocationService;
        this.dataFileHub = dataFileHub;
    }

    public JsonObject getTsnePlotDataAsJson() {
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

        result.addProperty("suggesterEndpoint", "json/suggestions");

        return result;
    }

    public JsonObject getExperimentDesignAsJson(String experimentAccession, JsonObject experimentDesignTableAsJson, String accessKey) {
        JsonObject result = new JsonObject();

        result.add("table", experimentDesignTableAsJson);

        String fileUri = experimentFileLocationService.getFileUri(experimentAccession, ExperimentFileType.EXPERIMENT_DESIGN, accessKey).toString();
        result.addProperty("downloadUrl", fileUri);

        return result;
    }

    public JsonArray getDownloadsAsJson(String experimentAccession, String accessKey) {
        JsonArray result = new JsonArray();

        result.add(getExperimentFileAsJson(ExperimentFileType.SDRF, experimentAccession, accessKey));
        result.add(getExperimentFileAsJson(ExperimentFileType.CLUSTERING, experimentAccession, accessKey));
        result.add(getExperimentFileAsJson(ExperimentFileType.EXPERIMENT_DESIGN, experimentAccession, accessKey));
        result.add(getExperimentFileAsJson(ExperimentFileType.QUANTIFICATION_FILTERED, experimentAccession, accessKey));

        return result;
    }

    public JsonArray getAnalysisMethodsAsJson(String experimentAccession) {
        JsonArray result;

        try (TsvStreamer tsvStreamer =
                     dataFileHub.getSingleCellExperimentFiles(experimentAccession).softwareUsed.get()) {
                            result = gson.toJsonTree(tsvStreamer.get().collect(Collectors.toList())).getAsJsonArray();
        };

        return result;
    }

    private JsonObject getExperimentFileAsJson(ExperimentFileType experimentFileType, String experimentAccession, String accessKey) {
        String url = experimentFileLocationService.getFileUri(experimentAccession, experimentFileType, accessKey).toString();

        JsonObject result = new JsonObject();

        result.addProperty("url", url);
        result.addProperty("type", experimentFileType.getIconType().getName());
        result.addProperty("description",  experimentFileType.getDescription());
        result.addProperty("isDownload", true);

        return result;
    }

}
