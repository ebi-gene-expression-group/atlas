package uk.ac.ebi.atlas.experimentpage;

import com.sun.management.UnixOperatingSystemMXBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.tsne.TSnePlotServiceDao;

import javax.inject.Inject;
import java.io.UncheckedIOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TsnePlotSettingsServiceIT {
    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private DataFileHub dataFileHub;

    @Inject
    private IdfParser idfParser;

    @Inject
    private TSnePlotServiceDao tSnePlotServiceDao;

    private TsnePlotSettingsService subject;

    @BeforeEach
    void setUp() {
        this.subject = new TsnePlotSettingsService(dataFileHub, idfParser, tSnePlotServiceDao);
    }


    @Test
    void getClustersForValidAccession() {
        List<Integer> result = subject.getAvailableClusters(jdbcTestUtils.fetchRandomSingleCellExperimentAccession());

        assertThat(result)
                .isNotEmpty()
                .doesNotHaveDuplicates();
    }

    @Test()
    void getClustersForInvalidAccessionThrowsException() {
        assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> subject.getAvailableClusters("FOO"));
    }

    @Test
    void getPerplexitiesForValidAccession() {
        List<Integer> result =
                subject.getAvailablePerplexities(jdbcTestUtils.fetchRandomSingleCellExperimentAccession());

        assertThat(result)
                .isNotEmpty()
                .doesNotHaveDuplicates();
    }

    // This is not a good test: the JVM may do things in the background we’re not aware of, or manage sockets or TCP
    // connections differently depending on the OS or other environment-specific factors, and can end up having more
    // open files than expected. However, because the clusters TSV file is accessed as a field without a getter, we
    // can’t mock the following call sequence and verify that TsvStreamer::close has been called:
    // dataFileHub.getSingleCellExperimentFiles(experimentAccession).clustersTsv.get().get()
    // The +1 magic number accounts for open DB connections in the build environment, in my laptop it’s +4.
    // This is the next best thing I could come up with... sorry! :(
    @ParameterizedTest
    @MethodSource("randomSingleCellExperimentAccessionProvider")
    void filesClosed(String experimentAccession) {
        long fileDescriptorsOpenBefore = getOpenFileCount();
        subject.getAvailableClusters(experimentAccession);
        subject.getExpectedClusters(experimentAccession);
        long fileDescriptorsOpenAfter = getOpenFileCount();

        assertThat(fileDescriptorsOpenAfter)
                .isEqualTo(fileDescriptorsOpenBefore);
    }

    // https://stackoverflow.com/questions/16360720/how-to-find-out-number-of-files-currently-open-by-java-application
    private long getOpenFileCount() {
        OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
        if (os instanceof UnixOperatingSystemMXBean) {
            return ((UnixOperatingSystemMXBean) os).getOpenFileDescriptorCount();
        } else {
            return -1;
        }
    }

    private Stream<String> randomSingleCellExperimentAccessionProvider() {
        return Stream.of(jdbcTestUtils.fetchRandomSingleCellExperimentAccession());
    }
}
