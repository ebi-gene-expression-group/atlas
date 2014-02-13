/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

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

            String specie = resultSet.getString("organism");
            if (!StringUtils.isBlank(specie)) {
                experiment.addSpecie(specie);
            }

        }

        return Lists.newArrayList(experimentByAccession.values());


    }

    private ExperimentDTO createExperimentDTO(ResultSet resultSet, String experimentAccession) throws SQLException {
        ExperimentDTO experiment;ExperimentType experimentType = ExperimentType.valueOf(resultSet.getString("type"));
        Date lastUpdate = resultSet.getTimestamp("last_update");
        boolean isPrivate = "T".equals(resultSet.getString("private"));
        String accessKeyUUID = resultSet.getString("access_key");
        String title = resultSet.getString("title");

        String pubMedIdsString = resultSet.getString("pubmed_Ids");
        Set<String> pubMedIds = StringUtils.isBlank(pubMedIdsString)? new HashSet<String>() : Sets.newHashSet(Splitter.on(", ").split(pubMedIdsString));

        experiment = new ExperimentDTO(experimentAccession
                , experimentType
                , pubMedIds
                , title
                , lastUpdate
                , isPrivate
                , accessKeyUUID);
        return experiment;
    }
}
