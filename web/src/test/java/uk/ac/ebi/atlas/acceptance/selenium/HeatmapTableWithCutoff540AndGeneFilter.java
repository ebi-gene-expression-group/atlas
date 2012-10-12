package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePageWithFilters;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithCutoff540AndGeneFilter extends SeleniumFixture {

    private HeatmapTablePageWithFilters subject;

    public void getStartingPage() {
        subject = new HeatmapTablePageWithFilters(firefoxDriver, "?geneIDsString=ENSG00000175084+ENSG00000210195&cutoff=540");
        subject.get();
    }

    @Test
    public void verifyOrganismParts() {
        assertThat(subject.getOrganismParts().size(), is(6));
        //and
        assertThat(subject.getOrganismParts(), contains("colon", "heart", "kidney", "prostate", "skeletal muscle", "thyroid"));
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getSelectedGenes();
        assertThat(selectedGenes.size(), is(2));
        assertThat(selectedGenes, contains("ENSG00000210195", "ENSG00000175084"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        assertThat(subject.getFirstGeneProfile(), contains("", "", "954.83", "", "", ""));
    }

    @Test
    public void verifyLastGeneProfile() {
        assertThat(subject.getLastGeneProfile(), contains("2272.05", "5004.5", "", "2720.43", "6611.91", "571.218"));
    }

    @Test
    public void verifyGeneIdsText() {
        assertThat(subject.getGeneIDsString(), is("ENSG00000175084 ENSG00000210195"));
    }
}
