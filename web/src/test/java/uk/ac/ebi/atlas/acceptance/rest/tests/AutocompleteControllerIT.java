package uk.ac.ebi.atlas.acceptance.rest.tests;


import com.google.common.collect.Lists;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;
import uk.ac.ebi.atlas.web.SemanticQuery;
import uk.ac.ebi.atlas.web.SemanticQueryTerm;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

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
        //when
        SemanticQuery query = SemanticQuery.fromJson(jsonString);
        SemanticQueryTerm firstTerm = query.iterator().next();
        //then
        assertThat(firstTerm.value(), is("ASPA"));
        assertThat(firstTerm.category(), is("symbol"));
        assertEquals(query.size(), 15);

    }

    @Test
    public void shouldReturnNonEmptyJSonObjectForBlankSpecies(){
        //given
        EndPoint emptySpeciesRequest = new EndPoint(END_POINT_URL,"query=ASP");
        ResponseBody responseBody = emptySpeciesRequest.getResponse().body();
        String jsonString = responseBody.asString();
        //when
        SemanticQuery query = SemanticQuery.fromJson(jsonString);
        List<SemanticQueryTerm> suggestionList = Lists.newArrayList(query);
        //then
        assertThat(suggestionList, hasSize(15));
        for (SemanticQueryTerm queryTerm : suggestionList) {
            assertThat(queryTerm.value().toLowerCase(), containsString("asp"));
        }
    }

}
