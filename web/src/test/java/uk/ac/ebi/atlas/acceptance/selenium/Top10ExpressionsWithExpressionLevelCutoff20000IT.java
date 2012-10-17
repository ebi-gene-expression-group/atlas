package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExpressionsTablePage;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Top10ExpressionsWithExpressionLevelCutoff20000IT extends SeleniumFixture {
    private ExpressionsTablePage subject;

    public void getStartingPage() {
        subject = new ExpressionsTablePage(firefoxDriver, "cutoff=20000&rankingSize=10");
        subject.get();
    }

    @Test
    public void titleShallBeExperiment() {
        assertThat(subject.getTitle(), is("Experiment"));
    }

    @Test
    public void verifyMostExpressedGeneId() {
        assertThat(subject.getGeneIdForGreatestExpressionLevel(), is("ENSG00000163631"));
    }

    @Test
    public void verifyFactorValuesForMostExpressedGeneId() {
        assertThat(subject.getOrganismPartForGreatestExpressionLevel(), is("liver"));
    }

    @Test
    public void verifyExpressionLevelForMostExpressedGeneId() {
        assertThat(subject.getGreatestExpressionLevel(), is("48053.1"));
    }

    @Test
    public void verifySpecificityForMostExpressedGeneId() {
        assertThat(subject.getSpecificityForGreatestExpressionLevel(), is("1"));
    }

    @Test
    public void verifyLeastExpressedGeneId() {
        assertThat(subject.getGeneIdForSmallestExpressionLevel(), is("ENSG00000198888"));
    }

    @Test
    public void verifyFactorValuesForLeastExpressedGeneId() {
        assertThat(subject.getOrganismPartForSmallestExpressionLevel(), is("heart"));
    }

    @Test
    public void verifyExpressionLevelForLeastExpressedGeneId() {
        assertThat(subject.getSmallestExpressionLevel(), is("27288.0"));
    }

    @Test
    public void verifySpecificityForLeastExpressedGeneId() {
        assertThat(subject.getSpecificityForSmallestExpressionLevel(), is("3"));
    }

    @Test
    public void numberOfRowsInExpressionsTableShallBeTen() {
        assertThat(subject.getExpressionsTableRowCount(), is(10));
    }

}
