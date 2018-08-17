package uk.ac.ebi.atlas.download;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
class ExperimentFileLocationServiceIT {
    private static final String EXPERIMENT_DESIGN_FILE_NAME_TEMPLATE = "ExpDesign-{0}.tsv";
    private static final String SDRF_FILE_NAME_TEMPLATE = "{0}.sdrf.txt";
    private static final String CLUSTERS_FILE_NAME_TEMPLATE = "{0}.clusters.tsv";

    private static final String MATRIX_MARKET_FILTERED_QUANTIFICATION_FILE_NAME_TEMPLATE = "{0}.expression_tpm.mtx";
    private static final String MATRIX_MARKET_FILTERED_QUANTIFICATION_ROWS_FILE_NAME_TEMPLATE =
            MATRIX_MARKET_FILTERED_QUANTIFICATION_FILE_NAME_TEMPLATE + "_rows";
    private static final String MATRIX_MARKET_FILTERED_QUANTIFICATION_COLUMNS_FILE_NAME_TEMPLATE =
            MATRIX_MARKET_FILTERED_QUANTIFICATION_FILE_NAME_TEMPLATE + "_cols";

    private static final String EXPERIMENT_FILES_URI_TEMPLATE = "experiment/{0}/download?fileType={1}&accessKey={2}";
    private static final String EXPERIMENT_FILES_ARCHIVE_URI_TEMPLATE =
            "experiment/{0}/download/zip?fileType={1}&accessKey={2}";

    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private DataFileHub dataFileHub;

    private ExperimentFileLocationService subject;

    @BeforeEach
    void setUp() {
        this.subject = new ExperimentFileLocationService(dataFileHub);
    }

    @Test
    void existingExperimentDesignFile() {
        existingFileOfType(jdbcTestUtils.fetchRandomSingleCellExperimentAccession(),
                ExperimentFileType.EXPERIMENT_DESIGN, EXPERIMENT_DESIGN_FILE_NAME_TEMPLATE);
    }

    @Test
    void existingSdrfFile() {
        existingFileOfType(jdbcTestUtils.fetchRandomSingleCellExperimentAccession(),
                ExperimentFileType.SDRF, SDRF_FILE_NAME_TEMPLATE);
    }

    @Test
    void existingClusteringFile() {
        existingFileOfType(jdbcTestUtils.fetchRandomSingleCellExperimentAccession(),
                ExperimentFileType.CLUSTERING, CLUSTERS_FILE_NAME_TEMPLATE);
    }

    @Test
    void existingFilteredQuantificationFiles() {
        List<String> fileNameTemplates =
                Arrays.asList(
                        MATRIX_MARKET_FILTERED_QUANTIFICATION_FILE_NAME_TEMPLATE,
                        MATRIX_MARKET_FILTERED_QUANTIFICATION_ROWS_FILE_NAME_TEMPLATE,
                        MATRIX_MARKET_FILTERED_QUANTIFICATION_COLUMNS_FILE_NAME_TEMPLATE);

        existingArchiveFilesOfType(jdbcTestUtils.fetchRandomSingleCellExperimentAccession(),
                ExperimentFileType.QUANTIFICATION_FILTERED, fileNameTemplates);
    }

    @Test
    void invalidExperimentAccession() {
        Path path = subject.getFilePath("", ExperimentFileType.EXPERIMENT_DESIGN);
        File file = path.toFile();

        assertThat(file).doesNotExist();
    }

    @Test
    void invalidFileType() {
        Path path = subject.getFilePath(jdbcTestUtils.fetchRandomSingleCellExperimentAccession(),
                ExperimentFileType.QUANTIFICATION_FILTERED);

        assertThat(path).isNull();

    }

    @Test
    void invalidArchiveFileType() {
        List<Path> paths = subject.getFilePathsForArchive(jdbcTestUtils.fetchRandomSingleCellExperimentAccession(),
                ExperimentFileType.SDRF);

        assertThat(paths).isNull();
    }

    @Test
    void uriForValidNonArchiveFileType() {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();

        ExperimentFileType fileType = ExperimentFileType.EXPERIMENT_DESIGN;
        URI uri = subject.getFileUri(experimentAccession, fileType, "");

        String expectedUrl = MessageFormat.format(EXPERIMENT_FILES_URI_TEMPLATE,
                experimentAccession, fileType.getId(), "");

        assertThat(uri.toString()).isEqualTo(expectedUrl);
    }

    @Test
    void uriForValidArchiveFileType() {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();

        ExperimentFileType fileType = ExperimentFileType.QUANTIFICATION_FILTERED;
        URI uri = subject.getFileUri(experimentAccession, fileType, "");

        String expectedUrl = MessageFormat.format(EXPERIMENT_FILES_ARCHIVE_URI_TEMPLATE,
                experimentAccession, fileType.getId(), "");

        assertThat(uri.toString()).isEqualTo(expectedUrl);
    }

    private void existingFileOfType(String experimentAccession, ExperimentFileType fileType, String fileNameTemplate) {
        Path path = subject.getFilePath(experimentAccession, fileType);
        File file = path.toFile();

        assertThat(file).hasName(MessageFormat.format(fileNameTemplate, experimentAccession));
        assertThat(file).exists();
        assertThat(file).isFile();
    }

    private void existingArchiveFilesOfType(String experimentAccession,
                                            ExperimentFileType fileType,
                                            List<String> fileNameTemplates) {
        List<Path> paths = subject.getFilePathsForArchive(experimentAccession, fileType);

        assertThat(paths).hasSameSizeAs(fileNameTemplates);

        for (Path path : paths) {
            File file = path.toFile();

            assertThat(file).exists();
            assertThat(file).isFile();
        }

        List<String> fileNames = paths.stream()
                .map(Path::toFile)
                .map(File::getName)
                .collect(Collectors.toList());

        assertThat(fileNames)
                .containsAll(
                        fileNameTemplates
                                .stream()
                                .map(template -> MessageFormat.format(template, experimentAccession))
                                .collect(Collectors.toList())
                );
    }
}
