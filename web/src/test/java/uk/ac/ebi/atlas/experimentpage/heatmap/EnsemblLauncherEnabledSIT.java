/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

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
