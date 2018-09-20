package uk.ac.ebi.atlas.experimentimport.idf;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional(transactionManager = "txManager")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IdfParserIT {
    @Inject
    private Path dataFilesPath;

    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcUtils jdbcUtils;

    @BeforeAll
    void beforeAllTests() {
        // Ideally we’d like to run the fixtures declaratively:
        // @SqlConfig(dataSource = "embeddedDataSource", transactionManager = "embeddedTxMgr")
        // @Sql({"/fixtures/experiment-fixture.sql", "/fixtures/scxa_experiment-fixture.sql"})
        //
        // Unfortunately the scripts are not executed before the argument providers. With @BeforeAll they are.

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/experiment-fixture.sql"),
                new ClassPathResource("fixtures/scxa_experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @ParameterizedTest
    @MethodSource("singleCellExperimentsProvider")
    void testParserForSingleCell(String experimentAccession) {
        IdfParser idfParser = new IdfParser(new DataFileHub(dataFilesPath.resolve("scxa")));
        IdfParserOutput result = idfParser.parse(experimentAccession);

        assertThat(result.getExpectedClusters()).isGreaterThanOrEqualTo(0);
        assertThat(result.getTitle()).isNotEmpty();
        assertThat(result.getExperimentDescription()).isNotEmpty();
        assertThat(result.getPublications()).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("expressionAtlasExperimentsProvider")
    void testParserForExpressionAtlas(String experimentAccession) {
        IdfParser idfParser = new IdfParser(new DataFileHub(dataFilesPath.resolve("gxa")));

        IdfParserOutput result = idfParser.parse(experimentAccession);

        assertThat(result.getExpectedClusters()).isEqualTo(0);
        assertThat(result.getTitle()).isNotEmpty();
        assertThat(result.getExperimentDescription()).isNotEmpty();
        assertThat(result.getPublications()).isNotNull();
    }

    private Iterable<String> singleCellExperimentsProvider() {
        return jdbcUtils.getPublicSingleCellExperimentAccessions();
    }

    private Iterable<String> expressionAtlasExperimentsProvider() {
        return jdbcUtils.getAllExpressionAtlasExperimentAccessions();
    }
}
