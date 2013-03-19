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

package uk.ac.ebi.atlas.acceptance.selenium.tests.geod22351;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SinglePageSeleniumFixture;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithDifferentRegulationsButDefaultQueryParams extends SeleniumFixture {

    private static final String E_GEOD_22351_ACCESSION = "E-GEOD-22351";
    protected HeatmapTablePage subject;

    @Test
    public void verifyQueryFactorLableAndHeatmapHeaders(){
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();

        assertThat(subject.getQueryFactorLabel(), is("Contrast"));

        assertThat(subject.getFactorValueHeaders().size(), is(1));
        assertThat(subject.getFactorValueHeaders().get(0), startsWith("genotype"));
    }

    @Test
    public void verifyResultsWithRegulationUp() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 40"));

        assertThat(subject.getSelectedGenes().size(), is(40));
        assertThat(subject.getSelectedGenes().subList(0,3), contains("Gpnmb","Cst7","Itgax"));

        assertThat(subject.getGeneProfile(1).size(), is(1));
        assertThat(subject.getGeneProfile(1).get(0), is("<10-10"));

        assertThat(subject.getLastGeneProfile().size(), is(1));
        assertThat(subject.getLastGeneProfile().get(0), is("0.041"));
    }

    @Test
    public void verifyResultsWithRegulationDown() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 9"));

        assertThat(subject.getSelectedGenes().size(), is(9));
        assertThat(subject.getSelectedGenes().subList(0,3), contains("Gm15512", "Pla2g3", "Pmp2"));

        assertThat(subject.getGeneProfile(1).size(), is(1));
        assertThat(subject.getGeneProfile(1).get(0), is("6.61 × 10-5"));

        assertThat(subject.getLastGeneProfile().size(), is(1));
        assertThat(subject.getLastGeneProfile().get(0), is("0.041"));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 49"));

        assertThat(subject.getSelectedGenes().size(), is(49));
        assertThat(subject.getSelectedGenes().subList(0,3), contains("Gpnmb","Cst7","Itgax"));

        assertThat(subject.getGeneProfile(1).size(), is(1));
        assertThat(subject.getGeneProfile(1).get(0), is("<10-10"));

        assertThat(subject.getLastGeneProfile().size(), is(1));
        assertThat(subject.getLastGeneProfile().get(0), is("0.041"));
    }

    @Test
    public void heatmapCellTooltipTest(){
        subject = new HeatmapTablePage(driver, E_GEOD_22351_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();

        assertThat(subject.getDifferentialExperimentTooltipTableHeader(0), is("P-value"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(1), startsWith("Log2-fold"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(0), is("<10-10"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(1), is("2.73"));
    }

}
