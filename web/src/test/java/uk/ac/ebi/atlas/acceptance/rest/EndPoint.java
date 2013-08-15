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

package uk.ac.ebi.atlas.acceptance.rest;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.acceptance.utils.URLBuilder;

import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class EndPoint {
    private static final String USERNAME = "TEST_USER";
    private static final String PASSWORD = "TEST_PASSWORD";

    private URLBuilder urlBuilder;

    private String httpParameters;

    private RequestSpecification requestSpecification;

    public EndPoint(String endPointURI) {
        this(endPointURI, null);
    }

    public EndPoint(String endPointURI, String httpParameters) {
        urlBuilder = new URLBuilder(endPointURI);
        this.httpParameters = httpParameters;
        requestSpecification = given();
    }

    private String buildURL(){
        return urlBuilder.buildURL(httpParameters);
    }

    public Response getResponse() {
        return requestSpecification.get(buildURL());
    }

    public EndPoint auth() {
        requestSpecification = requestSpecification.auth().basic(USERNAME, PASSWORD);
        return this;
    }

    public ResponseBody getResponseBody() {
        return getResponse().getBody();
    }

    public String getResponseString() {
        return getResponse().getBody().asString();
    }

    //Only useful for csv file download services
    public List<String> getRowValues(int rowIndex) {
        String bodyAsString = getResponseBody().asString();
        String[] rows = bodyAsString.split("\n");
        String row = rows[rowIndex];
        return Arrays.asList(StringUtils.splitPreserveAllTokens(row, "\t"));
    }
}