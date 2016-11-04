package uk.ac.ebi.atlas.experimentpage.differential.microarray;

import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredAuthenticatedFixture;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentAnalysisMethodsPage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentDesignTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.QCReportPage;
import com.jayway.restassured.response.Response;
import org.junit.*;

import java.text.MessageFormat;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PrivateMicroarrayExperimentWithAccessKeySIT extends SeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-11758";
    public static final String ARRAY_DESIGN = "A-AFFY-2";

    private HeatmapTablePage subject;

    private String accessKey;

    @BeforeClass
    public static void initRestAssuredRequestSpec(){
        RestAssuredAuthenticatedFixture.initRestAssured();
    }

    @Before
    public void init() {
        // TODO Uncomment when https://www.pivotaltracker.com/story/show/101118548 is done
        //expect().body(containsString(EXPERIMENT_ACCESSION))
        //        .when().get("updateStatus?accession=" + EXPERIMENT_ACCESSION + "&private=true");

        //String jsonResponse = given().get("listExperiments?accession=" + EXPERIMENT_ACCESSION).body().asString();

        //accessKey = from(jsonResponse).get("accessKey[0]");

        //subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, "accessKey=" + accessKey);
        //subject.get();
    }

    @After
    public void cleanup() {
        expect().body(containsString(EXPERIMENT_ACCESSION))
                .when().get("updateStatus?accession=" + EXPERIMENT_ACCESSION + "&private=false");
    }

    @Ignore//(expected = NoSuchElementException.class)
    public void pageShouldNotBeAvailableWithoutAccessKey() {
        HeatmapTablePage page = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        page.get();
        page.getExperimentDescription();
    }

    @Ignore//(expected = NoSuchElementException.class)
    public void pageShouldBeAvailableWithAccessKey() {
        assertThat(subject.getExperimentDescription(), is("Transcription profiling by array of mouse neurospheres cultured from p107-/- embryos and their wildtype littermates"));
    }

    @Ignore//(expected = NoSuchElementException.class)
    public void buttonLinksShouldContainAccessKeyQueryString() {
        assertThat(subject.getDisplayExperimentLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDisplayExperimentDesignLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDisplayExperimentAnalysisLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDownloadNormalizedLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getQCReportLink(), endsWith("?accessKey=" + accessKey));
    }

    @Ignore//(expected = NoSuchElementException.class)
    public void experimentDesignPageWillFailWithoutAccessKey() {
        ExperimentDesignTablePage experimentDesignPage = new ExperimentDesignTablePage(driver, EXPERIMENT_ACCESSION);
        experimentDesignPage.get();
        experimentDesignPage.getExperimentDescription();
    }

    @Ignore//(expected = NoSuchElementException.class)
    public void experimentDesignPageWillBeAvailableWithAccessKey() {
        ExperimentDesignTablePage experimentDesignPage =
                new ExperimentDesignTablePage(driver, EXPERIMENT_ACCESSION, "accessKey=" + accessKey);
        experimentDesignPage.get();
        experimentDesignPage.getExperimentDescription();
    }

    @Ignore//(expected = NoSuchElementException.class)
    public void analysisMethodsPageWillBeAvailableWithAccessKey() {
        ExperimentAnalysisMethodsPage analysisMethodsPage =
                new ExperimentAnalysisMethodsPage(driver, EXPERIMENT_ACCESSION, "accessKey=" + accessKey);
        analysisMethodsPage.get();
        analysisMethodsPage.getExperimentDescription();
    }

    @Ignore//(expected = NoSuchElementException.class)
    public void qcReportPageWillFailWithoutAccessKey() {
        QCReportPage qcReportPage =
                new QCReportPage(driver, EXPERIMENT_ACCESSION, ARRAY_DESIGN);
        qcReportPage.get();
        qcReportPage.getExperimentDescription();
    }

    @Ignore//(expected = NoSuchElementException.class)
    public void qcReportPageWillBeAvailableWithAccessKey() {
        QCReportPage qcReportPage =
                new QCReportPage(driver, EXPERIMENT_ACCESSION, ARRAY_DESIGN, "accessKey=" + accessKey);
        qcReportPage.get();
        qcReportPage.getExperimentDescription();
    }

    @Ignore
    public void qcReportResourcesAvailableWithoutAccessKey(){
        RestAssuredFixture.initRestAssured();

        Response response = get(MessageFormat.format("/experiments/{0}/qc/{1}/box.png", EXPERIMENT_ACCESSION, ARRAY_DESIGN));

        response.then().assertThat().statusCode(200);

        RestAssuredAuthenticatedFixture.initRestAssured();
    }

}
