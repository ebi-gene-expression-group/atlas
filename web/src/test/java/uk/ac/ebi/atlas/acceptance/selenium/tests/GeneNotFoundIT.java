package uk.ac.ebi.atlas.acceptance.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithSearchFormPage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeneNotFoundIT extends SeleniumFixture {

    private HeatmapTableWithSearchFormPage subject;

    public void getStartingPage() {
        subject = new HeatmapTableWithSearchFormPage(driver, "geneQuery=cdnjdsnfdksjfbc&serializedFilterFactors=&queryFactorType=&heatmapMatrixSize=50&displayLevels=false&displayGeneDistribution=false&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5");
        subject.get();
    }

    @Test
    public void shouldDisplayErrorMessage() {
        assertThat(subject.getPreferencesErrors(), startsWith("No genes found"));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotDisplayHeatmapMessage() {
        subject.getHeatmapMessage();
    }

}
