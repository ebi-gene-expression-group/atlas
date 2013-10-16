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

package uk.ac.ebi.atlas.acceptance.selenium.tests.mtab1066;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.model.ExperimentType;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithDifferentRegulationsButDefaultQueryParamsIT extends SeleniumFixture {

    private static final String E_MTAB_1066_ACCESSION = "E-MTAB-1066";
    protected HeatmapTablePage subject;

    @Test
    public void verifyResultsWithRegulationUp() {
        subject = new HeatmapTablePage(driver, E_MTAB_1066_ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 71"));

        assertThat(subject.getSelectedProfiles().size(), is(50));
        assertThat(subject.getSelectedProfiles().subList(0, 3), contains("Irc", "CG8303", "Ykt6"));

        assertThat(subject.getGeneProfile(1).size(), is(2));
        assertThat(subject.getGeneProfile(1).get(0), is("4.32 × 10-4"));
        assertThat(subject.getGeneProfile(1).get(1), is(""));

        assertThat(subject.getLastGeneProfile().size(), is(2));
        assertThat(subject.getLastGeneProfile().get(0), is("3.73 × 10-5"));
        assertThat(subject.getLastGeneProfile().get(1), is("0.005"));
    }

    @Test
    public void verifyResultsWithRegulationDown() {
        subject = new HeatmapTablePage(driver, E_MTAB_1066_ACCESSION, "regulation=DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 104"));

        assertThat(subject.getSelectedProfiles().size(), is(50));
        assertThat(subject.getSelectedProfiles().subList(0, 3), contains("CG13876", "CG7742", "CG31803"));

        assertThat(subject.getGeneProfile(1).size(), is(2));
        assertThat(subject.getGeneProfile(1).get(0), is("8.36" + " \u00D7 " + "10-6"));
        assertThat(subject.getGeneProfile(1).get(1), is(""));

        assertThat(subject.getLastGeneProfile().size(), is(2));
        assertThat(subject.getLastGeneProfile().get(0), is(""));
        assertThat(subject.getLastGeneProfile().get(1), is("0.043"));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_MTAB_1066_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 174"));

        assertThat(subject.getSelectedProfiles().size(), is(50));
        assertThat(subject.getSelectedProfiles().subList(0, 3), contains("CG13876", "CG7742", "CG31803"));

        assertThat(subject.getGeneProfile(1).size(), is(2));
        assertThat(subject.getGeneProfile(1).get(0), is("8.36" + " \u00D7 " + "10-6"));
        assertThat(subject.getGeneProfile(1).get(1), is(""));

        assertThat(subject.getLastGeneProfile().size(), is(2));
        assertThat(subject.getLastGeneProfile().get(0), is("0.022"));
        assertThat(subject.getLastGeneProfile().get(1), is(""));
    }

    //This will fail with PhantomJS
    @Test
    public void heatmapCellTooltipTest() {
        subject = new HeatmapTablePage(driver, E_MTAB_1066_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();

        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 0, ExperimentType.MICROARRAY), is("Adjusted p-value"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 1, ExperimentType.MICROARRAY), is("t-statistic"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 2, ExperimentType.MICROARRAY), startsWith("Log2-fold"));

        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 0, ExperimentType.MICROARRAY), is("8.36 × 10-6"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 1, ExperimentType.MICROARRAY), is("-19.16"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 2, ExperimentType.MICROARRAY), is("-1.56"));
    }

}
