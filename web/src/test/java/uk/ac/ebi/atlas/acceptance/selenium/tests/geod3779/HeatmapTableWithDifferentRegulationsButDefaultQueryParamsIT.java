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

package uk.ac.ebi.atlas.acceptance.selenium.tests.geod3779;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.model.ExperimentType;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithDifferentRegulationsButDefaultQueryParamsIT extends SeleniumFixture {

    private static final String E_GEOD_3779_ACCESSION = "E-GEOD-3779";
    protected HeatmapTablePage subject;

    @Test
    public void verifyResultsWithRegulationUp() {
        subject = new HeatmapTablePage(driver, E_GEOD_3779_ACCESSION, "regulation=UP&displayLevels=true&cutoff=1");
        subject.get();
        assertThat(subject.getGeneCount(), is("Showing 21 of 21 genes found:"));

        assertThat(subject.getSelectedProfiles().size(), is(21));
        assertThat(subject.getSelectedProfiles().subList(0, 3), contains("Snx30", "Mycl1", "Scn2a1"));

        assertThat(subject.getGeneProfile(1).size(), is(2));
        assertThat(subject.getGeneProfile(1).get(0), is("0.02"));
        assertThat(subject.getGeneProfile(1).get(1), is(""));

        assertThat(subject.getLastGeneProfile().size(), is(2));
        assertThat(subject.getLastGeneProfile().get(0), is(""));
        assertThat(subject.getLastGeneProfile().get(1), is("0.987"));
    }

    @Test
    public void verifyResultsWithRegulationDown() {
        subject = new HeatmapTablePage(driver, E_GEOD_3779_ACCESSION, "regulation=DOWN&displayLevels=true&cutoff=1");
        subject.get();
        assertThat(subject.getGeneCount(), is("Showing 39 of 39 genes found:"));

        assertThat(subject.getSelectedProfiles().size(), is(39));
        assertThat(subject.getSelectedProfiles().subList(0, 3), contains("Nhlrc1", "4933433P14Rik", "Snx30"));

        assertThat(subject.getGeneProfile(1).size(), is(2));
        assertThat(subject.getGeneProfile(1).get(0), is("0.914"));
        assertThat(subject.getGeneProfile(1).get(1), is(""));

        assertThat(subject.getLastGeneProfile().size(), is(2));
        assertThat(subject.getLastGeneProfile().get(0), is("0.993"));
        assertThat(subject.getLastGeneProfile().get(1), is(""));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_GEOD_3779_ACCESSION, "regulation=UP_DOWN&displayLevels=true&cutoff=1");
        subject.get();
        assertThat(subject.getGeneCount(), is("Showing 50 of 60 genes found:"));

        assertThat(subject.getSelectedProfiles().size(), is(50));
        assertThat(subject.getSelectedProfiles().subList(0, 3), contains("Snx30", "Mycl1", "Snx30"));

        assertThat(subject.getGeneProfile(1).size(), is(2));
        assertThat(subject.getGeneProfile(1).get(0), is("0.02"));
        assertThat(subject.getGeneProfile(1).get(1), is(""));

        assertThat(subject.getLastGeneProfile().size(), is(2));
        assertThat(subject.getLastGeneProfile().get(0), is(""));
        assertThat(subject.getLastGeneProfile().get(1), is("0.979"));
    }

    @Test
    public void heatmapCellTooltipTest() {
        subject = new HeatmapTablePage(driver, E_GEOD_3779_ACCESSION, "regulation=UP_DOWN&displayLevels=true&cutoff=1");
        subject.get();

        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 0, ExperimentType.MICROARRAY), is("Adjusted P-value"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 1, ExperimentType.MICROARRAY), is("T-statistic"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 2, ExperimentType.MICROARRAY), startsWith("Log2-fold"));

        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 0, ExperimentType.MICROARRAY), is("0.02"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 1, ExperimentType.MICROARRAY), is("0.12"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 2, ExperimentType.MICROARRAY), is("0.01"));
    }

}
