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

package uk.ac.ebi.atlas.bioentity;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageControllerPropertiesSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSMUSG00000029816";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes", "openPanelIndex=0");
        subject.get();
    }

    @Test
    public void searchResultsHeader(){
        assertThat(subject.getSearchResultsHeader(), endsWith("results for ENSMUSG00000029816"));
    }

    //This will fail with PhantomJS
    @Test
    public void infoCardExpansion() {
        assertThat(subject.isInfoCardExpanded(), is(true));
        subject.clickInfoCard(false);
        assertThat(subject.isInfoCardExpanded(), is(false));
    }

    @Test
    public void infoCard() {
        assertThat(subject.getBioEntityCardTitle(), is("Gpnmb Mus musculus glycoprotein (transmembrane) nmb"));
        assertThat(subject.getPropertiesTableSize(), is(12));
        assertThat(subject.getPropertiesTableRow(0), hasItems("Synonyms", "Dchil, Osteoactivin"));
        assertThat(subject.getPropertiesTableRow(1), hasItems("Orthologs", "BT.99652 (Bos taurus), GPNMB (Canis familiaris), gpnmb (Danio rerio), GPNMB (Equus caballus), GPNMB (Homo sapiens), GPNMB (Gallus gallus), GPNMB (Macaca mulatta), Gpnmb (Rattus norvegicus), gpnmb (Xenopus tropicalis)"));
        assertThat(subject.getPropertiesTableRow(2), hasItems("Gene Ontology", "melanosome, osteoblast differentiation, integrin binding, cytoplasmic membrane-bounded vesicle, bone mineralization, cytoplasmic vesicle membrane (... and 3 more)"));
        assertThat(subject.getPropertiesTableRow(3), hasItems("InterPro", "PKD domain (domain), PKD/Chitinase domain (domain)"));
        assertThat(subject.getPropertiesTableRow(4), hasItems("Ensembl Family", "TRANSMEMBRANE GLYCOPROTEIN NMB PRECURSOR"));
        assertThat(subject.getPropertiesTableRow(5), hasItems("Ensembl Gene", "ENSMUSG00000029816"));
        assertThat(subject.getPropertiesTableRow(6), hasItems("Entrez", "93695"));
        assertThat(subject.getPropertiesTableRow(7), hasItems("UniProt", "Q3UE75, Q99P91"));
        assertThat(subject.getPropertiesTableRow(8), hasItems("EMAGE", "MGI:1934765"));
        assertThat(subject.getPropertiesTableRow(9), hasItems("MGI", "glycoprotein (transmembrane) nmb"));
        assertThat(subject.getPropertiesTableRow(10), hasItems("Gene Biotype", "protein_coding"));
        assertThat(subject.getPropertiesTableRow(11), hasItems("Design Element", "10538187, 108822_at, 1448303_at, 4386581, 4444155, 4619897, 4701136, 4723852, 4992000, 5030507, 5044337, 5052678, 5182097, 5192219, 5246058, 5345790, 5526274, 5548029, 5605047, 5610568, A_51_P438967, A_52_P417819"));

        assertThat(subject.getLinksInTableRow(1).get(0), containsString("/gxa/genes/ENS"));
        assertThat(subject.getLinksInTableRow(2).get(0), is("http://amigo.geneontology.org/amigo/term/GO%3A0042470"));
        assertThat(subject.getLinksInTableRow(3).get(0), startsWith("http://www.ebi.ac.uk/interpro/entry/IPR000601"));
        assertThat(subject.getLinksInTableRow(4).get(0), is("http://www.ensembl.org/mus_musculus/Search/Details?db=core;end=1;idx=Family;q=TRANSMEMBRANE+GLYCOPROTEIN+NMB+PRECURSOR;species=mus_musculus"));
        assertThat(subject.getLinksInTableRow(5).get(0), is("http://www.ensemblgenomes.org/id-gene/ENSMUSG00000029816"));
        assertThat(subject.getLinksInTableRow(6).get(0), is("http://www.ncbi.nlm.nih.gov/sites/entrez?db=gene&term=93695"));
    }

    @Test
    public void showMoreAndLessGeneOntologyTerms() {
        subject.clickShowMoreGoLinks();
        assertThat(subject.getPropertiesTableRow(2), hasItems("melanosome, osteoblast differentiation, integrin binding, cytoplasmic membrane-bounded vesicle, bone mineralization, cytoplasmic vesicle membrane, integral component of plasma membrane, heparin binding, cell adhesion (show less)"));

        subject.clickShowLessGoLinks();
        assertThat(subject.getPropertiesTableRow(2), hasItems("Gene Ontology", "melanosome, osteoblast differentiation, integrin binding, cytoplasmic membrane-bounded vesicle, bone mineralization, cytoplasmic vesicle membrane (... and 3 more)"));
    }

}
