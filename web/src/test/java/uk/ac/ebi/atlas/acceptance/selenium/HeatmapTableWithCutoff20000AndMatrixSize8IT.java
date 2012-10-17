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
        assertThat(subject.getSelectedGenes(), contains("ENSG00000163631", "ENSG00000198938", "ENSG00000211592", "ENSG00000132693", "ENSG00000171564", "ENSG00000257764", "ENSG00000166710"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        assertThat(subject.getFirstGeneProfile(), contains("", "", "48053.1", "", ""));
    }

    @Test
    public void verifyLastGeneProfile() {
        assertThat(subject.getLastGeneProfile(), contains("", "", "", "27520.9", "41998.5"));
    }

}
