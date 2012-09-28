package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExpressionsTablePage;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Top10ExpressionsOrderedBySpecificityIT extends SeleniumFixture {

    private ExpressionsTablePage subject;

    public void getStartingPage() {
        subject = new ExpressionsTablePage(firefoxDriver, "?rankingSize=10");
        subject.get();
    }

    @Test
    public void titleShallBeExperiment() {
        assertThat(subject.getTitle(), is("Experiment"));
    }

    @Test
    public void verifyMostExpressedGeneId() {
        assertThat(subject.getGeneIdForGreatestExpressionLevel(), is("ENSG00000211888"));
    }

    @Test
    public void verifyFactorValuesForMostExpressedGeneId() {
        assertThat(subject.getOrganismPartForGreatestExpressionLevel(), is("adrenal"));
    }

    @Test
    public void verifyExpressionLevelForMostExpressedGeneId() {
        assertThat(subject.getGreatestExpressionLevel(), is("2543.24"));
    }

    @Test
    public void verifySpecificityForMostExpressedGeneId() {
        assertThat(subject.getSpecificityForGreatestExpressionLevel(), is("1"));
    }

    @Test
    public void verifyLeastExpressedGeneId() {
        assertThat(subject.getGeneIdForSmallestExpressionLevel(), is("ENSG00000200769"));
    }

    @Test
    public void verifyFactorValuesForLeastExpressedGeneId() {
        assertThat(subject.getOrganismPartForSmallestExpressionLevel(), is("ovary"));
    }

    @Test
    public void verifyExpressionLevelForLeastExpressedGeneId() {
        assertThat(subject.getSmallestExpressionLevel(), is("651.968"));
    }

    @Test
    public void numberOfRowsInExpressionsTableShallBeTen() {
        assertThat(subject.getExpressionsTableRowCount(), is(10));
    }
}