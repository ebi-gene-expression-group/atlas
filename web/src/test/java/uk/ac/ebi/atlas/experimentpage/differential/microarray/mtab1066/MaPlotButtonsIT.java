
package uk.ac.ebi.atlas.experimentpage.differential.microarray.mtab1066;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithMaPlotButtonsPage;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;

public class MaPlotButtonsIT extends SeleniumFixture {

    private static final String E_MTAB_1066_ACCESSION = "E-MTAB-1066";
    protected HeatmapTableWithMaPlotButtonsPage subject;

    @Before
    public void initSubject() {
        subject = new HeatmapTableWithMaPlotButtonsPage(driver, E_MTAB_1066_ACCESSION);
        subject.get();
    }

    @Ignore //TODO fix test
    @Test
    public void verifyButtonClick() {
        HeatmapTableWithMaPlotButtonsPage page = subject.clickMaPlotButton(0);
        assertThat(page.getMaPlotImageAnchor(), endsWith("E-MTAB-1066/A-AFFY-35/g2_g1/ma-plot.png"));
    }

}
