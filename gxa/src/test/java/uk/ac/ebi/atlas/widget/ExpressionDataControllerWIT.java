package uk.ac.ebi.atlas.widget;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.TestConfig;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.ac.ebi.atlas.widget.BaselineAndDifferentialAnalyticsServiceIT.BASELINE_GENE;
import static uk.ac.ebi.atlas.widget.BaselineAndDifferentialAnalyticsServiceIT.DIFFERENTIAL_GENE;
import static uk.ac.ebi.atlas.widget.BaselineAndDifferentialAnalyticsServiceIT.NON_EXISTENT_GENE;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
class ExpressionDataControllerWIT {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    void geneExpressedInBaselineAndDifferentialExperimentsReturnsTrue() throws Exception {
        this.mockMvc
                .perform(get("/json/expressionData").param("geneId", BASELINE_GENE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$." + BASELINE_GENE, is(true)));
    }

    @Test
    void geneExpressedInDifferentialExperimentsOnlyReturnsFalse() throws Exception {
        this.mockMvc
                .perform(get("/json/expressionData").param("geneId", DIFFERENTIAL_GENE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$." + DIFFERENTIAL_GENE, is(false)));
    }

    @Test
    void nonExistentGeneReturnsFalse() throws Exception {
        this.mockMvc
                .perform(get("/json/expressionData").param("geneId", NON_EXISTENT_GENE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$." + NON_EXISTENT_GENE, is(false)));
    }
}
