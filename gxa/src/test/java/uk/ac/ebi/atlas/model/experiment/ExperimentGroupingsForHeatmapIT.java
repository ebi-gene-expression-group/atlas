package uk.ac.ebi.atlas.model.experiment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.RNASEQ_MRNA_BASELINE;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExperimentGroupingsForHeatmapIT {
    private static final int NUMBER_OF_EXPERIMENTS_TO_TEST = 5;

    @Inject
    private DataSource dataSource;

    @Inject
    private JdbcUtils jdbcUtils;

    @Inject
    private ExperimentTrader experimentTrader;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @ParameterizedTest
    @MethodSource("baselineExperimentAccessionsProvider")
    void outputInFineFormatForExperiment(String accession) {
        Experiment<DescribesDataColumns> experiment = experimentTrader.getPublicExperiment(accession);

        List<String> allDescriptorIds = experiment.getDataColumnDescriptors().stream()
                .map(DescribesDataColumns::getId)
                .collect(Collectors.toList());

        JsonArray result = experiment.groupingsForHeatmap();

        assertThat(result.size(), greaterThan(0));

        for (JsonElement element: result) {
            assertTrue(element.getAsJsonObject().has("name"));
            assertTrue(element.getAsJsonObject().has("selected"));
            assertThat(element.getAsJsonObject().get("groupings").getAsJsonArray().size(), greaterThan(0));
            for (JsonElement grouping: element.getAsJsonObject().get("groupings").getAsJsonArray()) {
                assertThat(grouping.getAsJsonArray().size(), is(2));
                assertThat(grouping.getAsJsonArray().get(1).getAsJsonArray().size(), greaterThan(0));
                for (JsonElement groupingValue : grouping.getAsJsonArray().get(1).getAsJsonArray()) {
                    assertTrue(allDescriptorIds.contains(groupingValue.getAsString()));
                }
            }
        }
    }

    private Iterable<String> baselineExperimentAccessionsProvider() {
        Set<String> baselineExperimentAccessions = new HashSet<>(NUMBER_OF_EXPERIMENTS_TO_TEST);

        while (baselineExperimentAccessions.size() < NUMBER_OF_EXPERIMENTS_TO_TEST) {
            baselineExperimentAccessions.add(jdbcUtils.fetchRandomExpressionAtlasExperimentAccession(RNASEQ_MRNA_BASELINE));
        }

        return baselineExperimentAccessions;
    }
}
