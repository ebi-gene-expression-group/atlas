
package uk.ac.ebi.atlas.experimentpage.differential.rnaseq.geod38400;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class NonSpecificSelectedContrastSIT extends SeleniumFixture {

    private static final String E_E_GEOD_38400_ACCESSION = "E-GEOD-38400";
    protected HeatmapTablePage subject;

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP_DOWN&displayLevels=true&queryFactorValues=g1_g4&specific=false");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 34"));

        assertThat(subject.getGeneNames().size(), is(34));
        assertThat(subject.getGeneNames().subList(0, 3), contains("DML1", "T5N23_130", "AT3G29644"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(2), is("-3.7"));

        assertThat(subject.getGeneProfile(2).size(), is(3));
        assertThat(subject.getGeneProfile(2).get(2), is("3.6"));

        assertThat(subject.getGeneProfile(3).size(), is(3));
        assertThat(subject.getGeneProfile(3).get(0), is("2.6"));
        assertThat(subject.getGeneProfile(3).get(2), is("3.3"));
    }

}
