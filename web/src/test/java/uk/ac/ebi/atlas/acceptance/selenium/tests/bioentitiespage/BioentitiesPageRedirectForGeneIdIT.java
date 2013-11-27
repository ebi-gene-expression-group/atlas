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
import org.openqa.selenium.support.PageFactory;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.utils.URLBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class BioentitiesPageRedirectForGeneIdIT extends SinglePageSeleniumFixture {


    @Override
    protected void getStartingPage() {

    }

    @Test
    public void geneIdShouldRedirect() {
        PageFactory.initElements(driver, this);

        String uri = "/gxa/query?geneQuery=ENSG00000161547";

        loadPage(uri);

        assertThat(driver.getCurrentUrl(), containsString("genes/ENSG00000161547"));

    }

    @Test
    public void proteinIdShouldRedirect() {
        PageFactory.initElements(driver, this);

        String uri = "/gxa/query?geneQuery=ENSP00000355434";

        loadPage(uri);

        assertThat(driver.getCurrentUrl(), containsString("proteins/ENSP00000355434"));

    }

    @Test
    public void mirbaseIdShouldRedirect() {
        PageFactory.initElements(driver, this);

        String uri = "/gxa/query?geneQuery=hsa-mir-636";

        loadPage(uri);

        assertThat(driver.getCurrentUrl(), containsString("genes/hsa-mir-636"));

    }

    @Test
    public void reactomeIdShouldRedirect() {
        PageFactory.initElements(driver, this);

        String uri = "/gxa/query?geneQuery=react_111217";

        loadPage(uri);

        assertThat(driver.getCurrentUrl(), containsString("genesets/react_111217"));

    }

    private void loadPage(String uri) {
        URLBuilder urlBuilder = new URLBuilder(uri);
        String pageURL = urlBuilder.buildURL();
        driver.get(pageURL);
    }


}
