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
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;
import uk.ac.ebi.atlas.model.ExperimentType;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class HeatmapTableWithDifferentRegulationsButDefaultQueryParamsIT extends SeleniumFixture {

    private static final String E_MTAB_1066_ACCESSION = "E-MTAB-1066";
    protected HeatmapTablePage subject;

/*
    @Test
    public void verifyResultsWithRegulationUp() {
        subject = new HeatmapTablePage(driver, E_MTAB_1066_ACCESSION, "regulation=UP&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 40"));

        assertThat(subject.getSelectedGenes().size(), is(40));
        assertThat(subject.getSelectedGenes().subList(0, 3), contains("Gpnmb", "Cst7", "Itgax"));

        assertThat(subject.getGeneProfile(1).size(), is(1));
        assertThat(subject.getGeneProfile(1).get(0), is("<10-10"));

        assertThat(subject.getLastGeneProfile().size(), is(1));
        assertThat(subject.getLastGeneProfile().get(0), is("0.041"));
    }

    @Test
    public void verifyResultsWithRegulationDown() {
        subject = new HeatmapTablePage(driver, E_MTAB_1066_ACCESSION, "regulation=DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 9"));

        assertThat(subject.getSelectedGenes().size(), is(9));
        assertThat(subject.getSelectedGenes().subList(0, 3), contains("Gm15512", "Pla2g3", "Pmp2"));

        assertThat(subject.getGeneProfile(1).size(), is(1));
        assertThat(subject.getGeneProfile(1).get(0), is("6.61"+ " \u00D7 " + "10-5"));

        assertThat(subject.getLastGeneProfile().size(), is(1));
        assertThat(subject.getLastGeneProfile().get(0), is("0.041"));
    }

    @Test
    public void verifyResultsWithRegulationUpDown() {
        subject = new HeatmapTablePage(driver, E_MTAB_1066_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();
        assertThat(subject.getGeneCount(), containsString("of 49"));

        assertThat(subject.getSelectedGenes().size(), is(49));
        assertThat(subject.getSelectedGenes().subList(0, 3), contains("Gpnmb", "Cst7", "Itgax"));

        assertThat(subject.getGeneProfile(1).size(), is(1));
        assertThat(subject.getGeneProfile(1).get(0), is("<10-10"));

        assertThat(subject.getLastGeneProfile().size(), is(1));
        assertThat(subject.getLastGeneProfile().get(0), is("0.041"));
    }
*/
    @Test
    public void heatmapCellTooltipTest(){
        subject = new HeatmapTablePage(driver, E_MTAB_1066_ACCESSION, "regulation=UP_DOWN&displayLevels=true");
        subject.get();

        assertThat(subject.getDifferentialExperimentTooltipTableHeader(1, 0, ExperimentType.MICROARRAY), is("Adjusted P-value"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(1, 1, ExperimentType.MICROARRAY), is("T-statistic"));
        assertThat(subject.getDifferentialExperimentTooltipTableHeader(1, 2, ExperimentType.MICROARRAY), startsWith("Log2-fold"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(1, 0, ExperimentType.MICROARRAY), is("8.36 × 10-6"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(1, 1, ExperimentType.MICROARRAY), is("-19.16"));
        assertThat(subject.getDifferentialExperimentTooltipTableCell(1, 2, ExperimentType.MICROARRAY), is("-1.56"));
    }

}
