package uk.ac.ebi.atlas.download;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.net.URI;
import java.nio.file.Path;
import java.text.MessageFormat;

@Component
public class FilePathService {

    private final DataFileHub dataFileHub;

    private final static String EXPERIMENT_FILES_URI_TEMPLATE = "experiment/{0}/download?fileType={1}&accessKey={2}";

    public FilePathService(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public Path getFilePath(String experimentAccession, String fileType) {
        // TODO Handle different file types

        return dataFileHub.getSingleCellExperimentFiles(experimentAccession).experimentFiles.experimentDesign.getPath();
    }

    public URI getFileUri(String experimentAccession, String fileType, String accessKey) {
        String uri = MessageFormat.format(EXPERIMENT_FILES_URI_TEMPLATE, experimentAccession, fileType, accessKey);

        return URI.create(uri);
    }
}
