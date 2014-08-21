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

package uk.ac.ebi.atlas.bioentity.widget;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;
import uk.ac.ebi.atlas.acceptance.utils.SeleniumUtil;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class GeneSetPageControllerReactomeBaselineWidgetSIT extends SinglePageSeleniumFixture {

    private static final String IDENTIFIER = "REACT_1698";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, IDENTIFIER, "genesets", "openPanelIndex=0");
        subject.get();
    }

    @Test
    public void checkGeneCardTitle() {
        assertThat(subject.getBioEntityCardTitle(), is("REACT_1698 Homo sapiens Metabolism of nucleotides"));
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
        assertThat(subject.getPropertiesTableSize(), is(1));
    }

    @Test
    public void checkTableRows() {
        assertThat(subject.getPropertiesTableRow(0), hasItems("Reactome", "Metabolism of nucleotides"));
    }

    @Test
    public void checkLinksInTable() {
        assertThat(subject.getLinksInTableRow(0).get(0), is("http://www.reactome.org/cgi-bin/eventbrowser_st_id?ST_ID=REACT_1698"));
    }

    @Test
    public void checkWidget() {
        subject.clickBaselinePane();

        // wait for ajax widget to load
        SeleniumUtil.waitForElementByIdUntilVisible(driver, "heatmap-div");

        assertThat(subject.isIndividualGenesVisible(), is(true));
        assertThat(subject.isGeneSetProfilesVisible(), is(false));
        List<String> geneNames = subject.getGeneNames();

        assertThat(geneNames, contains("NT5C1B", "GART", "UPB1", "UPP2", "AGXT2", "NT5C1A", "NME2", "DPYS", "XDH", "AMPD1", "GDA", "TYMS", "ADA", "GMPR2", "CDA", "AK5", "NT5E", "NT5M", "RRM2", "TK1", "GPX1P1", "NUDT18", "TXN", "GUK1", "CAT", "CMPK1", "IMPDH2", "GLRX", "AK2", "DUT", "APRT", "AK1", "DCTD", "PNP", "ADK", "TXNRD1", "ATIC", "DGUOK", "PAICS", "NT5C2", "GSR", "TYMP", "NUDT9", "HPRT1", "AMPD2", "ADSS", "NT5C", "NT5C3A", "ITPA", "GMPS"));

        subject.clickShowGeneSetProfiles();

        assertThat(subject.isIndividualGenesVisible(), is(false));
        assertThat(subject.isGeneSetProfilesVisible(), is(true));

        //System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");
        //assertThat(geneNames, contains("REACT_1698"));

    }
}