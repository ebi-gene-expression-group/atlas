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

public class GenePageControllerDifferentialResultsMultiFactorSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "AT3G11340";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes", "cutoff=0.5&openPanelIndex=2");
        subject.get();
    }

    @Test
    public void checkPaneExpansion() {
        assertThat(subject.isDifferentialPaneExpanded(), is(true));
    }

    @Test
    public void checkSelectedProfiles() {
        subject.clickDiffResultsDisplayLevelsButton();
        assertThat(subject.getContrastColumn(), contains(
                "treatment: 'salicylic acid' vs 'Silwet' at time: '4 hours' in ecotype: 'Col-0'"));
        assertThat(subject.getFoldChange(), hasItems("3.8"));
    }

    @Test
    public void checkHeatmap() {
        assertThat(subject.getDiffHeatmapHeaders(), contains("Comparison", "Log2-fold change"));
        assertThat(subject.getDiffHeatmapRow(1), contains("treatment: 'salicylic acid' vs 'Silwet' at time: '4 hours' in ecotype: 'Col-0'", ""));
    }
}
