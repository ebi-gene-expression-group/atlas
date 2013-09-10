package uk.ac.ebi.atlas.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

@Named
@Scope("singleton")
public class DiffExpressionDao {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public DiffExpressionDao(@Qualifier("dataSourceOracle") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
