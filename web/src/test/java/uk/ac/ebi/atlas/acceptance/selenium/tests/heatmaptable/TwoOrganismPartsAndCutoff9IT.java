package uk.ac.ebi.atlas.acceptance.selenium.tests.heatmaptable;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TwoOrganismPartsAndCutoff9IT extends SeleniumFixture {

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver,
                "heatmapMatrixSize=5&queryFactorValues=adipose&geneQuery=&queryFactorValues=heart&_queryFactorValues=2&cutoff=9");
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getSelectedGenes();
        assertThat(selectedGenes.size(), is(5));
        assertThat(selectedGenes, contains("AL031284.1", "AC068312.1", "NEBL", "AC091038.1", "S1PR1"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("228", "", "", "", "", "579", "", ""
                , "", "", "", "", "", "", "", ""));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("35"), is(true));
    }

}
