package uk.ac.ebi.atlas.download;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExperimentFileLocationServiceIT {
    private static final String EXPERIMENT_DESIGN_FILE_NAME_TEMPLATE = "ExpDesign-{0}.tsv";
    private static final String SDRF_FILE_NAME_TEMPLATE = "{0}.sdrf.txt";
    private static final String CLUSTERS_FILE_NAME_TEMPLATE = "{0}.clusters.tsv";
    private static final String MARKER_GENES_FILE_NAME_TEMPLATE = "{0}.marker_genes_{1}.tsv";

    private static final String MATRIX_MARKET_FILTERED_QUANTIFICATION_FILE_NAME_TEMPLATE = "{0}.expression_tpm.mtx";
    private static final String MATRIX_MARKET_FILTERED_QUANTIFICATION_ROWS_FILE_NAME_TEMPLATE =
            MATRIX_MARKET_FILTERED_QUANTIFICATION_FILE_NAME_TEMPLATE + "_rows";
    private static final String MATRIX_MARKET_FILTERED_QUANTIFICATION_COLUMNS_FILE_NAME_TEMPLATE =
            MATRIX_MARKET_FILTERED_QUANTIFICATION_FILE_NAME_TEMPLATE + "_cols";

    private static final String SINGLE_CELL_MATRIX_MARKET_FILTERED_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE =
            "{0}/{0}.aggregated_filtered_counts.mtx";
    private static final String SINGLE_CELL_MATRIX_MARKET_FILTERED_AGGREGATED_COUNTS_GENE_IDS_FILE_PATH_TEMPLATE =
            SINGLE_CELL_MATRIX_MARKET_FILTERED_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE + "_rows";
    private static final String SINGLE_CELL_MATRIX_MARKET_FILTERED_AGGREGATED_COUNTS_CELL_IDS_FILE_PATH_TEMPLATE =
            SINGLE_CELL_MATRIX_MARKET_FILTERED_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE + "_cols";

    private static final String SINGLE_CELL_MATRIX_MARKET_NORMALISED_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE =
            "{0}/{0}.aggregated_filtered_normalised_counts.mtx";
    private static final String SINGLE_CELL_MATRIX_MARKET_NORMALISED_AGGREGATED_COUNTS_GENE_IDS_FILE_PATH_TEMPLATE =
            SINGLE_CELL_MATRIX_MARKET_NORMALISED_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE + "_rows";
    private static final String SINGLE_CELL_MATRIX_MARKET_NORMALISED_AGGREGATED_COUNTS_CELL_IDS_FILE_PATH_TEMPLATE =
            SINGLE_CELL_MATRIX_MARKET_NORMALISED_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE + "_cols";

    private static final String EXPERIMENT_FILES_URI_TEMPLATE = "experiment/{0}/download?fileType={1}&accessKey={2}";
    private static final String EXPERIMENT_FILES_ARCHIVE_URI_TEMPLATE =
            "experiment/{0}/download/zip?fileType={1}&accessKey={2}";

    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private DataFileHub dataFileHub;

    private ExperimentFileLocationService subject;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-fixture.sql"),
                new ClassPathResource("fixtures/scxa_marker_genes-fixture.sql"),
                new ClassPathResource("fixtures/scxa_cell_clusters-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-delete.sql"),
                new ClassPathResource("fixtures/scxa_marker_genes-delete.sql"),
                new ClassPathResource("fixtures/scxa_cell_clusters-delete.sql"));
        populator.execute(dataSource);
    }

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
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        List<String> expectedFileNames =
                Stream.of(
                        MATRIX_MARKET_FILTERED_QUANTIFICATION_FILE_NAME_TEMPLATE,
                        MATRIX_MARKET_FILTERED_QUANTIFICATION_ROWS_FILE_NAME_TEMPLATE,
                        MATRIX_MARKET_FILTERED_QUANTIFICATION_COLUMNS_FILE_NAME_TEMPLATE)
                        .map(template -> MessageFormat.format(template, experimentAccession))
                        .collect(Collectors.toList());

        existingArchiveFilesOfType(experimentAccession,
                ExperimentFileType.QUANTIFICATION_FILTERED, expectedFileNames, false);
    }

    @Test
    void existingNormalisedQuantificationFiles() {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        List<String> expectedFileNames =
                Stream.of(
                        SINGLE_CELL_MATRIX_MARKET_NORMALISED_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE,
                        SINGLE_CELL_MATRIX_MARKET_NORMALISED_AGGREGATED_COUNTS_CELL_IDS_FILE_PATH_TEMPLATE,
                        SINGLE_CELL_MATRIX_MARKET_NORMALISED_AGGREGATED_COUNTS_GENE_IDS_FILE_PATH_TEMPLATE)
                        .map(template -> MessageFormat.format(template, experimentAccession))
                        .collect(Collectors.toList());

        existingArchiveFilesOfType(experimentAccession,
                ExperimentFileType.NORMALISED, expectedFileNames, true);
    }

    @Test
    void existingRawFilteredQuantificationFiles() {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        List<String> expectedFileNames =
                Stream.of(
                        SINGLE_CELL_MATRIX_MARKET_FILTERED_AGGREGATED_COUNTS_FILE_PATH_TEMPLATE,
                        SINGLE_CELL_MATRIX_MARKET_FILTERED_AGGREGATED_COUNTS_CELL_IDS_FILE_PATH_TEMPLATE,
                        SINGLE_CELL_MATRIX_MARKET_FILTERED_AGGREGATED_COUNTS_GENE_IDS_FILE_PATH_TEMPLATE)
                        .map(template -> MessageFormat.format(template, experimentAccession))
                        .collect(Collectors.toList());

        existingArchiveFilesOfType(experimentAccession,
                ExperimentFileType.QUANTIFICATION_RAW, expectedFileNames, true);
    }

    @Test
    void existingMarkerGeneFiles() {
        String experimentAccession = "E-MTAB-5061"; //jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        List<Integer> ks = jdbcTestUtils.fetchKsFromCellClusters(experimentAccession);

        List<String> expectedFileNames = ks
                .stream()
                .map(k -> MessageFormat.format(MARKER_GENES_FILE_NAME_TEMPLATE, experimentAccession, k))
                .collect(Collectors.toList());

        existingArchiveFilesOfType(experimentAccession, ExperimentFileType.MARKER_GENES, expectedFileNames, false);
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
                                            List<String> expectedFileNames,
                                            Boolean isArchive) {
        List<Path> paths = subject.getFilePathsForArchive(experimentAccession, fileType);

        // Some paths, e.g. marker genes, might not be all in the DB
        assertThat(paths.size()).isGreaterThanOrEqualTo(expectedFileNames.size());

        for (Path path : paths) {
            File file = path.toFile();

            assertThat(file).exists();
            assertThat(file).isFile();
        }

        List<String> fileNames = paths.stream()
                .map(Path::toFile)
                .map(File::getName)
                .map(entry -> isArchive ? experimentAccession + "/" + entry : entry)
                .collect(Collectors.toList());

        assertThat(expectedFileNames)
                .isNotEmpty()
                .containsAnyElementsOf(fileNames);
    }

}
