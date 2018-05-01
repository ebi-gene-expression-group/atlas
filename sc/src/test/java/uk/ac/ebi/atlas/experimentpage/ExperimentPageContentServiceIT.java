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
import uk.ac.ebi.atlas.utils.EuropePmcClient;

import javax.inject.Inject;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
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
    @Inject
    private EuropePmcClient europePmcClient;

    private ExperimentPageContentService subject;

    @Before
    public void setUp() {
        this.subject = new ExperimentPageContentService(experimentFileLocationService, dataFileHub, europePmcClient);
    }

    @Test
    public void getValidExperimentDesignJson() {
        // TODO replace empty experiment design table with mock table
        JsonObject result = this.subject.getExperimentDesign(EXPERIMENT_ACCESSION, new JsonObject(), "");

        assertThat(result.has("table"), is(true));
        assertThat(result.has("downloadUrl"), is(true));
    }

    @Test
    public void getValidAnalysisMethodsJson() {
        JsonArray result = this.subject.getAnalysisMethods(EXPERIMENT_ACCESSION);

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
        JsonArray result = this.subject.getDownloads(EXPERIMENT_ACCESSION, "");

        assertThat(result.size(), is(2));

        for(JsonElement download : result) {
            JsonObject downloadObject = download.getAsJsonObject();

            assertThat(downloadObject.get("title").getAsString(), is(not(isEmptyString())));

            JsonArray downloadFiles = downloadObject.get("files").getAsJsonArray();

            assertThat(downloadFiles.size(), is(greaterThan(0)));

            for(JsonElement file : downloadFiles) {
                JsonObject fileObject = file.getAsJsonObject();

                assertThat(fileObject.has("url"), is(true));
                assertThat(fileObject.get("url").getAsString(), is(not(isEmptyString())));

                assertThat(fileObject.has("type"), is(true));
                assertThat(fileObject.get("type").getAsString(), is(not(isEmptyString())));

                assertThat(fileObject.has("description"), is(true));
                assertThat(fileObject.get("description").getAsString(), is(not(isEmptyString())));

                assertThat(fileObject.has("isDownload"), is(true));
                assertThat(fileObject.get("isDownload").getAsBoolean(), is(true));
            }
        }
    }

    @Test
    public void getValidTsnePlotDataJson() {
        JsonObject result = this.subject.getTsnePlotData();

        assertThat(result.has("suggesterEndpoint"), is(true));
        assertThat(result.get("suggesterEndpoint").getAsString(), is("json/suggestions"));

        assertThat(result.has("ks"), is(true));
        assertThat(result.get("ks").getAsJsonArray().size(), is(greaterThanOrEqualTo(1)));

        assertThat(result.has("perplexities"), is(true));
        assertThat(result.get("perplexities").getAsJsonArray().size(), is(greaterThanOrEqualTo(1)));

        assertThat(result.has("units"), is(true));
        assertThat(result.get("units").getAsJsonArray().size(), is(greaterThanOrEqualTo(1)));
    }

}
