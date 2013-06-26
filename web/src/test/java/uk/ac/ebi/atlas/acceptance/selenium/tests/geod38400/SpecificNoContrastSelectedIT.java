package uk.ac.ebi.atlas.acceptance.selenium.tests.geod38400;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class SpecificNoContrastSelectedIT extends SeleniumFixture {

    private static final String E_E_GEOD_38400_ACCESSION = "E-GEOD-38400";
    protected HeatmapTablePage subject;

    @Test
    public void verifyQueryFactorLableAndHeatmapHeaders() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is("Contrast"));

        assertThat(subject.getFactorValueHeaders().size(), is(3));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("nrpe1"));
    }

    @Test
    public void verifyResultsWithRegulationUp() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 36"));

        assertThat(subject.getSelectedProfiles().size(), is(36));
        assertThat(subject.getSelectedProfiles().subList(0, 3), contains("AT1G33840", "F14M2.2", "T5N23_130"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(0), is("<10-10"));
    }

    @Test
    public void verifyResultsWithRegulationDown() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 15"));

        assertThat(subject.getSelectedProfiles().size(), is(15));
        assertThat(subject.getSelectedProfiles().subList(0, 3), contains("NRPD1B", "AT2G07733", "AT5G40450"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(0), is("4.25" + " \u00D7 " + "10-5"));

        assertThat(subject.getLastGeneProfile().size(), is(3));
        assertThat(subject.getLastGeneProfile().get(0), is("3.22" + " \u00D7 " + "10-4"));
        assertThat(subject.getLastGeneProfile().get(2), is("7.29" + " \u00D7 " + "10-6"));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 51"));

        assertThat(subject.getSelectedProfiles().size(), is(50));
        assertThat(subject.getSelectedProfiles().subList(0, 3), contains("AT1G33840", "F14M2.2", "T5N23_130"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(0), is("<10-10"));

        assertThat(subject.getLastGeneProfile().size(), is(3));
        assertThat(subject.getLastGeneProfile().get(0), is("3.22" + " \u00D7 " + "10-4"));
        assertThat(subject.getLastGeneProfile().get(2), is("7.29" + " \u00D7 " + "10-6"));
    }

}
