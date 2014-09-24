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

package uk.ac.ebi.atlas.transcript;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWidgetPage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWithTranscriptBreakdownPage;
import uk.ac.ebi.atlas.acceptance.utils.SeleniumUtil;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class TranscriptPopupSIT extends SeleniumFixture {

    private static final String E_GEOD_26284_ACCESSION = "E-GEOD-26284";
    private static final String E_MTAB_599_ACCESSION = "E-MTAB-599";
    private static final String E_MTAB_513_ACCESSION = "E-MTAB-513";

    @Test
    public void verifyButtonClickFirstProfile_E_MTAB_26284() {
        HeatmapTableWithTranscriptBreakdownPage subject = new HeatmapTableWithTranscriptBreakdownPage(driver, E_GEOD_26284_ACCESSION);
        subject.get();
        HeatmapTableWithTranscriptBreakdownPage page = subject.clickOnCell(9, 3);
        assertThat(page.getTranscriptBreakdownTitle(), is("Expression Level Breakdown for PTBP3 in IMR-90\n(4 out of 8 transcripts are expressed):"));
        assertThat(page.getTranscriptBreakdownLegendLabels(), contains("ENST00000374257", "ENST00000343327", "ENST00000374255", "Others"));
    }

    @Test
    public void verifyButtonClickOnProfileExpression_E_MTAB_599() {
        HeatmapTableWithTranscriptBreakdownPage subject = new HeatmapTableWithTranscriptBreakdownPage(driver, E_MTAB_599_ACCESSION);
        subject.get();
        HeatmapTableWithTranscriptBreakdownPage page = subject.clickOnCell(2, 3);
        assertThat(page.getTranscriptBreakdownTitle(), is("Expression Level Breakdown for Sftpa1 in lung\n(2 out of 2 transcripts are expressed):"));
        assertThat(page.getTranscriptBreakdownLegendLabels(), contains("ENSMUST00000022314", "ENSMUST00000170719"));
    }

    @Test
    public void verifyButtonClickOnProfileExpression_E_MTAB_513() {
        HeatmapTableWithTranscriptBreakdownPage subject = new HeatmapTableWithTranscriptBreakdownPage(driver, E_MTAB_513_ACCESSION);
        subject.get();
        HeatmapTableWithTranscriptBreakdownPage page = subject.clickOnCell(11, 14);
        assertThat(page.getTranscriptBreakdownTitle(), is("Expression Level Breakdown for USP26 in testis\n(2 out of 4 transcripts are expressed):"));
        assertThat(page.getTranscriptBreakdownLegendLabels(), contains("ENST00000417459", "ENST00000370832"));
    }

    // NB: this test will fail if plants.ensembl.org is down, which can happen during an Ensembl release
    @Test
    public void verifyEnsemblPlantsLinkOnTranscriptPopup() {
        HeatmapTableWithTranscriptBreakdownPage subject = new HeatmapTableWithTranscriptBreakdownPage(driver, "E-MTAB-2039", "geneQuery=");
        subject.get();
        HeatmapTableWithTranscriptBreakdownPage page = subject.clickOnCell(0, 5);
        assertThat(page.getTranscriptBreakdownTitle(), is("Expression Level Breakdown for OS12G0515800 in leaf\n(1 out of 1 transcript is expressed):"));
        assertThat(page.getTranscriptBreakdownLegendLabels(), contains("OS12T0515800-01"));
        assertThat(page.getTranscriptBreakdownLegendLinks(), contains("http://plants.ensembl.org/Oryza_sativa/Transcript/Summary?g=OS12G0515800;t=OS12T0515800-01"));
        assertThat(page.getGeneLink(), is("http://plants.ensembl.org/Oryza_sativa/Gene/Summary?g=OS12G0515800"));

        List<WebElement> legendElements = page.getLegendElements();
        legendElements.get(0).click();

        SeleniumUtil.switchToOpenedWindow(driver);

        SeleniumUtil.waitForPageTitle(driver);

        assertThat(driver.getTitle(), containsString("Transcript: OS12T0515800-01"));
    }

    @Test
    public void verifyNoTranscriptPopUpForGeneSetQuery() {
        HeatmapTableWidgetPage subject = HeatmapTableWidgetPage.create(driver, "geneQuery=REACT_6900&species=homo%20sapiens&rootContext=");
        subject.get();

        assertThat(subject.getGeneCount(), is("Showing 3 of 3 experiments found:"));
        assertThat(subject.getGeneNames(), contains("Twenty seven tissues", "Illumina Body Map", "Vertebrate tissues"));

        HeatmapTableWithTranscriptBreakdownPage page1 = subject.clickOnCell(0, 5);
        assertThat(page1.getTitle(), is(""));
    }

    @Test
    public void multiExperimentWidget() {
        HeatmapTableWidgetPage subject = HeatmapTableWidgetPage.createGenePage(driver, "ENSG00000228278");
        subject.get();

        subject.waitForHeatmapToBeVisible();

        assertThat(subject.getGeneCount(), is("Showing 2 of 2 experiments found:"));
        assertThat(subject.getGeneNames(), contains("Twenty seven tissues", "Vertebrate tissues"));

        // no slice experiment
        HeatmapTableWithTranscriptBreakdownPage page1 = subject.clickOnCell(0, 3);
        assertThat(page1.getTranscriptBreakdownTitle(), is("Expression Level Breakdown for ENSG00000228278 in appendix\n(1 out of 2 transcripts are expressed):"));
        assertThat(page1.getTranscriptBreakdownLegendLabels(), contains("ENST00000431067"));

        // single slice experiment
        HeatmapTableWithTranscriptBreakdownPage page2 = subject.clickOnCell(1, 16);
        assertThat(page2.getTranscriptBreakdownTitle(), is("Expression Level Breakdown for ENSG00000228278 in liver\n(0 out of 0 transcript is expressed):"));
    }

}
