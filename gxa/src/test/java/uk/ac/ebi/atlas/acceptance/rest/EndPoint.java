package uk.ac.ebi.atlas.acceptance.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.acceptance.utils.URLBuilder;

import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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

    public JsonElement getJsonResponse(){
        Response response = getResponse();

        assertThat(response.getStatusCode(), is(200));
        //TODO does not work for experiment page endpoints
        // assertThat(response.getContentType(), is("application/json;charset=UTF-8"));

        return new Gson().fromJson(response.getBody().asString(), JsonElement.class);
    }

    public EndPoint auth() {
        requestSpecification = requestSpecification.auth().basic(USERNAME, PASSWORD);
        return this;
    }

    public ResponseBody getResponseBody() {
        return getResponse().getBody();
    }

    //Only useful for csv file download services
    public List<String> getRowValues(int rowIndex) {
        String bodyAsString = getResponseBody().asString();
        String[] rows = bodyAsString.split("\n");
        String row = rows[rowIndex];
        return Arrays.asList(StringUtils.splitPreserveAllTokens(row, "\t"));
    }
}