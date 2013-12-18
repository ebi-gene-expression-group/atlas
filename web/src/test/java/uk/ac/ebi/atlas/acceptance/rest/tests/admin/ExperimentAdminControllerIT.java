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

package uk.ac.ebi.atlas.acceptance.rest.tests.admin;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredAuthenticatedFixture;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ExperimentAdminControllerIT extends RestAssuredAuthenticatedFixture {


    private static final String EXISTING_EXPERIMENT_ACCESSION = "E-MTAB-599";
    private static final String NEW_EXPERIMENT_ACCESSION = "TEST-CRUD";
    private static final String DIFFERENTIAL_EXPERIMENT_ACCESSION = "E-GEOD-21860";

    @Test
    public void listExperiments() {
        expect().body(containsString(EXISTING_EXPERIMENT_ACCESSION)).when().get("listExperiments?accession=" + EXISTING_EXPERIMENT_ACCESSION);
    }

    @Test
    public void listSelectedExperiments() {
        expect().body("experimentAccession", containsInAnyOrder(EXISTING_EXPERIMENT_ACCESSION, "E-MTAB-513")).when()
                .get("listExperiments?accession=" + EXISTING_EXPERIMENT_ACCESSION + ",E-MTAB-513");
    }

    public void deleteInactiveExpressions() {
        get("/deleteInactiveExpressions").then().assertThat().statusCode(200);
    }

    @Test
    public void loadAndDeleteNewBaselineExperimentPublic() {

        expect().body(startsWith("Experiment " + NEW_EXPERIMENT_ACCESSION + " loaded, accessKey:")).when()
                .get("loadExperiment?accession=" + NEW_EXPERIMENT_ACCESSION + "&private=false");

        expect().body("experimentAccession", hasItem(NEW_EXPERIMENT_ACCESSION))
                .and().body("isPrivate", contains(false))
                .when().get("listExperiments?accession=" + NEW_EXPERIMENT_ACCESSION);

        expect().body(is("Experiment TEST-CRUD successfully deleted.")).when()
                .get("deleteExperiment?accession=" + NEW_EXPERIMENT_ACCESSION);

        expect().body("experimentAccession", is(empty())).when().get("listExperiments?accession=" + NEW_EXPERIMENT_ACCESSION);

        deleteInactiveExpressions();

    }

    @Test
    public void loadAndDeleteNewBaselineExperimentPrivate() {

        expect().body(startsWith("Experiment " + NEW_EXPERIMENT_ACCESSION + " loaded, accessKey:")).when()
                .get("loadExperiment?accession=" + NEW_EXPERIMENT_ACCESSION + "&private=true");

        expect().body("experimentAccession", hasItem(NEW_EXPERIMENT_ACCESSION))
                .and().body("isPrivate", contains(true))
                .when().get("listExperiments?accession=" + NEW_EXPERIMENT_ACCESSION);

        expect().body(is("Experiment TEST-CRUD successfully deleted.")).when()
                .get("deleteExperiment?accession=" + NEW_EXPERIMENT_ACCESSION);

        expect().body("experimentAccession", is(empty())).when().get("listExperiments?accession=" + NEW_EXPERIMENT_ACCESSION);

        deleteInactiveExpressions();

    }

    @Test
    public void deleteNonExisting() {
        String blablaExperimentAccession = "E-MTAB-BLA-BLA-BLA";

        expect().body(is("ResourceNotFoundException: Experiment not found for experiment accession: " + blablaExperimentAccession))
                .when().get("deleteExperiment?accession=" + blablaExperimentAccession);
    }

    @Test
    public void reloadExistingExperiment() {

        expect().body("experimentAccession", hasItem(EXISTING_EXPERIMENT_ACCESSION))
                .when().get("listExperiments?accession=" + EXISTING_EXPERIMENT_ACCESSION);

        expect().body(startsWith("Experiment " + EXISTING_EXPERIMENT_ACCESSION + " loaded, accessKey:")).when()
                .get("loadExperiment?accession=" + EXISTING_EXPERIMENT_ACCESSION + "&private=false");
    }

    @Test
    public void loadInvalidExperiment() {
        String blablaExperimentAccession = "E-MTAB-BLA-BLA-BLA";

        expect().body(startsWith("IllegalStateException: Required file can not be read"))
                .when().get("loadExperiment?accession=" + blablaExperimentAccession);

        expect().body("experimentAccession", is(empty())).when().get("listExperiments?accession=" + blablaExperimentAccession);

    }

    @Test
    public void updateDifferentialExperiment(){

        expect().body(is("Experiment E-GEOD-21860 successfully updated.")).when()
                .get("updateStatus?accession=" + DIFFERENTIAL_EXPERIMENT_ACCESSION + "&private=true");

        expect().body(is("Experiment E-GEOD-21860 successfully updated.")).when()
                .get("updateStatus?accession=" + DIFFERENTIAL_EXPERIMENT_ACCESSION + "&private=false");

        assertThat(countConditionProperties(DIFFERENTIAL_EXPERIMENT_ACCESSION), is(4));


    }

    @Test
    public void deleteAndLoadDifferentialExperimentPublic() {

        expect().body(is("Experiment E-GEOD-21860 successfully deleted.")).when()
                .get("deleteExperiment?accession=" + DIFFERENTIAL_EXPERIMENT_ACCESSION);

        assertThat(countConditionProperties(DIFFERENTIAL_EXPERIMENT_ACCESSION), is(0));

        expect().body(startsWith("Experiment " + DIFFERENTIAL_EXPERIMENT_ACCESSION + " loaded, accessKey:")).when()
                .get("loadExperiment?accession=" + DIFFERENTIAL_EXPERIMENT_ACCESSION + "&private=false");

        assertThat(countConditionProperties(DIFFERENTIAL_EXPERIMENT_ACCESSION), is(4));

    }

    private int countConditionProperties(String experimentAccession){
        Response conditionIndexResponse = queryConditionIndex(experimentAccession);

        String responseBody = conditionIndexResponse.body().asString();

        return Integer.parseInt(StringUtils.substringBetween(responseBody, "\"numFound\":", ","));
    }

    private Response queryConditionIndex(String experimentAccession){
        return RestAssured.get("http://lime:8983/solr/differentialConditions/select?q=experiment_accession:"
                + experimentAccession + "&wt=json&indent=true");
    }


}