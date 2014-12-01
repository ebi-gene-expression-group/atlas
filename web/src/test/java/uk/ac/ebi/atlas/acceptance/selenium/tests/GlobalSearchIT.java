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

import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HomePage;
import uk.ac.ebi.atlas.acceptance.utils.URLBuilder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GlobalSearchIT extends SinglePageSeleniumFixture {

    private HomePage subject;

    public void getStartingPage() {
        subject = new HomePage(driver);
        subject.get();
    }

    @Ignore //TODO need to fix it. Find a proper identifier for the pill box
    @Test
    public void globalSearchRedirectsToGeneQuerySearch(){
        String searchText = "ENSG00000179218";
        subject.setGlobalSearchText(searchText);
        subject.globalSearchSubmit();

        assertThat(driver.getCurrentUrl(), is(new URLBuilder("/gxa/genes/" + searchText).buildURL()));
    }
}
