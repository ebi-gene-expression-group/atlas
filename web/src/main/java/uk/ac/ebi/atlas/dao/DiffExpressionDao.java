package uk.ac.ebi.atlas.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import uk.ac.ebi.atlas.model.ContrastTrader;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Named
@Scope("singleton")
public class DiffExpressionDao {

    private JdbcTemplate jdbcTemplate;

    private ContrastTrader contrastTrader;

    public static final int RESULT_SIZE = 50;

    @Inject
    public DiffExpressionDao(@Qualifier("dataSourceOracle") DataSource dataSource, ContrastTrader contrastTrader) {
        this.contrastTrader = contrastTrader;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<DifferentialBioentityExpression> getExpressions() {
        String query = "SELECT IDENTIFIER, DESIGNELEMENT, NAME, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT  \n" +
                "FROM VW_DIFFANALYTICS\n" +
                "where rownum < ? order by PVAL";
        List<DifferentialBioentityExpression> result = jdbcTemplate.query(query, new RowMapper<DifferentialBioentityExpression>() {

            @Override
            public DifferentialBioentityExpression mapRow(ResultSet rs, int rowNum) throws SQLException {
                return null;
            }
        }, RESULT_SIZE);

        return result;
    }

}
