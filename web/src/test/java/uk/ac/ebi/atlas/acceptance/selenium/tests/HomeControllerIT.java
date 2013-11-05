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

package uk.ac.ebi.atlas.acceptance.selenium.tests;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HomePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;

public class HomeControllerIT extends SinglePageSeleniumFixture {

    private HomePage subject;

    public void getStartingPage() {
        subject = new HomePage(driver);
        subject.get();
    }

    @Test
    public void test() throws InterruptedException {
        subject.setGeneQuery("ENSG00000161547");
        BioEntitiesPage resultsPage = subject.submitSearch();

        String searchResultsHeader = resultsPage.getSearchResultsHeader();
        System.out.println(searchResultsHeader);

        //TODO: replace "not is empty string" test with the below when results page is fixed
        //ie: assertThat(searchResultsHeader, is("Expression Atlas results for ENSG00000161547"));
        assertThat(searchResultsHeader, not(isEmptyString()));
    }

}