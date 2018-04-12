package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.download.ExperimentFileLocationService;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class ExperimentPageContentServiceIT {

    private final String EXPERIMENT_ACCESSION = "E-GEOD-106540";

    @Inject
    private ExperimentFileLocationService experimentFileLocationService;
    @Inject
    private DataFileHub dataFileHub;

    private ExperimentPageContentService subject;

    @Before
    public void setUp() {
        this.subject = new ExperimentPageContentService(experimentFileLocationService, dataFileHub);
    }

    @Test
    public void getValidExperimentDesignJson() {
        // TODO replace empty experiment design table with mock table
        JsonObject result = this.subject.getExperimentDesignAsJson(EXPERIMENT_ACCESSION, new JsonObject(), "");

        assertThat(result.has("table"), is(true));
        assertThat(result.has("downloadUrl"), is(true));
    }

    @Test
    public void getValidAnalysisMethodsJson() {
        JsonArray result = this.subject.getAnalysisMethodsAsJson(EXPERIMENT_ACCESSION);

        // Should have header row and at least one other
        assertThat(result.size(), is(greaterThan(1)));

        JsonArray headerRow = result.get(0).getAsJsonArray();

        assertThat(headerRow.size(), is(4));
        assertThat(headerRow.contains(new JsonPrimitive("Analysis")), is(true));
        assertThat(headerRow.contains(new JsonPrimitive("Software")), is(true));
        assertThat(headerRow.contains(new JsonPrimitive("Version")), is(true));
        assertThat(headerRow.contains(new JsonPrimitive("Citation")), is(true));
    }

    @Test
    public void getValidDownloadsJson() {
        JsonArray result = this.subject.getDownloadsAsJson(EXPERIMENT_ACCESSION, "");

        assertThat(result.size(), is(4));

        for(JsonElement download : result) {
            JsonObject downloadObject = download.getAsJsonObject();

            assertThat(downloadObject.has("url"), is(true));
            assertThat(downloadObject.get("url").getAsString(), is(not(isEmptyString())));

            assertThat(downloadObject.has("type"), is(true));
            assertThat(downloadObject.get("type").getAsString(), is(not(isEmptyString())));

            assertThat(downloadObject.has("description"), is(true));
            assertThat(downloadObject.get("description").getAsString(), is(not(isEmptyString())));

            assertThat(downloadObject.has("isDownload"), is(true));
            assertThat(downloadObject.get("isDownload").getAsBoolean(), is(true));
        }
    }


}
