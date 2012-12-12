package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithCutoff05AndGeneFilterIT extends SeleniumFixture {

    private static final String HTTP_PARAMETERS = "geneQuery=LINC00402%2C+RP11-192H23.4&cutoff=0.5";
    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, HTTP_PARAMETERS);
        subject.get();
    }

    @Test
    public void verifyOrganismParts() {
        assertThat(subject.getOrganismParts().size(), is(4));
        //and
        assertThat(subject.getOrganismParts(), contains("adrenal", "brain", "lymph node", "white blood cells"));
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getSelectedGenes();
        assertThat(selectedGenes.size(), is(2));
        assertThat(selectedGenes, contains("RP11-192H23.4", "LINC00402"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        assertThat(subject.getFirstGeneProfile(), contains("7", "6", "", ""));
    }

    @Test
    public void verifyLastGeneProfile() {
        assertThat(subject.getLastGeneProfile(), contains("2", "", "0.8", "3"));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("2"), is(true));
    }

    @Test
    public void verifyDownloadExpressionProfilesLink() {
        assertThat(subject.getDownloadExpressionProfilesLink(), endsWith(HeatmapTablePage.EXPERIMENT_ACCESSION + ".tsv?" + HTTP_PARAMETERS));
    }
}
