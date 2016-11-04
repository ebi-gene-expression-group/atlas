
package uk.ac.ebi.atlas.experimentpage.tooltip;

import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DifferentialExperimentContrastTooltipSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MEXP-1099";

    private HeatmapTablePage subject;

    @Override
    protected void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }
    //This is not working with PhantomJS browser :((
    @Test
    public void checkContrastSummaryTooltipTableHeader() {
        assertThat(subject.getContrastTooltipTableHeader(0), is("Property"));
        assertThat(subject.getContrastTooltipTableHeader(1), is("Test value (N=5)"));
        assertThat(subject.getContrastTooltipTableHeader(2), is("Reference value (N=5)"));
    }

    //This is not working with PhantomJS browser :((
    @Test
    public void checkContrastSummaryTooltipTableContext() {
        assertThat(subject.getContrastTooltipContent(1, 0, 0), is("genotype"));
        assertThat(subject.getContrastTooltipContent(1, 0, 1), is("heterozygous for chico mutation"));
        assertThat(subject.getContrastTooltipContent(1, 0, 2), is("wild type genotype"));
        assertThat(subject.getContrastTooltipContent(1, 7, 0), is("sex"));
        assertThat(subject.getContrastTooltipContent(1, 7, 1), is("female"));
        assertThat(subject.getContrastTooltipContent(1, 7, 2), is("female"));
    }

    //This is not working with PhantomJS browser :((
    @Test
    public void checkContrastSummaryTooltipExperimentAndContrastDescription() {
        assertThat(subject.getContrastTooltipExperimentDescription(), is("Transcription profiling by array of Drosophila homozygous and heterozygous chico mutants"));
        assertThat(subject.getContrastTooltipContrastDescription(), is("'heterozygous for chico mutation' vs 'wild type'"));
    }

}
