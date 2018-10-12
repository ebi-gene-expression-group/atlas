package uk.ac.ebi.atlas.species.services;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Named
public class PopularSpeciesDao {

    protected static final String SELECT_SPECIES_WITH_EXPERIMENT_TYPE_COUNT_BULK =
            "SELECT experiment_organism.organism, experiment.type, " +
            "count(experiment_organism.organism) AS c " +
            "FROM experiment " +
            "LEFT OUTER JOIN  experiment_organism ON experiment_organism.experiment=experiment.accession " +
            "WHERE private='F' GROUP BY experiment.type, experiment_organism.organism";

    private static final String SELECT_SPECIES_WITH_EXPERIMENT_COUNT_SINGLE_CELL =
            "SELECT species, COUNT(species) AS count FROM scxa_experiment "
                    + "WHERE private=FALSE "
                    + "GROUP BY species ";

    private final SpeciesFactory speciesFactory;
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public PopularSpeciesDao(JdbcTemplate jdbcTemplate, SpeciesFactory speciesFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.speciesFactory = speciesFactory;
    }

    public ImmutableList<PopularSpeciesInfo> getBulkExperimentCountBySpecies() {
        return jdbcTemplate.query(SELECT_SPECIES_WITH_EXPERIMENT_TYPE_COUNT_BULK, (ResultSet resultSet) -> {
            Map<String, Pair<Long, Long>> result = new HashMap<>();

            while (resultSet.next()) {
                String species = speciesFactory.create(resultSet.getString("organism")).getReferenceName();
                ExperimentType experimentType = ExperimentType.valueOf(resultSet.getString("type"));
                long experimentCount = resultSet.getLong("c");

                if (experimentType.isBaseline()) {
                    result.merge(
                            species,
                            Pair.of(experimentCount, 0L),
                            (oldValue, newValue) -> Pair.of(
                                    oldValue.getLeft() + newValue.getLeft(),
                                    oldValue.getRight() + newValue.getRight()));
                } else { //if (experimentType.isDifferential()) {
                    result.merge(
                            species,
                            Pair.of(0L, experimentCount),
                            (oldValue, newValue) -> Pair.of(
                                    oldValue.getLeft() + newValue.getLeft(),
                                    oldValue.getRight() + newValue.getRight()));
                }
            }

            return result.entrySet().stream()
                    .map(entry -> {
                        String speciesName = entry.getKey();
                        String kingdom = speciesFactory.create(speciesName).getKingdom();
                        long baselineExperimentsCount = entry.getValue().getLeft();
                        long differentialExperimentsCount = entry.getValue().getRight();
                        return PopularSpeciesInfo.create(
                                speciesName,
                                kingdom,
                                baselineExperimentsCount,
                                differentialExperimentsCount);
                    })
                    .collect(ImmutableList.toImmutableList());
        });
    }

    @Transactional(readOnly = true)
    public ImmutableList<PopularSpeciesInfo> getSingleCellExperimentCountBySpecies() {
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
