package uk.ac.ebi.atlas.resource;

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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.TestConfig;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContrastImageControllerWIT {
    private static final String RNASEQ_EXPERIMENT_ACCESSION = "E-MTAB-1913";
    private static final String MICROARRAY_EXPERIMENT_ACCESSION = "E-MEXP-1968";

    private static final String RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE =
            "/external-resources/".concat(RNASEQ_EXPERIMENT_ACCESSION).concat("/{0}/ma-plot.png");
    private static final String MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE =
            "/external-resources/".concat(MICROARRAY_EXPERIMENT_ACCESSION).concat("/{0}/{1}/ma-plot.png");
    private static final String GSEA_PLOT_IMAGE_URL_TEMPLATE =
            "/external-resources/".concat(RNASEQ_EXPERIMENT_ACCESSION).concat("/{0}/gsea_{1}.png");

    @Inject
    private DataSource dataSource;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void responseForGseaPlotsShouldNotBeEmpty() throws Exception {
        responseAssertions(
                this.mockMvc.perform(get(MessageFormat.format(GSEA_PLOT_IMAGE_URL_TEMPLATE, "g7_g8", "go"))));
        responseAssertions(
                this.mockMvc.perform(get(MessageFormat.format(GSEA_PLOT_IMAGE_URL_TEMPLATE, "g7_g8", "interpro"))));
        responseAssertions(
                this.mockMvc.perform(get(MessageFormat.format(GSEA_PLOT_IMAGE_URL_TEMPLATE, "g7_g8", "reactome"))));
    }

    @Test
    void responseForMicroarrayMaPlotImageShouldNotBeEmpty() throws Exception {
        responseAssertions(
                this.mockMvc.perform(
                        get(MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-45", "g7_g3"))));
        responseAssertions(
                this.mockMvc.perform(
                        get(MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-45", "g1_g2"))));
    }

    @Test
    void responseForRnaSeqMaPlotImageShouldNotBeEmpty() throws Exception {
        responseAssertions(
                this.mockMvc.perform(
                        get(MessageFormat.format(RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE, "g1_g5"))));
        responseAssertions(
                this.mockMvc.perform(
                        get(MessageFormat.format(RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE, "g3_g8"))));
        responseAssertions(
                this.mockMvc.perform(
                        get(MessageFormat.format(RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE, "g6_g7"))));
    }

    @Test
    void responseForDifferentContrastsShouldBeDifferent() throws Exception {
        List<byte[]> responseBodies = new ArrayList<>();
        this.mockMvc.perform(get(MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-45", "g7_g3")))
                .andDo(mvcResult -> responseBodies.add(mvcResult.getResponse().getContentAsByteArray()));
        this.mockMvc.perform(get(MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-45", "g1_g2")))
                .andDo(mvcResult -> responseBodies.add(mvcResult.getResponse().getContentAsByteArray()));

        assertThat(responseBodies, everyItem(is(notNullValue())));
        assertThat(responseBodies.get(0).length, is(not(responseBodies.get(1).length)));
    }

    private void responseAssertions(ResultActions resultActions) throws Exception {
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG));
    }

}
