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

package uk.ac.ebi.atlas.acceptance.selenium.tests.genepage;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageControllerMirbaseWith1EnsgeneResultSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "hsa-mir-15a";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes", "openPanelIndex=0");
        subject.get();
    }

    @Test
    public void searchResultsHeader(){
        assertThat(subject.getSearchResultsHeader(), endsWith("results for ENSG00000231607"));
    }

    @Test
    public void bioEntityCardContainsMirBaseResults() {
        assertThat(subject.getBioEntityCardTitle(), is("DLEU2 Homo sapiens deleted in lymphocytic leukemia 2 (non-protein coding)"));
        assertThat(subject.getPropertiesTableSize(), is(8));
        assertThat(subject.getPropertiesTableRow(0), hasItems("Synonyms", "BCMSUN, DLB2, LEU2, LINC00022, MIR15AHG, NCRNA00022, RFP2OS, TRIM13OS"));
        assertThat(subject.getPropertiesTableRow(1), hasItems("Ensembl Gene", "ENSG00000231607"));
        assertThat(subject.getPropertiesTableRow(2), hasItems("Entrez", "406948"));
        assertThat(subject.getPropertiesTableRow(3), hasItems("Gene Biotype", "processed_transcript"));
        assertThat(subject.getPropertiesTableRow(4), hasItems("miRBase", "MI0000069"));
        assertThat(subject.getPropertiesTableRow(5), hasItems("miRBase Identifier", "hsa-mir-15a"));
        assertThat(subject.getLinksInTableRow(1).get(0), is("http://www.ensemblgenomes.org/id-gene/ENSG00000231607"));
    }

    @Test
    public void baselineHeatmapContains1Ensgene() {
        subject.clickBaselineProfile();

//        FluentWait wait = new WebDriverWait(driver, 10L).pollingEvery(1, TimeUnit.SECONDS);
//        wait.until(ExpectedConditions.textToBePresentInElement(By.cssSelector(".bioEntityCardDifferentialSummary"), "Expression Level cut-off:"));

        assertThat(subject.isBaselineProfileExpanded(), is(true));

        assertThat(subject.getGeneNames().size(), is(1));
        assertThat(subject.getGeneNames(), contains("DLEU2"));
    }

    @Test
    public void globalSearchWidget() {
        assertThat(subject.getGlobalSearchTerm(), is("ENSG00000231607"));
        subject.clickShowMoreDataWidget();
        assertThat(subject.getGlobalSearchAllResultsTotal(), is(greaterThan(0)));
    }

}
