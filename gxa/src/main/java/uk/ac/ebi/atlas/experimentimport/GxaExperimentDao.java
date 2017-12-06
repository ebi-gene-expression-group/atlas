package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.Sets;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
public class GxaExperimentDao extends ExperimentDao {
    // Create
    private static final String INSERT_NEW_EXPERIMENT =
            "INSERT INTO experiment " +
            "(accession, type, private, access_key, pubmed_ids, title) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_EXPERIMENT_SPECIES =
            "INSERT INTO experiment_organism (experiment, organism) values (?, ?)";
    // Read
    private static final String SELECT_EXPERIMENT_AS_ADMIN_BY_ACCESSION =
            "SELECT * FROM experiment " +
            "LEFT OUTER JOIN  experiment_organism on experiment_organism.experiment=experiment.accession " +
            "WHERE accession=?";
    private static final String SELECT_EXPERIMENT_BY_ACCESSION_AND_ACCESS_KEY =
            "SELECT * FROM experiment " +
            "LEFT OUTER JOIN  experiment_organism on experiment_organism.experiment=experiment.accession " +
            "WHERE accession=? AND (private='F' OR access_key=?)";
    private static final String SELECT_PUBLIC_EXPERIMENTS_BY_EXPERIMENT_TYPE =
            "SELECT accession FROM public_experiment WHERE type IN(:experimentTypes)";
    private static final String SELECT_ALL_EXPERIMENTS_AS_ADMIN =
            "SELECT * FROM experiment " +
            "LEFT OUTER JOIN  experiment_organism on experiment_organism.experiment=experiment.accession";
    private static final String COUNT_EXPERIMENTS = "SELECT COUNT(*) FROM experiment";
    // Update
    private static final String UPDATE_EXPERIMENT = "UPDATE experiment SET private=? where accession=?";
    // Delete
    private static final String DELETE_EXPERIMENT = "DELETE FROM experiment WHERE accession=?";


    @Inject
    public GxaExperimentDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    // Will fail when trying to add experiment already present in database
    @Override
    public void addExperiment(ExperimentDTO experimentDto, UUID accessKeyUuid) {
        // Add experiment row
        jdbcTemplate.update(
                INSERT_NEW_EXPERIMENT,
                experimentDto.getExperimentAccession(),
                experimentDto.getExperimentType().name(),
                isPrivateAsString(experimentDto.isPrivate()),
                accessKeyUuid.toString(),
                experimentDto.getPubmedIds().stream().collect(Collectors.joining(", ")),
                experimentDto.getTitle());

        // Add species row
        jdbcTemplate.update(
                INSERT_EXPERIMENT_SPECIES,
                experimentDto.getExperimentAccession(),
                experimentDto.getSpecies());
    }

    @Override
    public HashSet<String> findPublicExperimentAccessions(ExperimentType... experimentTypes) {
        Set<String> experimentTypeNames =
                Arrays.stream(experimentTypes)
                        .map(ExperimentType::name)
                        .collect(Collectors.toSet());

        return Sets.newHashSet(
                namedParameterJdbcTemplate.queryForList(
                        SELECT_PUBLIC_EXPERIMENTS_BY_EXPERIMENT_TYPE,
                        new MapSqlParameterSource("experimentTypes", experimentTypeNames),
                        String.class));
    }

    @Override
    public ExperimentDTO findExperiment(String experimentAccession, String accessKey) {
        return getSingleExperiment(
                jdbcTemplate.query(
                        SELECT_EXPERIMENT_BY_ACCESSION_AND_ACCESS_KEY,
                        new GxaExperimentDtoResultSetExtractor(),
                        experimentAccession,
                        accessKey),
                experimentAccession);
    }

    @Override
    public ExperimentDTO getExperimentAsAdmin(String experimentAccession) {
        return getSingleExperiment(
                jdbcTemplate.query(
                        SELECT_EXPERIMENT_AS_ADMIN_BY_ACCESSION,
                        new GxaExperimentDtoResultSetExtractor(),
                        experimentAccession),
                experimentAccession);
    }

    @Override
    public List<ExperimentDTO> getAllExperimentsAsAdmin() {
        return jdbcTemplate.query(SELECT_ALL_EXPERIMENTS_AS_ADMIN, new GxaExperimentDtoResultSetExtractor());
    }

    @Override
    public int countExperiments() {
        return jdbcTemplate.queryForObject(COUNT_EXPERIMENTS, Integer.class);
    }

    @Override
    public void setExperimentPrivacyStatus(String experimentAccession, boolean isPrivate) {
        int recordsCount = jdbcTemplate.update(UPDATE_EXPERIMENT, isPrivateAsString(isPrivate), experimentAccession);
        checkExperimentFound(recordsCount == 1, experimentAccession);
    }

    @Override
    public void deleteExperiment(String experimentAccession) {
        int deletedRecordsCount = jdbcTemplate.update(DELETE_EXPERIMENT, experimentAccession);
        checkExperimentFound(deletedRecordsCount == 1, experimentAccession);
    }

    private String isPrivateAsString(boolean isPrivate) {
        return isPrivate ? "T" : "F";
    }

    private ExperimentDTO getSingleExperiment(List<ExperimentDTO> experimentDtos, String accession) {
        checkExperimentFound(experimentDtos.size() == 1, accession);
        return experimentDtos.get(0);
    }
}