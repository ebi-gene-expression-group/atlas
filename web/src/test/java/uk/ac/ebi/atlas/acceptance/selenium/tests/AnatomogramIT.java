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

package uk.ac.ebi.atlas.acceptance.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AnatomogramIT extends SeleniumFixture {

    private static final String E_GEOD_30352_ACCESSION = "E-GEOD-30352";
    private static final String E_GEOD_26284_ACCESSION = "E-GEOD-26284";
    private static final String E_MTAB_513_ACCESSION = "E-MTAB-513";

    private HeatmapTablePage getPage(String experimentAccession, String params){
        HeatmapTablePage heatmapTablePage = new HeatmapTablePage(driver, experimentAccession, params);
        heatmapTablePage.get();
        return heatmapTablePage;
    }

    private boolean isAnatomogramElementFound(String experimentAccession, String params) {
        return getPage(experimentAccession, params).getAnatomogram().isDisplayed();
    }

    private boolean isAnatomogramElementFound(String experimentAccession) {
        return isAnatomogramElementFound(experimentAccession, "");
    }

    @Test
    public void testAnatomogramIsThereForHomoSapiensExp() {
        assertThat(isAnatomogramElementFound(E_GEOD_30352_ACCESSION), is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void testAnatomogramIsNotThereForMultiSpeciesExpForChicken() {
        String gallusParams = "serializedFilterFactors=ORGANISM:Gallus+gallus&queryFactorType=ORGANISM_PART&geneQuery=";
        isAnatomogramElementFound(E_GEOD_30352_ACCESSION, gallusParams);
    }

    @Test
    public void testAnatomogramIsThereForMultiSpeciesExpForHomoSapiens() {
        String homoSapiensParams = "serializedFilterFactors=ORGANISM:Homo+sapiens&queryFactorType=ORGANISM_PART&geneQuery=";
        assertThat(isAnatomogramElementFound(E_GEOD_30352_ACCESSION, homoSapiensParams), is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void testAnatomogramIsNotThereForMultiSpeciesExpForOrganismPartFiltering() {
        String nullSpeciesParams = "serializedFilterFactors=ORGANISM_PART:liver&queryFactorType=ORGANISM&geneQuery=";
        isAnatomogramElementFound(E_GEOD_30352_ACCESSION, nullSpeciesParams);
    }

    @Test(expected = NoSuchElementException.class)
    public void testAnatomogramIsNotThereForCellTypeExperiment() {
        isAnatomogramElementFound(E_GEOD_26284_ACCESSION);
    }

    @Test
    public void testAnatomogramIsThereForSingleSpeciesExp() {
        assertThat(isAnatomogramElementFound(E_MTAB_513_ACCESSION), is(true));
    }
}
