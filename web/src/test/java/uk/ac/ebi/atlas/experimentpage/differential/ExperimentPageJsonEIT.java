package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ExperimentPageJsonEIT {


    JsonObject getExperimentHeaderSummary(String experimentAccession) {
       return new EndPoint("/gxa/json/experiments/"+experimentAccession).getJsonResponse();
    }

    void assertAboutBaselineColumnHeaders(JsonObject payload){
        assertTrue(payload.has("columnHeaders"));
        assertThat(payload.get("columnHeaders").getAsJsonArray().size(), greaterThan(0));
        for(JsonElement e: payload.get("columnHeaders").getAsJsonArray()){
            JsonObject columnHeader = e.getAsJsonObject();
            assertTrue(columnHeader.has("factorValue"));
            assertTrue(columnHeader.has("assayGroupSummary"));
        }

        assertTrue(payload.has("columnGroupings"));
        for(JsonElement e: payload.get("columnGroupings").getAsJsonArray()){
            JsonObject columnHeader = e.getAsJsonObject();
            assertTrue(columnHeader.has("name"));
            assertTrue(columnHeader.has("groups"));
            for(JsonElement ee: columnHeader.get("groups").getAsJsonArray()){
                JsonObject group = ee.getAsJsonObject();
                assertTrue(group.has("name"));
                assertTrue(group.has("id"));
                assertTrue(group.has("values"));
                for(JsonElement v : group.get("values").getAsJsonArray()){
                    assertTrue("values are ontology ids :"+v.getAsString() , v.getAsString().matches("\\w+_\\d+"));
                }
            }
        }

    }

    void assertAboutDifferentialColumnHeaders(JsonObject payload){
        assertTrue(payload.has("columnHeaders"));
        assertThat(payload.get("columnHeaders").getAsJsonArray().size(), greaterThan(0));
        for(JsonElement e: payload.get("columnHeaders").getAsJsonArray()){
            JsonObject columnHeader = e.getAsJsonObject();
            assertTrue(columnHeader.has("displayName"));
            assertTrue(columnHeader.has("contrastSummary"));
        }
    }

    @Test
    public void testBaselineRnaSeq(){
        assertAboutBaselineColumnHeaders(getExperimentHeaderSummary("E-MTAB-513"));
    }
    @Test
    public void testBaselineProteomics(){
        assertAboutBaselineColumnHeaders(getExperimentHeaderSummary("E-PROT-1"));
    }
    @Test
    public void testDifferentialRnaSeq(){
        assertAboutDifferentialColumnHeaders(getExperimentHeaderSummary("E-GEOD-54705"));
    }
    @Test
    public void testDifferentialMicroarray(){
        assertAboutDifferentialColumnHeaders(getExperimentHeaderSummary("E-GEOD-57907"));
    }
}
