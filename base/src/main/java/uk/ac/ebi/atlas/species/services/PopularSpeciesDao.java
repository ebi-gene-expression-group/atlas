package uk.ac.ebi.atlas.species.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
public class PopularSpeciesDao {

    protected static final String SPECIES_WITH_EXPERIMENT_TYPE_COUNT_QUERY =
            "SELECT species, type, COUNT(species) c " +
            "FROM experiment " +
            "WHERE private=FALSE GROUP BY type, species";

    private final SpeciesFactory speciesFactory;
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public PopularSpeciesDao(JdbcTemplate jdbcTemplate, SpeciesFactory speciesFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.speciesFactory = speciesFactory;
    }

    public ImmutableList<PopularSpeciesInfo> popularSpecies() {
        List<Map<String, Object>> results =
                jdbcTemplate.queryForList(SPECIES_WITH_EXPERIMENT_TYPE_COUNT_QUERY);

        HashMap<String, Pair<Long, Long>> speciesToExperimentCounts = Maps.newHashMap();
        for (Map<String, Object> resultRow : results) {
            String species = speciesFactory.create((String) resultRow.get("species")).getReferenceName();
            ExperimentType experimentType = ExperimentType.valueOf((String) resultRow.get("type"));
            long experimentCount = (long) resultRow.get("c");

            if (!speciesToExperimentCounts.containsKey(species)) {
                speciesToExperimentCounts.put(species, Pair.of(0L, 0L));
            }

            Pair<Long, Long> experimentCounts = speciesToExperimentCounts.get(species);
            if (experimentType.isBaseline()) {
                speciesToExperimentCounts.put(
                        species, Pair.of(experimentCounts.getLeft() + experimentCount, experimentCounts.getRight()));
            } else { //if (experimentType.isDifferential()) {
                speciesToExperimentCounts.put(
                        species, Pair.of(experimentCounts.getLeft(), experimentCounts.getRight() + experimentCount));
            }
        }

        ImmutableList.Builder<PopularSpeciesInfo> popularSpeciesInfoBuilder = ImmutableList.builder();
        for (Map.Entry<String, Pair<Long, Long>> speciesToExperimentCount : speciesToExperimentCounts.entrySet()) {
            String speciesName = speciesToExperimentCount.getKey();
            String kingdom = speciesFactory.create(speciesName).getKingdom();
            long baselineExperimentsCount = speciesToExperimentCount.getValue().getLeft();
            long differentialExperimentsCount = speciesToExperimentCount.getValue().getRight();
            popularSpeciesInfoBuilder.add(
                    PopularSpeciesInfo.create(
                            speciesName, kingdom, baselineExperimentsCount, differentialExperimentsCount)
            );
        }

        return popularSpeciesInfoBuilder.build();
    }

}
