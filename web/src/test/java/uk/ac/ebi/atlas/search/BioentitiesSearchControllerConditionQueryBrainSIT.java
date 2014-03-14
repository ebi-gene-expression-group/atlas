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

package uk.ac.ebi.atlas.search;

import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesCountWithHref;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BioentitiesSearchControllerConditionQueryBrainSIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "condition=lung");
        subject.get();
    }


    @Test
    @Ignore
    public void checkBaselineExperimentCounts() {
        //given
        subject.clickBaselineProfile();

        List<BaselineBioEntitiesCountWithHref> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(1));
        assertThat(baselineCounts.get(0).getExperimentAccession(), is("E-MTAB-1733"));
        assertThat(baselineCounts.get(0).getExperimentName(), is("Twenty seven tissues"));
        assertThat(baselineCounts.get(0).getSpecies(), is("Homo sapiens"));


    }

    @Test
    @Ignore
    public void checkDifferentialProfiles() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getContrastColumn(), contains(
                "genotype:'p107 -/-' vs 'wild type' on A-AFFY-24",
                "genotype:'p107 -/-' vs 'wild type' on A-AFFY-23"));
        assertThat(subject.getFoldChange(), hasItems("0.02", "0.04"));
    }

    @Test
    @Ignore
    public void checkDifferentialProfilesCount() {
        assertThat(subject.diffExpressionResultCount(), is("2 search result(s) found"));
    }

    @Test
    @Ignore
    public void checkDifferentialContrastSummaryTooltipTableHeader() {
        assertThat(subject.getContrastSummaryTooltipTableHeader(0, 0), is("Property"));
        assertThat(subject.getContrastSummaryTooltipTableHeader(0, 1), is("Test value"));
        assertThat(subject.getContrastSummaryTooltipTableHeader(0, 2), is("Reference value"));
    }

    @Test
    @Ignore
    public void checkDifferentialContrastSummaryTooltipTableFirstRow() {
        assertThat(subject.getContrastSummaryTooltipTableData(0, 0, 0), is("genotype"));
        assertThat(subject.getContrastSummaryTooltipTableData(0, 0, 1), is("p107 -/-"));
        assertThat(subject.getContrastSummaryTooltipTableData(0, 0, 2), is("wild type"));
    }

    @Test
    @Ignore
    public void checkDifferentialContrastSummaryTooltipExperimentAndContrastDescription() {
        assertThat(subject.getContrastSummaryTooltipExperimentDescription(0), is("Transcription profiling by array of mouse neurospheres cultured from p107-/- embryos and their wildtype littermates"));
        assertThat(subject.getContrastSummaryTooltipContrastDescription(0), is("genotype:'p107 -/-' vs 'wild type' on A-AFFY-24"));
    }

    @Test
    @Ignore
    public void checkDifferentialFirstLinkIsCorrect() {
        assertThat(subject.getLinkInDiffTableRow(1), endsWith("E-GEOD-3779?geneQuery=ENSMUSG00000028385&queryFactorValues=g2_g1"));
    }

}
