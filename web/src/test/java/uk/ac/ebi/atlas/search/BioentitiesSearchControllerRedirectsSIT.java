package uk.ac.ebi.atlas.search;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;

public class BioentitiesSearchControllerRedirectsSIT extends RestAssuredFixture {


    void assertRedirect(String from, String to) {
        Response response = given().redirects().follow(false).get(from);

        response.then().assertThat().statusCode(302);
        response.then().assertThat().header("Location", containsString(to));
    }

    @Test
    public void geneId(){
        assertRedirect("/query?geneQuery=ENSMUSG00000021789", "genes/ENSMUSG00000021789");
    }

    @Test
    public void geneIdWithLeadingAndTrailingSpace(){
        String from = "/query?geneQuery=+ENSMUSG00000021789+";
        String to = "genes/ENSMUSG00000021789";

        Response response = given().redirects().follow(false).urlEncodingEnabled(false).get(from);

        response.then().assertThat().statusCode(302);
        response.then().assertThat().header("Location", containsString(to));
    }

    @Test
    public void proteinId() {
        assertRedirect("/query?geneQuery=ENSPPYP00000006717", "proteins/ENSPPYP00000006717");
    }

    @Test
    public void geneSetReact(){
        assertRedirect("/query?geneQuery=R-HSA-73887", "genesets/R-HSA-73887");
    }

    @Test
    public void geneSetReactLowercase() {
        assertRedirect("/query?geneQuery=r-hsa-1430728", "genesets/r-hsa-1430728");
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
        assertRedirect("/query?geneQuery=Q01130", "genes/ENSG00000161547");
    }

    @Test
    public void geneNameAndOrganismMatchingSingleGeneId() {
        assertRedirect("/query?geneQuery=ASPM&exactMatch=true&_exactMatch=on&organism=Homo sapiens&condition=", "genes/ENSG00000066279");
    }

}
