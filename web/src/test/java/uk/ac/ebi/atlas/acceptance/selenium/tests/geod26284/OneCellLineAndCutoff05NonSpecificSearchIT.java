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

package uk.ac.ebi.atlas.acceptance.selenium.tests.geod26284;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SeleniumFixture;

import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OneCellLineAndCutoff05NonSpecificSearchIT extends SeleniumFixture {

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(driver,
                "filterFactorValues=CELLULAR_COMPONENT%3Awhole+cell%2CMATERIAL_TYPE%3Atotal+rna&queryFactorType=&heatmapMatrixSize=50&displayLevels=false&displayGeneDistribution=false&geneQuery=&queryFactorValues=cd34-positive+mobilized+cell+cell+line&_queryFactorValues=1&_specific=on&cutoff=0.5");
        subject.get();
    }

    @Test
    public void verifySelectedGenes() {
        List<String> selectedGenes = subject.getSelectedGenes();
        assertThat(selectedGenes.get(0), is("TMSB10"));
        assertThat(selectedGenes.get(1), is("PTBP3"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getFirstGeneProfile(), hasItems("123", "288", "490", "695", "1023"));
    }

    @Test
    public void verifyGeneCount() {
        assertThat(subject.getGeneCount().contains("25"), is(true));
    }


}