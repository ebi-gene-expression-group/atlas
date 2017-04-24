package uk.ac.ebi.atlas.widget;

import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import javax.inject.Inject;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class BaselineAndDifferentialAnalyticsServiceIT extends RestAssuredFixture {

    static final SemanticQuery EMPTY_QUERY = SemanticQuery.create();

    public static final String BASELINE_GENE = "ENSG00000000003";
    public static final String DIFFERENTIAL_GENE = "ENSSSCG00000000024";
    public static final String NON_EXISTENT_GENE = "FOOBAR";

    @Inject
    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;

    @Inject
    private DifferentialAnalyticsSearchService differentialAnalyticsSearchService;

    //These tests are really the assertions for ExpressionDataControllerEIT.
    @Test
    public void geneExpressedInBaselineAndDifferentialExperiments() {
        JsonObject result = baselineAnalyticsSearchService.findFacetsForTreeSearch(SemanticQuery.create(BASELINE_GENE),
                SemanticQuery.create(), new Species("Foous baris", SpeciesProperties.UNKNOWN));
        assertThat(result.entrySet(), not(Matchers.empty()));
        assertTrue("This Ensembl gene has a homo sapiens result", result.has("homo sapiens"));

    }

    @Test
    public void geneExpressedInDifferentialExperimentsOnly() {
        assertThat(
                baselineAnalyticsSearchService.findFacetsForTreeSearch(SemanticQuery.create(DIFFERENTIAL_GENE), SemanticQuery.create(), new Species("Foous baris", SpeciesProperties.UNKNOWN)),
                is(new JsonObject()));
        assertThat(
                differentialAnalyticsSearchService.fetchFacets(SemanticQuery.create(DIFFERENTIAL_GENE), EMPTY_QUERY)
                        .entrySet(), not(Matchers.<Map.Entry<String,JsonElement>>empty()));
    }

    @Test
    public void nonExistentGene() {
        assertThat(
                baselineAnalyticsSearchService.findFacetsForTreeSearch(SemanticQuery.create(NON_EXISTENT_GENE), SemanticQuery.create(), new Species("Foous baris", SpeciesProperties.UNKNOWN)),
                is(new JsonObject()));
        assertThat(
                differentialAnalyticsSearchService.fetchResults(SemanticQuery.create(NON_EXISTENT_GENE), EMPTY_QUERY)
                        .get("results").getAsJsonArray().size(),
                is(0));
    }

    @Test
    public void differentialAnalyticsSearchServiceHasTheRightReturnFormat(){
        JsonObject result = differentialAnalyticsSearchService.fetchResults(SemanticQuery.create("GO:0008150"), EMPTY_QUERY);
        testDifferentialResultsAreInRightFormat(result);
    }

    private ImmutableList<String> fieldsNeededInDifferentialResults = ImmutableList.of(
            "species",
            "kingdom",
            "experimentType",
            "numReplicates",
            "regulation",
            "factors",
            "bioentityIdentifier",
            "experimentAccession",
            "experimentName",
            "contrastId",
            "comparison",
            "foldChange",
            "colour",
            "id");

    private void testDifferentialResultsAreInRightFormat(JsonObject result){
        assertTrue(new Gson().toJson(result), result.has("results"));
        assertThat(result.get("results").getAsJsonArray().size(), greaterThan(0));
        for(JsonElement e: result.get("results").getAsJsonArray()){
            for(String fieldName: fieldsNeededInDifferentialResults) {
                assertTrue("result has "+fieldName, e.getAsJsonObject().has(fieldName));
            }
        }
    }
}
