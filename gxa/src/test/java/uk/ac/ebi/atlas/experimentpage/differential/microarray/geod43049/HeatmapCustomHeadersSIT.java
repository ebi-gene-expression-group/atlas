
package uk.ac.ebi.atlas.experimentpage.differential.microarray.geod43049;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapCustomHeadersSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-43049";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void verifyDownloadLinks() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();

        assertThat(subject.getDownloadExpressionProfilesLink(), endsWith("/gxa/experiments/E-GEOD-43049.tsv"));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("/gxa/experiments/E-GEOD-43049/all-analytics.tsv"));
        assertThat(subject.getDownloadLogFoldLink(), endsWith("/gxa/experiments/E-GEOD-43049/logFold.tsv"));
    }

    @Test
    public void shouldHaveAGeneHeader() {

        assertThat(subject.getGeneColumnHeader(), is("Gene"));

    }

    @Test
    public void shouldHaveADesignElement() {

        assertThat(subject.getDesignElementHeader(), is("Design Element"));

    }
}
