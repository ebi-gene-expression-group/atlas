package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.LoadableComponent;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExpressionsTablePage;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Top10ExpLevelsWithRpkmCutoff20000IT extends SeleniumFixture {
    private ExpressionsTablePage subject;

    public LoadableComponent getStartingPage(FirefoxDriver firefoxDriver) {
        subject = new ExpressionsTablePage(firefoxDriver, "?rpkmCutOff=20000&rankingSize=10");
        return subject.get();
    }

    @Test
    public void titleShallBeExperiment() {
        assertThat(subject.getTitle(), is("Experiment"));
    }

    @Test
    public void verifyMostExpressedTranscriptId() {
        assertThat(subject.getTranscriptIdForGreatestRPKMValue(), is("ENST00000486939"));
    }

    @Test
    public void verifyFactorValuesForMostExpressedTranscriptId() {
        assertThat(subject.getOrganismPartForGreatestRPKMValue(), is("liver"));
    }

    @Test
    public void verifyRPKMForMostExpressedTranscriptId() {
        assertThat(subject.getGreatestRPKMValue(), is("36872.6684888937"));
    }
 
    @Test
    public void verifySpecificityForMostExpressedTranscriptId() {
        assertThat(subject.getSpecificityForGreatestRPKMValue(), is("1"));
    }

    @Test
    public void verifyLeastExpressedTranscriptId() {
        assertThat(subject.getTranscriptIdForSmallestRPKMValue(), is("ENST00000361789"));
    }

    @Test
    public void verifyFactorValuesForLeastExpressedTranscriptId() {
        assertThat(subject.getOrganismPartForSmallestRPKMValue(), is("adipose"));
    }

    @Test
    public void verifyRPKMForLeastExpressedTranscriptId() {
        assertThat(subject.getSmallestRPKMValue(), is("24566.6097632967"));
    }

    @Test
    public void verifySpecificityForLeastExpressedTranscriptId() {
        assertThat(subject.getSpecificityForSmallestRPKMValue(), is("5"));
    }

    @Test
    public void numberOfRowsInExpressionLevelsTableShallBeTen() {
        assertThat(subject.getExpressionsTableRowCount(), is(10));
    }

}
