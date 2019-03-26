package uk.ac.ebi.atlas.home;

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
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GxaSpeciesSummaryDaoIT {
    @Inject
    private SpeciesFactory speciesFactory;

    @Inject
    private GxaSpeciesSummaryDao subject;

    @Inject
    private DataSource dataSource;

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
    public void returnsPopularSpeciesWithExperimentCounts() {
        assertThat(subject.getExperimentCountBySpecies())
                .isNotEmpty()
                .allMatch(species -> species.getBaselineExperiments() > 0 || species.getDifferentialExperiments() > 0)
                .allSatisfy(species -> {
                    assertThat(species.getTotalExperiments()).isGreaterThan(0);
                    assertThat(speciesFactory.create(species.getSpecies()).isUnknown()).isFalse();
                    assertThat(speciesFactory.create(species.getSpecies()).getKingdom())
                            .isEqualToIgnoringCase(species.getKingdom());
                });
    }
}
