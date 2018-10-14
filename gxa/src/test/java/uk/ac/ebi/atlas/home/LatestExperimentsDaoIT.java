package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import javax.sql.DataSource;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LatestExperimentsDaoIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private LatestExperimentsDao subject;

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

    @Test
    void experimentsCount() {
        assertThat(
                subject.fetchPublicExperimentsCount(ImmutableSet.copyOf(ExperimentType.values())),
                is(greaterThan(10L)));

        assertThat(
                subject.fetchPublicExperimentsCount(ImmutableSet.of()),
                is(subject.fetchPublicExperimentsCount(ImmutableSet.copyOf(ExperimentType.values()))));

        assertThat(
                subject.fetchPublicExperimentsCount(
                        ImmutableSet.of(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)),
                is(lessThan(subject.fetchPublicExperimentsCount(ImmutableSet.copyOf(ExperimentType.values())))));

    }

    @Test
    void experimentAccessions() {
        assertThat(
                subject.fetchLatestExperimentAccessions(ImmutableSet.copyOf(ExperimentType.values())),
                hasSize(lessThanOrEqualTo(LatestExperimentsDao.LIMIT)));
    }
}
