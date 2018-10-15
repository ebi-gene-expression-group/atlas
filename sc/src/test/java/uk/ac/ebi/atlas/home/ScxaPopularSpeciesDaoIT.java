package uk.ac.ebi.atlas.home;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.services.PopularSpeciesInfo;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class})
public class ScxaPopularSpeciesDaoIT {

    @Inject
    private JdbcTemplate jdbcTemplate;
    @Inject
    private SpeciesFactory speciesFactory;

    private ScxaPopularSpeciesDao subject;

    @BeforeEach
    void setUp() {
        subject = new ScxaPopularSpeciesDao(jdbcTemplate, speciesFactory);
    }

    @Test
    void returnsListOfSpecies() {
        assertThat(subject.getExperimentCountBySpecies())
                .isNotEmpty()
                .extracting(PopularSpeciesInfo::baselineExperiments, PopularSpeciesInfo::differentialExperiments)
                .containsOnly(Tuple.tuple(0L, 0L));
    }
}
