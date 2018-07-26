package uk.ac.ebi.atlas.widget;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.analytics.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.solr.analytics.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class BaselineAndDifferentialAnalyticsServiceIT {
    private static final SemanticQuery EMPTY_QUERY = SemanticQuery.create();

    static final String BASELINE_GENE = "ENSG00000000003";
    static final String DIFFERENTIAL_GENE = "ENSSSCG00000000024";
    static final String NON_EXISTENT_GENE = "FOOBAR";

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
                baselineAnalyticsSearchService.findFacetsForTreeSearch(
                        SemanticQuery.create(DIFFERENTIAL_GENE),
                        SemanticQuery.create(),
                        new Species("Foous baris", SpeciesProperties.UNKNOWN)),
                is(new JsonObject()));

        assertThat(
                differentialAnalyticsSearchService.fetchFacets(SemanticQuery.create(DIFFERENTIAL_GENE), EMPTY_QUERY)
                        .entrySet(), not(Matchers.empty()));
    }

    @Test
    public void nonExistentGene() {
        assertThat(
                baselineAnalyticsSearchService.findFacetsForTreeSearch(
                        SemanticQuery.create(NON_EXISTENT_GENE),
                        SemanticQuery.create(),
                        new Species("Foous baris", SpeciesProperties.UNKNOWN)),
                is(new JsonObject()));
        assertThat(
                differentialAnalyticsSearchService.fetchResults(SemanticQuery.create(NON_EXISTENT_GENE), EMPTY_QUERY)
                        .get("results").getAsJsonArray().size(),
                is(0));
    }

    @Test
    public void differentialAnalyticsSearchServiceHasTheRightReturnFormat(){
        JsonObject result =
                differentialAnalyticsSearchService.fetchResults(SemanticQuery.create("GO:0008150"), EMPTY_QUERY);
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
            "pValue",
            "colour",
            "id");

    private ImmutableList<String> fieldsNeededInMicroarrayDifferentialResults =
            ImmutableList.<String>builder()
                    .addAll(fieldsNeededInDifferentialResults)
                    .add("tStatistic")
                    .build();

    private void testDifferentialResultsAreInRightFormat(JsonObject result) {
        assertTrue(GSON.toJson(result), result.has("results"));
        assertThat(result.get("results").getAsJsonArray().size(), greaterThan(0));

        for (JsonElement jsonElement: result.get("results").getAsJsonArray()) {
            ExperimentType experimentType =
                    ExperimentType.valueOf(jsonElement.getAsJsonObject().get("experimentType").getAsString());

            if (experimentType.isMicroarray()) {
                for (String fieldName: fieldsNeededInMicroarrayDifferentialResults) {
                    assertTrue("result has "+fieldName, jsonElement.getAsJsonObject().has(fieldName));
                }
            } else {
                for (String fieldName: fieldsNeededInDifferentialResults) {
                    assertTrue("result has "+fieldName, jsonElement.getAsJsonObject().has(fieldName));
                }
            }
        }
    }
}
