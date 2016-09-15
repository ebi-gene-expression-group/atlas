package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
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
