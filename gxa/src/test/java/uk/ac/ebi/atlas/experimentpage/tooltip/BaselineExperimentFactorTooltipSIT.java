
package uk.ac.ebi.atlas.experimentpage.tooltip;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BaselineExperimentFactorTooltipSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void factorTooltipHeader() {
        assertThat(subject.getFactorTooltipHeader(0), is("Property"));
        assertThat(subject.getFactorTooltipHeader(1), is("Value (N=1)"));
    }

    @Test
    public void adiposeFactorTooltip() {
        assertThat(subject.getFactorTooltipContent(1, 0, 0), is("organism part"));
        assertThat(subject.getFactorTooltipContent(1, 0, 1), is("adipose tissue"));
        assertThat(subject.getFactorTooltipContent(1, 1, 0), is("age"));
        assertThat(subject.getFactorTooltipContent(1, 1, 1), is("73 year"));
        assertThat(subject.getFactorTooltipContent(1, 4, 0), is("sex"));
        assertThat(subject.getFactorTooltipContent(1, 4, 1), is("female"));
    }

}
