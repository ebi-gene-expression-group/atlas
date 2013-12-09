package uk.ac.ebi.atlas.integration.ebisearch;

import org.springframework.jdbc.core.RowMapper;

import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;


@Named
public class ExperimentDescriptionRowMapper implements RowMapper<ExperimentDescription> {

    @Override
    public ExperimentDescription mapRow(ResultSet rs, int rowNum) throws SQLException {
        String accession = rs.getString("accession");
        String description = rs.getString("title");

        return new ExperimentDescription(accession, description);
    }
}
