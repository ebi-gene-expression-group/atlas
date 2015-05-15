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

package uk.ac.ebi.atlas.bioentity.geneset;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredAuthenticatedFixture;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static ch.lambdaj.Lambda.extract;
import static ch.lambdaj.Lambda.on;
import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class GeneSetPageControllerPrivateExperimentSIT extends SinglePageSeleniumFixture {

    private static final String GENESET_IDENTIFIER = "REACT_1619";
    private static final String EXPERIMENT_ACCESSION = "E-MTAB-1733";

    private BioEntitiesPage subject;

    @BeforeClass
    public static void initRestAssuredRequestSpec(){
        RestAssuredAuthenticatedFixture.initRestAssured();
    }

    @Before
    public void init() {
        expect().body(containsString(EXPERIMENT_ACCESSION))
                .when().get("updateStatus?accession=" + EXPERIMENT_ACCESSION + "&private=true");
    }

    @After
    public void cleanup() {
        expect().body(containsString(EXPERIMENT_ACCESSION))
                .when().get("updateStatus?accession=" + EXPERIMENT_ACCESSION + "&private=false");
    }

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "genesets/" + GENESET_IDENTIFIER + "?openPanelIndex=1");
    }

    @Test
    public void privateExperimentsAreNotShown() {
        subject.get();

        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results found"));

        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getAllBaselineResults();
        assertThat(baselineCounts, hasSize(20));

        List<String> accessions = extract(baselineCounts, on(BaselineBioEntitiesSearchResult.class).getExperimentAccession());
        assertThat(accessions, hasItems("E-GEOD-26284", "E-MTAB-2980"));
        assertThat(accessions, not(hasItem("E-MTAB-1733")));
    }
}
