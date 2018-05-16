package uk.ac.ebi.atlas.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:applicationContext.xml")
public class DataFileHubIT {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataFileHubIT.class);

    @Inject
    private DataFileHubFactory dataFileHubFactory;
    @Inject
    private JdbcUtils jdbcUtils;

    @Test
    public void testGetExperimentFiles() {
        DataFileHub subject = dataFileHubFactory.getGxaDataFileHub();
        String experimentAccession = jdbcUtils.fetchRandomExpressionAtlasExperimentAccession();
        LOGGER.info("Test experiment files for experiment " + experimentAccession);

        assertAtlasResourceExists(subject.getExperimentFiles(experimentAccession).analysisMethods);
        assertAtlasResourceExists(subject.getExperimentFiles(experimentAccession).condensedSdrf);
        assertAtlasResourceExists(subject.getExperimentFiles(experimentAccession).experimentDesign);
    }

    @Test
    public void testGetBaselineFiles() {
        DataFileHub subject = dataFileHubFactory.getGxaDataFileHub();
        String experimentAccession = jdbcUtils.fetchRandomExpressionAtlasExperimentAccession(ExperimentType.RNASEQ_MRNA_BASELINE);
        LOGGER.info("Test baseline experiment files for experiment " + experimentAccession);

        assertAtlasResourceExists(
                subject.getRnaSeqBaselineExperimentFiles(experimentAccession).dataFile(ExpressionUnit.Absolute.Rna.TPM));
        assertAtlasResourceExists(
                subject.getRnaSeqBaselineExperimentFiles(experimentAccession).dataFile(ExpressionUnit.Absolute.Rna.FPKM));
    }

    @Test
    public void testGetProteomicsBaselineFiles() {
        DataFileHub subject = dataFileHubFactory.getGxaDataFileHub();
        String experimentAccession = jdbcUtils.fetchRandomExpressionAtlasExperimentAccession(ExperimentType.PROTEOMICS_BASELINE);
        LOGGER.info("Test proteomics baseline experiment files for experiment " + experimentAccession);

        assertAtlasResourceExists(subject.getProteomicsBaselineExperimentFiles(experimentAccession).main);
    }

    @Test
    public void testGetDifferentialExperimentFiles() {
        DataFileHub subject = dataFileHubFactory.getGxaDataFileHub();
        String experimentAccession = jdbcUtils.fetchRandomExpressionAtlasExperimentAccession(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);
        LOGGER.info("Test differential experiment files for experiment " + experimentAccession);

        assertAtlasResourceExists(subject.getRnaSeqDifferentialExperimentFiles(experimentAccession).analytics);
        assertAtlasResourceExists(subject.getRnaSeqDifferentialExperimentFiles(experimentAccession).rawCounts);
    }

    @Test
    public void findsTSnePlotFiles() {
        DataFileHub subject = dataFileHubFactory.getScxaDataFileHub();
        String experimentAccession = jdbcUtils.fetchRandomSingleCellExperimentAccession();
        LOGGER.info("Test tsne plot files for experiment " + experimentAccession);

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
