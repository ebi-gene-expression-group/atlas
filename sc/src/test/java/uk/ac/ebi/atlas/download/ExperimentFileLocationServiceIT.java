package uk.ac.ebi.atlas.download;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class ExperimentFileLocationServiceIT {

    private final String EXPERIMENT_DESIGN_FILE_NAME_TEMPLATE = "ExpDesign-{0}.tsv";
    private final String SDRF_FILE_NAME_TEMPLATE = "{0}.sdrf.txt";
    private final String CLUSTERS_FILE_NAME_TEMPLATE = "{0}.clusters.tsv";

    private final String MATRIX_MARKET_FILTERED_QUANTIFICATION_FILE_NAME_TEMPLATE = "{0}.expression_tpm.mtx";
    private final String MATRIX_MARKET_FILTERED_QUANTIFICATION_ROWS_FILE_NAME_TEMPLATE =
            MATRIX_MARKET_FILTERED_QUANTIFICATION_FILE_NAME_TEMPLATE + "_rows";
    private final String MATRIX_MARKET_FILTERED_QUANTIFICATION_COLUMNS_FILE_NAME_TEMPLATE =
            MATRIX_MARKET_FILTERED_QUANTIFICATION_FILE_NAME_TEMPLATE + "_cols";

    private final static String EXPERIMENT_FILES_URI_TEMPLATE = "experiment/{0}/download?fileType={1}&accessKey={2}";
    private final static String EXPERIMENT_FILES_ARCHIVE_URI_TEMPLATE = "experiment/{0}/download/zip?fileType={1}&accessKey={2}";

    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private DataFileHub dataFileHub;

    private ExperimentFileLocationService subject;

    @Before
    public void setUp() {
        this.subject = new ExperimentFileLocationService(dataFileHub);
    }

    @Test
    public void existingExperimentDesignFile() {
        existingFileOfType(jdbcTestUtils.fetchRandomSingleCellExperimentAccession(),
                ExperimentFileType.EXPERIMENT_DESIGN, EXPERIMENT_DESIGN_FILE_NAME_TEMPLATE);
    }

    @Test
    public void existingSdrfFile() {
        existingFileOfType(jdbcTestUtils.fetchRandomSingleCellExperimentAccession(),
                ExperimentFileType.SDRF, SDRF_FILE_NAME_TEMPLATE);
    }

    @Test
    public void existingClusteringFile() {
        existingFileOfType(jdbcTestUtils.fetchRandomSingleCellExperimentAccession(),
                ExperimentFileType.CLUSTERING, CLUSTERS_FILE_NAME_TEMPLATE);
    }

    @Test
    public void existingFilteredQuantificationFiles() {
        List<String> fileNameTemplates = Arrays.asList(MATRIX_MARKET_FILTERED_QUANTIFICATION_FILE_NAME_TEMPLATE, MATRIX_MARKET_FILTERED_QUANTIFICATION_ROWS_FILE_NAME_TEMPLATE, MATRIX_MARKET_FILTERED_QUANTIFICATION_COLUMNS_FILE_NAME_TEMPLATE);

        existingArchiveFilesOfType(jdbcTestUtils.fetchRandomSingleCellExperimentAccession(),
                ExperimentFileType.QUANTIFICATION_FILTERED, fileNameTemplates);
    }

    @Test
    public void invalidExperimentAccession() {
        Path path = subject.getFilePath("", ExperimentFileType.EXPERIMENT_DESIGN);
        File file = path.toFile();

        assertThat(file.exists(), is(false));
    }

    @Test
    public void invalidFileType() {
        Path path = subject.getFilePath(jdbcTestUtils.fetchRandomSingleCellExperimentAccession(),
                ExperimentFileType.QUANTIFICATION_FILTERED);

        assertThat(path, is(nullValue()));
    }

    @Test
    public void invalidArchiveFileType() {
        List<Path> paths = subject.getFilePathsForArchive(jdbcTestUtils.fetchRandomSingleCellExperimentAccession(),
                ExperimentFileType.SDRF);

        assertThat(paths, is(nullValue()));
    }

    @Test
    public void uriForValidNonArchiveFileType() {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();

        ExperimentFileType fileType = ExperimentFileType.EXPERIMENT_DESIGN;
        URI uri = subject.getFileUri(experimentAccession, fileType, "");

        String expectedUrl = MessageFormat.format(EXPERIMENT_FILES_URI_TEMPLATE,
                experimentAccession, fileType.getId(), "");
        assertThat(uri.toString(), is(expectedUrl));
    }

    @Test
    public void uriForValidArchiveFileType() {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();

        ExperimentFileType fileType = ExperimentFileType.QUANTIFICATION_FILTERED;
        URI uri = subject.getFileUri(experimentAccession, fileType, "");

        String expectedUrl = MessageFormat.format(EXPERIMENT_FILES_ARCHIVE_URI_TEMPLATE,
                experimentAccession, fileType.getId(), "");
        assertThat(uri.toString(), is(expectedUrl));
    }

    private void existingFileOfType(String experimentAccession, ExperimentFileType fileType, String fileNameTemplate) {
        Path path = subject.getFilePath(experimentAccession, fileType);
        File file = path.toFile();

        assertThat(file.getName(), is(MessageFormat.format(fileNameTemplate, experimentAccession)));

        assertThat(file.exists(), is(true));
        assertThat(file.isDirectory(), is(false));
    }

    private void existingArchiveFilesOfType(String experimentAccession, ExperimentFileType fileType, List<String> fileNameTemplates) {
        List<Path> paths = subject.getFilePathsForArchive(experimentAccession, fileType);

        assertThat(paths.size(), is(fileNameTemplates.size()));

        for(Path path : paths) {
            File file = path.toFile();

            assertThat(file.exists(), is(true));
            assertThat(file.isDirectory(), is(false));
        }

        List<String> fileNames = paths.stream()
                .map(Path::toFile)
                .map(File::getName)
                .collect(Collectors.toList());

        assertThat(fileNames,
                containsInAnyOrder(
                        fileNameTemplates
                                .stream()
                                .map(template -> MessageFormat.format(template, experimentAccession))
                                .toArray()));
    }
}
