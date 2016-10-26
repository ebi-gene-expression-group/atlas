package uk.ac.ebi.atlas.search.analyticsindex.differential;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Inject;

import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class DifferentialAnalyticsSearchServiceIT {

    @Inject
    DifferentialAnalyticsSearchService subject;

    SemanticQuery query = SemanticQuery.create("zinc finger");

    SemanticQuery condition = SemanticQuery.create("pish");

    String species = "oryza sativa";


    @Test
    public void fetchDifferentialFacetsForSearch() {
        JsonObject result = subject.fetchDifferentialFacetsForSearch(query);
        assertAboutFacets(result);
    }

    @Test
    public void fetchDifferentialResultsForSearch() {
        JsonObject result = subject.fetchDifferentialResultsForSearch(query);
        assertAboutResults(result);
    }

    @Test
    public void fetchDifferentialFacetsForQuery1() {
        JsonObject result = subject.fetchDifferentialFacetsForQuery(query);
        assertAboutFacets(result);
    }

    @Test
    public void fetchDifferentialFacetsForQuery3() {
        JsonObject result = subject.fetchDifferentialFacetsForQuery(query,condition, species);
        assertAboutFacets(result);
    }

    @Test
    public void fetchDifferentialResultsForQuery1() {
        JsonObject result = subject.fetchDifferentialResultsForQuery(query);
        assertAboutResults(result);
    }
    @Test
    public void fetchDifferentialResultsForQuery() {
        JsonObject result = subject.fetchDifferentialResultsForQuery(query, condition, species);
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
            assertTrue(e.getAsJsonObject().has("experimentAccession"));
            assertTrue(e.getAsJsonObject().has("experimentType"));
            assertTrue(e.getAsJsonObject().has("contrastId"));
            assertTrue(e.getAsJsonObject().has("foldChange"));
            assertTrue(e.getAsJsonObject().has("comparison"));
            assertTrue(e.getAsJsonObject().has("experimentName"));
        }
    }
}
