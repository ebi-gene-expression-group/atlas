package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithCutoff540AndOrganismPartFilterIT extends SeleniumFixture {

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(firefoxDriver,
                "heatmapMatrixSize=5&organismParts=heart&_organismParts=1&cutoff=20");
        subject.get();
    }

    @Test
    public void verifyOrganismParts() {
        assertThat(subject.getOrganismParts().size(), is(1));
        //and
        assertThat(subject.getOrganismParts(), contains("heart"));
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getSelectedGenes();
        assertThat(selectedGenes.size(), is(5));
        assertThat(selectedGenes, contains("J01415.2", "TNNT2", "TNNI3", "NPPA", "TECRL"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        assertThat(subject.getFirstGeneProfile(), contains("979"));
    }

    @Test
    public void verifyLastGeneProfile() {
        assertThat(subject.getLastGeneProfile(), contains("378"));
    }

}
