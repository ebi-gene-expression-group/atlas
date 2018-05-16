package uk.ac.ebi.atlas.resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
class DataFileHubIT {
    @Inject
    JdbcUtils jdbcTestUtils;

    @Inject
    DataFileHubFactory dataFileHubFactory;

    @Test
    void testGetExperimentFiles() {
        DataFileHub subject = dataFileHubFactory.getGxaDataFileHub();
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").analysisMethods);
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").condensedSdrf);
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").experimentDesign);
    }

    @Test
    void testGetBaselineFiles() {
        DataFileHub subject = dataFileHubFactory.getGxaDataFileHub();
        assertAtlasResourceExists(
                subject.getRnaSeqBaselineExperimentFiles("E-MTAB-513").dataFile(ExpressionUnit.Absolute.Rna.TPM));
        assertAtlasResourceExists(
                subject.getRnaSeqBaselineExperimentFiles("E-MTAB-513").dataFile(ExpressionUnit.Absolute.Rna.FPKM));
        assertAtlasResourceExists(subject.getProteomicsBaselineExperimentFiles("E-PROT-1").main);
    }

    @Test
    void testGetDifferentialExperimentFiles() {
        DataFileHub subject = dataFileHubFactory.getGxaDataFileHub();
        assertAtlasResourceExists(subject.getRnaSeqDifferentialExperimentFiles("E-GEOD-54705").analytics);
        assertAtlasResourceExists(subject.getRnaSeqDifferentialExperimentFiles("E-GEOD-54705").rawCounts);
    }

    @Test
    void findsTSnePlotFiles() {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        DataFileHub subject = dataFileHubFactory.getScxaDataFileHub();
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
