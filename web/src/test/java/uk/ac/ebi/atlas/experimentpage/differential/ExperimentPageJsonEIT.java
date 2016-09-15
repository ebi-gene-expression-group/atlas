package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import static org.junit.Assert.assertTrue;

public class ExperimentPageJsonEIT {


    JsonObject getExperimentHeaderSummary(String experimentAccession) {
       return new EndPoint("/gxa/json/experiments/"+experimentAccession).getJsonResponse();
    }

    void assertAboutBaselinePayload(JsonObject payload){
        assertTrue(payload.has("columnHeaders"));
        for(JsonElement e: payload.get("columnHeaders").getAsJsonArray()){
            JsonObject columnHeader = e.getAsJsonObject();
            assertTrue(columnHeader.has("factorValue"));
            assertTrue(columnHeader.has("assayGroupSummary"));
        }
    }

    void assertAboutDifferentialPayload(JsonObject payload){
        assertTrue(payload.has("columnHeaders"));
        for(JsonElement e: payload.get("columnHeaders").getAsJsonArray()){
            JsonObject columnHeader = e.getAsJsonObject();
            assertTrue(columnHeader.has("displayName"));
            assertTrue(columnHeader.has("contrastSummary"));
        }
    }

    @Test
    public void testBaselineRnaSeq(){
        assertAboutBaselinePayload(getExperimentHeaderSummary("E-MTAB-513"));
    }
    @Test
    public void testBaselineProteomics(){
        assertAboutBaselinePayload(getExperimentHeaderSummary("E-PROT-1"));
    }
    @Test
    public void testDifferentialRnaSeq(){
        assertAboutDifferentialPayload(getExperimentHeaderSummary("E-GEOD-54705"));
    }
    @Test
    public void testDifferentialMicroarray(){
        assertAboutDifferentialPayload(getExperimentHeaderSummary("E-GEOD-57907"));
    }
}
