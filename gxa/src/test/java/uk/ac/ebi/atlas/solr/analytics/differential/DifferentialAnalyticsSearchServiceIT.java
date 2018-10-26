package uk.ac.ebi.atlas.solr.analytics.differential;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DifferentialAnalyticsSearchServiceIT {
    private static final SemanticQuery EMPTY_QUERY = SemanticQuery.create();

    @Inject
    private DataSource dataSource;

    @Inject
    private SpeciesFactory speciesFactory;

    @Inject
    private DifferentialAnalyticsSearchService subject;

    private SemanticQuery query = SemanticQuery.create("zinc finger");
    private SemanticQuery condition = SemanticQuery.create("watering");
    private String species = "oryza sativa";

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @Test
    void fetchDifferentialFacetsForSearch() {
        JsonObject result = subject.fetchFacets(query, EMPTY_QUERY);
        assertAboutFacets(result);
    }

    @Test
    void fetchDifferentialResultsForSearch() {
        JsonObject result = subject.fetchResults(query, EMPTY_QUERY);
        assertAboutResults(result);
    }

    @Test
    void fetchDifferentialFacetsForQuery1() {
        JsonObject result = subject.fetchFacets(query, EMPTY_QUERY);
        assertAboutFacets(result);
    }

    @Test
    void fetchDifferentialFacetsForQuery3() {
        JsonObject result = subject.fetchFacets(query, condition, speciesFactory.create(species));
        assertAboutFacets(result);
    }

    @Test
    void fetchDifferentialResultsForQuery1() {
        JsonObject result = subject.fetchResults(query, EMPTY_QUERY);
        assertAboutResults(result);
    }
    @Test
    void fetchDifferentialResultsForQuery() {
        JsonObject result = subject.fetchResults(query, condition, speciesFactory.create(species));
        assertAboutResults(result);
    }

    private void assertAboutFacets(JsonObject result) {
        assertThat(result.entrySet().size(), greaterThan(0));
        for (Map.Entry<String, ?> e: result.entrySet()) {
            assertThat(e.getKey(),
                    isOneOf("kingdom", "species", "experimentType", "factors", "numReplicates", "regulation"));
        }
    }

    private void assertAboutResults(JsonObject result) {
        assertTrue(result.has("results"));
        assertThat(result.get("results").getAsJsonArray().size(), greaterThan(0));
        for (JsonElement e: result.get("results").getAsJsonArray()) {
            assertTrue(e.getAsJsonObject().has("bioentityIdentifier"));
            assertTrue(e.getAsJsonObject().has("bioentityName"));
            assertTrue(e.getAsJsonObject().has("experimentAccession"));
            assertTrue(e.getAsJsonObject().has("experimentType"));
            assertTrue(e.getAsJsonObject().has("contrastId"));
            assertTrue(e.getAsJsonObject().has("foldChange"));
            assertTrue(e.getAsJsonObject().has("comparison"));
            assertTrue(e.getAsJsonObject().has("experimentName"));
        }
    }
}
