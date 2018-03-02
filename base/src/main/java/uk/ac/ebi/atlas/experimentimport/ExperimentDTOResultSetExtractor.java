package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class ExperimentDTOResultSetExtractor implements ResultSetExtractor<List<ExperimentDTO>> {
    @Override
    public List<ExperimentDTO> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<String, ExperimentDTO> experimentByAccession = Maps.newHashMap();

        while (resultSet.next()) {
            String experimentAccession = resultSet.getString("accession");

            ExperimentDTO experiment = experimentByAccession.get(experimentAccession);

            if (experiment == null) {
                experiment = createExperimentDTO(resultSet, experimentAccession);
                experimentByAccession.put(experimentAccession, experiment);
            }
        }

        return Lists.newArrayList(experimentByAccession.values());
    }

    protected abstract ExperimentDTO createExperimentDTO(ResultSet resultSet, String experimentAccession) throws SQLException;
}
