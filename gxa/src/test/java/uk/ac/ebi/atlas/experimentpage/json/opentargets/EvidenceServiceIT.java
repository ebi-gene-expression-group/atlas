package uk.ac.ebi.atlas.experimentpage.json.opentargets;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;

import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.stream.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.sql.DataSource;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EvidenceServiceIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private ExpressionAtlasExperimentTrader experimentTrader;

    @Inject
    private MicroarrayProfileStreamFactory microarrayProfileStreamFactory;

    @Inject
    private DataFileHub dataFileHub;

    private EvidenceService<MicroarrayExpression, MicroarrayExperiment, MicroarrayRequestContext, MicroarrayProfile>
            subject;

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

    @BeforeEach
    void setUp() {
        subject = new EvidenceService<>(microarrayProfileStreamFactory, dataFileHub, "test");
    }

    @Test
    void organismPartIsIncludedIfAvailable() {
        ImmutableList.Builder<JsonObject> listBuilder = ImmutableList.builder();
        MicroarrayExperiment experiment = (MicroarrayExperiment) experimentTrader.getPublicExperiment("E-GEOD-43049");
        subject.evidenceForExperiment(
                experiment, contrast -> {
                    MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
                    requestPreferences.setFoldChangeCutoff(0.0);
                    requestPreferences.setCutoff(1.0);
                    requestPreferences.setHeatmapMatrixSize(1000);
                    requestPreferences.setSelectedColumnIds(ImmutableSet.of(contrast.getId()));
                    return new MicroarrayRequestContext(requestPreferences, experiment); },
                listBuilder::add);

        for (JsonObject jsonObject : listBuilder.build()) {
            assertThat(
                    jsonObject.get("evidence").getAsJsonObject().get("organism_part").getAsString(),
                    is(not(emptyString())));
        }
    }
}
