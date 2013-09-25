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

package uk.ac.ebi.atlas.acceptance.selenium.tests.bioentitiespage;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DifferentialBioentitiesPageIT extends SinglePageSeleniumFixture {

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "brain");
        subject.get();
    }

    @Test
    public void checkSelectedProfiles() {
        subject.clickDisplayLevelsButton();
        assertThat(subject.getContrastColumn(), contains(
                "genotype:'p107 -/-' vs 'wild type' on A-AFFY-24",
                "genotype:'p107 -/-' vs 'wild type' on A-AFFY-23"));
        assertThat(subject.getPValues(), hasItems("0.02", "0.04"));
    }

    @Test
    public void checkContrastSummaryTooltipTableHeader() {
        assertThat(subject.getContrastSummaryTooltipTableHeader(0, 0), is("Property"));
        assertThat(subject.getContrastSummaryTooltipTableHeader(0, 1), is("Test value"));
        assertThat(subject.getContrastSummaryTooltipTableHeader(0, 2), is("Reference value"));
    }

    @Test
    public void checkContrastSummaryTooltipTableFirstRow() {
        assertThat(subject.getContrastSummaryTooltipTableData(0, 0, 0), is("genotype"));
        assertThat(subject.getContrastSummaryTooltipTableData(0, 0, 1), is("p107 -/-"));
        assertThat(subject.getContrastSummaryTooltipTableData(0, 0, 2), is("wild type"));
    }

    @Test
    public void checkContrastSummaryTooltipExperimentAndContrastDescription() {
        assertThat(subject.getContrastSummaryTooltipExperimentDescription(0), is("Transcription profiling by array of mouse neurospheres cultured from p107-/- embryos and their wildtype littermates"));
        assertThat(subject.getContrastSummaryTooltipContrastDescription(0), is("genotype:'p107 -/-' vs 'wild type' on A-AFFY-24"));
    }

    @Test
    public void checkFirstLinkIsCorrect() {
        assertThat(subject.getLinkInDiffTableRow(1), endsWith("E-GEOD-3779?geneQuery=ENSMUSG00000028385&queryFactorValues=g2_g1"));
    }

}
