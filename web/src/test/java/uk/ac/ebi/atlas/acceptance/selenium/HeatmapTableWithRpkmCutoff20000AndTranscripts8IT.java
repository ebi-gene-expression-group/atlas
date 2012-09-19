package uk.ac.ebi.atlas.acceptance.selenium;

import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.LoadableComponent;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithRpkmCutoff20000AndTranscripts8IT extends SeleniumFixture {

    private HeatmapTablePage subject;

    public LoadableComponent getStartingPage(FirefoxDriver firefoxDriver) {
        subject = new HeatmapTablePage(firefoxDriver, "?rpkmCutOff=20000&heatmapMatrixSize=8");
        return subject.get();
    }

    @Test
    public void verifyOrganismParts() {
        assertThat(subject.getOrganismParts().size(), is(9));
        //and
        assertThat(subject.getOrganismParts(), hasItems("adipose", "colon"));
    }

}
