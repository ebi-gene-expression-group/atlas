package uk.ac.ebi.atlas.home;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LatestExperimentsDao {
    static final int LIMIT = 8;
    private static final String SELECT_PUBLIC_ACCESSIONS =
            "SELECT accession FROM experiment WHERE private=FALSE ";
    private static final String IN_DESCENDING_ORDER_BY_DATE =
            " ORDER BY last_update DESC LIMIT " + LIMIT;
    private static final String EXPERIMENT_COUNT =
            "SELECT COUNT(*) FROM experiment WHERE private=FALSE ";

    private final JdbcTemplate jdbcTemplate;

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
