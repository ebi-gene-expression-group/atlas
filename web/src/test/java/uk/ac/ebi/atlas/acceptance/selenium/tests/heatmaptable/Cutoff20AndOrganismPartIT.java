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

package uk.ac.ebi.atlas.acceptance.selenium.tests.heatmaptable;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SinglePageSeleniumFixture;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Cutoff20AndOrganismPartIT extends SinglePageSeleniumFixture {

    private HeatmapTablePage subject;

    private final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION,
                "specific=true&heatmapMatrixSize=5&geneQuery=&queryFactorValues=adrenal&_queryFactorValues=1&cutoff=20");
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getSelectedProfiles();
        assertThat(selectedGenes.size(), is(5));
        assertThat(selectedGenes, contains("TRAJ13", "Y_RNA", "ABCD4", "PLCG2", "AC011293.1"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("", "766", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
    }

    @Test
    public void verifyLastGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getLastGeneProfile(), contains("", "186", "", "", "", "", "", "", ""
                , "386", "", "", "", "", "", ""));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("31"), is(true));
    }

}
