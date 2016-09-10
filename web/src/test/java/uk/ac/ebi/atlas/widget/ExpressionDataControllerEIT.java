package uk.ac.ebi.atlas.widget;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsString;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExpressionDataControllerEIT extends RestAssuredFixture {

    private static final String BASELINE_GENE = "ENSG00000000003";
    private static final String DIFFERENTIAL_GENE = "AT2G06310";
    private static final String NON_EXISTENT_GENE = "FOOBAR";

    @Test
    public void geneExpressedInBaselineAndDifferentialExperimentsReturnsTrue() {
        Response response = get("/json/expressionData?geneId=" + BASELINE_GENE);

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("application/json;charset=UTF-8");
        response.then().assertThat().body(containsString(String.format("\"%s\":true", BASELINE_GENE)));
    }

    @Test
    public void geneExpressedInDifferentialExperimentsOnlyReturnsFalse() {
        Response response = get("/json/expressionData?geneId=" + DIFFERENTIAL_GENE);

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("application/json;charset=UTF-8");
        response.then().assertThat().body(containsString(String.format("\"%s\":false", DIFFERENTIAL_GENE)));
    }

    @Test
    public void nonExistentGeneReturnsFalse() {
        Response response = get("/json/expressionData?geneId=" + NON_EXISTENT_GENE);

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("application/json;charset=UTF-8");
        response.then().assertThat().body(containsString(String.format("\"%s\":false", NON_EXISTENT_GENE)));
    }
}
