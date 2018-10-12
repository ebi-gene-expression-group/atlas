package uk.ac.ebi.atlas.acceptance.rest.tests.DAS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.WebConfig;

import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
class DASFeaturesControllerWIT {
    private static final String[] MODEL_ATTRIBUTE_NAMES =
            new String[] {
                    "geneId", "geneName", "factorValues_ORGANISM_PART", "factorValues_DISEASE",
                    "factorValues_CELL_TYPE", "factorValues_CELL_LINE", "factorValues_COMPOUND",
                    "factorValues_DEVELOPMENTAL_STAGE", "factorValues_INFECT", "factorValues_PHENOTYPE" };

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void featuresURLGeneWithExpressions() throws Exception {
        String geneId = "WBGene00003778";

        this.mockMvc.perform(get("/das/s4/features").param("segment", geneId))
                .andExpect(status().isOk())
                .andExpect(view().name("das-features"))
                .andExpect(model().attribute(
                        "factorValues_COMPOUND",
                        is(not("Not studied or no differential expression found for this gene"))));
    }

    @Test
    void featuresURLGeneWithoutExpressions() throws Exception {
        String geneId = "ENSG00000139618";

        this.mockMvc.perform(get("/das/s4/features").param("segment", geneId))
                .andExpect(status().isOk())
                .andExpect(view().name("das-features"))
                .andExpect(model().attribute(
                        MODEL_ATTRIBUTE_NAMES[ThreadLocalRandom.current().nextInt(2, MODEL_ATTRIBUTE_NAMES.length)],
                        "Not studied or no differential expression found for this gene"));
    }

    @Test
    void featuresWithNonExistantGeneIsLikeGeneWithoutExpression() throws Exception {
        String geneId = "FOOBAR";

        this.mockMvc.perform(get("/das/s4/features").param("segment", geneId))
                .andExpect(status().isOk())
                .andExpect(view().name("das-features"))
                .andExpect(model().attribute(
                        MODEL_ATTRIBUTE_NAMES[ThreadLocalRandom.current().nextInt(2, MODEL_ATTRIBUTE_NAMES.length)],
                        "Not studied or no differential expression found for this gene"));
    }
}
