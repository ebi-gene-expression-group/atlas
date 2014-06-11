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

public class GeneBioEntityPageIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSMUSG00000029816";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes", "openPanelIndex=0");
        subject.get();
    }

    @Test
    public void checkGeneCardTitle() {
        assertThat(subject.getBioEntityCardTitle(), is("Gpnmb Mus musculus glycoprotein (transmembrane) nmb"));
    }

    @Test
    public void searchResultsHeader(){
        assertThat(subject.getSearchResultsHeader(), endsWith("results for ENSMUSG00000029816"));
    }


    //This will fail with PhantomJS
    @Test
    public void checkCardExpansion() {
        assertThat(subject.isInfoCardExpanded(), is(true));
        subject.clickInfoCard(false);
        assertThat(subject.isInfoCardExpanded(), is(false));
    }

    @Test
    public void checkTableSize() {
        assertThat(subject.getPropertiesTableSize(), is(12));
    }

    @Test
    public void checkTableRows() {
        assertThat(subject.getPropertiesTableRow(0), hasItems("Synonyms", "Dchil, Osteoactivin"));
        assertThat(subject.getPropertiesTableRow(1), hasItems("Orthologs"));
        assertThat(subject.getPropertiesTableRow(1).get(1), containsString("Gpnmb"));
        assertThat(subject.getPropertiesTableRow(10), hasItems("Gene Biotype", "protein_coding"));
    }

    @Test
    public void checkLinksInTable() {
        assertThat(subject.getLinksInTableRow(1).get(0), containsString("/gxa/genes/ENS"));
        assertThat(subject.getLinksInTableRow(2).get(0), startsWith("http://amigo.geneontology.org/cgi-bin/amigo/search.cgi?search_constraint=term&exact_match=yes&action=new-search&search_query="));
        assertThat(subject.getLinksInTableRow(3).get(0), startsWith("http://www.ebi.ac.uk/interpro/search?q="));
        assertThat(subject.getLinksInTableRow(4).get(0), is("http://www.ensembl.org/mus_musculus/Search/Details?db=core;end=1;idx=Family;q=TRANSMEMBRANE+GLYCOPROTEIN+NMB+PRECURSOR;species=mus_musculus"));
        assertThat(subject.getLinksInTableRow(5).get(0), is("http://www.ensemblgenomes.org/id-gene/ENSMUSG00000029816"));
        assertThat(subject.getLinksInTableRow(6).get(0), is("http://www.ncbi.nlm.nih.gov/sites/entrez?db=gene&term=93695"));
    }

    @Test
    public void shouldDisplaySearchResultsHeader(){
        assertThat(subject.getSearchResultsHeader(), endsWith("results for ENSMUSG00000029816"));
    }

    @Test
    public void globalSearchTermIsGeneIdentifier() {
        assertThat(subject.getGlobalSearchTerm(), is(GENE_IDENTIFIER));
    }

    @Test
    public void clickingOnGlobalSearchWidgetShouldDisplayGlobalSearchResults(){
        subject.clickShowMoreDataWidget();
        assertThat(subject.getGlobalSearchAllResultsTotal(), is(greaterThan(0)));
    }

}
