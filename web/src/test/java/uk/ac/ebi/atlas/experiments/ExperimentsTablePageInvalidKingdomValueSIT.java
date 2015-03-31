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

package uk.ac.ebi.atlas.experiments;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentsTablePage;

import java.util.concurrent.TimeUnit;

public class ExperimentsTablePageInvalidKingdomValueSIT extends SinglePageSeleniumFixture {

    private ExperimentsTablePage subject;
    private static final String PLANTS_KINGDOM_PARAMETER = "kingdom=foobar";

    @Override
    protected void getStartingPage() {

        subject = new ExperimentsTablePage(driver, PLANTS_KINGDOM_PARAMETER);
        subject.get();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.pollingEvery(5, TimeUnit.SECONDS).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return !subject.getExperimentsTableInfo().startsWith("Showing 0");
            }
        });
    }

    @Test
    public void invalidKingdomReturnsAllExperiments() {
        subject.getExperimentsTableInfo().contains("of " + NumberOfExperiments.ALL + " entries");
    }

}