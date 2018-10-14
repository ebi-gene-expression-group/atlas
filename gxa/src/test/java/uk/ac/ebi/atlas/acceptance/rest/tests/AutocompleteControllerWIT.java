package uk.ac.ebi.atlas.acceptance.rest.tests;

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
import uk.ac.ebi.atlas.search.suggester.SuggesterService;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.oneOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
class AutocompleteControllerWIT {
    // TODO Get first chars of random genes/symbols/properties from analytics and see that they show up as suggestions

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void verifyHeader() throws Exception {
        this.mockMvc
                .perform(get("/json/suggestions").param("query", "ASP").param("species", "homo_sapiens"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void unmatchedQueryReturnsEmptyArray() throws Exception {
        this.mockMvc
                .perform(get("/json/suggestions").param("query", "foobar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void suggestionsWithSpeciesAreNotHighlighted() throws Exception {
        this.mockMvc
                .perform(get("/json/suggestions").param("query", "ASP").param("species", "homo_sapiens"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$..value", everyItem(containsStringIgnoringCase("asp"))))
                .andExpect(jsonPath("$[0].category", is(oneOf("ensgene", "symbol"))));
    }

    @Test
    void suggestionsWithoutSpeciesAreHighlighted() throws Exception {
        this.mockMvc
                .perform(get("/json/suggestions").param("query", "ASP"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$..value", everyItem(containsStringIgnoringCase("<b>asp</b>"))))
                .andExpect(jsonPath("$[0].category", is(oneOf("ensgene", "symbol"))));
    }

    @Test
    void limitNumberOfSuggestions() throws Exception {
        this.mockMvc
                .perform(get("/json/suggestions").param("query", "ASP"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(
                        jsonPath(
                                "$",
                                hasSize(allOf(
                                        greaterThan(0),
                                        lessThanOrEqualTo(SuggesterService.DEFAULT_MAX_NUMBER_OF_SUGGESTIONS)))))
                .andExpect(jsonPath("$..value", everyItem(containsStringIgnoringCase("<b>asp</b>"))))
                .andExpect(jsonPath("$[0].category", is(oneOf("ensgene", "symbol"))));
    }
}
