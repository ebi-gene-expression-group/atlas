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

package uk.ac.ebi.atlas.experimentpage.differential.microarray.geod3779;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.model.ExperimentType;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class MicroArray2ArrayDesignsHeatmapTableWithDifferentRegulationsButDefaultQueryParamsSIT extends SeleniumFixture {

    private static final String E_GEOD_3779_ACCESSION = "E-GEOD-3779";
    protected HeatmapTablePage subject;

    @Test
    public void verifyResultsWithRegulationUp() {
        subject = new HeatmapTablePage(driver, E_GEOD_3779_ACCESSION, "regulation=UP&displayLevels=true&cutoff=1&foldChangeCutOff=0");
        subject.get();
        assertThat(subject.getGeneCount(), is("Showing 21 of 21 genes found:"));

        assertThat(subject.getGeneNames().size(), is(21));
        assertThat(subject.getGeneNames().subList(0, 3), contains("Prrx2", "Scn2a1", "Iws1"));

        assertThat(subject.getGeneProfile(1).size(), is(2));
        assertThat(subject.getGeneProfile(1).get(0), is(""));
        assertThat(subject.getGeneProfile(1).get(1), is("0.2"));

        assertThat(subject.getLastGeneProfile().size(), is(2));
        assertThat(subject.getLastGeneProfile().get(0), is(""));
        assertThat(subject.getLastGeneProfile().get(1), is("0"));
    }

    @Test
    public void verifyResultsWithRegulationDown() {
        subject = new HeatmapTablePage(driver, E_GEOD_3779_ACCESSION, "regulation=DOWN&displayLevels=true&cutoff=1&foldChangeCutOff=0");
        subject.get();
        assertThat(subject.getGeneCount(), is("Showing 39 of 39 genes found:"));

        assertThat(subject.getGeneNames().size(), is(39));
        assertThat(subject.getGeneNames().subList(0, 3), contains("Ikzf5", "Mapre1", "Tdrd7"));

        assertThat(subject.getGeneProfile(1).size(), is(2));
        assertThat(subject.getGeneProfile(1).get(0), is("-0.5"));
        assertThat(subject.getGeneProfile(1).get(1), is(""));

        assertThat(subject.getLastGeneProfile().size(), is(2));
        assertThat(subject.getLastGeneProfile().get(0), is("-0"));
        assertThat(subject.getLastGeneProfile().get(1), is(""));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_GEOD_3779_ACCESSION, "regulation=UP_DOWN&displayLevels=true&cutoff=1&foldChangeCutOff=0");
        subject.get();
        assertThat(subject.getGeneCount(), is("Showing 50 of 60 genes found:"));

        assertThat(subject.getGeneNames().size(), is(50));
        assertThat(subject.getGeneNames().subList(0, 3), contains("Ikzf5", "Mapre1", "Tdrd7"));

        assertThat(subject.getGeneProfile(1).size(), is(2));
        assertThat(subject.getGeneProfile(1).get(0), is("-0.5"));
        assertThat(subject.getGeneProfile(1).get(1), is(""));

        assertThat(subject.getLastGeneProfile().size(), is(2));
        assertThat(subject.getLastGeneProfile().get(0), is(""));
        assertThat(subject.getLastGeneProfile().get(1), is("-0"));
    }

    @Test
    public void heatmapCellTooltipTest() {
        subject = new HeatmapTablePage(driver, E_GEOD_3779_ACCESSION, "regulation=UP_DOWN&displayLevels=true&cutoff=1&foldChangeCutOff=0");
        subject.get();

        // dismiss cookie notice otherwise we get a Element cannot be scrolled into view
        subject.dismissCookieNotice();

        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 0, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("Adjusted p-value"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 1, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("t-statistic"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0, 0, 2, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), startsWith("Log2-fold"));

        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 0, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("0.914"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 1, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("-1.26"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(0, 0, 2, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL), is("-0.5"));
    }

}
