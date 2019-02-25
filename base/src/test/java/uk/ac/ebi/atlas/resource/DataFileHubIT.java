package uk.ac.ebi.atlas.resource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.nio.file.Path;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional(transactionManager = "txManager")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataFileHubIT {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataFileHubIT.class);

    @Inject
    private DataSource dataSource;

    @Inject
    private Path dataFilesPath;

    @Inject
    private JdbcUtils jdbcUtils;

    private DataFileHub subject;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/experiment-fixture.sql"),
                new ClassPathResource("fixtures/scxa_experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/experiment-delete.sql"),
                new ClassPathResource("fixtures/scxa_experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @Test
    void testGetExperimentFiles() {
        subject = new DataFileHub(dataFilesPath.resolve("gxa"));
        String experimentAccession = jdbcUtils.fetchRandomExpressionAtlasExperimentAccession();
        LOGGER.info("Test experiment files for experiment {}", experimentAccession);

        assertAtlasResourceExists(subject.getExperimentFiles(experimentAccession).analysisMethods);
        assertAtlasResourceExists(subject.getExperimentFiles(experimentAccession).condensedSdrf);
        assertAtlasResourceExists(subject.getExperimentFiles(experimentAccession).experimentDesign);
    }

    @Test
    void testGetBaselineFiles() {
        subject = new DataFileHub(dataFilesPath.resolve("gxa"));
        String experimentAccession =
                jdbcUtils.fetchRandomExpressionAtlasExperimentAccession(ExperimentType.RNASEQ_MRNA_BASELINE);
        LOGGER.info("Test baseline experiment files for experiment {}", experimentAccession);

        assertAtlasResourceExists(
                subject.getRnaSeqBaselineExperimentFiles(experimentAccession)
                        .dataFile(ExpressionUnit.Absolute.Rna.TPM));
        assertAtlasResourceExists(
                subject.getRnaSeqBaselineExperimentFiles(experimentAccession)
                        .dataFile(ExpressionUnit.Absolute.Rna.FPKM));
    }

    @Test
    void testGetProteomicsBaselineFiles() {
        subject = new DataFileHub(dataFilesPath.resolve("gxa"));
        String experimentAccession =
                jdbcUtils.fetchRandomExpressionAtlasExperimentAccession(ExperimentType.PROTEOMICS_BASELINE);
        LOGGER.info("Test proteomics baseline experiment files for experiment {}", experimentAccession);

        assertAtlasResourceExists(subject.getProteomicsBaselineExperimentFiles(experimentAccession).main);
    }

    @Test
    void testGetDifferentialExperimentFiles() {
        subject = new DataFileHub(dataFilesPath.resolve("gxa"));
        String experimentAccession =
                jdbcUtils.fetchRandomExpressionAtlasExperimentAccession(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);
        LOGGER.info("Test differential experiment files for experiment {}", experimentAccession);

        assertAtlasResourceExists(subject.getRnaSeqDifferentialExperimentFiles(experimentAccession).analytics);
        assertAtlasResourceExists(subject.getRnaSeqDifferentialExperimentFiles(experimentAccession).rawCounts);
    }

    @Test
    void findsTSnePlotFiles() {
        String experimentAccession = jdbcUtils.fetchRandomSingleCellExperimentAccession();
        subject = new DataFileHub(dataFilesPath.resolve("scxa"));
        LOGGER.info("Test tsne plot files for experiment {}", experimentAccession);
        assertAtlasResourceExists(subject.getSingleCellExperimentFiles(experimentAccession).tSnePlotTsvs.values());
    }

    @Test
    void findsMarkerGeneFiles() {
        String experimentAccession = jdbcUtils.fetchRandomSingleCellExperimentAccession();
        DataFileHub subject = new DataFileHub(dataFilesPath.resolve("scxa"));
        LOGGER.info("Test marker gene files for experiment {}", experimentAccession);
        assertAtlasResourceExists(subject.getSingleCellExperimentFiles(experimentAccession).markerGeneTsvs.values());
    }

    @Test
    void findsRawFilteredCountsFiles() {
        String experimentAccession = jdbcUtils.fetchRandomSingleCellExperimentAccession();
        DataFileHub subject = new DataFileHub(dataFilesPath.resolve("scxa"));
        LOGGER.info("Test raw filtered count files for experiment {}", experimentAccession);
        assertAtlasResourceExists(subject.getSingleCellExperimentFiles(experimentAccession).filteredCountsMatrix);
        assertAtlasResourceExists(subject.getSingleCellExperimentFiles(experimentAccession).filteredCountsGeneIdsTsv);
        assertAtlasResourceExists(subject.getSingleCellExperimentFiles(experimentAccession).filteredCountsCellIdsTsv);
    }

    @Test
    void findsNormalisedCountsFiles() {
        String experimentAccession = jdbcUtils.fetchRandomSingleCellExperimentAccession();
        DataFileHub subject = new DataFileHub(dataFilesPath.resolve("scxa"));
        LOGGER.info("Test normalised filtered count files for experiment {}", experimentAccession);
        assertAtlasResourceExists(subject.getSingleCellExperimentFiles(experimentAccession).normalisedCountsMatrix);
        assertAtlasResourceExists(subject.getSingleCellExperimentFiles(experimentAccession).normalisedCountsGeneIdsTsv);
        assertAtlasResourceExists(subject.getSingleCellExperimentFiles(experimentAccession).normalisedCountsCellIdsTsv);
    }

    private void assertAtlasResourceExists(AtlasResource<?> resource) {
        assertThat(resource.exists()).isTrue();
    }

    private void assertAtlasResourceExists(Collection<? extends AtlasResource<?>> resource) {
        assertThat(resource).isNotEmpty();
        assertThat(resource).allMatch(AtlasResource::exists);
    }
}
