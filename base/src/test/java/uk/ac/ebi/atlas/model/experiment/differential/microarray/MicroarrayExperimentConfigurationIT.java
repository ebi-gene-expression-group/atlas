package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import org.junit.jupiter.api.AfterAll;
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
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_ANY;

@Transactional(transactionManager = "txManager")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MicroarrayExperimentConfigurationIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcUtils jdbcUtils;

    @Inject
    private Path dataFilesPath;

    private MicroarrayExperimentConfiguration subject;

    @BeforeAll
    void beforeAllTests() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/experiment-fixture.sql"),
                new ClassPathResource("fixtures/scxa_experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void afterAllTests() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("fixtures/experiment-delete.sql"),
                new ClassPathResource("fixtures/scxa_experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @ParameterizedTest
    @MethodSource("microArrayExperimentAccessionProvider")
    void testGetArrayDesignNames(String experimentAccession) {
        subject =
                new ConfigurationTrader(new DataFileHub(dataFilesPath.resolve("gxa")))
                        .getMicroarrayExperimentConfiguration(experimentAccession);

        assertThat(subject.getArrayDesignAccessions())
                .isNotEmpty();
    }

    @ParameterizedTest
    @MethodSource("microArrayExperimentAccessionProvider")
    void testGetContrasts(String experimentAccession) {
        subject =
                new ConfigurationTrader(new DataFileHub(dataFilesPath.resolve("gxa")))
                        .getMicroarrayExperimentConfiguration(experimentAccession);

        assertThat(subject.getContrasts())
                .isNotEmpty()
                .allSatisfy(contrast -> assertThat(contrast.getReferenceAssayGroup().assaysAnalyzedForThisDataColumn()).isNotEmpty())
                .allSatisfy(contrast -> assertThat(contrast.getTestAssayGroup().assaysAnalyzedForThisDataColumn()).isNotEmpty())
                .extracting("id", "displayName")
                .doesNotContainNull();
    }

    private Stream<String> microArrayExperimentAccessionProvider() {
        List<ExperimentType> microarrayExperimentTypes =
                Arrays.stream(ExperimentType.values())
                        .filter(experimentType -> experimentType.getParent() == MICROARRAY_ANY)
                        .filter(experimentType -> experimentType != MICROARRAY_ANY)
                        .collect(toList());

        Collections.shuffle(microarrayExperimentTypes);

        return Stream.of(jdbcUtils.fetchRandomExpressionAtlasExperimentAccession(microarrayExperimentTypes.get(0)));
    }
}
