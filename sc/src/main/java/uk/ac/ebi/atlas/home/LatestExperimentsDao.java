package uk.ac.ebi.atlas.home;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class LatestExperimentsDao {
    private static final String PUBLIC_ACCESSIONS_IN_DESCENDING_ORDER_BY_DATE =
            "SELECT accession FROM scxa_public_experiment ORDER BY last_update DESC LIMIT 5";
    private static final String PUBLIC_EXPERIMENTS_COUNT = "SELECT COUNT(*) FROM scxa_public_experiment";

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public LatestExperimentsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> fetchLatestExperimentAccessions() {
        return jdbcTemplate.queryForList(PUBLIC_ACCESSIONS_IN_DESCENDING_ORDER_BY_DATE, String.class);
    }

    public long fetchPublicExperimentsCount() {
        return jdbcTemplate.queryForObject(PUBLIC_EXPERIMENTS_COUNT, Long.class);
    }

}
