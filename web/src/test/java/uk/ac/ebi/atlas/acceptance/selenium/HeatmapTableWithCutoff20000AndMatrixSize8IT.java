package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithCutoff20000AndMatrixSize8IT extends SeleniumFixture {

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(firefoxDriver, "cutoff=20000&heatmapMatrixSize=8");
        subject.get();
    }

    @Test
    public void verifyOrganismParts() {
        assertThat(subject.getOrganismParts().size(), is(5));
        //and
        assertThat(subject.getOrganismParts(), contains("adrenal", "heart", "liver", "lung", "white blood cells"));
    }

    @Test
    public void verifySelectedGenes() {
        assertThat(subject.getSelectedGenes(), contains("ALB", "MT-CO3", "IGKC", "CRP", "FGB", "RP11-1143G9.4",
                "B2M"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        assertThat(subject.getFirstGeneProfile(), contains("", "", "48053", "", ""));
    }

    @Test
    public void verifyLastGeneProfile() {
        assertThat(subject.getLastGeneProfile(), contains("", "", "", "27521", "41999"));
    }

}
