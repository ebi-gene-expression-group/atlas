package uk.ac.ebi.atlas.experimentimport;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class ExperimentDao {
    protected final JdbcTemplate jdbcTemplate;
    protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ExperimentDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public abstract List<ExperimentDTO> getAllExperimentsAsAdmin();
    public abstract int countExperiments();
    public abstract Set<String> findPublicExperimentAccessions(ExperimentType... experimentTypes);
    public abstract ExperimentDTO findExperiment(String experimentAccession, String accessKey);
    public abstract ExperimentDTO getExperimentAsAdmin(String experimentAccession);
    public abstract void addExperiment(ExperimentDTO experimentDto, UUID accessKeyUuid);
    public abstract void setExperimentPrivacyStatus(String experimentAccession, boolean isPrivate);
    public abstract void deleteExperiment(String experimentAccession);

    protected void checkExperimentFound(boolean conditionThatIndicatesExperimentWasFound, String accession) {
        if (!conditionThatIndicatesExperimentWasFound) {
            throw new ResourceNotFoundException("Experiment with accession:" + accession + " not found");
        }
    }
}