package uk.ac.ebi.atlas.acceptance.selenium.tests.mirna;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class SpecificNoContrastSelectedIT extends SeleniumFixture {

    private static final String ACCESSION = "E-TABM-713";
    protected HeatmapTablePage subject;

    @Test
    public void verifyResultsWithRegulationUp() {
        subject = new HeatmapTablePage(driver, ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 42"));

        assertThat(subject.getSelectedProfiles().size(), is(42));
        assertThat(subject.getSelectedProfiles().subList(0, 3), contains("MIMAT0003220", "MIMAT0000439", "MIMAT0003249"));

        assertThat(subject.getGeneProfile(1).size(), is(1));
        assertThat(subject.getGeneProfile(1).get(0), is("0.003"));
    }

    @Test
    public void verifyResultsWithRegulationDown() {
        subject = new HeatmapTablePage(driver, ACCESSION, "regulation=DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 56"));

        assertThat(subject.getSelectedProfiles().size(), is(50));
        assertThat(subject.getSelectedProfiles().subList(0, 3), contains("MIMAT0002184", "MIMAT0003227", "MIMAT0002184"));

        assertThat(subject.getGeneProfile(1).size(), is(1));
        assertThat(subject.getGeneProfile(1).get(0), is("1.81" + " \u00D7 " + "10-4"));

    }

}
