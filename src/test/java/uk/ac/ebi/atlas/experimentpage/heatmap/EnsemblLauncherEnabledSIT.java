
package uk.ac.ebi.atlas.experimentpage.heatmap;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EnsemblLauncherEnabledSIT extends SinglePageSeleniumFixture {

    public void getStartingPage() {
    }

    @Test
    public void diffExperimentWithTracksShouldHaveEnsemblLauncher() {
        HeatmapTablePage subject = new HeatmapTablePage(driver, "E-GEOD-3307", "foldChangeCutOff=0&cutoff=1");
        subject.get();
        assertThat(subject.hasEnsemblLauncher(), is(true));
    }

    @Test
    public void diffExperimentWithoutTracksShouldNotHaveEnsemblLauncher() {
        HeatmapTablePage subject = new HeatmapTablePage(driver, "E-TABM-713");
        subject.get();
        assertThat(subject.hasEnsemblLauncher(), is(false));
    }

    @Test
    public void baselineExperimentWithTracksShouldHaveEnsemblLauncher() {
        HeatmapTablePage subject = new HeatmapTablePage(driver, "E-MTAB-599");
        subject.get();
        assertThat(subject.hasEnsemblLauncher(), is(true));
    }

    @Test
    public void baselineExperimentWithoutTracksShouldNotHaveEnsemblLauncher() {
        HeatmapTablePage subject = new HeatmapTablePage(driver, "E-MTAB-1733");
        subject.get();
        assertThat(subject.hasEnsemblLauncher(), is(false));
    }

}
