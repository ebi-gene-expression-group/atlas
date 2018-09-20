package uk.ac.ebi.atlas.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.configuration.JdbcConfig;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional(transactionManager = "txManager")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JdbcConfig.class})
class FeaturedSpeciesDaoIT {
    @Inject
    private JdbcTemplate jdbcTemplate;

    private FeaturedSpeciesDao subject;

    @BeforeEach
    void setUp() {
        subject = new FeaturedSpeciesDao(jdbcTemplate);
    }

    @Test
    void whenNoExperimentsAreLoadedResultsAreEmpty() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "scxa_experiment");
        assertThat(subject.fetchSpeciesSortedByExperimentCount()).isEmpty();
    }

    @Test
    @Sql("scxa_experiment_fixture.sql")
    void sortsSpeciesNamesByNumberOfExperiments() {
        assertThat(subject.fetchSpeciesSortedByExperimentCount())
                .containsExactly("Mus musculus", "Homo sapiens");
    }
}
