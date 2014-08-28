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
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageControllerBaselineResultsWidgetSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSG00000163331";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes");
        subject.get();
    }

    @Test
    public void checkPaneExpansion() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));
        assertThat(subject.isInfoCardExpanded(), is(false));
    }

    @Test
    public void baselineWidgetGenes() {
        FluentWait wait = new WebDriverWait(driver, 10L).pollingEvery(1, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.textToBePresentInElement(By.cssSelector(".bioEntityCardDifferentialSummary"), "Expression Level cut-off:"));

        assertThat(subject.isBaselinePaneExpanded(), is(true));

        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results in tissues"));

        subject.clickDisplayLevelsButton();

        assertThat(subject.getGeneNames().size(), is(3));
        assertThat(subject.getGeneNames(), contains("Vertebrate tissues","Twenty seven tissues","Illumina Body Map"));
        assertThat(subject.getGeneLink(0), endsWith("/experiments/E-GEOD-30352?geneQuery=ENSG00000163331"));
    }


}
