package uk.ac.ebi.atlas.acceptance.rest.tests.admin;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredAuthenticatedFixture;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ExperimentAdminControllerIT extends RestAssuredAuthenticatedFixture {

    private static final String EXISTING_EXPERIMENT_ACCESSION = "E-MTAB-2706";
    private static final String NEW_EXPERIMENT_ACCESSION = "TEST-BASELINE";
    private static final String DIFFERENTIAL_EXPERIMENT_ACCESSION = "E-GEOD-22351";

    @BeforeClass
    public static void assertDatabaseStateIsInExpectedState(){
        expect().body(containsString(EXISTING_EXPERIMENT_ACCESSION)).when().get("listExperiments?accession=" + EXISTING_EXPERIMENT_ACCESSION);
        assertThat(countConditionProperties(DIFFERENTIAL_EXPERIMENT_ACCESSION), is(2));
    }

    @Ignore
    public void listSelectedExperiments() {
        expect().body("experimentAccession", containsInAnyOrder(EXISTING_EXPERIMENT_ACCESSION, "E-MTAB-513")).when()
                .get("listExperiments?accession=" + EXISTING_EXPERIMENT_ACCESSION + ",E-MTAB-513");
    }

    @Ignore
    public void loadAndDeleteNewBaselineExperimentPublic() {

        expect().body(loaded(NEW_EXPERIMENT_ACCESSION )).when()
                .get("importExperiment?accession=" + NEW_EXPERIMENT_ACCESSION + "&private=false");

        expect().body("experimentAccession", hasItem(NEW_EXPERIMENT_ACCESSION))
                .and().body("isPrivate", contains(false))
                .when().get("listExperiments?accession=" + NEW_EXPERIMENT_ACCESSION);

        expect().body(is("Experiment TEST-BASELINE successfully deleted.")).when()
                .get("deleteExperimentDeletesDB?accession=" + NEW_EXPERIMENT_ACCESSION);

        expect().body("experimentAccession", is(empty())).when().get("listExperiments?accession=" + NEW_EXPERIMENT_ACCESSION);
    }

    @Ignore
    public void loadAndDeleteNewBaselineExperimentPrivate() {

        expect().body(loaded(NEW_EXPERIMENT_ACCESSION )).when()
                .get("importExperiment?accession=" + NEW_EXPERIMENT_ACCESSION + "&private=true");

        expect().body("experimentAccession", hasItem(NEW_EXPERIMENT_ACCESSION))
                .and().body("isPrivate", contains(true))
                .when().get("listExperiments?accession=" + NEW_EXPERIMENT_ACCESSION);

        expect().body(is("Experiment TEST-BASELINE successfully deleted.")).when()
                .get("deleteExperimentDeletesDB?accession=" + NEW_EXPERIMENT_ACCESSION);

        expect().body("experimentAccession", is(empty())).when().get("listExperiments?accession=" + NEW_EXPERIMENT_ACCESSION);
    }

    @Ignore
    public void deleteNonExisting() {
        String blablaExperimentAccession = "E-MTAB-BLA-BLA-BLA";

        expect().body(is("ResourceNotFoundException: Experiment: " + blablaExperimentAccession + " not found"))
                .when().get("deleteExperimentDeletesDB?accession=" + blablaExperimentAccession);
    }

    @Ignore
    public void reloadExistingExperiment() {

        expect().body("experimentAccession", hasItem(EXISTING_EXPERIMENT_ACCESSION))
                .when().get("listExperiments?accession=" + EXISTING_EXPERIMENT_ACCESSION);

        expect().body(loaded(EXISTING_EXPERIMENT_ACCESSION )).when()
                .get("importExperiment?accession=" + EXISTING_EXPERIMENT_ACCESSION + "&private=false");
    }

    @Ignore
    public void loadInvalidExperiment() {
        String blablaExperimentAccession = "E-MTAB-BLA-BLA-BLA";

        expect().body(startsWith("IllegalStateException: Required file can not be read"))
                .when().get("importExperiment?accession=" + blablaExperimentAccession);

        expect().body("experimentAccession", is(empty())).when().get("listExperiments?accession=" + blablaExperimentAccession);

    }

    @Ignore
    public void updateDifferentialExperiment() {

        expect().body(updated(DIFFERENTIAL_EXPERIMENT_ACCESSION)).when()
                .get("updateStatus?accession=" + DIFFERENTIAL_EXPERIMENT_ACCESSION + "&private=true");

        expect().body(updated(DIFFERENTIAL_EXPERIMENT_ACCESSION)).when()
                .get("updateStatus?accession=" + DIFFERENTIAL_EXPERIMENT_ACCESSION + "&private=false");

        assertThat(countConditionProperties(DIFFERENTIAL_EXPERIMENT_ACCESSION), is(2));


    }

    @Ignore
    public void deleteAndLoadDifferentialExperimentPublic() {

        expect().body(deleted(DIFFERENTIAL_EXPERIMENT_ACCESSION)).when()
                .get("deleteExperimentDeletesDB?accession=" + DIFFERENTIAL_EXPERIMENT_ACCESSION);

        assertThat(countConditionProperties(DIFFERENTIAL_EXPERIMENT_ACCESSION), is(0));

        expect().body(loaded(DIFFERENTIAL_EXPERIMENT_ACCESSION)).when()
                .get("importExperiment?accession=" + DIFFERENTIAL_EXPERIMENT_ACCESSION + "&private=false");

        assertThat(countConditionProperties(DIFFERENTIAL_EXPERIMENT_ACCESSION), is(2));

    }

    @Ignore
    public void serializeExpressionData() {
        expect().body(is("Expression data successfully serialized for " + EXISTING_EXPERIMENT_ACCESSION))
                .when().get("serializeExpressionData?accession=" + EXISTING_EXPERIMENT_ACCESSION);
    }

    @Ignore
    public void serializeAllBaselineExpressionData() {
        expect().body(is("All baseline experiments expression data successfully serialized")).when().get("serializeAllBaselineExpressionData");
    }

    @Ignore
    public void serializeDifferentialExpressionDataFails() {
        expect().body(startsWith("UnsupportedOperationException: No expression serializer for experiment type RNASEQ_MRNA_DIFFERENTIAL"))
                .when().get("serializeExpressionData?accession=" + DIFFERENTIAL_EXPERIMENT_ACCESSION);
    }

    private Matcher<String> loaded(String experiment){
        return startsWith("Experiment " + experiment + " loaded, accessKey:");
    }

    private Matcher<String> updated(String experiment){
        return is("Experiment " + experiment + " successfully updated.");
    }

    private Matcher<String> deleted(String experiment){
        return is("Experiment " + experiment + " successfully deleted.");
    }

    private static int countConditionProperties(String experimentAccession) {
        Response conditionIndexResponse = queryConditionIndex(experimentAccession);

        String responseBody = conditionIndexResponse.body().asString();

        return Integer.parseInt(StringUtils.substringBetween(responseBody, "\"numFound\":", ","));
    }

    private static Response queryConditionIndex(String experimentAccession) {
        return RestAssured.get("http://lime:8983/solr/differentialConditions/select?q=experiment_accession:"
                + experimentAccession + "&wt=json&indent=true");
    }

}