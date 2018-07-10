package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.download.ExperimentFileLocationService;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.utils.EuropePmcClient;

import javax.inject.Inject;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class ExperimentPageContentServiceIT {
    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private ExperimentFileLocationService experimentFileLocationService;

    @Inject
    private DataFileHub dataFileHub;

    @Inject
    private EuropePmcClient europePmcClient;

    @Inject
    private TsnePlotSettingsService tsnePlotSettingsService;

    private ExperimentPageContentService subject;

    @BeforeEach
    public void setUp() {
        this.subject = new ExperimentPageContentService(experimentFileLocationService, dataFileHub, tsnePlotSettingsService, europePmcClient);
    }

    @Test
    public void getValidExperimentDesignJson() {
        // TODO replace empty experiment design table with mock table
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        JsonObject result = this.subject.getExperimentDesign(experimentAccession, new JsonObject(), "");
        assertThat(result.has("table")).isTrue();
        assertThat(result.has("downloadUrl")).isTrue();
    }

    @Test
    public void getValidAnalysisMethodsJson() {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        JsonArray result = this.subject.getAnalysisMethods(experimentAccession);

        // Should have header row and at least one other
        assertThat(result.size()).isGreaterThan(1);

        JsonArray headerRow = result.get(0).getAsJsonArray();

        assertThat(headerRow)
                .hasSize(4)
                .containsExactlyInAnyOrder(new JsonPrimitive("Analysis"), new JsonPrimitive("Software"), new JsonPrimitive("Version"), new JsonPrimitive("Citation"));
    }

    @Test
    public void getValidDownloadsJson() {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        JsonArray result = this.subject.getDownloads(experimentAccession, "");

        assertThat(result).hasSize(2);

        for(JsonElement download : result) {
            JsonObject downloadObject = download.getAsJsonObject();

            assertThat(downloadObject.get("title").getAsString()).isNotEmpty();

            JsonArray downloadFiles = downloadObject.get("files").getAsJsonArray();

            assertThat(downloadFiles).isNotEmpty();

            for(JsonElement file : downloadFiles) {
                JsonObject fileObject = file.getAsJsonObject();

                assertThat(fileObject.has("url")).isTrue();
                assertThat(fileObject.get("url").getAsString()).isNotEmpty();

                assertThat(fileObject.has("type")).isTrue();
                assertThat(fileObject.get("type").getAsString()).isNotEmpty();

                assertThat(fileObject.has("description")).isTrue();
                assertThat(fileObject.get("description").getAsString()).isNotEmpty();

                assertThat(fileObject.has("isDownload")).isTrue();
                assertThat(fileObject.get("isDownload").getAsBoolean()).isTrue();
            }
        }
    }

    @Test
    public void getValidTsnePlotDataJson() {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        JsonObject result = this.subject.getTsnePlotData(experimentAccession);

        assertThat(result.has("suggesterEndpoint")).isTrue();
        assertThat(result.get("suggesterEndpoint").getAsString()).isEqualToIgnoringCase("json/suggestions");

        assertThat(result.has("ks")).isTrue();
        assertThat(
                ImmutableSet.copyOf(result.get("ks").getAsJsonArray()).stream()
                        .map(JsonElement::getAsInt)
                        .collect(toSet()))
                .containsExactlyInAnyOrder(
                        jdbcTestUtils.fetchKsFromCellClusters(experimentAccession).toArray(new Integer[0]));

        if(result.has("selectedK")) {
            assertThat(jdbcTestUtils.fetchKsFromCellClusters(experimentAccession))
                    .contains(result.get("selectedK").getAsInt());
        }

        assertThat(result.has("perplexities")).isTrue();
        assertThat(result.get("perplexities").getAsJsonArray()).isNotEmpty();

        assertThat(result.has("units")).isTrue();
        assertThat(result.get("units").getAsJsonArray()).isNotEmpty();
    }

}
