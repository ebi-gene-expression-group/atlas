package uk.ac.ebi.atlas.acceptance.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.restassured.RestAssured;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;
import uk.ac.ebi.atlas.search.SemanticQuery;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class RestfulLinksEIT extends RestAssuredFixture{

    private SemanticQuery query = SemanticQuery.create("zinc finger");

    @Test
    public void testWholeQueryFlow(){
        JsonObject facets = new EndPoint("/gxa/json/search/baselineFacets", "query="+query.toUrlEncodedJson())
                .getJsonResponse();

        assertThat(facets.entrySet().size(), greaterThan(0));

        /*
        If only the facets were something like:
        [
          {
            name: "triticum aestivum",
            values: [
              {
              name: "Organism part",
              uri: "http://localhost:8080/gxa/json/search/baselineResults?query=[{"value":"zinc+finger"}]&species=triticum aestivum&source=sampling_time_point"
              }
            ]
          }
        ]
        For now we are not restful in regard of picking which results to query for which facets.
         */

        JsonObject results = new EndPoint(
                "/gxa/json/search/baselineResults",
                MessageFormat.format("query={0}&species={1}&source={2}",query.toUrlEncodedJson(), "homo sapiens",
                        "organism_part")
        ).getJsonResponse();


        assertThat(results.entrySet().size(), greaterThan(0));

        for(JsonElement row: results.get("profiles").getAsJsonObject().get("rows").getAsJsonArray()){
            String accession = row.getAsJsonObject().get("id").getAsString();
            String uri =row.getAsJsonObject().get("uri").getAsString();
            RestAssured.get(uri).then()
                    .assertThat()
                    .content(containsString(accession))
                    .content(containsString("rows"));
        }
    }

    @Test
    public void urisAreAllDifferent(){
        JsonObject results = new EndPoint(
                "/gxa/json/search/baselineResults",
                MessageFormat.format("query={0}&species={1}&source={2}",query.toUrlEncodedJson(), "homo sapiens",
                        "organism_part")
        ).getJsonResponse();

        assertThat(results.entrySet().size(), greaterThan(0));

        List<String> l = new ArrayList<>();
        for (JsonElement row: results.get("profiles").getAsJsonObject().get("rows").getAsJsonArray()) {
            String uri = row.getAsJsonObject().get("uri").getAsString();
            l.add(uri);
        }

        assertThat(l.size(), is(new ArrayList(new HashSet<>(l)).size()));
    }

    @Test
    public void urisContainGeneQuery(){
        /*
        I don't think this fully works
         */
        JsonObject results = new EndPoint(
                "/gxa/json/search/baselineResults",
                MessageFormat.format("query={0}&species={1}&source={2}",query.toUrlEncodedJson(), "homo sapiens",
                        "organism_part")
        ).getJsonResponse();

        assertThat(results.entrySet().size(), greaterThan(0));

        List<String> l = new ArrayList<>();
        for(JsonElement row: results.get("profiles").getAsJsonObject().get("rows").getAsJsonArray()){
            String uri =row.getAsJsonObject().get("uri").getAsString();
            assertThat(uri, containsString(query.toUrlEncodedJson()));
        }


    }

}
