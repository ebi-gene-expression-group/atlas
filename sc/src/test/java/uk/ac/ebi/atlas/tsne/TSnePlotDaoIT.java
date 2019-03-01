package uk.ac.ebi.atlas.tsne;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TSnePlotDaoIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private Path dataFilesPath;

    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private TSnePlotDao subject;

    public ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

    @BeforeAll
    void populateDatabaseTables() {
        populator.setScripts(
                new ClassPathResource("fixtures/scxa_experiment-fixture.sql"),
                new ClassPathResource("fixtures/scxa_tsne-fixture.sql"),
                new ClassPathResource("fixtures/scxa_cell_clusters-fixture.sql"),
                new ClassPathResource("fixtures/scxa_analytics-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        populator.setScripts(
                new ClassPathResource("fixtures/scxa_experiment-delete.sql"),
                new ClassPathResource("fixtures/scxa_tsne-delete.sql"),
                new ClassPathResource("fixtures/scxa_cell_clusters-delete.sql"),
                new ClassPathResource("fixtures/scxa_analytics-delete.sql"));
        populator.execute(dataSource);
    }

    @ParameterizedTest
    @MethodSource("randomExperimentAccessionAndPerplexityProvider")
    void testExpression(String experimentAccession, int perplexity) {
        String geneId = jdbcTestUtils.fetchRandomGeneFromSingleCellExperiment(experimentAccession);

        assertThat(subject.fetchTSnePlotWithExpression(experimentAccession, perplexity, geneId))
                .isNotEmpty()
                .doesNotHaveDuplicates()
                .allMatch(tSnePointDto -> tSnePointDto.expressionLevel() >= 0.0)
                .extracting("name")
                .isSubsetOf(fetchCellIds(experimentAccession));
    }

    @ParameterizedTest
    @MethodSource("randomExperimentAccessionKAndPerplexityProvider")
    void testClustersForK(String experimentAccession, int k, int perplexity) {
        assertThat(subject.fetchTSnePlotWithClusters(experimentAccession, perplexity, k))
                .isNotEmpty()
                .doesNotHaveDuplicates()
                .allMatch(tSnePointDto -> tSnePointDto.clusterId() <= k)
                .extracting("name")
                .isSubsetOf(fetchCellIds(experimentAccession));
    }

    @ParameterizedTest
    @MethodSource("randomExperimentAccessionAndPerplexityProvider")
    void testClustersForPerplexity(String experimentAccession, int perplexity) {
        assertThat(subject.fetchTSnePlotForPerplexity(experimentAccession, perplexity))
                .isNotEmpty()
                .doesNotHaveDuplicates()
                .extracting("name")
                .isSubsetOf(fetchCellIds(experimentAccession));
    }

    @ParameterizedTest
    @MethodSource("randomExperimentAccessionProvider")
    void testPerplexities(String experimentAccession) {
        assertThat(subject.fetchPerplexities(experimentAccession))
                .isNotEmpty()
                .doesNotHaveDuplicates();
    }

    //In this test, we test the count of cells. To make a comprehensive test we count the lines of the local file to match the return result by querying in the fixture.
    //If the fixture is a partition of the full dataset, then it will fail, so we load a full test dataset.
    @ParameterizedTest
    @MethodSource("randomExperimentAccessionProvider")
    void testNumberOfCellsByExperimentAccession(String experimentAccession) {
        cleanDatabaseTables();
        populator.setScripts(new ClassPathResource("fixtures/scxa_tsne-full.sql"));
        populator.execute(dataSource);
        Map<Integer, AtlasResource<TsvStreamer>> resource = new DataFileHub(dataFilesPath.resolve("scxa"))
                .getSingleCellExperimentFiles(experimentAccession).tSnePlotTsvs;
        Map.Entry<Integer, AtlasResource<TsvStreamer>> firstFile = resource.entrySet().iterator().next();
        Stream<String[]> fileContent = firstFile.getValue().get().get();
        Integer fileContentLines = Math.toIntExact(fileContent.count());
        Integer numberOfcells = subject.fetchNumberOfCellsByExperimentAccession(experimentAccession);
        assertThat(numberOfcells)
                .isEqualTo(fileContentLines-1);
        cleanDatabaseTables();
        populateDatabaseTables();
    }

    private static final String SELECT_CELL_IDS_STATEMENT =
            "SELECT DISTINCT(cell_id) FROM scxa_analytics WHERE experiment_accession=?";
    private List<String> fetchCellIds(String experimentAccession) {
        return jdbcTemplate.queryForList(SELECT_CELL_IDS_STATEMENT, String.class, experimentAccession);
    }

    private Stream<String> randomExperimentAccessionProvider() {
        return Stream.of(jdbcTestUtils.fetchRandomSingleCellExperimentAccession());
    }

    private Stream<Arguments> randomExperimentAccessionAndPerplexityProvider() {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        int perplexity = jdbcTestUtils.fetchRandomPerplexityFromExperimentTSne(experimentAccession);

        return Stream.of(Arguments.of(experimentAccession, perplexity));
    }

    private Stream<Arguments> randomExperimentAccessionKAndPerplexityProvider() {
        String experimentAccession = jdbcTestUtils.fetchRandomSingleCellExperimentAccession();
        int k = jdbcTestUtils.fetchRandomKFromCellClusters(experimentAccession);
        int perplexity = jdbcTestUtils.fetchRandomPerplexityFromExperimentTSne(experimentAccession);

        return Stream.of(Arguments.of(experimentAccession, k, perplexity));
    }
}
