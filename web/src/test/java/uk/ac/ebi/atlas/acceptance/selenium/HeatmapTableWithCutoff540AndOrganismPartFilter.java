package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePageWithFilters;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithCutoff540AndOrganismPartFilter extends SeleniumFixture {

    private HeatmapTablePageWithFilters subject;

    public void getStartingPage() {
        subject = new HeatmapTablePageWithFilters(firefoxDriver,
                "?heatmapMatrixSize=5&organismParts=heart&_organismParts=1&cutoff=20&organismOriented");
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
        assertThat(selectedGenes, contains("ENSG00000210049", "ENSG00000118194", "ENSG00000129991",
                "ENSG00000175206", "ENSG00000205678"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        assertThat(subject.getFirstGeneProfile(), contains("978.915"));
    }

    @Test
    public void verifyLastGeneProfile() {
        assertThat(subject.getLastGeneProfile(), contains("378.219"));
    }

    @Test
    public void verifyGeneIdsText() {
        assertThat(subject.getGeneIDsString(), is(""));
    }
}
