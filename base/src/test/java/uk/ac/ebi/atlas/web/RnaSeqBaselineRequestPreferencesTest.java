package uk.ac.ebi.atlas.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RnaSeqBaselineRequestPreferencesTest {

    @Test
    public void toJson(){
        assertThat(
                new Gson().toJsonTree(new RnaSeqBaselineRequestPreferences()).getAsJsonObject().has("cutoff"),
                is(true)
        );
    }

}