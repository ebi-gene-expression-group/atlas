package uk.ac.ebi.atlas.experimentpage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JsonCellMetadataControllerWIT {
    @Inject
    private JdbcUtils jdbcTestUtils;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void validJsonForExistingCellId() throws Exception {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        String cellId = jdbcTestUtils.fetchRandomCellFromExperiment(experimentAccession);

        this.mockMvc
                .perform(get(
                        "/json/experiment/" + experimentAccession + "/cell/" + cellId + "/metadata"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].displayName", isA(String.class)))
                .andExpect(jsonPath("$[0].value", isA(String.class)));
    }

    @Test
    public void emptyJsonForInvalidCellId() throws Exception {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        String cellId = "FOO";

        this.mockMvc
                .perform(get(
                        "/json/experiment/" + experimentAccession + "/cell/" + cellId + "/metadata"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void validJsonForInvalidExperimentAccession() throws Exception {
        String experimentAccession = "FOO";
        String cellId = "BAR";

        this.mockMvc
                .perform(get(
                        "/json/experiment/" + experimentAccession + "/cell/" + cellId + "/metadata")).andDo(print())
                .andExpect(status().is4xxClientError());
    }
}

