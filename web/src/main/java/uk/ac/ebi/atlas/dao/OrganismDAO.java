package uk.ac.ebi.atlas.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by barrera on 04/08/2014.
 *
 * Retrieve the list of species/organisms from the DB
 */
@Named
@Scope("singleton")
public class OrganismDAO {

    private static final String SELECT_ORGANISMS = "SELECT DISTINCT ORGANISM FROM EXPERIMENT_ORGANISM" +
            " ORDER BY ORGANISM";

    private JdbcTemplate jdbcTemplate;

    @Inject
    public OrganismDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getOrganisms() {
        return jdbcTemplate.queryForList(SELECT_ORGANISMS, String.class);
    }

}
