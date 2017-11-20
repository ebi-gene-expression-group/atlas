package uk.ac.ebi.atlas.solr.analytics.differential;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;

import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class DifferentialAnalyticsSearchServiceIT {

    static final SemanticQuery EMPTY_QUERY = SemanticQuery.create();

    @Inject
    SpeciesFactory speciesFactory;

    @Inject
    DifferentialAnalyticsSearchService subject;

    private SemanticQuery query = SemanticQuery.create("zinc finger");
    private SemanticQuery condition = SemanticQuery.create("pish");
    private String species = "oryza sativa";


    @Test
    public void fetchDifferentialFacetsForSearch() {
        JsonObject result = subject.fetchFacets(query, EMPTY_QUERY);
        assertAboutFacets(result);
    }

    @Test
    public void fetchDifferentialResultsForSearch() {
        JsonObject result = subject.fetchResults(query, EMPTY_QUERY);
        assertAboutResults(result);
    }

    @Test
    public void fetchDifferentialFacetsForQuery1() {
        JsonObject result = subject.fetchFacets(query, EMPTY_QUERY);
        assertAboutFacets(result);
    }

    @Test
    public void fetchDifferentialFacetsForQuery3() {
        JsonObject result = subject.fetchFacets(query,condition, speciesFactory.create(species));
        assertAboutFacets(result);
    }

    @Test
    public void fetchDifferentialResultsForQuery1() {
        JsonObject result = subject.fetchResults(query, EMPTY_QUERY);
        assertAboutResults(result);
    }
    @Test
    public void fetchDifferentialResultsForQuery() {
        JsonObject result = subject.fetchResults(query, condition, speciesFactory.create(species));
        assertAboutResults(result);
    }

    private void assertAboutFacets(JsonObject result){
        assertThat(result.entrySet().size(), greaterThan(0));
        for(Map.Entry<String,?> e: result.entrySet()){
            assertThat(e.getKey(),
                    isOneOf("kingdom", "species", "experimentType", "factors", "numReplicates","regulation"));
        }
    }

    private void assertAboutResults(JsonObject result){
        assertTrue(result.has("results"));
        assertThat(result.get("results").getAsJsonArray().size(), greaterThan(0));
        for(JsonElement e: result.get("results").getAsJsonArray()){
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
