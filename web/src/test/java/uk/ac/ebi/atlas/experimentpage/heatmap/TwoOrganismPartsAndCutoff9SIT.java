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

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TwoOrganismPartsAndCutoff9SIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION,
                "heatmapMatrixSize=5&queryFactorValues=adipose&geneQuery=&queryFactorValues=heart&_queryFactorValues=2&cutoff=9");
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getGeneNames();
        assertThat(selectedGenes.size(), is(3));
        assertThat(selectedGenes, contains("ENSG00000244656", "NEBL", "AL031284.1"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), contains("", "", "", "", "", "", "57", ""
                , "", "", "", "", "", "", "", ""));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("3"), is(true));
    }

}
