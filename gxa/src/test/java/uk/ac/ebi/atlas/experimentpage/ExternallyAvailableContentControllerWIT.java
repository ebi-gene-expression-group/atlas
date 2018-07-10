package uk.ac.ebi.atlas.experimentpage;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
class ExternallyAvailableContentControllerWIT {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternallyAvailableContentControllerWIT.class);

    @Inject
    private ExpressionAtlasExperimentTrader experimentTrader;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    private String endPointForExperiment(String accession, ExternallyAvailableContent.ContentType contentType){
        return MessageFormat.format("/json/experiments/{0}/resources/{1}", accession, contentType);
    }

    private void testAllResourcesAreNonemptyAndContainValidLinks(String accession,
                                                                 ExternallyAvailableContent.ContentType contentType,
                                                                 boolean expectNonEmpty) throws Exception {
        StringBuilder sb = new StringBuilder();
        this.mockMvc.perform(get(endPointForExperiment(accession, contentType)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(mvcResult -> sb.append(mvcResult.getResponse().getContentAsString()));

        List<String> urls = JsonPath.parse(sb.toString()).read("$..url");
        if (expectNonEmpty) {
            assertThat(urls).isNotEmpty();
        }

        for (String url : urls) {
             if (!url.contains("www.ebi.ac.uk")) {
                LOGGER.info(url);
                this.mockMvc.perform(get("/" + url)).andExpect(status().isOk());
            }
        }
    }

    @Test
    void shouldReturnSomeResourcesForEachExperiment() throws Exception {
        for(String accession : experimentTrader.getPublicExperimentAccessions(
                ExperimentType.RNASEQ_MRNA_BASELINE, ExperimentType.PROTEOMICS_BASELINE,
                ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, ExperimentType.MICROARRAY_ANY)){
            testAllResourcesAreNonemptyAndContainValidLinks(accession, ExternallyAvailableContent.ContentType.DATA, true);
            testAllResourcesAreNonemptyAndContainValidLinks(accession, ExternallyAvailableContent.ContentType.SUPPLEMENTARY_INFORMATION, true);
        }
        for(String accession : experimentTrader.getPublicExperimentAccessions(
                ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, ExperimentType.MICROARRAY_ANY)){
            testAllResourcesAreNonemptyAndContainValidLinks(accession, ExternallyAvailableContent.ContentType.PLOTS, false);
        }
    }

}