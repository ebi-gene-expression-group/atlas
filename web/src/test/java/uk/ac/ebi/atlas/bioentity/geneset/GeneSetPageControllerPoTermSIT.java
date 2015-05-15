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

package uk.ac.ebi.atlas.bioentity.geneset;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeneSetPageControllerPoTermSIT extends SinglePageSeleniumFixture {

    private static final String IDENTIFIER = "PO:0000013";

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "genesets/" + IDENTIFIER + "?openPanelIndex=0");
        subject.get();
    }

    @Test
    public void searchResultsHeader() {
        assertThat(subject.getSearchResultsHeader(), endsWith("results for " + IDENTIFIER));
    }

    @Test
    public void infoCard() {
        assertThat(subject.getBioEntityCardTitle(), is("PO:0000013 Arabidopsis thaliana cauline leaf"));
        assertThat(subject.getPropertiesTableSize(), is(1));
        assertThat(subject.getPropertiesTableRow(0), hasItems("Plant Ontology", "cauline leaf"));
        assertThat(subject.getLinksInTableRow(0).get(0), is("http://plantontology.org/amigo/go.cgi?view=details&search_constraint=terms&depth=0&query=PO%3A0000013"));
    }

    @Test
    public void baselineResults() {
        subject.clickBaselinePane();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("No results"));
    }

    @Test
    public void hasDifferentialResults() {
        subject.clickDifferentialPane();
        subject.clickDiffResultsDisplayLevelsButton();
        assertThat(subject.getDiffPaneHeaderResultsMessage(), is("2152 results"));

        assertThat(subject.getDiffHeatmapHeaders(), contains("Gene", "Organism", "Comparison", "Log2-fold change"));
        assertThat(subject.getDiffHeatmapRow(1), contains("BAG6", "Arabidopsis thaliana", "'37 degrees celsius' vs '20 degrees celsius'", "7.9"));

        //System.out.println("\"" + Joiner.on("\", \"").join(subject.getDiffHeatmapTableGeneColumn()) + "\"");
        assertThat(subject.getDiffHeatmapTableGeneColumn(), contains("BAG6", "ROF2", "HSP70T-2", "HSP17.6B", "F10O3.11", "A37", "HSP98.7", "ROF2", "AR192", "F5F19.6", "DOF4.1", "F15E12.12", "DREB2A", "MBF1C", "F28O16.16", "CDC48D", "ATEGY3", "HSP17.6B", "AT4G02550/T10P11_16", "AT1G62730", "T1P2.12", "AT4G17250", "AT1G07350", "F7O12.5", "CPN60B2", "T19K4.240", "AT4G28240", "CYP19-2", "HAP5B", "NPC3", "ROF1", "IAA29", "HSP17.4B", "AT1G16040/T24D18_15", "ATRBL14", "HPL1", "F28G11.5", "Fes1A", "MBF1C", "TPR10", "AT2G30000", "PFK3", "A37", "F1O17.1", "Hsp89.1", "F15E12.12", "HSP18.5", "BEH1", "HSP60", "AT2G33735"));
    }

}