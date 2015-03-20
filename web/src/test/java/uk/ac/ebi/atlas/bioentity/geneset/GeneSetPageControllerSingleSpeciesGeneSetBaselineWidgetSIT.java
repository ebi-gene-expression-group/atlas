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
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class GeneSetPageControllerSingleSpeciesGeneSetBaselineWidgetSIT extends SinglePageSeleniumFixture {

    private static final String IDENTIFIER = "REACT_1619";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, IDENTIFIER, "genesets");
        subject.get();
    }

    //This will fail with PhantomJS
    @Test
    public void checkCardExpansion() {
        assertThat(subject.isInfoCardExpanded(), is(false));
        subject.clickInfoCard(true);
        assertThat(subject.isInfoCardExpanded(), is(true));
    }

    @Test
    public void checkInfoCard() {
        subject.clickInfoCard(true);
        assertThat(subject.getBioEntityCardTitle(), is("REACT_1619 Homo sapiens Death Receptor Signalling"));
        assertThat(subject.getPropertiesTableSize(), is(1));
        assertThat(subject.getPropertiesTableRow(0), hasItems("Reactome", "Death Receptor Signalling"));
        assertThat(subject.getLinksInTableRow(0).get(0), is("http://www.reactome.org/cgi-bin/eventbrowser_st_id?ST_ID=REACT_1619"));
    }

    @Test
    public void checkWidget() {
        // wait for ajax widget to load
        subject.waitForHeatmapToBeVisible();
        assertThat(subject.getGeneCount(), is("Showing 4 of 4 experiments found:"));
        assertThat(subject.getGeneColumnHeader(), is("Experiment"));

        assertThat(subject.getGeneNames(), contains("Thirty two tissues", "Twenty seven tissues", "Vertebrate tissues", "Human Proteome Map - adult"));
        assertThat(subject.getGeneLink(0), endsWith("/experiments/E-MTAB-2836?geneQuery=REACT_1619"));
        assertThat(subject.getGeneLink(1), endsWith("/experiments/E-MTAB-1733?geneQuery=REACT_1619"));
        assertThat(subject.getGeneLink(2), endsWith("/experiments/E-GEOD-30352?geneQuery=REACT_1619&serializedFilterFactors=ORGANISM%3AHomo%20sapiens"));
        assertThat(subject.getGeneLink(3), endsWith("/experiments/E-PROT-1?geneQuery=REACT_1619&serializedFilterFactors=DEVELOPMENTAL_STAGE%3Aadult"));
    }
}