
package uk.ac.ebi.atlas.experimentpage.differential.rnaseq;

import org.junit.*;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredAuthenticatedFixture;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentDesignTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PrivateExperimentWithAccessKeySIT extends SeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-698";

    private HeatmapTablePage subject;

    private String accessKey;

    @BeforeClass
    public static void initRestAssuredRequestSpec(){
        RestAssuredAuthenticatedFixture.initRestAssured();
    }

    @Before
    public void init() {
        // TODO Uncomment when https://www.pivotaltracker.com/story/show/101118548 is done
        //expect().body(containsString(EXPERIMENT_ACCESSION)).when().get("updateStatus?accession=" + EXPERIMENT_ACCESSION + "&private=true");

        //String jsonResponse = given().get("listExperiments?accession=" + EXPERIMENT_ACCESSION).body().asString();

        //accessKey = from(jsonResponse).get("accessKey[0]");

        //subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, "accessKey=" + accessKey);
        //subject.get();
    }

    @After
    public void cleanup() {
        expect().body(containsString(EXPERIMENT_ACCESSION)).when().get("updateStatus?accession=" + EXPERIMENT_ACCESSION + "&private=false");
    }

    @Ignore//(expected = NoSuchElementException.class)
    public void pageShouldNotBeAvailableWithoutAccessKey() {
        HeatmapTablePage page = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        page.get();
        page.getExperimentDescription();
    }

    @Ignore
    public void pageShouldBeAvailableWithAccessKey() {
        assertThat(subject.getExperimentDescription(), is("RNA-seq of vomeronasal tissue from adult male and female mice"));
    }

    @Ignore
    public void buttonLinksShouldContainAccessKeyQueryString() {
        assertThat(subject.getDisplayExperimentLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDisplayExperimentDesignLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDisplayExperimentAnalysisLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDownloadRawCountsLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("?accessKey=" + accessKey));
    }

    @Ignore//(expected = NoSuchElementException.class)
    public void experimentDesignPageWillFailWithoutAccessKey() {
        ExperimentDesignTablePage experimentDesignPage = new ExperimentDesignTablePage(driver, EXPERIMENT_ACCESSION);
        experimentDesignPage.get();
        experimentDesignPage.getExperimentDescription();
    }

    @Ignore
    public void experimentDesignPageWillBeAvailableWithAccessKey() {
        ExperimentDesignTablePage experimentDesignPage = new ExperimentDesignTablePage(driver, EXPERIMENT_ACCESSION, "accessKey=" + accessKey);
        experimentDesignPage.get();
        experimentDesignPage.getExperimentDescription();
    }

}