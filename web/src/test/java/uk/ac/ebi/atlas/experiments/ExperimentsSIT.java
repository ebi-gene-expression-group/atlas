package uk.ac.ebi.atlas.experiments;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import java.util.List;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

public class ExperimentsSIT extends RestAssuredFixture {

    @Test
    public void numberOfExperiments(){
        Response response = get("/json/experiments");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType(ContentType.JSON);

        List<String> accessions = response.jsonPath().get("aaData.experimentAccession");

        assertThat(accessions, hasSize(NumberOfExperiments.ALL));
    }

    @Test
    public void hasProteomicsExperiment_E_PROT_1(){
        Response response = get("/json/experiments");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType(ContentType.JSON);

        List<String> accessions = response.jsonPath().get("aaData.experimentAccession");

        //System.out.println(Joiner.on("\", \"").join(accessions));

        assertThat(accessions, hasItem("E-PROT-1"));
    }

}
