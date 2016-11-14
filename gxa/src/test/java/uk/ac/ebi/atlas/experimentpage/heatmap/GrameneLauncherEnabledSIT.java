
package uk.ac.ebi.atlas.experimentpage.heatmap;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GrameneLauncherEnabledSIT extends SinglePageSeleniumFixture {

    public void getStartingPage() {
    }

    @Test
    public void diffPlantExperimentWithTracksShouldHaveGrameneLauncher() {
        HeatmapTablePage subject = new HeatmapTablePage(driver, "E-TABM-51");
        subject.get();
        assertThat(subject.hasGrameneLauncher(), is(true));
    }

    @Test
    public void diffPlantExperimentWithoutTracksShouldNotHaveGrameneLauncher() {
        HeatmapTablePage subject = new HeatmapTablePage(driver, "E-GEOD-38400");
        subject.get();
        assertThat(subject.hasGrameneLauncher(), is(false));
    }

    @Test
    public void baselinePlantExperimentWithTracksShouldHaveGrameneLauncher() {
        HeatmapTablePage subject = new HeatmapTablePage(driver, "E-MTAB-2039", "geneQuery=");
        subject.get();
        assertThat(subject.hasGrameneLauncher(), is(true));
    }

    @Test
    public void baselinePlantExperimentWithoutTracksShouldNotHaveGrameneLauncher() {
        HeatmapTablePage subject = new HeatmapTablePage(driver, "E-MTAB-2809", "geneQuery=");
        subject.get();
        assertThat(subject.hasGrameneLauncher(), is(false));
    }

}
