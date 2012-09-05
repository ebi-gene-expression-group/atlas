package uk.ac.ebi.atlas.acceptance.selenium;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.LoadableComponent;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentPage;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.assertThat;

public class VisualizeTopTenExpressionLevelsIT extends SeleniumFixture{

    private ExperimentPage subject;

    public LoadableComponent getStartingPage(FirefoxDriver firefoxDriver) {
        subject = new ExperimentPage(firefoxDriver);
        return subject.get();
    }

    @Test
    public void titleShallBeExperiment() {
        assertThat(subject.getTitle(), is("Experiment"));
    }

    @Test
    public void verifyMostExpressedTranscriptId() {
        assertThat(subject.getTranscriptIdForGreatestRPKMValue(), is("ENST00000005178"));
    }

    @Test
    public void verifyFactorValuesForMostExpressedTranscriptId() {
        assertThat(subject.getFactorValuesForGreatestRPKMValue(), stringContainsInOrder(Lists.newArrayList("adipose", "caucasian")));
    }

    @Test
    public void verifyRPKMForMostExpressedTranscriptId() {
        assertThat(subject.getGreatestRPKMValue(), is("285.083"));
    }

    @Test
    public void verifyLeastExpressedTranscriptId() {
        assertThat(subject.getTranscriptIdForSmallestRPKMValue(), is("ENST00000004982"));
    }

    @Test
    public void verifyFactorValuesForLeastExpressedTranscriptId() {
        assertThat(subject.getFactorValuesForSmallestRPKMValue(), stringContainsInOrder(Lists.newArrayList("heart", "caucasian")));
    }

    @Test
    public void verifyRPKMForLeastExpressedTranscriptId() {
        assertThat(subject.getSmallestRPKMValue(), is("159.087"));
    }

    @Test
    public void numberOfRowsInExpressionLevelsTableShallBeTen() {
        assertThat(subject.getTableRowCount(), is(10));
    }
}