package uk.ac.ebi.atlas.species.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopularSpeciesDao {

    protected static final String SPECIES_WITH_EXPERIMENT_TYPE_COUNT_QUERY =
            "SELECT experiment_organism.organism, experiment.type, " +
            "count(experiment_organism.organism) AS c " +
            "FROM experiment " +
            "LEFT OUTER JOIN  experiment_organism ON experiment_organism.experiment=experiment.accession " +
            "WHERE private='F' GROUP BY experiment.type, experiment_organism.organism";

    private final SpeciesFactory speciesFactory;
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public PopularSpeciesDao(JdbcTemplate jdbcTemplate, SpeciesFactory speciesFactory){
        this.jdbcTemplate = jdbcTemplate;
        this.speciesFactory = speciesFactory;
    }

    public ImmutableList<PopularSpeciesInfo> popularSpecies() {
        List<Map<String, Object>> results =
                jdbcTemplate.queryForList(SPECIES_WITH_EXPERIMENT_TYPE_COUNT_QUERY);

        HashMap<String, Pair<Integer, Integer>> speciesToExperimentCounts = Maps.newHashMap();
        for (Map<String, Object> resultRow: results) {
            String species = (String) resultRow.get("organism");
            ExperimentType experimentType = ExperimentType.valueOf((String) resultRow.get("type"));
            int experimentCount = (int) resultRow.get("c");

            if (!speciesToExperimentCounts.containsKey(species)) {
                speciesToExperimentCounts.put(species, Pair.of(0, 0));
            }

            Pair<Integer, Integer> experimentCounts = speciesToExperimentCounts.get(species);
            if (experimentType.isBaseline()) {
                speciesToExperimentCounts.put(species, Pair.of(experimentCounts.getLeft() + experimentCount, experimentCounts.getRight()));
            } else { //if (experimentType.isDifferential()) {
                speciesToExperimentCounts.put(species, Pair.of(experimentCounts.getLeft(), experimentCounts.getRight() + experimentCount));
            }
        }

        ImmutableList.Builder<PopularSpeciesInfo> popularSpeciesInfoBuilder = ImmutableList.builder();
        for (Map.Entry<String, Pair<Integer, Integer>> speciesToExperimentCount : speciesToExperimentCounts.entrySet()) {
            String speciesName = speciesToExperimentCount.getKey();
            String kingdom = speciesFactory.create(speciesName).getKingdom();
            int baselineExperimentsCount = speciesToExperimentCount.getValue().getLeft();
            int differentialExperimentsCount = speciesToExperimentCount.getValue().getRight();
            popularSpeciesInfoBuilder.add(
                    PopularSpeciesInfo.create(speciesName, kingdom, baselineExperimentsCount, differentialExperimentsCount)
            );
        }

        return popularSpeciesInfoBuilder.build();
    }

}
