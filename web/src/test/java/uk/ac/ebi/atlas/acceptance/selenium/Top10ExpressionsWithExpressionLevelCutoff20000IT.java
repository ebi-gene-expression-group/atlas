package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExpressionsTablePage;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Top10ExpressionsWithExpressionLevelCutoff20000IT extends SeleniumFixture {
    private ExpressionsTablePage subject;

    public void getStartingPage() {
        subject = new ExpressionsTablePage(firefoxDriver, "?cutoff=20000&rankingSize=10");
        subject.get();
    }

    @Test
    public void titleShallBeExperiment() {
        assertThat(subject.getTitle(), is("Experiment"));
    }

    @Test
    public void verifyMostExpressedTranscriptId() {
        assertThat(subject.getTranscriptIdForGreatestExpressionLevel(), is("ENST00000486939"));
    }

    @Test
    public void verifyFactorValuesForMostExpressedTranscriptId() {
        assertThat(subject.getOrganismPartForGreatestExpressionLevel(), is("liver"));
    }

    @Test
    public void verifyExpressionLevelForMostExpressedTranscriptId() {
        assertThat(subject.getGreatestExpressionLevel(), is("36872.6684888937"));
    }

    @Test
    public void verifySpecificityForMostExpressedTranscriptId() {
        assertThat(subject.getSpecificityForGreatestExpressionLevel(), is("1"));
    }

    @Test
    public void verifyLeastExpressedTranscriptId() {
        assertThat(subject.getTranscriptIdForSmallestExpressionLevel(), is("ENST00000361789"));
    }

    @Test
    public void verifyFactorValuesForLeastExpressedTranscriptId() {
        assertThat(subject.getOrganismPartForSmallestExpressionLevel(), is("adipose"));
    }

    @Test
    public void verifyExpressionLevelForLeastExpressedTranscriptId() {
        assertThat(subject.getSmallestExpressionLevel(), is("24566.6097632967"));
    }

    @Test
    public void verifySpecificityForLeastExpressedTranscriptId() {
        assertThat(subject.getSpecificityForSmallestExpressionLevel(), is("5"));
    }

    @Test
    public void numberOfRowsInExpressionsTableShallBeTen() {
        assertThat(subject.getExpressionsTableRowCount(), is(10));
    }

}
