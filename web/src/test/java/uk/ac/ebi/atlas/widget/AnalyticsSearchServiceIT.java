package uk.ac.ebi.atlas.widget;

import com.google.gson.JsonElement;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.acceptance.rest.fixtures.RestAssuredFixture;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.trader.SpeciesFactory;

import javax.inject.Inject;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static uk.ac.ebi.atlas.utils.RegexMatcher.matches;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class AnalyticsSearchServiceIT extends RestAssuredFixture {

    private static final String BASELINE_GENE = "ENSG00000000003";
    private static final String DIFFERENTIAL_GENE = "AT2G06310";
    private static final String NON_EXISTENT_GENE = "FOOBAR";

    private static final String EMPTY_JSON_OBJECT = "{}";

    private static final String NON_EMPTY_JSON_OBJECT_REGEX = "(?s)\\{.+\\}";

    @Inject
    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;

    @Inject
    private DifferentialAnalyticsSearchService differentialAnalyticsSearchService;

    @Test
    public void geneExpressedInBaselineAndDifferentialExperimentsReturnsTrue() {
        assertThat(
                baselineAnalyticsSearchService.findFacetsForTreeSearch(SemanticQuery.create(BASELINE_GENE),
                        SemanticQuery.create(), SpeciesFactory.NULL),
                matches(NON_EMPTY_JSON_OBJECT_REGEX));
    }

    @Test
    public void geneExpressedInDifferentialExperimentsOnlyReturnsFalse() {
        assertThat(
                baselineAnalyticsSearchService.findFacetsForTreeSearch(SemanticQuery.create(DIFFERENTIAL_GENE), SemanticQuery.create(), SpeciesFactory.NULL),
                is(EMPTY_JSON_OBJECT));
        assertThat(
                differentialAnalyticsSearchService.fetchDifferentialFacetsForSearch(SemanticQuery.create
                        (DIFFERENTIAL_GENE)).entrySet(), not(Matchers.<Map.Entry<String,JsonElement>>empty()));
    }

    @Test
    public void nonExistentGeneReturnsFalse() {
        assertThat(
                baselineAnalyticsSearchService.findFacetsForTreeSearch(SemanticQuery.create(NON_EXISTENT_GENE), SemanticQuery.create(), SpeciesFactory.NULL),
                is(EMPTY_JSON_OBJECT));
        assertThat(
                differentialAnalyticsSearchService.fetchDifferentialResultsForSearch(SemanticQuery.create
                        (NON_EXISTENT_GENE)).get("results").getAsJsonArray().size(),
                is(0));
    }
}
