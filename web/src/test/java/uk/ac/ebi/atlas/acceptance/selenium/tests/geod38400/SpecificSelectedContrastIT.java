package uk.ac.ebi.atlas.acceptance.selenium.tests.geod38400;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class SpecificSelectedContrastIT extends SeleniumFixture {

    private static final String E_E_GEOD_38400_ACCESSION = "E-GEOD-38400";
    protected HeatmapTablePage subject;

    @Test
    public void verifyQueryFactorLableAndHeatmapHeaders() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP&displayLevels=true&queryFactorValues=g1_g4");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is("Contrast"));

        assertThat(subject.getFactorValueHeaders().size(), is(3));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("idn2"));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP_DOWN&displayLevels=true&queryFactorValues=g1_g4");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 34"));

        assertThat(subject.getSelectedProfiles().size(), is(34));
        assertThat(subject.getSelectedProfiles().subList(0, 3), contains("T5N23_130", "AT3G29644", "GRXS4"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(0), is("<10-10"));

        assertThat(subject.getGeneProfile(2).size(), is(3));
        assertThat(subject.getGeneProfile(2).get(0), is("6.64"+ " \u00D7 " + "10-9"));
        assertThat(subject.getGeneProfile(2).get(1), is("0.014"));
    }

}
