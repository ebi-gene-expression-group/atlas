
package uk.ac.ebi.atlas.bioentity.widget;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntityPage;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenePageControllerHomoSapiensGeneBaselineWidgetSIT extends SinglePageSeleniumFixture {

    private static final String GENE_IDENTIFIER = "ENSG00000163331";

    private BioEntityPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntityPage(driver, GENE_IDENTIFIER, "genes");
        subject.get();
    }

    @Test
    public void checkPaneExpansion() {
        assertThat(subject.isBaselinePaneExpanded(), is(true));
        assertThat(subject.isInfoCardExpanded(), is(false));
    }

    @Test
    public void baselineWidgetGenes() {
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results in tissues"));

        subject.waitForHeatmapToBeVisible();

        assertThat(subject.getGeneCount(), is("Showing 3 of 3 experiments found:"));
        assertThat(subject.getGeneColumnHeader(), is("Experiment"));

        List<String> factorValueHeaders = subject.getFactorValueHeaders();
        assertThat(factorValueHeaders, contains("adipose tissue", "adrenal gland", "animal ovary", "appendix", "bladder", "bone marrow", "brain", "breast", "cerebellum", "cerebral cortex", "colon", "duodenum", "endometrium", "esophagus", "frontal lobe", "gall bladder", "heart", "kidney", "leukocyte", "liver", "lung", "lymph node", "pancreas", "placenta", "prefrontal…", "prostate", "salivary gland", "skeletal muscle", "skin", "small intestine", "spleen", "stomach", "temporal lobe", "testis", "thyroid"));

        assertThat(subject.getGeneNames().size(), is(3));
        assertThat(subject.getGeneNames(), contains("Twenty seven tissues","Illumina Body Map","Vertebrate tissues"));
        assertThat(subject.getGeneLink(2), endsWith("/experiments/E-GEOD-30352?geneQuery=ENSG00000163331&serializedFilterFactors=ORGANISM%3AHomo%20sapiens"));
        assertThat(subject.getGeneLink(0), endsWith("/experiments/E-MTAB-1733?geneQuery=ENSG00000163331"));
        assertThat(subject.getGeneLink(1), endsWith("/experiments/E-MTAB-513?geneQuery=ENSG00000163331"));
        assertThat(subject.hasAnatomogram(), is(true));
    }

    @Test
    public void downloadLink() {
        subject.waitForHeatmapToBeVisible();

        String downloadUrl = subject.downloadProfilesLink();
        System.out.println(downloadUrl);

        Response response = given().urlEncodingEnabled(false).get(downloadUrl);

        response.then().assertThat().statusCode(200);

        ResponseBody body = response.getBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(7));
        response.then().assertThat().body(containsString("Twenty seven tissues"));
        response.then().assertThat().body(containsString("Illumina Body Map"));
        response.then().assertThat().body(containsString("Vertebrate tissues"));
    }


}
