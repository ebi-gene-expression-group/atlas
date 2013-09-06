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

package uk.ac.ebi.atlas.acceptance.rest.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredAuthenticatedFixture;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;

public class ExperimentLoaderIT extends RestAssuredAuthenticatedFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-599";

    @Before
    public void init(){
    }

    @After
    public void cleanup() {
        expect().body(containsString(EXPERIMENT_ACCESSION)).
            when().get("updateExperiment?accession=" + EXPERIMENT_ACCESSION + "&private=false");
    }

    @Test
    public void testListExperiments() {
        expect().body(containsString(EXPERIMENT_ACCESSION)).when().get("listExperiments?accession=" + EXPERIMENT_ACCESSION);
    }

    @Test
    public void testListSelectedExperiments() {

        expect().body("experimentAccession", containsInAnyOrder(EXPERIMENT_ACCESSION, "E-MTAB-513")).when()
                .get("listExperiments?accession=" + EXPERIMENT_ACCESSION + ",E-MTAB-513");

    }

    @Test
    public void testDeleteAndLoadExperimentPublic() {

        expect().body(is("Experiment E-MTAB-599 successfully deleted.")).when()
                .get("deleteExperiment?accession=" + EXPERIMENT_ACCESSION);

        expect().body("experimentAccession", is(empty())).when().get("listExperiments?accession=" + EXPERIMENT_ACCESSION);

        expect().body(startsWith("Experiment " + EXPERIMENT_ACCESSION + " loaded, accessKey:")).when()
                .get("loadExperiment?accession=" + EXPERIMENT_ACCESSION + "&type=BASELINE&private=false");

        expect().body("experimentAccession", hasItem(EXPERIMENT_ACCESSION))
                .and().body("isPrivate", contains(false))
                .when().get("listExperiments?accession=" + EXPERIMENT_ACCESSION);

    }

    @Test
    public void testDeleteAndLoadExperimentPrivate() {

        expect().body(is("Experiment E-MTAB-599 successfully deleted.")).when()
                .get("deleteExperiment?accession=" + EXPERIMENT_ACCESSION);

        expect().body("experimentAccession", is(empty())).when().get("listExperiments?accession=" + EXPERIMENT_ACCESSION);

        expect().body(startsWith("Experiment " + EXPERIMENT_ACCESSION + " loaded, accessKey:")).when()
                .get("loadExperiment?accession=" + EXPERIMENT_ACCESSION + "&type=BASELINE&private=true");

        expect().body("experimentAccession", hasItem(EXPERIMENT_ACCESSION))
                .and().body("isPrivate", contains(true))
                .when().get("listExperiments?accession=" + EXPERIMENT_ACCESSION);

    }

    @Test
    public void testDeleteNonExisting() {
        String blablaExperimentAccession = "E-MTAB-BLA-BLA-BLA";

        expect().body(is("ResourceNotFoundException: Experiment not found for experiment accession: " + blablaExperimentAccession))
                .when().get("deleteExperiment?accession=" + blablaExperimentAccession);
    }

    @Test
    public void loadShouldFailWhenExperimentHasAlreadyBeenImported() {
        expect().body(is("IllegalStateException: Experiment with experimentAccession " + EXPERIMENT_ACCESSION + " has been already imported."))
                .when().get("loadExperiment?accession=" + EXPERIMENT_ACCESSION + "&type=BASELINE");
    }

    @Test
    public void testLoadInvalidExperiment() {
        String blablaExperimentAccession = "E-MTAB-BLA-BLA-BLA";

        expect().body(startsWith("IllegalStateException: Required file can not be read"))
                .when().get("loadExperiment?accession=" + blablaExperimentAccession + "&type=BASELINE");

        expect().body("experimentAccession", is(empty())).when().get("listExperiments?accession=" + blablaExperimentAccession);

    }

    @Test
    public void testUpdate(){

        expect().body(is("Experiment E-MTAB-599 successfully updated.")).when()
                .get("updateExperiment?accession=" + EXPERIMENT_ACCESSION + "&private=true");

        expect().body("experimentAccession", contains(EXPERIMENT_ACCESSION))
                .and().body("isPrivate", contains(true))
                .when().get("listExperiments?accession=" + EXPERIMENT_ACCESSION);

        expect().body(is("Experiment E-MTAB-599 successfully updated.")).when()
                .get("updateExperiment?accession=" + EXPERIMENT_ACCESSION + "&private=false");

        expect().body("experimentAccession", contains(EXPERIMENT_ACCESSION))
                .and().body("isPrivate", contains(false))
                .when().get("listExperiments?accession=" + EXPERIMENT_ACCESSION);

    }

}