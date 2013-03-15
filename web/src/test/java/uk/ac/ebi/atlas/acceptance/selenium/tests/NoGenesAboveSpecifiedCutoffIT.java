package uk.ac.ebi.atlas.acceptance.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SinglePageSeleniumFixture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

public class NoGenesAboveSpecifiedCutoffIT extends SinglePageSeleniumFixture {

    private HeatmapTableWithSearchFormPage subject;

    public void getStartingPage() {
        subject = new HeatmapTableWithSearchFormPage(driver, "serializedFilterFactors=&queryFactorType=&heatmapMatrixSize=50&displayLevels=true&displayGeneDistribution=true&geneQuery=U3&_queryFactorValues=1&specific=true&_specific=on&cutoff=50");
        subject.get();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotDisplayErrorMessage() {
        assertThat(subject.getPreferencesErrors(), startsWith("No genes found"));
    }

    @Test
    public void shouldNotDisplayHeatmapMessage() {
        assertThat(subject.getHeatmapMessage(), startsWith("No expressions found"));
    }

}
