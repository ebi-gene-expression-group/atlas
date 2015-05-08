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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class GenePageControllerBaselineWidgetGallusGallusSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSGALG00000006591";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes");
        subject.get();
    }

    @Test
    public void baselinePaneHasResults() {
        String widgetBody = subject.getBaselinePaneHeaderResultsMessage();
        assertThat(widgetBody, is("Results in tissues"));

        Wait<WebDriver> wait = new WebDriverWait(driver, 10L);
        WebElement bioEntityCardDifferentialSummary = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.gxaBioEntityCardDifferentialSummary")));
        wait.until(ExpectedConditions.textToBePresentInElement(bioEntityCardDifferentialSummary, "Within Sample Abundance (Proteomics) > 0"));

        assertThat(subject.isBaselinePaneExpanded(), is(true));

        assertThat(subject.getGeneNames().size(), is(1));
        assertThat(subject.getGeneNames(), contains("Vertebrate tissues"));
    }

}
