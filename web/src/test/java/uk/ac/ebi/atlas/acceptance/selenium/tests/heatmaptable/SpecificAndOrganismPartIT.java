package uk.ac.ebi.atlas.acceptance.selenium.tests.heatmaptable;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SpecificAndOrganismPartIT extends SeleniumFixture {

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver,
                "specific=true&geneQuery=Cyp2d10+Tdo2+Serpina1d+Apoh+Albumin&queryFactorValues=liver&_queryFactorValues=1&cutoff=0.5") {
            @Override
            protected String getPageURI() {
                return "/gxa/experiments/E-MTAB-599";
            }
        };
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getSelectedGenes();
        assertThat(selectedGenes.size(), is(8));
        assertThat(selectedGenes, contains("Gc", "Apoh", "Serpina1d", "Tdo2", "Cyp2d10", "Afm", "Ecm1", "5830473C10Rik"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("", "", "4582", "0.7", "36", "0.8"));
    }

    @Test
    public void verifyLastGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getLastGeneProfile(), contains("", "", "18", "", "", ""));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("8"), is(true));
    }

}
