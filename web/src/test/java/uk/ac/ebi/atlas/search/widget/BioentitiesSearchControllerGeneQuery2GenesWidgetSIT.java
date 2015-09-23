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

public class BioentitiesSearchControllerGeneQuery2GenesWidgetSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = BioEntitiesPage.search(driver, "geneQuery=ENSMUSG00000097801%09ENSMUSG00000090429");
        subject.get();
    }

    @Test
    public void baselinePaneResultsMessage() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results in tissues"));
    }

    @Test
    public void displaysWidget() {
        // wait for ajax widget to load
        subject.waitForHeatmapToBeVisible();
        assertThat(subject.getGeneNames(), contains("Tissues - 6"));
        assertThat(subject.getGeneLink(0), endsWith("/experiments/E-MTAB-599?geneQuery=ENSMUSG00000097801%09ENSMUSG00000090429"));
    }

}
