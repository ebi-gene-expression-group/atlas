package uk.ac.ebi.atlas.experimentpage;

import com.sun.management.UnixOperatingSystemMXBean;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.markergenes.MarkerGenesDao;
import uk.ac.ebi.atlas.markergenes.MarkerGenesDaoIT;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.tsne.TSnePlotDao;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.UncheckedIOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TsnePlotSettingsServiceIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private DataFileHub dataFileHub;

    @Inject
    private IdfParser idfParser;

    @Inject
    private TSnePlotDao tSnePlotDao;

    @Inject
    private MarkerGenesDao markerGenesDao;

    private TsnePlotSettingsService subject;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-fixture.sql"),
                new ClassPathResource("fixtures/scxa_tsne-fixture.sql"),
                new ClassPathResource("fixtures/scxa_cell_clusters-fixture.sql"),
                new ClassPathResource("fixtures/scxa_analytics-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-delete.sql"),
                new ClassPathResource("fixtures/scxa_tsne-delete.sql"),
                new ClassPathResource("fixtures/scxa_cell_clusters-delete.sql"),
                new ClassPathResource("fixtures/scxa_analytics-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        this.subject = new TsnePlotSettingsService(dataFileHub, idfParser, tSnePlotDao, markerGenesDao);
    }

    @Test
    void getClustersForValidAccession() {
        List<Integer> result = subject.getAvailableKs(jdbcTestUtils.fetchRandomSingleCellExperimentAccession());

        assertThat(result)
                .isNotEmpty()
                .doesNotHaveDuplicates();
    }

    @Test()
    void getClustersForInvalidAccessionThrowsException() {
        assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> subject.getAvailableKs("FOO"));
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
        subject.getAvailableKs(experimentAccession);
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
