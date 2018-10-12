package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.services.PopularSpeciesDao;
import uk.ac.ebi.atlas.species.services.PopularSpeciesInfo;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Named
public class ScxaPopularSpeciesDao extends PopularSpeciesDao {

    private static final String SELECT_SPECIES_WITH_EXPERIMENT_COUNT_SINGLE_CELL =
            "SELECT species, COUNT(species) AS count FROM scxa_experiment "
                    + "WHERE private=FALSE "
                    + "GROUP BY species ";

    @Inject
    public ScxaPopularSpeciesDao(JdbcTemplate jdbcTemplate, SpeciesFactory speciesFactory) {
        super(jdbcTemplate, speciesFactory);
    }

    @Override
    @Transactional(readOnly = true)
    public ImmutableList<PopularSpeciesInfo> getExperimentCountBySpecies() {
        return jdbcTemplate.query(SELECT_SPECIES_WITH_EXPERIMENT_COUNT_SINGLE_CELL, (ResultSet resultSet) -> {
            Map<String, Long> result = new HashMap<>();
            while (resultSet.next()) {
                // Normalise species name
                String species = speciesFactory.create(resultSet.getString("species")).getReferenceName();
                long count = resultSet.getLong("count");

                result.merge(species, count, Long::sum);
            }

            return result.entrySet().stream()
                    .map(entry -> {
                        Species species = speciesFactory.create(entry.getKey());
                        return PopularSpeciesInfo.create(
                                species.getReferenceName(),
                                species.getKingdom(),
                                entry.getValue());
                    })
                    .collect(ImmutableList.toImmutableList());
        });
    }
}
