package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.resource.DataFileHubFactory;

import javax.inject.Inject;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClustersStreamerIT {
    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private DataFileHubFactory dataFileHubFactory;

    private DataFileHub dataFileHub;

    @BeforeEach
    void setUp() {
        dataFileHub = dataFileHubFactory.getScxaDataFileHub();
    }

    @ParameterizedTest
    @MethodSource("singleCellExperimentsProvider")
    @DisplayName("All k values are read successfully")
    void testReadAllKs(String experimentAccession) {
        ClustersStreamer subject =
                new ClustersStreamer(dataFileHub.getSingleCellExperimentFiles(experimentAccession).clustersTsv);

        assertThat(subject.stream().collect(toList())).extracting("left")
                .containsExactlyInAnyOrder(
                        dataFileHub.getSingleCellExperimentFiles(experimentAccession).clustersTsv.get().get()
                                .skip(1)
                                .map(line -> Integer.parseInt(line[1]))
                                .toArray(Integer[]::new));
    }

    private Iterable<String> singleCellExperimentsProvider() {
        return jdbcTestUtils.getPublicSingleCellExperimentAccessions();
    }
}