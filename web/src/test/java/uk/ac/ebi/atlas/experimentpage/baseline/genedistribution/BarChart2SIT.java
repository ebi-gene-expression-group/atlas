
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;


import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormAndBarChartPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BarChart2SIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

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

    @Test
    public void checkBarChartAxisForAllOrganismParts() {
        assertThat(subject.getXAxisValue(0), is("0"));
        assertThat(subject.getXAxisValue(1), is("0.2"));
        assertThat(subject.getMaxXAxisValue(), is("40"));

        assertThat(subject.getYAxisValue(0), is("0"));
        assertThat(subject.getYAxisValue(1), is("100"));
        assertThat(subject.getMaxYAxisValue(), is("400"));
    }

    @Test
    public void checkBarChartAxisForSkeletalMuscle() {
        subject.selectQueryFactorValue("skeletal muscle");
        assertThat(subject.getYAxisValue(0), is("0"));
        assertThat(subject.getYAxisValue(1), is("50"));
        assertThat(subject.getMaxYAxisValue(), is("200"));
    }

}