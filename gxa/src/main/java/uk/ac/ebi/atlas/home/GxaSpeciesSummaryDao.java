package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Named
public class GxaSpeciesSummaryDao extends SpeciesSummaryDao {

    protected static final String SELECT_SPECIES_WITH_EXPERIMENT_TYPE_COUNT_BULK =
            "SELECT species, type, COUNT(species) c " +
                    "FROM experiment " +
                    "WHERE private=FALSE GROUP BY type, species";

    @Inject
    public GxaSpeciesSummaryDao(JdbcTemplate jdbcTemplate, SpeciesFactory speciesFactory) {
        super(jdbcTemplate, speciesFactory);
    }

    public ImmutableList<SpeciesSummary> getExperimentCountBySpecies() {
        return jdbcTemplate.query(SELECT_SPECIES_WITH_EXPERIMENT_TYPE_COUNT_BULK, (ResultSet resultSet) -> {
            Map<String, Pair<Long, Long>> result = new HashMap<>();

            while (resultSet.next()) {
                String species = speciesFactory.create(resultSet.getString("species")).getReferenceName();
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
                        return SpeciesSummary.create(
                                speciesName,
                                kingdom,
                                baselineExperimentsCount,
                                differentialExperimentsCount);
                    })
                    .collect(ImmutableList.toImmutableList());
        });
    }
}
