package uk.ac.ebi.atlas.search;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.TestConfig;

import javax.inject.Inject;
import javax.sql.DataSource;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.ID_PROPERTY_NAMES;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AutocompleteControllerWIT {
    @Inject
    private DataSource dataSource;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/scxa_experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/scxa_experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void supportsMultipleSpecies() throws Exception {
        this.mockMvc
                .perform(
                        get("/json/suggestions").param("species", "Homo sapiens,Mus musculus").param("query", "ASPM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$..value", everyItem(containsStringIgnoringCase("ASPM"))))
                .andExpect(jsonPath("$..category", everyItem(anyOf(startsWith("ENSG"), startsWith("ENSMUSG")))))
                .andExpect(jsonPath("$..category", hasItem(startsWith("ENSG"))))
                .andExpect(jsonPath("$..category", hasItem(startsWith("ENSMUSG"))));
    }

    @Test
    void geneIdSuggestions() throws Exception {
        this.mockMvc
                .perform(get("/json/suggestions/gene_ids").param("species", "Homo sapiens").param("query", "asp"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[*].label", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(
                        jsonPath(
                                "$[*].label",
                                everyItem(in(ID_PROPERTY_NAMES.stream().map(p -> p.label).collect(toList())))))
                .andExpect(jsonPath("$[*].options", everyItem(hasSize(greaterThanOrEqualTo(1)))))
                .andExpect(jsonPath("$[*].options.label", everyItem(not(empty()))))
                .andExpect(jsonPath("$[*].options.value", everyItem(not(empty()))));
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
