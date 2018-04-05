package uk.ac.ebi.atlas.download;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.net.URI;
import java.nio.file.Path;
import java.text.MessageFormat;

@Component
public class ExperimentFileLocationService {

    private final DataFileHub dataFileHub;

    private final static String EXPERIMENT_FILES_URI_TEMPLATE = "experiment/{0}/download?fileType={1}&accessKey={2}";

    public ExperimentFileLocationService(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public Path getFilePath(String experimentAccession, ExperimentFileType fileType) {
        switch (fileType) {
            case EXPERIMENT_DESIGN:
                return dataFileHub.getSingleCellExperimentFiles(experimentAccession).experimentFiles.experimentDesign.getPath();
            case SDRF:
                return dataFileHub.getSingleCellExperimentFiles(experimentAccession).sdrf.getPath();

            default:
                return dataFileHub.getSingleCellExperimentFiles(experimentAccession).experimentFiles.experimentDesign.getPath();
        }
    }

    public URI getFileUri(String experimentAccession, String fileTypeId, String accessKey) {
        String uri = MessageFormat.format(EXPERIMENT_FILES_URI_TEMPLATE, experimentAccession, fileTypeId, accessKey);

        return URI.create(uri);
    }
}
