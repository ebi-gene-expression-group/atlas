package uk.ac.ebi.atlas.search;

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
import uk.ac.ebi.atlas.configuration.WebConfig;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
class AutocompleteControllerWIT {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void supportsMultipleSpecies() throws Exception {
        this.mockMvc
                .perform(get("/json/suggestions").param("species", "Homo sapiens,Mus musculus").param("query", "zinc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$..value", everyItem(containsStringIgnoringCase("zinc"))))
                .andExpect(jsonPath("$..category", everyItem(anyOf(startsWith("ENSG"), startsWith("ENSMUSG")))))
                .andExpect(jsonPath("$..category", hasItem(startsWith("ENSG"))))
                .andExpect(jsonPath("$..category", hasItem(startsWith("ENSMUSG"))));
    }

    @Test
    void atLeastHumanAndMouseAreSuggested() throws Exception {
        this.mockMvc
                .perform(get("/json/suggestions/species"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.allSpecies", hasItems("Homo sapiens", "Mus musculus")));
    }

    @Test
    void hasAsManyTopSpeciesAsSpecified() throws Exception {
        this.mockMvc
                .perform(get("/json/suggestions/species"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.topSpecies", hasSize(AutocompleteController.FEATURED_SPECIES)));
    }

}