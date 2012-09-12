package uk.ac.ebi.atlas.acceptance.selenium;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.LoadableComponent;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentPage;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.assertThat;

public class VisualizeOrderedBySpecificityTopTenExpressionLevelsIT extends SeleniumFixture {

    private ExperimentPage subject;

    public LoadableComponent getStartingPage(FirefoxDriver firefoxDriver) {
        subject = new ExperimentPage(firefoxDriver, "?rankingSize=10");
        return subject.get();
    }

    @Test
    public void titleShallBeExperiment() {
        assertThat(subject.getTitle(), is("Experiment"));
    }

    @Test
    public void verifyMostExpressedTranscriptId() {
        assertThat(subject.getTranscriptIdForGreatestRPKMValue(), is("ENST00000390536"));
    }

    @Test
    public void verifyFactorValuesForMostExpressedTranscriptId() {
        assertThat(subject.getFactorValuesForGreatestRPKMValue(), stringContainsInOrder(Lists.newArrayList("adrenal", "caucasian")));
    }

    @Test
    public void verifyRPKMForMostExpressedTranscriptId() {
        assertThat(subject.getGreatestRPKMValue(), is("2543.2410431147"));
    }

    @Test
    public void verifySpecificityForMostExpressedTranscriptId() {
        assertThat(subject.getSpecificityForGreatestRPKMValue(), is("1"));
    }

    @Test
    public void verifyLeastExpressedTranscriptId() {
        assertThat(subject.getTranscriptIdForSmallestRPKMValue(), is("ENST00000401258"));
    }

    @Test
    public void verifyFactorValuesForLeastExpressedTranscriptId() {
        assertThat(subject.getFactorValuesForSmallestRPKMValue(), stringContainsInOrder(Lists.newArrayList("brain", "caucasian")));
    }

    @Test
    public void verifyRPKMForLeastExpressedTranscriptId() {
        assertThat(subject.getSmallestRPKMValue(), is("687.2741888147"));
    }

    @Test
    public void numberOfRowsInExpressionLevelsTableShallBeTen() {
        assertThat(subject.getTableRowCount(), is(10));
    }
}