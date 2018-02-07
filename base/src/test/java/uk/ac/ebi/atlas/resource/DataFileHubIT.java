package uk.ac.ebi.atlas.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
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
    private DataFileHub subject;

    @Test
    public void testGetExperimentFiles() {
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").analysisMethods);
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").condensedSdrf);
        assertAtlasResourceExists(subject.getExperimentFiles("E-MTAB-513").experimentDesign);
    }

    @Test
    public void testGetBaselineFiles() {
        assertAtlasResourceExists(
                subject.getRnaSeqBaselineExperimentFiles("E-MTAB-513").dataFile(ExpressionUnit.Absolute.Rna.TPM));
        assertAtlasResourceExists(
                subject.getRnaSeqBaselineExperimentFiles("E-MTAB-513").dataFile(ExpressionUnit.Absolute.Rna.FPKM));
        assertAtlasResourceExists(subject.getProteomicsBaselineExperimentFiles("E-PROT-1").main);
    }

    @Test
    public void testGetDifferentialExperimentFiles() {
        assertAtlasResourceExists(subject.getRnaSeqDifferentialExperimentFiles("E-GEOD-54705").analytics);
        assertAtlasResourceExists(subject.getRnaSeqDifferentialExperimentFiles("E-GEOD-54705").rawCounts);
    }

    @Test
    public void findsTSnePlotFiles() {
        assertAtlasResourceExists(subject.getSingleCellExperimentFiles("E-MTAB-5061").tSnePlotTsvs.values());
    }

    private void assertAtlasResourceExists(AtlasResource<?> resource) {
        assertThat(resource.exists());
    }

    private void assertAtlasResourceExists(Collection<? extends AtlasResource<?>> resource) {
        assertThat(resource).isNotEmpty();
        assertThat(resource).allMatch(AtlasResource::exists);
    }
}
