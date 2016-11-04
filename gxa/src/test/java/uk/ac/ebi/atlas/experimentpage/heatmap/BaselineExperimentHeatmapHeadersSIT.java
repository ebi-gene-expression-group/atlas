
package uk.ac.ebi.atlas.experimentpage.heatmap;

import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BaselineExperimentHeatmapHeadersSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void shouldHaveAGeneHeader() {
        assertThat(subject.getGeneColumnHeader(), is("Gene"));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotHaveADesignElement() {
        subject.getDesignElementHeader();
    }
}
