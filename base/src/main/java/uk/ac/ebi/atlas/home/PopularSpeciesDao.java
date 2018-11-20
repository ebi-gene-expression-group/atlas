package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableList;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.species.SpeciesFactory;

public abstract class PopularSpeciesDao {
    protected final SpeciesFactory speciesFactory;
    protected final JdbcTemplate jdbcTemplate;

    public PopularSpeciesDao(JdbcTemplate jdbcTemplate, SpeciesFactory speciesFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.speciesFactory = speciesFactory;
    }

    public abstract ImmutableList<PopularSpeciesInfo> getExperimentCountBySpecies();
}
