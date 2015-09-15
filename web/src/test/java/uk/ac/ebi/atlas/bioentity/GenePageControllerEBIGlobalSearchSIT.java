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

package uk.ac.ebi.atlas.bioentity;

import org.codehaus.jackson.annotate.JsonIgnoreType;
import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class GenePageControllerEBIGlobalSearchSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSMUSG00000029816";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes", "openPanelIndex=0");
        subject.get();
    }

    @Test
    public void globalSearchTermIsGeneIdentifier() {
        assertThat(subject.getGlobalSearchTerm(), is(GENE_IDENTIFIER));
    }

    // Unignore when EBI enables CORS headers:
    // Cross-Origin Request Blocked: The Same Origin Policy disallows reading the remote resource at http://www.ebi.ac.uk/ebisearch/globalsearchsummary.ebi?query=ENSMUSG00000029816&noResults=undefined. (Reason: CORS header 'Access-Control-Allow-Origin' missing).
    @Ignore
    public void clickingOnGlobalSearchWidgetShouldDisplayGlobalSearchResults(){
        subject.clickShowMoreDataWidget();
        assertThat(subject.getGlobalSearchAllResultsTotal(), is(greaterThan(0)));
    }

}
