
package uk.ac.ebi.atlas.experimentpage.heatmap;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Cutoff20AndOrganismPartSIT extends SinglePageSeleniumFixture {

    private HeatmapTablePage subject;

    private final static String EXPERIMENT_ACCESSION = "E-MTAB-513";

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION,
                "specific=true&heatmapMatrixSize=5&geneQuery=&queryFactorValues=adrenal+gland&_queryFactorValues=1&cutoff=20");
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getGeneNames();
        assertThat(selectedGenes.size(), is(3));
        assertThat(selectedGenes, contains("TRAJ13", "Y_RNA", "MT-ATP6"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("", "766", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
    }

    @Test
    public void verifyLastGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getLastGeneProfile(), contains("", "10690", "6724", "", "", "", "", "", "4149", ""
                , "6899", "7810", "", "", "", "8664"));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("3"), is(true));
    }

}
