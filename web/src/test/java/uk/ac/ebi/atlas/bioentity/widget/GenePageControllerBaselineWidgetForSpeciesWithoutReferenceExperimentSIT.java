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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageControllerBaselineWidgetForSpeciesWithoutReferenceExperimentSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSGGOG00000005112"; //gorilla gorilla

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes");
        subject.get();
    }

    @Test
    public void baselinePaneHasResultsForGorillaGorilla() {
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results in tissues"));

        subject.waitForHeatmapToBeVisible();

        assertThat(subject.isBaselinePaneExpanded(), is(true));

        assertThat(subject.getGeneNames().size(), is(1));
        assertThat(subject.getGeneNames(), contains("Vertebrate tissues"));
        assertThat(subject.getGeneLink(0), endsWith("/experiments/E-GEOD-30352?geneQuery=ENSGGOG00000005112&serializedFilterFactors=ORGANISM%3AGorilla%20gorilla"));

    }

}
