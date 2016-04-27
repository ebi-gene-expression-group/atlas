package uk.ac.ebi.atlas.acceptance.rest.tests.DAS;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

public class DASFeaturesControllerIT {

    private static final int HTTP_OK = 200;

    @Test
    public void featuresURLGeneWithExpressions() {
        String geneID = "ENSMUSG00000061306";
        EndPoint subject = new EndPoint("/gxa/das/s4/features", "segment=" + geneID);
        Response response = subject.getResponse();

        assertThat(response.getStatusCode(), is(HTTP_OK));
        assertThat(response.getContentType(), is("application/xml;charset=UTF-8"));

        String responseXML = response.getBody().asString();
        assertThat(responseXML, not(isEmptyOrNullString()));
        assertThat(responseXML, startsWith("<?xml"));
    }


    @Test
    public void featuresURLNonExistantGene() {
        String geneID = "FOOBAR";
        EndPoint subject = new EndPoint("/gxa/das/s4/features", "segment=" + geneID);
        Response response = subject.getResponse();

        assertThat(response.getStatusCode(), is(HTTP_OK));
        assertThat(response.getContentType(), is("application/xml;charset=UTF-8"));

        String responseXML = response.getBody().asString();
        assertThat(responseXML, not(isEmptyOrNullString()));
        assertThat(responseXML, startsWith("<?xml"));
//        assertThat(responseXML, startsWith("<DASGFF>\n" +
//                "<GFF href=\"http://www.ebi.ac.uk/gxa/das/s4/features?segment=asdfasd\">\n" +
//                "<UNKNOWNSEGMENT id=\"FOOBAR\"/>\n" +
//                "</GFF>\n" +
//                "</DASGFF>"));
    }

    @Test
    public void featuresURLGeneWithoutExpressions() {
        String geneID = "ENSG00000139618";
        EndPoint subject = new EndPoint("/gxa/das/s4/features", "segment=" + geneID);
        Response response = subject.getResponse();

        assertThat(response.getStatusCode(), is(HTTP_OK));
        assertThat(response.getContentType(), is("application/xml;charset=UTF-8"));

        String responseXML = response.getBody().asString();
        assertThat(responseXML, not(isEmptyOrNullString()));
        assertThat(responseXML, startsWith("<?xml"));
    }

}
