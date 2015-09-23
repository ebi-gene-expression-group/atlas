package uk.ac.ebi.atlas.tracks;


import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.get;

public class TracksControllerSIT extends RestAssuredFixture {

    @Test
    public void baselineTracks(){
        Response response_g1 = get("/experiments/E-MTAB-599/tracks/E-MTAB-599.g1.genes.expressions.bedGraph");

        response_g1.then().assertThat().statusCode(200);
        response_g1.then().assertThat().contentType("");

        Response response_g2 = get("/experiments/E-MTAB-599/tracks/E-MTAB-599.g2.genes.expressions.bedGraph");

        response_g2.then().assertThat().statusCode(200);
        response_g2.then().assertThat().contentType("");
    }

    @Test
    public void rnaseqTracks(){
        Response response_g1 = get("/experiments/E-MTAB-698/tracks/E-MTAB-698.g1_g2.genes.log2foldchange.bedGraph");

        response_g1.then().assertThat().statusCode(200);
        response_g1.then().assertThat().contentType("");

        Response response_g2 = get("/experiments/E-MTAB-698/tracks/E-MTAB-698.g1_g2.genes.pval.bedGraph");

        response_g2.then().assertThat().statusCode(200);
        response_g2.then().assertThat().contentType("");
    }

    @Test
    public void microarrayTracks(){
        Response response_g1 = get("/experiments/E-GEOD-3307/tracks/E-GEOD-3307.g1_g2.genes.log2foldchange.bedGraph");

        response_g1.then().assertThat().statusCode(200);
        response_g1.then().assertThat().contentType("");

        Response response_g2 = get("/experiments/E-GEOD-3307/tracks/E-GEOD-3307.g1_g2.genes.pval.bedGraph");

        response_g2.then().assertThat().statusCode(200);
        response_g2.then().assertThat().contentType("");
    }

}
