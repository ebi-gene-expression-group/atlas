
package uk.ac.ebi.atlas.experimentpage.differential.microarray.geod3307;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithMaPlotButtonsPage;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;

public class MaPlotButtonsIT extends SeleniumFixture {

    private static final String E_GEOD_3307_ACCESSION = "E-GEOD-3307?foldChangeCutOff=0";
    protected HeatmapTableWithMaPlotButtonsPage subject;

    @Before
    public void initSubject() {
        subject = new HeatmapTableWithMaPlotButtonsPage(driver, E_GEOD_3307_ACCESSION);
        subject.get();
    }

    @Ignore //TODO fix test
    @Test
    public void verifyButtonClick() {
        //HeatmapTableWithMaPlotButtonsPage page = subject.clickMaPlotButton(0);
        //assertThat(page.getMaPlotImageAnchor(), endsWith("E-GEOD-3779/A-AFFY-24/g2_g1/ma-plot.png"));
        HeatmapTableWithMaPlotButtonsPage page = subject.clickMaPlotButton(1);
        assertThat(page.getMaPlotImageAnchor(), endsWith("E-GEOD-3779/A-AFFY-23/g3_g4/ma-plot.png"));
    }

}
