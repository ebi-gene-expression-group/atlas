package uk.ac.ebi.atlas.download;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.net.URI;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

@Component
public class ExperimentFileLocationService {

    private final DataFileHub dataFileHub;

    private static final String EXPERIMENT_FILES_URI_TEMPLATE =
            "experiment/{0}/download?fileType={1}&accessKey={2}";
    private static final String EXPERIMENT_FILES_ARCHIVE_URI_TEMPLATE =
            "experiment/{0}/download/zip?fileType={1}&accessKey={2}";

    public ExperimentFileLocationService(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public Path getFilePath(String experimentAccession, ExperimentFileType fileType) {
        switch (fileType) {
            case EXPERIMENT_DESIGN:
                return dataFileHub
                        .getSingleCellExperimentFiles(experimentAccession)
                        .experimentFiles.experimentDesign.getPath();
            case SDRF:
                return dataFileHub.getSingleCellExperimentFiles(experimentAccession).experimentFiles.sdrf.getPath();
            case IDF:
                return dataFileHub.getSingleCellExperimentFiles(experimentAccession).experimentFiles.idf.getPath();
            case CLUSTERING:
                return dataFileHub.getSingleCellExperimentFiles(experimentAccession).clustersTsv.getPath();
            default:
                return null;
        }
    }

    public List<Path> getFilePathsForArchive(String experimentAccession, ExperimentFileType fileType) {
        switch (fileType) {
            case QUANTIFICATION_FILTERED:
                return
                        Arrays.asList(
                                dataFileHub.getSingleCellExperimentFiles(experimentAccession).tpmsMatrix.getPath(),
                                dataFileHub.getSingleCellExperimentFiles(experimentAccession).cellIdsTsv.getPath(),
                                dataFileHub.getSingleCellExperimentFiles(experimentAccession).geneIdsTsv.getPath());
            default:
                return null;
        }
    }

    public URI getFileUri(String experimentAccession, ExperimentFileType fileType, String accessKey) {
        String uri = fileType.isArchive() ?
                MessageFormat.format(
                        EXPERIMENT_FILES_ARCHIVE_URI_TEMPLATE, experimentAccession, fileType.getId(), accessKey) :
                MessageFormat.format(
                        EXPERIMENT_FILES_URI_TEMPLATE, experimentAccession, fileType.getId(), accessKey);

        return URI.create(uri);
    }
}
