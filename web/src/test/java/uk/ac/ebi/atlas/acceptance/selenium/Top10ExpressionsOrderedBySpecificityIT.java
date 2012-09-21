package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.LoadableComponent;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExpressionsTablePage;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Top10ExpressionsOrderedBySpecificityIT extends SeleniumFixture {

    private ExpressionsTablePage subject;

    public LoadableComponent getStartingPage(FirefoxDriver firefoxDriver) {
        subject = new ExpressionsTablePage(firefoxDriver, "?rankingSize=10");
        return subject.get();
    }

    @Test
    public void titleShallBeExperiment() {
        assertThat(subject.getTitle(), is("Experiment"));
    }

    @Test
    public void verifyMostExpressedTranscriptId() {
        assertThat(subject.getTranscriptIdForGreatestExpressionLevel(), is("ENST00000390536"));
    }

    @Test
    public void verifyFactorValuesForMostExpressedTranscriptId() {
        assertThat(subject.getOrganismPartForGreatestExpressionLevel(), is("adrenal"));
    }

    @Test
    public void verifyExpressionLevelForMostExpressedTranscriptId() {
        assertThat(subject.getGreatestExpressionLevel(), is("2543.2410431147"));
    }

    @Test
    public void verifySpecificityForMostExpressedTranscriptId() {
        assertThat(subject.getSpecificityForGreatestExpressionLevel(), is("1"));
    }

    @Test
    public void verifyLeastExpressedTranscriptId() {
        assertThat(subject.getTranscriptIdForSmallestExpressionLevel(), is("ENST00000401258"));
    }

    @Test
    public void verifyFactorValuesForLeastExpressedTranscriptId() {
        assertThat(subject.getOrganismPartForSmallestExpressionLevel(), is("brain"));
    }

    @Test
    public void verifyExpressionLevelForLeastExpressedTranscriptId() {
        assertThat(subject.getSmallestExpressionLevel(), is("687.2741888147"));
    }

    @Test
    public void numberOfRowsInExpressionsTableShallBeTen() {
        assertThat(subject.getExpressionsTableRowCount(), is(10));
    }
}