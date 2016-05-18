
package uk.ac.ebi.atlas.experimentpage.genequery;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SpecificAndOrganismPartSIT extends SinglePageSeleniumFixture {

    private static final String E_MTAB_599_ACCESSION = "E-MTAB-599";
    private static final String HTTP_PARAMETERS = "exactMatch=false&specific=true&geneQuery=Cyp2d10%09Tdo2%09Serpina1d%09Apoh%09Albumin&queryFactorValues=liver&_queryFactorValues=1&cutoff=0.5";

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, E_MTAB_599_ACCESSION, HTTP_PARAMETERS);
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getGeneNames();
        assertThat(selectedGenes.size(), is(9));
        assertThat(selectedGenes, contains("Gc", "Alb", "Apoh", "Cyp2d10",  "Afm", "Serpina1d", "Tdo2", "5830473C10Rik", "Afp"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("", "", "4209", "", "", ""));
    }

    @Test
    public void verifyLastGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getLastGeneProfile(), contains("", "", "3", "", "1", ""));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("9"), is(true));
    }

}
