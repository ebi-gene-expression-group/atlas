package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScxaExperimentDtoResultSetExtractor extends ExperimentDTOResultSetExtractor {
    @Override
    protected ExperimentDTO createExperimentDTO(ResultSet resultSet, String experimentAccession) throws SQLException {
        ExperimentType experimentType = ExperimentType.valueOf(resultSet.getString("type"));
        String species = resultSet.getString("species");
        Date lastUpdate = resultSet.getTimestamp("last_update");
        boolean isPrivate = resultSet.getBoolean("private");
        String accessKeyUUID = resultSet.getString("access_key");
        String title = StringUtils.isEmpty(resultSet.getString("title")) ? "" : resultSet.getString("title");

        String pubMedIdsString = resultSet.getString("pubmed_Ids");
        Set<String> pubMedIds = StringUtils.isBlank(pubMedIdsString) ?
                new HashSet<>() :
                Sets.newHashSet(Splitter.on(", ").split(pubMedIdsString));

        return new ExperimentDTO(
                experimentAccession, experimentType, species, pubMedIds, title, lastUpdate, isPrivate, accessKeyUUID);
    }
}
