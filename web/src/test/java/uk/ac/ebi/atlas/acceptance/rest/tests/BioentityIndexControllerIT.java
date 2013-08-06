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

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BioentityIndexControllerIT {

    private static final String STATUS_SERVICE_END_POINT_URL = "/gxa/buildIndex/status";
    private static final String BUILD_INDEX_SERVICE_END_POINT_URL = "/gxa/buildIndex";

    private EndPoint statusServiceEndPoint = new EndPoint(STATUS_SERVICE_END_POINT_URL);
    private EndPoint buildIndexServiceEndPoint = new EndPoint(BUILD_INDEX_SERVICE_END_POINT_URL);

    @Test
    public void statusShouldBeInitialized() {
        Response response = statusServiceEndPoint.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        assertThat(response.getBody().asString(), is("INITIALIZED"));

    }

    @Test
    public void statusShouldNotBeInitialized(){
        //given
        String responseBody = buildIndexServiceEndPoint.getResponse().body().asString();

        assertThat(responseBody, is(Matchers.not("INITIALIZED")));
        assertThat(responseBody, is(Matchers.not("FAILED")));

    }

}

