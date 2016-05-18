
package uk.ac.ebi.atlas.experimentpage.differential.microarray;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public class DifferentialExperimentPageControllerNoExpressionSIT extends SeleniumFixture {

    protected HeatmapTablePage subject;

    @Test
    public void microarrayDifferential_noExpressionsFoundMessageWhenGeneHasNoExpression() {
        subject = new HeatmapTablePage(driver, "E-TABM-713", "geneQuery=miR-17-92");
        subject.get();

        assertThat(subject.getHeatmapMessage(), startsWith("No expressions found"));
    }

    @Test
    public void rnaSeqDifferential_noExpressionsFoundMessageWhenGeneHasNoExpression() {
        subject = new HeatmapTablePage(driver, "E-GEOD-21860", "geneQuery=Brca1");
        subject.get();

        assertThat(subject.getHeatmapMessage(), startsWith("No expressions found"));
    }

}
