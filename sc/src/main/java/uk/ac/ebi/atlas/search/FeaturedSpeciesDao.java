package uk.ac.ebi.atlas.search;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Return a list of species names sorted by how many times they appear in experiments

@Component
public class FeaturedSpeciesDao {
    private final JdbcTemplate jdbcTemplate;

    public FeaturedSpeciesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_DISTINCT_SPECIES_IN_PUBLIC_EXPERIMENTS =
            "SELECT n.species FROM " +
                    "(SELECT species, COUNT(species) AS count FROM scxa_experiment " +
                    "WHERE private=FALSE GROUP BY species ORDER BY count DESC) n";

    @Transactional(transactionManager = "txManager", readOnly = true)
    public List<String> fetchSpeciesSortedByExperimentCount() {
        return jdbcTemplate.queryForList(SELECT_DISTINCT_SPECIES_IN_PUBLIC_EXPERIMENTS, String.class);
    }
}
