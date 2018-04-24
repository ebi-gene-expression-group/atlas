package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.JdbcUtils;
import uk.ac.ebi.atlas.resource.DataFileHubFactory;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClustersStreamerFactoryIT {
    @Inject
    private JdbcUtils jdbcUtils;

    @Inject
    private DataFileHubFactory dataFileHubFactory;

    @Inject
    ClustersStreamerFactory subject;

    @BeforeEach
    void setUp() {
        subject = new ClustersStreamerFactory(dataFileHubFactory.getScxaDataFileHub());
    }

    @ParameterizedTest
    @MethodSource("singleCellExperimentsProvider")
    void testblah(String experimentAccession) {
        // TODO This is a very dumb test, and I couldn’t think of anything better, which makes me think that the design
        // TODO of streamer factories is flawed. I think we should collapse both classes into a loader.
        assertThat(subject.create(experimentAccession)).isInstanceOf(ClustersStreamer.class);
    }

    private Iterable<String> singleCellExperimentsProvider() {
        return jdbcUtils.getSingleCellExperimentAccessions();
    }
}