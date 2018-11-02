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
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TSnePlotServiceDaoIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private TSnePlotServiceDao subject;

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

    @ParameterizedTest
    @MethodSource("randomExperimentAccessionProvider")
    void testNumberOfCellsByExperimentAccession(String experimentAccession) {
        assertThat(subject.fetchCellNumberByExperimentAccession(experimentAccession))
                .isInstanceOfAny(Integer.class);
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
