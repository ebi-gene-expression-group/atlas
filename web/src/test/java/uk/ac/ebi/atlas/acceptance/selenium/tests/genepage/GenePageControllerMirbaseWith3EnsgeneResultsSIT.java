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
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageControllerMirbaseWith3EnsgeneResultsSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "hsa-mir-636";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes", "openPanelIndex=0");
        subject.get();
    }

    @Test
    public void searchResultsHeader(){
        assertThat(subject.getSearchResultsHeader(), endsWith("results for hsa-mir-636"));
    }

    @Test
    public void bioEntityCardContainsMirBaseResults() {
        assertThat(subject.getBioEntityCardTitle(), is("MI0003651 Homo sapiens"));
        assertThat(subject.getPropertiesTableSize(), is(3));
        assertThat(subject.getPropertiesTableRow(0), hasItems("Gene Biotype", "miRNA"));
        assertThat(subject.getPropertiesTableRow(1), hasItems("miRBase", "MI0003651"));
        assertThat(subject.getLinksInTableRow(1).get(0), is("http://www.mirbase.org/cgi-bin/mirna_entry.pl?acc=MI0003651"));
        assertThat(subject.getPropertiesTableRow(2), hasItems("Sequence", "UGGCGGCCUGGGCGGGAGCGCGCGGGCGGGGCCGGCCCCGCUGCCUGGAAUUAACCCCGCUGUGCUUGCUCGUCCCGCCCGCAGCCCUAGGCGGCGUCG"));
    }

    @Test
    public void baselineHeatmapContains3EnsgeneResults() {
        subject.clickBaselineProfile();

        FluentWait wait = new WebDriverWait(driver, 10L).pollingEvery(1, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.textToBePresentInElement(By.cssSelector(".bioEntityCardDifferentialSummary"), "Expression Level cut-off:"));

        assertThat(subject.isBaselineProfileExpanded(), is(true));

        assertThat(subject.getGeneNames().size(), is(3));
        assertThat(subject.getGeneNames(), contains("MFSD11","SRSF2","MIR636"));
    }

    @Test
    public void globalSearchWidget() {
        assertThat(subject.getGlobalSearchTerm(), is(GENE_IDENTIFIER));
        subject.clickShowMoreDataWidget();
        assertThat(subject.getGlobalSearchAllResultsTotal(), is(greaterThan(0)));
    }

}
