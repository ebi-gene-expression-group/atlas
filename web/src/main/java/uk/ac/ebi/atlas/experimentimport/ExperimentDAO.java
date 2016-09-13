package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

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

    private static final String PING_EXPERIMENT = "SELECT COUNT (1) FROM experiment WHERE accession = ?";

    private static final String SELECT_EXPERIMENT_BY_ACCESSION = "SELECT * FROM EXPERIMENT LEFT OUTER JOIN  EXPERIMENT_ORGANISM on EXPERIMENT_ORGANISM.EXPERIMENT=EXPERIMENT.ACCESSION WHERE accession = ?";

    private static final String SELECT_EXPERIMENTS_BY_ACCESSION = "SELECT * FROM EXPERIMENT LEFT OUTER JOIN  EXPERIMENT_ORGANISM on EXPERIMENT_ORGANISM.EXPERIMENT=EXPERIMENT.ACCESSION WHERE accession IN(:accessions)";

    private static final String SELECT_PUBLIC_EXPERIMENTS_BY_ACCESSION = "SELECT * FROM experiment LEFT OUTER JOIN  EXPERIMENT_ORGANISM on EXPERIMENT_ORGANISM.EXPERIMENT=EXPERIMENT.ACCESSION WHERE accession IN(:accessions) and private = 'F'";

    private static final String SELECT_PUBLIC_EXPERIMENT_BY_ACCESSION = "SELECT * FROM experiment LEFT OUTER JOIN  EXPERIMENT_ORGANISM on EXPERIMENT_ORGANISM.EXPERIMENT=EXPERIMENT.ACCESSION WHERE accession = ? and private = 'F'";

    private static final String SELECT_PUBLIC_EXPERIMENTS_BY_EXPERIMENT_TYPE = "SELECT accession " +
            "FROM public_experiment WHERE type IN(:experimentTypes)";

    private static final String SELECT_EXPERIMENT_BY_ACCESSION_AND_ACCESS_KEY = "SELECT * FROM experiment LEFT OUTER JOIN  EXPERIMENT_ORGANISM on EXPERIMENT_ORGANISM.EXPERIMENT=EXPERIMENT.ACCESSION  WHERE accession = ? AND access_key = ?";

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
    public List<ExperimentDTO> findAllExperiments() {

        String query = "SELECT * FROM EXPERIMENT\n" +
                "LEFT OUTER JOIN  EXPERIMENT_ORGANISM on EXPERIMENT_ORGANISM.EXPERIMENT=EXPERIMENT.ACCESSION";
        return jdbcTemplate.query(query, new ExperimentDTOResultSetExtractor());
    }

    public Set<String> findPublicExperimentAccessions(ExperimentType... experimentTypes) {

        Set<String> experimentTypeNames = Sets.newHashSet();
        for (ExperimentType experimentType : experimentTypes) {
            experimentTypeNames.add(experimentType.name());
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource("experimentTypes", experimentTypeNames);
        List<String> experimentAccessions = namedParameterJdbcTemplate.queryForList(SELECT_PUBLIC_EXPERIMENTS_BY_EXPERIMENT_TYPE, parameters, String.class);

        return Sets.newHashSet(experimentAccessions);
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

        String pubmedIds = Joiner.on(", ").join(experimentDTO.getPubmedIds());

        jdbcTemplate.update(INSERT_NEW_EXPERIMENT, experimentDTO.getExperimentAccession(),
                experimentDTO.getExperimentType().name(), toString(experimentDTO.isPrivate()),
                accessKeyUUID.toString(), pubmedIds, experimentDTO.getTitle());
        return accessKeyUUID;
    }

    private static final String INSERT_EXPERIMENT_SPECIE = "INSERT INTO EXPERIMENT_ORGANISM (EXPERIMENT, ORGANISM) values (?, ?)";

    private void addExperimentSpeciesRows(ExperimentDTO experimentDTO) {
        jdbcTemplate.update(INSERT_EXPERIMENT_SPECIE,  experimentDTO.getExperimentAccession(), experimentDTO.getSpecies());
    }

    public void deleteExperiment(String experimentAccession) {

        int deletedRecordsCount = jdbcTemplate.update(DELETE_EXPERIMENT, experimentAccession);

        if (deletedRecordsCount != 1) {
            throw new IllegalArgumentException("Experiment not found for accession " + experimentAccession);
        }
    }

    public ExperimentDTO findPublicExperiment(String experimentAccession) {
        return findExperiment(experimentAccession, false);
    }

    public ExperimentDTO findExperiment(String experimentAccession, String accessKey) {

        List<ExperimentDTO> experimentDTOs = jdbcTemplate.query(SELECT_EXPERIMENT_BY_ACCESSION_AND_ACCESS_KEY, new ExperimentDTOResultSetExtractor(), experimentAccession, accessKey);

        return getSingleExperiment(experimentDTOs, experimentAccession);


    }

    //TODO: replace this with a one parameter method findExperimentIncludingPrivate
    public ExperimentDTO findExperiment(String experimentAccession, boolean includePrivates) {

        String findExperimentQuery = includePrivates ? SELECT_EXPERIMENT_BY_ACCESSION : SELECT_PUBLIC_EXPERIMENT_BY_ACCESSION;
        List<ExperimentDTO> experimentDTOs = jdbcTemplate.query(findExperimentQuery, new ExperimentDTOResultSetExtractor(), experimentAccession);

        return getSingleExperiment(experimentDTOs, experimentAccession);

    }

    public void updateExperiment(String experimentAccession, boolean isPrivate) {

        int recordsCount = jdbcTemplate.update(UPDATE_EXPERIMENT, toString(isPrivate), experimentAccession);

        if (recordsCount == 0) {
            throw new IllegalArgumentException("Experiment not found for accession " + experimentAccession);
        }

    }

    private String toString(boolean isPrivate) {
        return isPrivate ? "T" : "F";
    }

    public boolean isImported(String experimentAccession) {

        int experimentCount = jdbcTemplate.queryForObject(PING_EXPERIMENT, Integer.class, experimentAccession);

        checkState(experimentCount <= 1, "Multiple experiments with experiment accession " + experimentAccession);

        return experimentCount == 1;

    }

    public List<ExperimentDTO> findExperiments(Set<String> experimentAccessions, boolean includePrivates) {
        try {

            String findExperimentsQuery = includePrivates ? SELECT_EXPERIMENTS_BY_ACCESSION : SELECT_PUBLIC_EXPERIMENTS_BY_ACCESSION;

            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("accessions", experimentAccessions);

            return namedParameterJdbcTemplate.query(findExperimentsQuery, parameters, new ExperimentDTOResultSetExtractor());

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("No experiments found for experiment accession: " + experimentAccessions);
        }
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
        String query = "SELECT COUNT(*) FROM EXPERIMENT";

        return jdbcTemplate.queryForObject(query, Integer.class);
    }
}