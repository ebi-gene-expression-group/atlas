
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormAndBarChartPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BarChartProteomicsSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-PROT-1";

    private HeatmapTableWithSearchFormAndBarChartPage subject;

    @Override
    protected void getStartingPage() {
        subject = new HeatmapTableWithSearchFormAndBarChartPage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Before
    public void displayBarChart() {
        subject.clickDisplayChartButton();
    }

    @Ignore
    public void legendLabel() {
        assertThat(subject.getLegendLabel(), is("Y = number of genes expressed above the given expression level cutoff in any experimental variable"));
    }

}