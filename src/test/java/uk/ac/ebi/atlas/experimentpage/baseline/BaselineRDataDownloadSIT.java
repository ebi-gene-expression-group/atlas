
package uk.ac.ebi.atlas.experimentpage.baseline;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class BaselineRDataDownloadSIT extends SinglePageSeleniumFixture {

    private static final String BASELINE_EXPERIMENT_WITH_R_DATA_DOWNLOAD_BUTTON = "E-GEOD-26284";
    private static final String BASELINE_EXPERIMENT_WITH_NO_R_DATA_DOWNLOAD_BUTTON = "E-MTAB-3358";
    private static final String PROTEOMICS_EXPERIMENT_WITH_NO_R_DATA_DOWNLOAD_BUTTON = "E-PROT-1";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
    }

    @Test
    public void testBaselineExperimentWithRData() {
        subject = new HeatmapTablePage(driver, BASELINE_EXPERIMENT_WITH_R_DATA_DOWNLOAD_BUTTON);
        subject.get();

        assertNotNull(driver.findElement(By.id("download-r")));
    }

    @Test(expected = NoSuchElementException.class)
    public void testBaselineExperimentWithoutRData() {
        subject = new HeatmapTablePage(driver, BASELINE_EXPERIMENT_WITH_NO_R_DATA_DOWNLOAD_BUTTON);
        subject.get();

        driver.findElement(By.id("download-r"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testProteomicsBaselineExperimentWithoutRData() {
        subject = new HeatmapTablePage(driver, PROTEOMICS_EXPERIMENT_WITH_NO_R_DATA_DOWNLOAD_BUTTON);
        subject.get();

        driver.findElement(By.id("download-r"));
    }


}
