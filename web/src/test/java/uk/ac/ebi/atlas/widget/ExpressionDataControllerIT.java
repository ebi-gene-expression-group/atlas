package uk.ac.ebi.atlas.widget;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;

import javax.inject.Inject;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static uk.ac.ebi.atlas.utils.RegexMatcher.matches;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ExpressionDataControllerIT extends RestAssuredFixture {

    private static final String BASELINE_GENE = "ENSG00000000003";
    private static final String DIFFERENTIAL_GENE = "AT2G06310";
    private static final String NON_EXISTENT_GENE = "FOOBAR";

    private static final String EMPTY_JSON_OBJECT = "{}";
    private static final String EMPTY_JSON_ARRAY =
            "{\n" +
            "  \"results\": []\n" +
            "}";
    private static final String NON_EMPTY_JSON_OBJECT_REGEX = "(?s)\\{.+\\}";

    @Inject
    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;

    @Inject
    private DifferentialAnalyticsSearchService differentialAnalyticsSearchService;

    @Test
    public void geneExpressedInBaselineAndDifferentialExperimentsReturnsTrue() {
        assertThat(
                baselineAnalyticsSearchService.findFacetsForTreeSearch(SemanticQuery.create(BASELINE_GENE), SemanticQuery.create(), ""),
                matches(NON_EMPTY_JSON_OBJECT_REGEX));

        Response response = get("/json/expressionData?geneId=" + BASELINE_GENE);

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("application/json;charset=UTF-8");
        response.then().assertThat().body(containsString(String.format("\"%s\":true", BASELINE_GENE)));
    }

    @Test
    public void geneExpressedInDifferentialExperimentsOnlyReturnsFalse() {
        assertThat(
                baselineAnalyticsSearchService.findFacetsForTreeSearch(SemanticQuery.create(DIFFERENTIAL_GENE), SemanticQuery.create(), ""),
                is(EMPTY_JSON_OBJECT));
        assertThat(
                differentialAnalyticsSearchService.fetchDifferentialFacetsForSearch(SemanticQuery.create(DIFFERENTIAL_GENE))
                .matches(NON_EMPTY_JSON_OBJECT_REGEX), is(true));

        Response response = get("/json/expressionData?geneId=" + DIFFERENTIAL_GENE);

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("application/json;charset=UTF-8");
        response.then().assertThat().body(containsString(String.format("\"%s\":false", DIFFERENTIAL_GENE)));
    }

    @Test
    public void nonExistentGeneReturnsFalse() {
        assertThat(
                baselineAnalyticsSearchService.findFacetsForTreeSearch(SemanticQuery.create(NON_EXISTENT_GENE), SemanticQuery.create(), ""),
                is(EMPTY_JSON_OBJECT));
        assertThat(
                differentialAnalyticsSearchService.fetchDifferentialResultsForSearch(SemanticQuery.create(NON_EXISTENT_GENE)),
                is(EMPTY_JSON_ARRAY));

        Response response = get("/json/expressionData?geneId=" + NON_EXISTENT_GENE);

        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("application/json;charset=UTF-8");
        response.then().assertThat().body(containsString(String.format("\"%s\":false", NON_EXISTENT_GENE)));
    }
}