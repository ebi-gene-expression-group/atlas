package uk.ac.ebi.atlas.resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import javax.inject.Inject;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:applicationContext.xml")
public class DataFileHubIT {

    @Inject
    DataFileHubFactory dataFileHubFactory;

    @Test
    public void testGetExperimentFiles() {
        DataFileHub subject = dataFileHubFactory.getGxaDataFileHub();
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").analysisMethods);
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").condensedSdrf);
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").experimentDesign);
    }

    @Test
    public void testGetBaselineFiles() {
        DataFileHub subject = dataFileHubFactory.getGxaDataFileHub();
        assertAtlasResourceExists(
                subject.getRnaSeqBaselineExperimentFiles("E-MTAB-513").dataFile(ExpressionUnit.Absolute.Rna.TPM));
        assertAtlasResourceExists(
                subject.getRnaSeqBaselineExperimentFiles("E-MTAB-513").dataFile(ExpressionUnit.Absolute.Rna.FPKM));
        assertAtlasResourceExists(subject.getProteomicsBaselineExperimentFiles("E-PROT-1").main);
    }

    @Test
    public void testGetDifferentialExperimentFiles() {
        DataFileHub subject = dataFileHubFactory.getGxaDataFileHub();
        assertAtlasResourceExists(subject.getRnaSeqDifferentialExperimentFiles("E-GEOD-54705").analytics);
        assertAtlasResourceExists(subject.getRnaSeqDifferentialExperimentFiles("E-GEOD-54705").rawCounts);
    }

    @Test
    public void findsTSnePlotFiles() {
        DataFileHub subject = dataFileHubFactory.getScxaDataFileHub();
        assertAtlasResourceExists(subject.getSingleCellExperimentFiles("E-MTAB-5061").tSnePlotTsvs.values());
    }

    private void assertAtlasResourceExists(AtlasResource<?> resource) {
        assertThat(resource.exists()).isTrue();
    }

    private void assertAtlasResourceExists(Collection<? extends AtlasResource<?>> resource) {
        assertThat(resource).isNotEmpty();
        assertThat(resource).allMatch(AtlasResource::exists);
    }
}
