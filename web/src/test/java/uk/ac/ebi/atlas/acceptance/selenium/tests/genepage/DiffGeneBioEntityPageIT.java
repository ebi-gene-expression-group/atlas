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

package uk.ac.ebi.atlas.acceptance.selenium.tests.genepage;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;
import uk.ac.ebi.atlas.acceptance.selenium.utils.SinglePageSeleniumFixture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class DiffGeneBioEntityPageIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "AT3G29644";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes", "cutoff=0.5&openPanelIndex=2");
        subject.get();
        subject.useDiffHeatmapTable();
    }

    @Test
    public void checkPaneExpansion() {
        assertThat(subject.isDifferentialProfileExpanded(), is(true));
    }

    @Test
    public void checkSelectedProfiles() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getSelectedProfiles(), contains("idn2 mutant vs wild type",
                "nrpe1 mutant vs wild type",
                "swi3b mutant vs wild type"));
        assertThat(subject.getFirstGeneProfile(), contains("6.64 × 10-9"));
        assertThat(subject.getLastGeneProfile(), contains("0.056"));
        assertThat(subject.getSelectedProfiles().size(), is(3));
    }

    @Test
    public void checkContrastSummaryTooltipTableHeader() {
        assertThat(subject.getContastSummaryTooltipTableHeader(0, 0), is("Property"));
        assertThat(subject.getContastSummaryTooltipTableHeader(0, 1), is("Test value"));
        assertThat(subject.getContastSummaryTooltipTableHeader(0, 2), is("Reference value"));
    }

    //This is not working with PhantomJS browser :((
    @Test
    public void checkContrastSummaryTooltipTableFirstRow() {
        assertThat(subject.getContastSummaryTooltipTableData(0, 0, 0), is("genotype"));
        assertThat(subject.getContastSummaryTooltipTableData(0, 0, 1), is("idn2-1"));
        assertThat(subject.getContastSummaryTooltipTableData(0, 0, 2), is("wild type"));
    }

    //This is not working with PhantomJS browser :((
    @Test
    public void checkContrastSummaryTooltipTableLastRow() {
        assertThat(subject.getContastSummaryTooltipTableData(0, 5, 0), is("Organism"));
        assertThat(subject.getContastSummaryTooltipTableData(0, 5, 1), is("Arabidopsis thaliana"));
        assertThat(subject.getContastSummaryTooltipTableData(0, 5, 2), is("Arabidopsis thaliana"));
    }

    @Test
    public void checkContrastSummaryTooltipExperimentAndContrastDescription() {
        assertThat(subject.getContastSummaryTooltipExperimentDescription(0), is("RNA-seq of Arabidopsis mutants with defects in long-non-coding-RNA-mediated transcriptional silencing"));
        assertThat(subject.getContastSummaryTooltipContrastDescription(0), is("idn2 mutant vs wild type"));
    }

}
