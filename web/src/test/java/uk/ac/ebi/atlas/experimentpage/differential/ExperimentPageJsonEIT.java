package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.gson.JsonObject;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import static org.junit.Assert.assertTrue;

public class ExperimentPageJsonEIT {


    JsonObject getExperimentHeaderSummary(String experimentAccession) {
       return new EndPoint("/gxa/json/experiments/"+experimentAccession).getJsonResponse();
    }

    void assertAboutPayload(JsonObject payload){
        assertTrue(payload.has("experiment"));
        assertTrue(payload.get("experiment").getAsJsonObject().has("headerSummary"));
    }

    @Test
    public void testBaselineRnaSeq(){
        assertAboutPayload(getExperimentHeaderSummary("E-MTAB-513"));
    }
    @Test
    public void testBaselineProteomics(){
        assertAboutPayload(getExperimentHeaderSummary("E-PROT-1"));
    }
    @Test
    public void testDifferentialRnaSeq(){
        assertAboutPayload(getExperimentHeaderSummary("E-GEOD-54705"));
    }
    @Test
    public void testDifferentialMicroarray(){
        assertAboutPayload(getExperimentHeaderSummary("E-GEOD-57907"));
    }
}
