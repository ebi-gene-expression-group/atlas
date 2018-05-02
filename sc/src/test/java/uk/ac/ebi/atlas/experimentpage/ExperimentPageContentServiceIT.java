package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableSet;
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
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class ExperimentPageContentServiceIT {
    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private ExperimentFileLocationService experimentFileLocationService;

    @Inject
    private DataFileHub dataFileHub;

    @Inject
    private TsnePlotSettingsService tsnePlotSettingsService;

    private ExperimentPageContentService subject;

    @Before
    public void setUp() {
        this.subject = new ExperimentPageContentService(experimentFileLocationService, dataFileHub, tsnePlotSettingsService);
    }

    @Test
    public void getValidExperimentDesignJson() {
        // TODO replace empty experiment design table with mock table
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        JsonObject result = this.subject.getExperimentDesignAsJson(experimentAccession, new JsonObject(), "");

        assertThat(result.has("table"), is(true));
        assertThat(result.has("downloadUrl"), is(true));
    }

    @Test
    public void getValidAnalysisMethodsJson() {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        JsonArray result = this.subject.getAnalysisMethodsAsJson(experimentAccession);

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
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        JsonArray result = this.subject.getDownloadsAsJson(experimentAccession, "");

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
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        JsonObject result = this.subject.getTsnePlotDataAsJson(experimentAccession);

        assertThat(result.has("suggesterEndpoint"), is(true));
        assertThat(result.get("suggesterEndpoint").getAsString(), is("json/suggestions"));

        assertThat(result.has("ks"), is(true));
        assertThat(
                ImmutableSet.copyOf(result.get("ks").getAsJsonArray()).stream()
                        .map(JsonElement::getAsInt)
                        .collect(toSet()),
                containsInAnyOrder(
                        jdbcTestUtils.fetchKsFromCellClusters(experimentAccession).toArray(new Integer[0])));

        assertThat(result.has("perplexities"), is(true));
        assertThat(result.get("perplexities").getAsJsonArray().size(), is(greaterThanOrEqualTo(1)));

        assertThat(result.has("units"), is(true));
        assertThat(result.get("units").getAsJsonArray().size(), is(greaterThanOrEqualTo(1)));
    }

}
