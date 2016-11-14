
package uk.ac.ebi.atlas.experimentpage.heatmap;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class DisplayValuesButtonSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    protected HeatmapTableWithSearchFormPage subject;

    public void getStartingPage() {
        subject = new HeatmapTableWithSearchFormPage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void displayLevelButtonShouldOnlyToggleHeatmapVisualizationStyle() throws InterruptedException {
        //given
        String geneCountBefore = subject.getGeneCount();
        //when
        String displayLevelsValue = subject.getDisplayLevelsButtonValue();
        //then
        assertThat(geneCountBefore, equalTo(subject.getGeneCount()));
        assertThat(subject.areGradientLevelsHidden(),is(true));
        assertThat(subject.areExpressionLevelsHidden(),is(true));
        assertThat(displayLevelsValue.toLowerCase(), containsString("display"));
        //and when
        subject.clickDisplayLevelsButton();
        displayLevelsValue = subject.getDisplayLevelsButtonValue();
        //Thread.sleep(2000);
        //then
        assertThat(geneCountBefore, equalTo(subject.getGeneCount()));
        assertThat(subject.areGradientLevelsHidden(), is(false));
        assertThat(subject.areExpressionLevelsHidden(),is(false));
        assertThat(displayLevelsValue.toLowerCase(), containsString("hide"));
        //and when
        subject.clickDisplayLevelsButton();
        displayLevelsValue = subject.getDisplayLevelsButtonValue();
        //then again
        assertThat(geneCountBefore, equalTo(subject.getGeneCount()));
        assertThat(subject.areGradientLevelsHidden(),is(true));
        assertThat(subject.areExpressionLevelsHidden(),is(true));
        assertThat(displayLevelsValue.toLowerCase(), containsString("display"));
    }

    @Test
    public void displayLevelStateShouldBePreservedBetweenSearchRequests() {
        //given
        String geneCountBefore = subject.getGeneCount();
        //when
        subject.clickDisplayLevelsButton();
        //and
        subject.setCutoff(11.1);
        //and
        subject = subject.submit();
        String displayLevelsValue = subject.getDisplayLevelsButtonValue();
        //then the page has reloaded, to verify it let's check that gene count has a different value
        assertThat(geneCountBefore, not(equalTo(subject.getGeneCount())));
        //and
        assertThat(displayLevelsValue.toLowerCase(), containsString("hide"));
        assertThat(subject.areGradientLevelsHidden(),is(false));
        assertThat(subject.areExpressionLevelsHidden(),is(false));

    }

}
