package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class HeatmapTableDisplayValuesIT extends SeleniumFixture {

    protected HeatmapTableWithSearchFormPage subject;

    public void getStartingPage() {
        subject = new HeatmapTableWithSearchFormPage(firefoxDriver);
        subject.get();
    }

    @Test
    public void displayLevelButtonShouldOnlyToggleHeatmapVisualizationStyle() {
        //given
        String geneCountBefore = subject.getGeneCount();
        //when
        String displayLevelsValue = subject.getDisplayLevelsButtonValue();
        //then
        assertThat(geneCountBefore, equalTo(subject.getGeneCount()));
        assertThat(displayLevelsValue.toLowerCase(), containsString("display"));
        //and when
        subject.clickDisplayLevelsButton();
        displayLevelsValue = subject.getDisplayLevelsButtonValue();
        //then
        assertThat(geneCountBefore, equalTo(subject.getGeneCount()));
        assertThat(displayLevelsValue.toLowerCase(), containsString("hide"));
        //and when
        subject.clickDisplayLevelsButton();
        displayLevelsValue = subject.getDisplayLevelsButtonValue();
        //then again
        assertThat(geneCountBefore, equalTo(subject.getGeneCount()));
        assertThat(displayLevelsValue.toLowerCase(), containsString("display"));
    }

    @Test
    public void displayLevelStateShouldBePreservedBetweenSearchRequests() {
        //given
        String geneCountBefore = subject.getGeneCount();
        //when
        subject.clickDisplayLevelsButton();
        //and
        subject.setCutoff(1111.1);
        //and
        subject = subject.submit();
        String displayLevelsValue = subject.getDisplayLevelsButtonValue();
        //then the page has refreshed, gene count is different for the new cutoff
        assertThat(geneCountBefore, not(equalTo(subject.getGeneCount())));
        //and
        assertThat(displayLevelsValue.toLowerCase(), containsString("hide"));

    }

}
