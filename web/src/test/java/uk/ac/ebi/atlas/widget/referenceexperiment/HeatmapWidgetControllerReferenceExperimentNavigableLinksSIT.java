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

package uk.ac.ebi.atlas.widget.referenceexperiment;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTableWidgetPage;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.startsWith;

public class HeatmapWidgetControllerReferenceExperimentNavigableLinksSIT extends SeleniumFixture {

    @BeforeClass
    public static void init() {
        RestAssuredFixture.initRestAssured();
    }

    // tests links on the widget when accessed from PAK-2p34:RHG10 in Reactome
    // ie: http://www.reactome.org/PathwayBrowser/#DIAGRAM=169911&ID=211701&PATH=5357801,109581&DTAB=EX
    @Test
    public void multipleGenesQuery_followingWidgetLinkShouldGoToExperimentPageWithResults() throws UnsupportedEncodingException {
        Response response = given().urlEncodingEnabled(false).get("/widgets/heatmap/referenceExperiment?geneQuery=A1A4S6+Q13177");

        response.then().assertThat().statusCode(200);

        JsonPath json = response.jsonPath();

        String experimentsUrl = json.get("experiment.URL");
        assertThat(experimentsUrl, startsWith("/experiments/E-MTAB-2836"));

        String pageLocation = experimentsUrl.replace(" ", "%20");
        HeatmapTablePage experimentPage = new HeatmapTableWidgetPage(driver, pageLocation, null);
        experimentPage.get();

        List<String> geneNames = experimentPage.getGeneNames();
        Assert.assertThat(geneNames, contains("ARHGAP10", "PAK2"));
    }

}
