package uk.ac.ebi.atlas.landingpage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringJUnitJupiterWebConfig(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
// The meta annotation above includes @ExtendWith(SpringExtension.class), @WebAppConfiguration and
// @ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"}) but it
// confuses IDEA and fails to detect the component scan beans (although the tests run fine). We can wait until we move
// to Spring 5.
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@TestInstance(Lifecycle.PER_CLASS)  // @BeforeAll and @MethodSource need not be static :)
class JsonLandingCardControllerWIT {
    private static final String URL = "/json/landingpage/{experimentAccession}";

    @Inject
    private JdbcUtils jdbcTestUtils;

    private MockMvc mockMvc;

    @BeforeAll
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @ParameterizedTest
    @MethodSource("publicExperimentsProvider")
    void validExperimentAccession(String experimentAccession) throws Exception {
        mockMvc.perform(get(URL, experimentAccession))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.[*].iconType").exists())
                .andExpect(jsonPath("$.[*].iconSrc").exists())
                .andExpect(jsonPath("$.[*].iconDescription").exists())
                .andExpect(jsonPath("$.[*].content").exists())
                .andExpect(jsonPath("$.[*].content.[*].text").exists())
                .andExpect(jsonPath("$.[*].content.[*].url").exists());
    }

    @Test
    void invalidExperimentAccession() throws Exception {
        mockMvc.perform(get(URL, "FOO"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private Iterable<String> publicExperimentsProvider() {
        return jdbcTestUtils.getPublicSingleCellExperimentAccessions();
    }
}
