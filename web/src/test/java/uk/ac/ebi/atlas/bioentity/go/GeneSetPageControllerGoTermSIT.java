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

package uk.ac.ebi.atlas.bioentity.go;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeneSetPageControllerGoTermSIT extends SinglePageSeleniumFixture {

    private static final String IDENTIFIER = "GO:0005515";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, IDENTIFIER, "genesets", "openPanelIndex=0");
        subject.get();
    }

    @Test
    public void searchResultsHeader() {
        assertThat(subject.getSearchResultsHeader(), endsWith("results for " + IDENTIFIER));
    }

    @Test
    public void infoCard() {
        assertThat(subject.getBioEntityCardTitle(), is("GO:0005515 protein binding"));
        assertThat(subject.getPropertiesTableSize(), is(1));
        assertThat(subject.getPropertiesTableRow(0), hasItems("Gene Ontology", "protein binding"));
        assertThat(subject.getLinksInTableRow(0).get(0), is("http://amigo.geneontology.org/amigo/term/GO%3A0005515"));
    }

    @Test
    public void hasDifferentialResults() {
        subject.clickDifferentialPane();
        subject.clickDiffResultsDisplayLevelsButton();
        assertThat(subject.getDiffHeatmapHeaders(), contains("Gene", "Organism", "Contrast", "Log2-fold change"));
        assertThat(subject.getDiffHeatmapRow(1), contains("Uty", "Mus musculus", "sex:'male' vs 'female'", "10.46"));
        assertThat(subject.getDiffHeatmapTableGeneColumn(), contains("Uty", "Hop3", "ATHSP101", "BAG6", "ROF2", "ATHSFA2", "A37", "ATHSFA2", "ROF2", "DREB2A", "Cldn8", "ATHSP101", "CDC48D", "ROF1", "TPR10", "A37", "CDC48D", "SDG37", "AT1G26800", "AT1G07500", "GAI", "ATNFXL1", "TPR2", "TSA1", "BRH1", "Hop2", "AT3G28220", "Sftpd", "Lonrf3", "F15E12.17", "DML1", "F15E12.17", "FGR", "AT2G30610", "ATSRP30", "SR33", "AT1G23180", "Itgax", "ANAC019", "AT2G41170", "ATMKK9", "SYP111", "ERS1", "Lcn2", "TPR5", "RGLG1", "WRKY38", "AT1G26800", "F7A19.29", "F13F21.11"));
    }


}