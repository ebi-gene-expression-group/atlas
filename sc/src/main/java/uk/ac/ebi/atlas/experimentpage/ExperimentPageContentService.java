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
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExperimentPageContentService {

    private final ExperimentFileLocationService experimentFileLocationService;
    private final DataFileHub dataFileHub;
    private final TsnePlotSettingsService tsnePlotSettingsService;

    private static final Gson gson = new Gson();

    public ExperimentPageContentService(ExperimentFileLocationService experimentFileLocationService,
                                        DataFileHub dataFileHub,
                                        TsnePlotSettingsService tsnePlotSettingsService) {
        this.experimentFileLocationService = experimentFileLocationService;
        this.dataFileHub = dataFileHub;
        this.tsnePlotSettingsService = tsnePlotSettingsService;
    }

    public JsonObject getTsnePlotDataAsJson(String experimentAccession) {
        JsonObject result = new JsonObject();

        result.add("ks", gson.toJsonTree(tsnePlotSettingsService.getAvailableClusters(experimentAccession)));

        Integer expectedClusters = tsnePlotSettingsService.getExpectedClusters(experimentAccession);
        if(expectedClusters != null) {
            result.addProperty("selectedK", expectedClusters);
        }

        JsonArray perplexityArray = new JsonArray();
        tsnePlotSettingsService.getAvailablePerplexities(experimentAccession).forEach(perplexityArray::add);
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

        List<ExperimentFileType> metadataFiles = Arrays.asList(ExperimentFileType.SDRF, ExperimentFileType.IDF, ExperimentFileType.EXPERIMENT_DESIGN);
        List<ExperimentFileType> resultFiles = Arrays.asList(ExperimentFileType.CLUSTERING, ExperimentFileType.QUANTIFICATION_FILTERED);

        result.add(getDownloadSection("Metadata files", metadataFiles, experimentAccession, accessKey));
        result.add(getDownloadSection("Result files", resultFiles, experimentAccession, accessKey));

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

    private JsonObject getDownloadSection(String sectionName, List<ExperimentFileType> experimentFileTypes, String experimentAccession, String accessKey) {
        JsonObject section = new JsonObject();
        section.addProperty("title", sectionName);

        JsonArray files = new JsonArray();
        experimentFileTypes.forEach(file -> files.add(getExperimentFileAsJson(file, experimentAccession, accessKey)));

        section.add("files", files);

        return section;
    }

}
