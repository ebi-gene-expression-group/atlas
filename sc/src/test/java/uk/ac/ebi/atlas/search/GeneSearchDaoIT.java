package uk.ac.ebi.atlas.search;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;
import static uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE;
import static uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_ORGANISM_PART;
import static uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_SPECIES;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
class GeneSearchDaoIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Inject
    private SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory;

    @Inject
    private JdbcUtils jdbcTestUtils;

    private GeneSearchDao subject;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-fixture.sql"),
                new ClassPathResource("fixtures/scxa_analytics-fixture.sql"),
                new ClassPathResource("fixtures/scxa_marker_genes-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-delete.sql"),
                new ClassPathResource("fixtures/scxa_analytics-delete.sql"),
                new ClassPathResource("fixtures/scxa_marker_genes-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        subject = new GeneSearchDao(namedParameterJdbcTemplate, solrCloudCollectionProxyFactory);
    }

    @ParameterizedTest
    @MethodSource("randomGeneIdProvider")
    void validGeneIdReturnsAtLeastOneCellId(String geneId) {
        assertThat(subject.fetchCellIds(geneId))
                .isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"ENSMUSG00000032036"})
    void validGeneIdReturnsKAndClusterIds(String geneId) {
        Map<String, Map<Integer, List<Integer>>> result = subject.fetchKAndClusterIds(geneId);

        assertThat(result)
                .containsOnlyKeys("E-GEOD-99058");

        // Only marker genes with probablity <= 0.05 are returned
        assertThat(result.get("E-GEOD-99058"))
                .containsOnly(
                        entry(4, singletonList(2)),
                        entry(8, singletonList(1)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ENSMUSG00000000006"})
    void searchForGeneOverProbabilityThresholdReturnsEmpty(String geneId) {
        assertThat(subject.fetchKAndClusterIds(geneId))
                .isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"FOO"})
    void invalidGeneIdReturnsEmpty(String geneId) {
        assertThat(subject.fetchKAndClusterIds(geneId))
                .isEmpty();
    }

    @ParameterizedTest
    @MethodSource("randomCellIdsProvider")
    void getFacetsForValidCellIds(List<String> cellIds) {
        Map<String, Map<String, List<String>>> result =
                subject.getFacets(
                        cellIds,
                        CHARACTERISTIC_INFERRED_CELL_TYPE, CHARACTERISTIC_ORGANISM_PART, CHARACTERISTIC_SPECIES);

        assertThat(result).isNotEmpty();
    }

    @Test
    void getForEmptyListOfCellIdsReturnsEmpty() {
        Map<String, Map<String, List<String>>> result =
                subject.getFacets(
                        emptyList(),
                        CHARACTERISTIC_INFERRED_CELL_TYPE, CHARACTERISTIC_ORGANISM_PART, CHARACTERISTIC_SPECIES);

        assertThat(result).isEmpty();
    }

    private Stream<String> randomGeneIdProvider() {
        return Stream.of(jdbcTestUtils.fetchRandomGene());
    }

    private Stream<List<String>> randomCellIdsProvider() {
        return Stream.of(jdbcTestUtils.fetchRandomListOfCells(ThreadLocalRandom.current().nextInt(1, 100)));
    }
}
