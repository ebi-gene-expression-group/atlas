
package uk.ac.ebi.atlas.experimentpage.differential.rnaseq.geod38400;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class NonSpecificNoContrastSelectedSIT extends SeleniumFixture {

    private static final String E_E_GEOD_38400_ACCESSION = "E-GEOD-38400";
    protected HeatmapTablePage subject;

    @Test
    public void verifyDownloadLinks() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION);
        subject.get();

        assertThat(subject.getDownloadExpressionProfilesLink(), endsWith("/gxa/experiments/E-GEOD-38400.tsv"));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("/gxa/experiments/E-GEOD-38400/all-analytics.tsv"));
        assertThat(subject.getDownloadRawCountsLink(), endsWith("/gxa/experiments/E-GEOD-38400/raw-counts.tsv"));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_E_GEOD_38400_ACCESSION, "regulation=UP_DOWN&displayLevels=true&specific=false");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 51"));

        assertThat(subject.getGeneNames().size(), is(50));
        assertThat(subject.getGeneNames().subList(0, 3), contains("AT3G48131", "DML1", "F14M2.2"));
        assertThat(subject.getGeneNames().subList(25, 26), contains("GRXS5"));

        assertThat(subject.getGeneProfile(1).size(), is(3));
        assertThat(subject.getGeneProfile(1).get(0), is("6.9"));
        assertThat(subject.getGeneProfile(1).get(1), isEmptyString());
        assertThat(subject.getGeneProfile(1).get(2), isEmptyString());

    }

}
