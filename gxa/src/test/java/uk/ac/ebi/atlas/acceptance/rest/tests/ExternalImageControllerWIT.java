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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
class ExternalImageControllerWIT {
    private static final String EXTRA_INFO_EXPERIMENT_ACCESSION = "E-MTAB-2812";
    private static final String RNASEQ_EXPERIMENT_ACCESSION = "E-GEOD-54705";
    private static final String MICROARRAY_EXPERIMENT_ACCESSION = "E-GEOD-34130";

    private static final String EXTRA_INFO_IMAGE_URL =
            "/external-resources/".concat(EXTRA_INFO_EXPERIMENT_ACCESSION).concat("/extra-info.png");
    private static final String RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE =
            "/external-resources/".concat(RNASEQ_EXPERIMENT_ACCESSION).concat("/{0}/ma-plot.png");
    private static final String MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE =
            "/external-resources/".concat(MICROARRAY_EXPERIMENT_ACCESSION).concat("/{0}/{1}/ma-plot.png");
    private static final String GSEA_PLOT_IMAGE_URL_TEMPLATE =
            "/external-resources/".concat(RNASEQ_EXPERIMENT_ACCESSION).concat("/{0}/gsea_{1}.png");

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void responseForExtraInfoImageShouldBeNonEmpty() throws Exception {
        responseAssertions(this.mockMvc.perform(get(EXTRA_INFO_IMAGE_URL)));
    }

    @Test
    void responseForGseaPlotsShouldBeNonEmpty() throws Exception {
        responseAssertions(
                this.mockMvc.perform(get(MessageFormat.format(GSEA_PLOT_IMAGE_URL_TEMPLATE, "g6_g2", "go"))));
        responseAssertions(
                this.mockMvc.perform(get(MessageFormat.format(GSEA_PLOT_IMAGE_URL_TEMPLATE, "g6_g2", "interpro"))));
        responseAssertions(
                this.mockMvc.perform(get(MessageFormat.format(GSEA_PLOT_IMAGE_URL_TEMPLATE, "g6_g2", "reactome"))));
    }

    @Test
    void responseForMicroarrayMaPlotImageShouldBeNonEmpty() throws Exception {
        responseAssertions(
                this.mockMvc.perform(
                        get(MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-2", "g9_g3"))));
        responseAssertions(
                this.mockMvc.perform(
                        get(MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-2", "g2_g1"))));
    }

    @Test
    void responseForRnaSeqMaPlotImageShouldBeNonEmpty() throws Exception {
        responseAssertions(
                this.mockMvc.perform(
                        get(MessageFormat.format(RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE, "g5_g2"))));
        responseAssertions(
                this.mockMvc.perform(
                        get(MessageFormat.format(RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE, "g3_g2"))));
        responseAssertions(
                this.mockMvc.perform(
                        get(MessageFormat.format(RNASEQ_MA_PLOT_IMAGE_URL_TEMPLATE, "g6_g2"))));
    }

    @Test
    void responseForDifferentContrastsShouldBeDifferent() throws Exception {
        List<byte[]> responseBodies = new ArrayList<>();
        this.mockMvc.perform(get(MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-2", "g9_g3")))
                .andDo(mvcResult -> responseBodies.add(mvcResult.getResponse().getContentAsByteArray()));
        this.mockMvc.perform(get(MessageFormat.format(MICROARRAY_MA_PLOT_IMAGE_URL_TEMPLATE, "A-AFFY-2", "g2_g1")))
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
