package uk.ac.ebi.atlas.acceptance.selenium.tests.geod38400;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class NonSpecificNoContrastSelectedIT extends SeleniumFixture {

    private static final String E_E_GEOD_38400_ACCESSION = "E-GEOD-38400";
    protected HeatmapTablePage subject;

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP_DOWN&displayLevels=true&specific=false");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 51"));

        assertThat(subject.getSelectedGenes().size(), is(50));
        assertThat(subject.getSelectedGenes().subList(0, 3), contains("AT1G33840", "AT1G33850", "AT3G54770"));
        assertThat(subject.getSelectedGenes().subList(13, 14), contains("AT2G36490"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(1), is("<10-10"));

        assertThat(subject.getGeneProfile(14).size(), is(3));
        assertThat(subject.getGeneProfile(14).get(0), is("7.29" + " \u00D7 " + "10-6"));
        assertThat(subject.getGeneProfile(14).get(1), is("3.22"+ " \u00D7 " + "10-4"));
    }

}
