package uk.ac.ebi.atlas.experimentpage.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
class JsonExperimentDispatcherWIT {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @ParameterizedTest
    @MethodSource("experimentAccesionWithTypeProvider")
    void forwardExperimentToType(String accession, ExperimentType type) throws Exception {
        this.mockMvc.perform(get("/json/experiments/" + accession))
                .andExpect(forwardedUrl("/json/experiments/" + accession + "?type=" + type.getParent().toString()));

    }

    private static Stream<Arguments> experimentAccesionWithTypeProvider() {
        return Stream.of(
                Arguments.of("E-MTAB-513", ExperimentType.RNASEQ_MRNA_BASELINE),
                Arguments.of("E-MTAB-3028", ExperimentType.RNASEQ_MRNA_BASELINE),
                Arguments.of("E-PROT-1", ExperimentType.PROTEOMICS_BASELINE),
                Arguments.of("E-GEOD-54705", ExperimentType.RNASEQ_MRNA_DIFFERENTIAL),
                Arguments.of("E-GEOD-57907", ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL));
    }
}
