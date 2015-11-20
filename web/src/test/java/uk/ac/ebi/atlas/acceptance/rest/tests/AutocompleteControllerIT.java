/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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


import com.google.gson.Gson;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;
import uk.ac.ebi.atlas.solr.query.TermSourceSuggestion;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutocompleteControllerIT {

    private static final String END_POINT_URL = "/gxa/json/suggestions";


    private EndPoint subject = new EndPoint(END_POINT_URL,"query=ASP&species=homo sapiens");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("application/json;charset=UTF-8"));

    }


    @Test
    public void shouldReturnNonEmptyJSonObject(){
        //given
        ResponseBody responseBody = subject.getResponse().body();
        String jsonString = responseBody.asString();
        Gson gson = new Gson();
        //when
        TermSourceSuggestion[] suggestions = gson.fromJson(jsonString, TermSourceSuggestion[].class);

        List<TermSourceSuggestion> suggestionList = Arrays.asList(suggestions);

        assertThat(suggestionList.get(0).term, is("ASPN"));
        assertThat(suggestionList.get(0).source, is("symbol"));
        assertThat(suggestionList, hasSize(15));

    }

    @Test
    public void shouldReturnNonEmptyJSonObjectForBlankSpecies(){
        //given
        EndPoint emptySpeciesRequest = new EndPoint(END_POINT_URL,"query=ASP");
        ResponseBody responseBody = emptySpeciesRequest.getResponse().body();
        String jsonString = responseBody.asString();
        Gson gson = new Gson();
        //when
        TermSourceSuggestion[] suggestions = gson.fromJson(jsonString, TermSourceSuggestion[].class);
        List<TermSourceSuggestion> suggestionList = Arrays.asList(suggestions);

        assertThat(suggestionList.get(11).term, is("ASPG (2 of 3)"));
        assertThat(suggestionList.get(11).source, is("symbol"));
        assertThat(suggestionList.get(14).term, is("asph"));
        assertThat(suggestionList.get(14).source, is("symbol"));

        assertThat(suggestionList, hasSize(15));

    }


}
