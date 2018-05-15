package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
class ExperimentPageJsonWIT {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @ParameterizedTest
    @MethodSource("experimentAccesionWithTypeProvider")
    void allExperimentTypesHaveColumnHeaders(String accession, ExperimentType type) throws Exception {
        StringBuilder sb = new StringBuilder();

        this.mockMvc.perform(get("/json/experiments/" + accession).param("type", type.getParent().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(mvcResult -> sb.append(mvcResult.getResponse().getContentAsString()));

        JsonObject jsonObject = new Gson().fromJson(sb.toString(), JsonObject.class);

        if (type.isBaseline()) {
            assertThat(jsonObject.get("columnHeaders").getAsJsonArray())
                    .allMatch(
                            jsonElement ->
                                    jsonElement.getAsJsonObject().has("factorValue") &&
                                    jsonElement.getAsJsonObject().has("assayGroupSummary"));
        } else if (type.isDifferential()) {
            assertThat(jsonObject.get("columnHeaders").getAsJsonArray())
                    .allMatch(
                            jsonElement ->
                                    jsonElement.getAsJsonObject().has("displayName") &&
                                    jsonElement.getAsJsonObject().has("contrastSummary"));
        }

        // We don’t do organs/anatomical systems in the experiment page
        assertThat(jsonObject.get("columnGroupings").getAsJsonArray()).isEmpty();
    }

    private static Stream<Arguments> experimentAccesionWithTypeProvider() {
        return Stream.of(
                Arguments.of("E-MTAB-513", ExperimentType.RNASEQ_MRNA_BASELINE),
                Arguments.of("E-MTAB-3028", ExperimentType.RNASEQ_MRNA_BASELINE),
                Arguments.of("E-PROT-1", ExperimentType.PROTEOMICS_BASELINE),
                Arguments.of("E-GEOD-54705", ExperimentType.RNASEQ_MRNA_DIFFERENTIAL),
                Arguments.of("E-GEOD-57907", ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL));
    }
}
