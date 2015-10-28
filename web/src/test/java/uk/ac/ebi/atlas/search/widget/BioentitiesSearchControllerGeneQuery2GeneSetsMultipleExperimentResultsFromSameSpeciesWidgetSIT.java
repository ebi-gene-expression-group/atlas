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

package uk.ac.ebi.atlas.search.widget;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerGeneQuery2GeneSetsMultipleExperimentResultsFromSameSpeciesWidgetSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=R-HSA-69278%09R-HSA-162582");
        subject.get();
    }

    @Test
    public void baselinePaneResultsMessage() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results found"));
    }

    @Test
    public void displaysWidget() {
        // wait for ajax widget to load
        subject.waitForHeatmapToBeVisible();
        assertThat(subject.getGeneNames(), contains("Thirty two tissues", "Twenty seven tissues", "Illumina Body Map", "Vertebrate tissues", "Human Proteome Map - adult", "Human Proteome Map - fetus"));
        assertThat(subject.getGeneLink(0), endsWith("/experiments/E-MTAB-2836?geneQuery=R-HSA-69278%09R-HSA-162582"));
        assertThat(subject.getGeneLink(1), endsWith("/experiments/E-MTAB-1733?geneQuery=R-HSA-69278%09R-HSA-162582"));
        assertThat(subject.getGeneLink(2), endsWith("/experiments/E-MTAB-513?geneQuery=R-HSA-69278%09R-HSA-162582"));
        assertThat(subject.getGeneLink(3), endsWith("/experiments/E-GEOD-30352?geneQuery=R-HSA-69278%09R-HSA-162582&serializedFilterFactors=ORGANISM%3AHomo%20sapiens"));
        assertThat(subject.getGeneLink(4), endsWith("/experiments/E-PROT-1?geneQuery=R-HSA-69278%09R-HSA-162582&serializedFilterFactors=DEVELOPMENTAL_STAGE%3Aadult"));
        assertThat(subject.getGeneLink(5), endsWith("/experiments/E-PROT-1?geneQuery=R-HSA-69278%09R-HSA-162582&serializedFilterFactors=DEVELOPMENTAL_STAGE%3Afetus"));
    }

    @Test
    public void hiddenBaselineResults() {
        assertThat(subject.getVisibleBaselineResultsWithoutSpecies(), hasSize(10));
        subject.clickMoreBaselineResults();
        assertThat(subject.getVisibleBaselineResultsWithoutSpecies(), hasSize(131));
    }
}
