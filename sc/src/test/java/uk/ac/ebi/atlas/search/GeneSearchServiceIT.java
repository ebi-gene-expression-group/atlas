package uk.ac.ebi.atlas.search;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.experimentpage.TsnePlotSettingsService;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GeneSearchServiceIT {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneSearchServiceIT.class);

    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private TsnePlotSettingsService tsnePlotSettingsService;

    @Inject
    private GeneSearchService subject;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-fixture.sql"),
                new ClassPathResource("fixtures/scxa_marker_genes-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/scxa_experiment-delete.sql"),
                new ClassPathResource("fixtures/scxa_marker_genes-delete.sql"));
        populator.execute(dataSource);
    }

    @ParameterizedTest
    @MethodSource("experimentAccesionWithoutPreferredKProvider")
    void experimentsWithoutPreferredKReturnASingleProfile(String experimentAccession) {
        String geneId = jdbcTestUtils.fetchRandomMarkerGeneFromSingleCellExperiment(experimentAccession);
        LOGGER.info("Fetching marker gene profile for {}", geneId);
        Map<String, Map<String, Map<Integer, List<Integer>>>> markerGeneProfile = subject.getMarkerGeneProfile(geneId);

        assertThat(markerGeneProfile).hasSize(1);
    }

    private Stream<String> experimentAccesionWithoutPreferredKProvider() {
        return jdbcTestUtils
                .fetchAllSingleCellExperimentAccessions()
                .stream()
                .filter(accession -> !tsnePlotSettingsService.getExpectedClusters(accession).isPresent());
    }
}
