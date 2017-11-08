package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkState;

@Named
public class ExperimentDAO {

    private static final String DELETE_EXPERIMENT = "DELETE FROM experiment WHERE accession = ?";

    private static final String UPDATE_EXPERIMENT = "UPDATE experiment SET private = ? where accession = ?";

    private static final String SELECT_EXPERIMENT_AS_ADMIN_GIVEN_ONLY_ACCESSION = "SELECT * FROM EXPERIMENT LEFT OUTER JOIN  EXPERIMENT_ORGANISM on EXPERIMENT_ORGANISM.EXPERIMENT=EXPERIMENT.ACCESSION WHERE accession = ?";

    private static final String SELECT_PUBLIC_EXPERIMENTS_BY_EXPERIMENT_TYPE = "SELECT accession " +
            "FROM public_experiment WHERE type IN(:experimentTypes)";

    private static final String SELECT_EXPERIMENT_BY_ACCESSION_AND_ACCESS_KEY = "SELECT * FROM experiment LEFT OUTER JOIN  EXPERIMENT_ORGANISM on EXPERIMENT_ORGANISM.EXPERIMENT=EXPERIMENT.ACCESSION  WHERE accession = ? AND (private = 'F' OR access_key = ?)";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Inject
    public ExperimentDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return All imported experiments, independently from their public and private status
     */
    public List<ExperimentDTO> getAllExperimentsAsAdmin() {

        String query = "SELECT * FROM EXPERIMENT\n" +
                "LEFT OUTER JOIN  EXPERIMENT_ORGANISM on EXPERIMENT_ORGANISM.EXPERIMENT=EXPERIMENT.ACCESSION";
        return jdbcTemplate.query(query, new ExperimentDTOResultSetExtractor());
    }

    public Set<String> findPublicExperimentAccessions(ExperimentType... experimentTypes) {

        Set<String> experimentTypeNames = Sets.newHashSet();
        for (ExperimentType experimentType : experimentTypes) {
            experimentTypeNames.add(experimentType.name());
        }

        return Sets.newHashSet(namedParameterJdbcTemplate.queryForList(SELECT_PUBLIC_EXPERIMENTS_BY_EXPERIMENT_TYPE, new MapSqlParameterSource("experimentTypes", experimentTypeNames), String.class));
    }

    public UUID addExperiment(ExperimentDTO experimentDTO, Optional<String> accessKey) {
        try {
            UUID accessKeyUUID = addExperimentRow(experimentDTO, accessKey);
            addExperimentSpeciesRows(experimentDTO);
            return accessKeyUUID;

        } catch (DuplicateKeyException e) {
            throw new IllegalStateException("Experiment with experimentAccession " + experimentDTO.getExperimentAccession() + " has been already imported.", e);
        }
    }

    private static final String INSERT_NEW_EXPERIMENT = "INSERT INTO experiment " +
            "(accession, type, private, access_key, pubmed_ids, title) VALUES (?, ?, ?, ?, ?, ?)";

    private UUID addExperimentRow(ExperimentDTO experimentDTO, Optional<String> accessKey) {
        UUID accessKeyUUID = accessKey.isPresent() ? UUID.fromString(accessKey.get()) : UUID.randomUUID();


        jdbcTemplate.update(INSERT_NEW_EXPERIMENT, experimentDTO.getExperimentAccession(),
                experimentDTO.getExperimentType().name(), isPrivateAsString(experimentDTO.isPrivate()),
                accessKeyUUID.toString(), Joiner.on(", ").join(experimentDTO.getPubmedIds()), experimentDTO.getTitle());
        return accessKeyUUID;
    }

    private static final String INSERT_EXPERIMENT_SPECIE = "INSERT INTO EXPERIMENT_ORGANISM (EXPERIMENT, ORGANISM) values (?, ?)";

    private void addExperimentSpeciesRows(ExperimentDTO experimentDTO) {
        jdbcTemplate.update(INSERT_EXPERIMENT_SPECIE,  experimentDTO.getExperimentAccession(), experimentDTO.getSpecies());
    }

    public void deleteExperiment(String experimentAccession) {

        int deletedRecordsCount = jdbcTemplate.update(DELETE_EXPERIMENT, experimentAccession);
        checkState(deletedRecordsCount == 1 , "Experiment not found for accession " + experimentAccession);
    }

    public ExperimentDTO findExperiment(String experimentAccession, String accessKey) {
        return getSingleExperiment(
                jdbcTemplate.query(SELECT_EXPERIMENT_BY_ACCESSION_AND_ACCESS_KEY, new ExperimentDTOResultSetExtractor(), experimentAccession, accessKey),
                experimentAccession
        );


    }

    public ExperimentDTO getExperimentAsAdmin(String experimentAccession) {
        return getSingleExperiment(
                jdbcTemplate.query(SELECT_EXPERIMENT_AS_ADMIN_GIVEN_ONLY_ACCESSION, new ExperimentDTOResultSetExtractor(), experimentAccession),
                experimentAccession
        );
    }

    public void setExperimentPrivacyStatus(String experimentAccession, boolean isPrivate) {

        int recordsCount = jdbcTemplate.update(UPDATE_EXPERIMENT, isPrivateAsString(isPrivate), experimentAccession);
        checkState(recordsCount == 1 , "Experiment not found for accession " + experimentAccession );

    }

    private String isPrivateAsString(boolean isPrivate) {
        return isPrivate ? "T" : "F";
    }

    private ExperimentDTO getSingleExperiment(List<ExperimentDTO> experimentDTOs, String accession) {
        if (experimentDTOs.size() == 1) {
            return experimentDTOs.get(0);
        }

        if (experimentDTOs.size() == 0) {
            throw new ResourceNotFoundException("Experiment: " + accession + " not found");
        }
        throw new IncorrectResultSizeDataAccessException(experimentDTOs.size());

    }

    public Integer countExperiments() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM EXPERIMENT", Integer.class);
    }
}