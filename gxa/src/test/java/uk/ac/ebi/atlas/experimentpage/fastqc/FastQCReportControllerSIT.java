package uk.ac.ebi.atlas.experimentpage.fastqc;

import com.jayway.restassured.response.Response;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FastQCReportControllerSIT extends RestAssuredFixture {

    @Test
    public void riqRawDataAssayIdFastQCReportHtml(){
        String url = "/experiments/E-GEOD-26284/fastqc/homo%20sapiens/riq/raw_data/SRR307897_pe_1.f_fastqc/fastqc_report.html";

        Response response = given().urlEncodingEnabled(false).get(url);

        response.then().assertThat().statusCode(HttpStatus.OK_200);
    }

    @Test
    public void riqRawDataAssayIdIconsPng(){
        String url = "/experiments/E-GEOD-26284/fastqc/homo%20sapiens/riq/raw_data/SRR307897_pe_1.f_fastqc/Icons/fastqc_icon.png";

        Response response = given().urlEncodingEnabled(false).get(url);

        response.then().assertThat().statusCode(HttpStatus.OK_200);
    }

    @Test
    public void riqRawDataAssayIdImagesPng(){
        String url = "/experiments/E-GEOD-26284/fastqc/homo%20sapiens/riq/raw_data/SRR307897_pe_1.f_fastqc/Images/per_base_quality.png";

        Response response = given().urlEncodingEnabled(false).get(url);

        response.then().assertThat().statusCode(HttpStatus.OK_200);
    }

    @Test
    public void return404WhenMissingPng() {
        String url = "/experiments/E-GEOD-26284/fastqc/homo%20sapiens/riq/raw_data/SRR534304_2_fastqc/Images/NON_EXISTANT.png";

        Response response = given().urlEncodingEnabled(false).get(url);

        response.then().assertThat().statusCode(HttpStatus.NOT_FOUND_404);
    }

    @Test
    public void return404WhenMissingFastQCReportHtml() {
        String url = "/experiments/E-GEOD-26284/fastqc/homo%20sapiens/riq/raw_data/SRR307897_pe_1.f_fastqc_NON_EXISTANT/fastqc_report.html";

        Response response = given().urlEncodingEnabled(false).get(url);

        response.then().assertThat().statusCode(HttpStatus.NOT_FOUND_404);
    }

    @Test
    public void riqGuidRawDataAssayIdFastQCReportHtml(){
        String url = "/experiments/E-MTAB-2980/fastqc/homo%20sapiens/riq/166efd97-7b71-4089-be92-d8d006f86c3b/raw_data/c786-O.1_1.f_fastqc/fastqc_report.html";

        Response response = given().urlEncodingEnabled(false).get(url);

        response.then().assertThat().statusCode(HttpStatus.OK_200);

        String body = response.getBody().asString();

        assertThat(body.contains("div class=\"gxaError\""), is(false));
    }

}