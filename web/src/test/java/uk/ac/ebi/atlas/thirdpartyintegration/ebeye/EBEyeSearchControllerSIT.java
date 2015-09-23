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

package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;
import uk.ac.ebi.atlas.experiments.NumberOfExperiments;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;

public class EBEyeSearchControllerSIT extends RestAssuredFixture {

    @Test
    public void experiments() {
        Response response = get("/api/experiments.xml");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType(ContentType.XML);
        response.then().assertThat().body("database.entry_count", equalTo(String.valueOf(NumberOfExperiments.ALL)));
        response.then().assertThat().body(hasXPath("/database/entries/entry[@id='E-GEOD-3307']/description", containsString("Comparative profiling in 13 muscle disease groups")));
    }
}