package uk.ac.ebi.atlas.search;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;

public class BioentitiesSearchControllerRedirectsSIT extends RestAssuredFixture {


    void assertRedirect(String from, String to) {
        Response response = given().redirects().follow(false).get(from);

        response.then().assertThat().statusCode(302);
        response.then().assertThat().header("Location", endsWith(to));
    }

    @Test
    public void geneId(){
        assertRedirect("/query?geneQuery=ENSG00000161547", "genes/ENSG00000161547");
    }

    @Test
    public void geneIdWithLeadingAndTrailingSpace(){
        String from = "/query?geneQuery=+ENSG00000161547+";
        String to = "genes/ENSG00000161547";

        Response response = given().redirects().follow(false).urlEncodingEnabled(false).get(from);

        response.then().assertThat().statusCode(302);
        response.then().assertThat().header("Location", endsWith(to));
    }


    @Test
    public void proteinId() {
        assertRedirect("/query?geneQuery=ENSP00000355434", "proteins/ENSP00000355434");
    }

    @Test
    public void geneSetReact(){
        assertRedirect("/query?geneQuery=REACT_1619", "genesets/REACT_1619");
    }

    @Test
    public void geneSetReactLowercase() {
        assertRedirect("/query?geneQuery=react_111217","genesets/react_111217");
    }

    @Test
    public void geneSetInterpro(){
        assertRedirect("/query?geneQuery=IPR000001", "genesets/IPR000001");
    }

    @Test
    public void geneSetGo(){
        assertRedirect("/query?geneQuery=GO:0005527", "genesets/GO:0005527");
    }

    @Test
    public void uniprotAccession() {
        assertRedirect("/query?geneQuery=Q01130","genes/ENSG00000161547");
    }


}
