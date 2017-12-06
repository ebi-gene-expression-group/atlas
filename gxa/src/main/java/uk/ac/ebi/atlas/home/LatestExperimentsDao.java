package uk.ac.ebi.atlas.home;

import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class LatestExperimentsDao {
    private static final String SELECT_PUBLIC_ACCESSIONS = "SELECT accession FROM experiment WHERE private='F' ";
    private static final String IN_DESCENDING_ORDER_BY_DATE = " ORDER BY last_update DESC LIMIT 5";
    private static final String EXPERIMENT_COUNT = "SELECT COUNT(*) FROM experiment WHERE private='F' ";

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public LatestExperimentsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String buildExperimentTypeConditions(Set<ExperimentType> experimentTypes) {
        if (experimentTypes.isEmpty()) {
            return "";
        }

        return String.format(
                "AND (%s)",
                experimentTypes.stream()
                        .map(experimentType -> String.format("type='%s'", experimentType.name()))
                        .collect(Collectors.joining(" OR ")));
    }

    public List<String> fetchLatestExperimentAccessions(Set<ExperimentType> experimentTypes) {
        return jdbcTemplate.queryForList(
                SELECT_PUBLIC_ACCESSIONS +
                buildExperimentTypeConditions(experimentTypes) +
                IN_DESCENDING_ORDER_BY_DATE,
                String.class);
    }

    public long fetchPublicExperimentsCount(Set<ExperimentType> experimentTypes) {
        return jdbcTemplate.queryForObject(
                EXPERIMENT_COUNT +
                buildExperimentTypeConditions(experimentTypes),
                Long.class);
    }
}
