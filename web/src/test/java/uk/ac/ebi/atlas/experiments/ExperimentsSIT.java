package uk.ac.ebi.atlas.experiments;

import com.google.common.base.Joiner;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import java.util.List;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;

public class ExperimentsSIT extends RestAssuredFixture {

    @Test
    public void experimentSet(){
        Response response = get("/json/experiments");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType(ContentType.JSON);

        List<String> accessions = response.jsonPath().get("aaData.experimentAccession");

        System.out.println("\"" + Joiner.on("\", \"").join(accessions) + "\"");

        assertThat(accessions, containsInAnyOrder("E-GEOD-10732", "E-GEOD-11758", "E-GEOD-12108", "E-GEOD-21860", "E-GEOD-22351", "E-GEOD-2507", "E-GEOD-26284", "E-GEOD-30352", "E-GEOD-3307", "E-GEOD-38400", "E-GEOD-41338", "E-GEOD-43049", "E-MEXP-1099",  "E-MEXP-1276", "E-MEXP-3628", "E-MTAB-1066", "E-MTAB-1733", "E-MTAB-2039", "E-MTAB-2800", "E-MTAB-2809", "E-MTAB-2812", "E-MTAB-2836", "E-MTAB-2980", "E-MTAB-513", "E-MTAB-599", "E-MTAB-698", "E-PROT-1", "E-TABM-51", "E-TABM-713"));
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
