package uk.ac.ebi.atlas.web;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

public class RnaSeqBaselineRequestPreferencesTest {
    @Test
    public void toJson() {
        assertThat(
                GSON.toJsonTree(new RnaSeqBaselineRequestPreferences()).getAsJsonObject().has("cutoff"),
                is(true)
        );
    }
}
