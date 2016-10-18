package uk.ac.ebi.atlas.experimentpage.analysismethods;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNot.not;

public class AnalysisMethodsPageSIT extends RestAssuredFixture {

    @Test
    public void baselineExperiment(){
        Response response = get("/experiments/E-MTAB-513/analysis-methods");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(not(containsString("display-qc-report")));
    }

    @Test
    public void rnaseqDiffExperiment(){
        Response response = get("/experiments/E-GEOD-21860/analysis-methods");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(not(containsString("display-qc-report")));
    }

    @Test
    public void microArrayExperiment(){
        Response response = get("/experiments/E-GEOD-3307/analysis-methods");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("text/html");
        response.then().assertThat().body(containsString("display-qc-report"));
    }


}
