
package uk.ac.ebi.atlas.experimentpage.heatmap;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TwoOrganismPartsAndCutoff9SIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION,
                "heatmapMatrixSize=5&queryFactorValues=adipose&geneQuery=&queryFactorValues=heart&_queryFactorValues=2&cutoff=9");
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getGeneNames();
        assertThat(selectedGenes.size(), is(3));
        assertThat(selectedGenes, contains("ENSG00000244656", "NEBL", "AL031284.1"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("", "", "", "", "", "", "57", ""
                , "", "", "", "", "", "", "", ""));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("3"), is(true));
    }

}
