package uk.ac.ebi.atlas.geneannotation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

@Named
@Scope("singleton")
public class BioEntityNameDao {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public BioEntityNameDao(@Qualifier("dataSource") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void count() {
        String query = "select count(*) from bioentity_name";
        int count = jdbcTemplate.queryForObject(query, Integer.class);
        System.out.println("count = " + count);
    }
}
