
package uk.ac.ebi.atlas.experimentpage.differential.microarray;

import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class MicroRnaExperimentSIT extends SeleniumFixture {

    private static final String ACCESSION = "E-TABM-713";
    protected HeatmapTablePage subject;

    @Test
    public void verifyResultsWithRegulationUp() {
        subject = new HeatmapTablePage(driver, ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 4"));

        assertThat(subject.getGeneNames().size(), is(4));
        assertThat(subject.getGeneNames().subList(0, 3), contains("MIMAT0002177", "MIMAT0002177", "MIMAT0000259"));

        assertThat(subject.getGeneProfile(1).size(), is(1));
        assertThat(subject.getGeneProfile(1).get(0), is("2.7"));
    }

    @Test
    public void verifyResultsWithRegulationDown() {
        subject = new HeatmapTablePage(driver, ACCESSION, "regulation=DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 5"));

        assertThat(subject.getGeneNames().size(), is(5));
        assertThat(subject.getGeneNames().subList(0, 3), contains("MIMAT0002809", "MIMAT0002809", "MIMAT0000449"));

        assertThat(subject.getGeneProfile(1).size(), is(1));
        assertThat(subject.getGeneProfile(1).get(0), is("-1.3"));

    }

}
