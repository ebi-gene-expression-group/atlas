package uk.ac.ebi.atlas.experimentpage.differential.microarray;

import com.jayway.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredAuthenticatedFixture;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentAnalysisMethodsPage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.ExperimentDesignTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;
import uk.ac.ebi.atlas.acceptance.selenium.pages.QCReportPage;

import java.text.MessageFormat;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PrivateMicroarrayExperimentWithAccessKeySIT extends SeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-3779";
    public static final String ARRAY_DESIGN = "A-AFFY-23";

    private HeatmapTablePage subject;

    private String accessKey;

    @BeforeClass
    public static void initRestAssuredRequestSpec(){
        RestAssuredAuthenticatedFixture.initRestAssured();
    }

    @Before
    public void init() {

        expect().body(containsString(EXPERIMENT_ACCESSION))
                .when().get("updateStatus?accession=" + EXPERIMENT_ACCESSION + "&private=true");

        String jsonResponse = given().get("listExperiments?accession=" + EXPERIMENT_ACCESSION).body().asString();

        accessKey = from(jsonResponse).get("accessKey[0]");

        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, "accessKey=" + accessKey);
        subject.get();
    }

    @After
    public void cleanup() {
        expect().body(containsString(EXPERIMENT_ACCESSION))
                .when().get("updateStatus?accession=" + EXPERIMENT_ACCESSION + "&private=false");
    }

    @Test(expected = NoSuchElementException.class)
    public void pageShouldNotBeAvailableWithoutAccessKey() {
        HeatmapTablePage page = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        page.get();
        page.getExperimentDescription();
    }

    @Test
    public void pageShouldBeAvailableWithAccessKey() {
        assertThat(subject.getExperimentDescription(), is("Transcription profiling by array of mouse neurospheres cultured from p107-/- embryos and their wildtype littermates"));
    }

    @Test
    public void buttonLinksShouldContainAccessKeyQueryString() {
        assertThat(subject.getDisplayExperimentLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDisplayExperimentDesignLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDisplayExperimentAnalysisLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDownloadNormalizedLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("?accessKey=" + accessKey));
        assertThat(subject.getQCReportLink(), endsWith("?accessKey=" + accessKey));
    }

    @Test(expected = NoSuchElementException.class)
    public void experimentDesignPageWillFailWithoutAccessKey() {
        ExperimentDesignTablePage experimentDesignPage = new ExperimentDesignTablePage(driver, EXPERIMENT_ACCESSION);
        experimentDesignPage.get();
        experimentDesignPage.getExperimentDescription();
    }

    @Test
    public void experimentDesignPageWillBeAvailableWithAccessKey() {
        ExperimentDesignTablePage experimentDesignPage =
                new ExperimentDesignTablePage(driver, EXPERIMENT_ACCESSION, "accessKey=" + accessKey);
        experimentDesignPage.get();
        experimentDesignPage.getExperimentDescription();
    }

    @Test
    public void analysisMethodsPageWillBeAvailableWithAccessKey() {
        ExperimentAnalysisMethodsPage analysisMethodsPage =
                new ExperimentAnalysisMethodsPage(driver, EXPERIMENT_ACCESSION, "accessKey=" + accessKey);
        analysisMethodsPage.get();
        analysisMethodsPage.getExperimentDescription();
    }

    @Test(expected = NoSuchElementException.class)
    public void qcReportPageWillFailWithoutAccessKey() {
        QCReportPage qcReportPage =
                new QCReportPage(driver, EXPERIMENT_ACCESSION, ARRAY_DESIGN);
        qcReportPage.get();
        qcReportPage.getExperimentDescription();
    }

    @Test
    public void qcReportPageWillBeAvailableWithAccessKey() {
        QCReportPage qcReportPage =
                new QCReportPage(driver, EXPERIMENT_ACCESSION, ARRAY_DESIGN, "accessKey=" + accessKey);
        qcReportPage.get();
        qcReportPage.getExperimentDescription();
    }

    @Test
    public void qcReportResourcesAvailableWithoutAccessKey(){
        RestAssuredFixture.initRestAssured();

        Response response = get(MessageFormat.format("/experiments/{0}/qc/{1}/box.png", EXPERIMENT_ACCESSION, ARRAY_DESIGN));

        response.then().assertThat().statusCode(200);

        RestAssuredAuthenticatedFixture.initRestAssured();
    }

}
