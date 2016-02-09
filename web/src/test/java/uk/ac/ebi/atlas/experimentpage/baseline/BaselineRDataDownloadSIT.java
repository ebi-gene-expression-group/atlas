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

package uk.ac.ebi.atlas.experimentpage.baseline;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class BaselineRDataDownloadSIT extends SinglePageSeleniumFixture {

    private static final String BASELINE_EXPERIMENT_WITH_R_DATA_DOWNLOAD_BUTTON = "E-GEOD-26284";
    private static final String BASELINE_EXPERIMENT_WITH_NO_R_DATA_DOWNLOAD_BUTTON = "E-GEOD-41338";
    private static final String PROTEOMICS_EXPERIMENT_WITH_NO_R_DATA_DOWNLOAD_BUTTON = "E-PROT-1";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, BASELINE_EXPERIMENT_WITH_R_DATA_DOWNLOAD_BUTTON);
        subject.get();
    }

    @Test
    public void testBaselineExperimentWithRData() {
        subject = new HeatmapTablePage(driver, BASELINE_EXPERIMENT_WITH_R_DATA_DOWNLOAD_BUTTON);
        subject.get();

        assertNotNull(driver.findElement(By.id("download-r")));
    }

    @Test(expected = NoSuchElementException.class)
    public void testBaselineExperimentWithoutRData() {
        subject = new HeatmapTablePage(driver, BASELINE_EXPERIMENT_WITH_NO_R_DATA_DOWNLOAD_BUTTON);
        subject.get();

        driver.findElement(By.id("download-r"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testProteomicsBaselineExperimentWithoutRData() {
        subject = new HeatmapTablePage(driver, PROTEOMICS_EXPERIMENT_WITH_NO_R_DATA_DOWNLOAD_BUTTON);
        subject.get();

        driver.findElement(By.id("download-r"));
    }


}
