package uk.ac.ebi.atlas.acceptance.selenium.tests.heatmaptable;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SinglePageSeleniumFixture;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Cutoff20AndOrganismPartIT extends SinglePageSeleniumFixture {

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver,
                "specific=true&heatmapMatrixSize=5&geneQuery=&queryFactorValues=adrenal&_queryFactorValues=1&cutoff=20");
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getSelectedGenes();
        assertThat(selectedGenes.size(), is(5));
        assertThat(selectedGenes, contains("TRAJ13", "Y_RNA", "ABCD4", "PLCG2", "AC011293.1"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("", "776", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
    }

    @Test
    public void verifyLastGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getLastGeneProfile(), contains("", "238", "", "", "", "", "", "", ""
                , "388", "", "", "", "", "", ""));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("31"), is(true));
    }

}
