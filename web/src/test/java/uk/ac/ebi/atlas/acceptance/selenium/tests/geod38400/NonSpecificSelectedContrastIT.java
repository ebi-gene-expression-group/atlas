package uk.ac.ebi.atlas.acceptance.selenium.tests.geod38400;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class NonSpecificSelectedContrastIT extends SeleniumFixture {

    private static final String E_E_GEOD_38400_ACCESSION = "E-GEOD-38400";
    protected HeatmapTablePage subject;

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP_DOWN&displayLevels=true&queryFactorValues=g1_g4&specific=false");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 34"));

        assertThat(subject.getSelectedGenes().size(), is(34));
        assertThat(subject.getSelectedGenes().subList(0, 3), contains("T5N23_130", "AT3G29644", "GRXS4"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(0), is("<10-10"));

        assertThat(subject.getGeneProfile(2).size(), is(3));
        assertThat(subject.getGeneProfile(2).get(0), is("6.64" + " \u00D7 " + "10-9"));
        assertThat(subject.getGeneProfile(2).get(1), is("0.014"));

        assertThat(subject.getGeneProfile(5).size(), is(3));
        assertThat(subject.getGeneProfile(5).get(0), is("7.29" + " \u00D7 " + "10-6"));
        assertThat(subject.getGeneProfile(5).get(1), is("3.22" + " \u00D7 " + "10-4"));
    }

}
