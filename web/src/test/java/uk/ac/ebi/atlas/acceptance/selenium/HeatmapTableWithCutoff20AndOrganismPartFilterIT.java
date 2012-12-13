package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithCutoff20AndOrganismPartFilterIT extends SeleniumFixture {

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver,
                "heatmapMatrixSize=5&geneQuery=&organismParts=adrenal&_organismParts=1&cutoff=20");
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getSelectedGenes();
        assertThat(selectedGenes.size(), is(2));
        assertThat(selectedGenes, contains("TRAJ13", "Y_RNA"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        assertThat(subject.getFirstGeneProfile(), contains("","776","","","","","",""
                                                           ,"","","","","","","",""));
    }

    @Test
    public void verifyLastGeneProfile() {
        assertThat(subject.getLastGeneProfile(), contains("","36","","","","","",""
                                                        ,"","","","","","","",""));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("2"), is(true));
    }

}
