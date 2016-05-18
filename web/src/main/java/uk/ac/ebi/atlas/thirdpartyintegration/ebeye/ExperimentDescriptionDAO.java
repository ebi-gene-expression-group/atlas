
package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.List;

@Named
public class ExperimentDescriptionDAO {

    private final JdbcTemplate jdbcTemplate;

    private final ExperimentDescriptionRowMapper experimentDescriptionRowMapper;

    @Inject
    public ExperimentDescriptionDAO(@Qualifier("dataSourceOracle") DataSource dataSource, ExperimentDescriptionRowMapper experimentDescriptionRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.experimentDescriptionRowMapper = experimentDescriptionRowMapper;
    }

    public List<ExperimentDescription> selectAllPublicExperimentDescriptions() {
        return jdbcTemplate.query("SELECT accession, title FROM experiment WHERE PRIVATE='F'", experimentDescriptionRowMapper);
    }

}