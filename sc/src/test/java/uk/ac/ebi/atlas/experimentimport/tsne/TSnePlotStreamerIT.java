package uk.ac.ebi.atlas.experimentimport.tsne;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.resource.DataFileHubFactory;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TSnePlotStreamerIT {
    @Inject
    private DataFileHubFactory dataFileHubFactory;

    private DataFileHub dataFileHub;

    @Inject
    private JdbcUtils jdbcTestUtils;

    @BeforeAll
    void setUp() {
        dataFileHub = dataFileHubFactory.getScxaDataFileHub();
    }

    @ParameterizedTest
    @MethodSource("singleCellExperimentsProvider")
    @DisplayName("Experiments have at least the default perplexities and at least one cell")
    void testPerplexitiesAndPlots(String experimentAccession) {
        DataFileHub.SingleCellExperimentFiles files = dataFileHub.getSingleCellExperimentFiles(experimentAccession);
        TSnePlotStreamer subject = new TSnePlotStreamer(files.tSnePlotTsvs);

        assertThat(subject.availablePerplexities())
                .contains(1, 5, 10, 15, 20);

        assertThat(subject.stream(1).collect(toList()))
                .hasSameSizeAs(subject.stream(5).collect(toList()))
                .hasSameSizeAs(subject.stream(10).collect(toList()))
                .hasSameSizeAs(subject.stream(15).collect(toList()))
                .hasSameSizeAs(subject.stream(20).collect(toList()))
                .isNotEmpty();
    }

    private Iterable<String> singleCellExperimentsProvider() {
        return jdbcTestUtils.getPublicSingleCellExperimentAccessions();
    }
}