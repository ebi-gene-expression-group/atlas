
package uk.ac.ebi.atlas.experimentpage.differential.rnaseq.geod38400;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class SpecificNoContrastSelectedSIT extends SeleniumFixture {

    private static final String E_E_GEOD_38400_ACCESSION = "E-GEOD-38400";
    protected HeatmapTablePage subject;

    @Test
    public void verifyQueryFactorLableAndHeatmapHeaders() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is("Comparison"));

        assertThat(subject.getFactorValueHeaders().size(), is(3));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("nrpe1"));
    }

    @Test
    public void verifyResultsWithRegulationUp() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 36"));

        assertThat(subject.getGeneNames().size(), is(36));
        assertThat(subject.getGeneNames().subList(0, 3), contains("AT3G48131", "F14M2.2", "AT1G33840"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(0), is("6.9"));
    }

    @Test
    public void verifyResultsWithRegulationDown() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 15"));

        assertThat(subject.getGeneNames().size(), is(15));
        assertThat(subject.getGeneNames().subList(0, 3), contains("NRPD1B", "ATMG00510", "PSBE"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(0), is("-2.3"));

        assertThat(subject.getLastGeneProfile().size(), is(3));
        assertThat(subject.getLastGeneProfile().get(0), is("-3.1"));
        assertThat(subject.getLastGeneProfile().get(2), is("-3.7"));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 51"));

        assertThat(subject.getGeneNames().size(), is(50));
        assertThat(subject.getGeneNames().subList(0, 3), contains("AT3G48131", "F14M2.2", "AT1G33840"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(0), is("6.9"));

        assertThat(subject.getLastGeneProfile().size(), is(3));
        assertThat(subject.getLastGeneProfile().get(0), is("-3.1"));
        assertThat(subject.getLastGeneProfile().get(2), is("-3.7"));
    }

}
