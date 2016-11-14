
package uk.ac.ebi.atlas.experimentpage.differential.rnaseq.geod38400;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class SpecificSelectedContrastSIT extends SeleniumFixture {

    private static final String E_E_GEOD_38400_ACCESSION = "E-GEOD-38400";
    protected HeatmapTablePage subject;

    @Test
    public void verifyQueryFactorLabelAndHeatmapHeaders() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP&displayLevels=true&queryFactorValues=g1_g4");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is("Comparison"));

        assertThat(subject.getFactorValueHeaders().size(), is(3));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("nrpe1"));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP_DOWN&displayLevels=true&queryFactorValues=g1_g4");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 34"));

        assertThat(subject.getGeneNames().size(), is(34));
        assertThat(subject.getGeneNames().subList(0, 3), contains("T5N23_130", "T5N23_90", "AT1G11785"));

        //This one drops down because of fold change algorithm
        assertThat(subject.getGeneNames().get(33), is("DML1"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(2), is("3.6"));

        assertThat(subject.getGeneProfile(34).size(), is(3));
        assertThat(subject.getGeneProfile(34).get(0), is("-3.1"));
        assertThat(subject.getGeneProfile(34).get(2), is("-3.7"));
    }

}
