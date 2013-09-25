package uk.ac.ebi.atlas.dao;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.model.baseline.BaselineBioentitiesCount;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BaselineBioentitiesCountRowMapper implements RowMapper<BaselineBioentitiesCount> {

    private ExperimentTrader experimentTrader;

    @Override
    public BaselineBioentitiesCount mapRow(ResultSet rs, int rowNum) throws SQLException {
//        BaselineBioentitiesCount count = new BaselineBioentitiesCount();
        return null;
    }
}
