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

package uk.ac.ebi.atlas.acceptance.rest.pages;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import uk.ac.ebi.atlas.acceptance.utils.URLBuilder;

import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.get;

public abstract class RestPage {

    private URLBuilder urlBuilder;

    private String pageURL;

    RestPage(String httpParameters) {
        urlBuilder = new URLBuilder(getPageURI());
        pageURL = urlBuilder.buildURL(httpParameters);
    }

    public Response getResponse() {
        return get(pageURL);
    }

    public ResponseBody getResponseBody() {
        return get(pageURL).getBody();
    }

    public List<String> getRowAsList(int row) {
        String bodyAsString = getResponseBody().asString();
        String[] lines = bodyAsString.split("\n");
        String line = lines[row];
        return Arrays.asList(line.split("\t"));
    }

    protected abstract String getPageURI();
}