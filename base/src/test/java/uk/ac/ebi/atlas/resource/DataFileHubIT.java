package uk.ac.ebi.atlas.resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class DataFileHubIT {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataFileHubIT.class);

    @Inject
    private Path dataFilesPath;

    @Inject
    private JdbcUtils jdbcUtils;

    private DataFileHub subject;

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

    private void assertAtlasResourceExists(AtlasResource<?> resource) {
        assertThat(resource.exists()).isTrue();
    }

    private void assertAtlasResourceExists(Collection<? extends AtlasResource<?>> resource) {
        assertThat(resource).isNotEmpty();
        assertThat(resource).allMatch(AtlasResource::exists);
    }
}
