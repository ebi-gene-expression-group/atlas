
package uk.ac.ebi.atlas.experimentpage.differential.microarray.mtab1066;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapCustomHeadersSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-1066";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void verifyDownloadLinks() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();

        assertThat(subject.getDownloadExpressionProfilesLink(), endsWith("/gxa/experiments/E-MTAB-1066.tsv"));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("/gxa/experiments/E-MTAB-1066/all-analytics.tsv"));
        assertThat(subject.getDownloadNormalizedLink(), endsWith("/gxa/experiments/E-MTAB-1066/normalized.tsv"));
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
