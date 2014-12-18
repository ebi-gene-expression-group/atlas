package uk.ac.ebi.atlas.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by barrera on 04/08/2014.
 *
 * Retrieve the list of shortened experiment species (ie: the first two species words only) so
 * we can match against bioentity species (which are always shortened) during search (differential in particular)
 */
@Named
@Scope("singleton")
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
