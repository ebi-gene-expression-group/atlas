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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class DefaultFilterFactorValuesAndCutoffButWithoutAnyGeneQueryIT extends SeleniumFixture {

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(driver, "geneQuery=");
        subject.get();
    }

    @Test
    public void verifyTop9SelectedGenes() {
        //given that we selected the default filterFactorValues RNA Type : total RNA and cellular component : whole cell

        //when we extract top 9 from heatmap
        List<String> selectedGenes = subject.getSelectedGenes().subList(0, 9);

        //then
        assertThat(selectedGenes, hasItems(
                            "RP11-384J4.2", "TERF2", "GFI1", "SCN2A", "SLC10A1", "TRPM2", "GEMIN8P4", //expressed on 1 FactorValue
                            "RP11-368L12.1", //expressed on two FactorValues
                            "RP11-20I23.6" //expressed on three FactorValues
                    ));
    }

    @Test
    public void heatmapHeadersShouldBeDependentOnSelectedFilterFactorValues(){
        //given that we selected the default filterFactorValues RNA Type : total RNA and cellular component : whole cell

        //then
        assertThat(subject.getFactorValueHeaders(),hasItems("cd34-positive...", "hfdpc cell line", "hmsc-at cell line", "hpc-pl cell line", "imr-90"));
    }

    @Test
    public void verifyFirstGeneProfile() {
        subject.clickDisplayLevelsButton();

        assertThat(subject.getFirstGeneProfile(), contains("", "", "", "6", ""));
    }

    @Test
    public void verifyNinthGeneProfile() {
        subject.clickDisplayLevelsButton();

        assertThat(subject.getGeneProfile(9), contains("4", "", "1", "1", ""));
    }

    @Test
    public void verifyGeneCount() { //verified
        assertThat(subject.getGeneCount().contains("31"), is(true));
    }


}