
package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ExperimentDTOResultSetExtractor implements ResultSetExtractor<List<ExperimentDTO>> {
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

    private ExperimentDTO createExperimentDTO(ResultSet resultSet, String experimentAccession) throws SQLException {
        ExperimentType experimentType = ExperimentType.valueOf(resultSet.getString("type"));
        String species = resultSet.getString("organism");
        Date lastUpdate = resultSet.getTimestamp("last_update");
        boolean isPrivate = "T".equals(resultSet.getString("private"));
        String accessKeyUUID = resultSet.getString("access_key");
        String title = resultSet.getString("title");

        String pubMedIdsString = resultSet.getString("pubmed_Ids");
        Set<String> pubMedIds = StringUtils.isBlank(pubMedIdsString)? new HashSet<String>() : Sets.newHashSet(Splitter.on(", ").split(pubMedIdsString));

        return new ExperimentDTO(experimentAccession
                , experimentType
                , species
                , pubMedIds
                , title
                , lastUpdate
                , isPrivate
                , accessKeyUUID);
    }
}
