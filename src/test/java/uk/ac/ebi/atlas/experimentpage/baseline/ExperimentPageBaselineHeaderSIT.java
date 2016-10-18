
package uk.ac.ebi.atlas.experimentpage.baseline;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;

public class ExperimentPageBaselineHeaderSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-1733";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void downloadAllExpressions() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();

        assertThat(subject.getDownloadAllExpressionsLink(), endsWith("/experiments/E-MTAB-1733.tsv?accessKey=&geneQuery=&cutoff=-0.1"));
    }

}
