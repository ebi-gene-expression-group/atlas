package uk.ac.ebi.atlas.ebeye;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class ExperimentDescriptionDao {

    private final JdbcTemplate jdbcTemplate;

    private final ExperimentDescriptionRowMapper experimentDescriptionRowMapper;

    @Inject
    public ExperimentDescriptionDao(JdbcTemplate jdbcTemplate,
                                    ExperimentDescriptionRowMapper experimentDescriptionRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.experimentDescriptionRowMapper = experimentDescriptionRowMapper;
    }

    public List<ExperimentDescription> selectAllPublicExperimentDescriptions() {
        return jdbcTemplate.query(
                "SELECT accession, title FROM experiment WHERE PRIVATE=FALSE", experimentDescriptionRowMapper);
    }

}
