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

public class GenePageControllerDifferentialResultsSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "AT3G29644";

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
        assertThat(subject.getContrastColumn(), contains("idn2 mutant vs wild type",
                "nrpe1 mutant vs wild type"));
        assertThat(subject.getFoldChange(), hasItems("3.3", "2.6"));
    }

    //This is not working with PhantomJS browser :((
    @Test
    public void checkContrastSummaryTooltipTableHeader() {
        assertThat(subject.getContrastSummaryTooltipTableHeader(0, 0), is("Property"));
        assertThat(subject.getContrastSummaryTooltipTableHeader(0, 1), is("Test value"));
        assertThat(subject.getContrastSummaryTooltipTableHeader(0, 2), is("Reference value"));
    }

    //This is not working with PhantomJS browser :((
    @Test
    public void checkContrastSummaryTooltipTableFirstRow() {
        assertThat(subject.getContrastSummaryTooltipTableData(0, 0, 0), is("genotype"));
        assertThat(subject.getContrastSummaryTooltipTableData(0, 0, 1), is("idn2-1"));
        assertThat(subject.getContrastSummaryTooltipTableData(0, 0, 2), is("wild type genotype"));
    }

    //This is not working with PhantomJS browser :((
    @Test
    public void checkContrastSummaryTooltipTableLastRow() {
        assertThat(subject.getContrastSummaryTooltipTableData(0, 4, 0), is("organism"));
        assertThat(subject.getContrastSummaryTooltipTableData(0, 4, 1), is("Arabidopsis thaliana"));
        assertThat(subject.getContrastSummaryTooltipTableData(0, 4, 2), is("Arabidopsis thaliana"));
    }

    //This is not working with PhantomJS browser :((
    @Test
    public void checkContrastSummaryTooltipExperimentAndContrastDescription() {
        assertThat(subject.getContrastSummaryTooltipExperimentDescription(0), is("RNA-seq of Arabidopsis mutants with defects in long-non-coding-RNA-mediated transcriptional silencing"));
        assertThat(subject.getContrastSummaryTooltipContrastDescription(0), is("idn2 mutant vs wild type"));
    }

}
