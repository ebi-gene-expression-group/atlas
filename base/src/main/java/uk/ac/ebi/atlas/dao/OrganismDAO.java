package uk.ac.ebi.atlas.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class OrganismDAO {

    private static final String SELECT_SHORTENED_ORGANISM = "select DISTINCT trim(regexp_replace(ORGANISM, '(\\w+ \\w+).*$','\\1 \\2')) AS ORGANISM_FIRST_2_WORDS FROM EXPERIMENT_ORGANISM " +
                                                            "order by ORGANISM_FIRST_2_WORDS";
    private JdbcTemplate jdbcTemplate;

    @Inject
    public OrganismDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getOrganisms() {
        return jdbcTemplate.queryForList(SELECT_SHORTENED_ORGANISM, String.class);
    }

}
