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

package uk.ac.ebi.atlas.experimentloader;

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
            String specie = resultSet.getString("species_name");

            ExperimentDTO experiment = experimentByAccession.get(experimentAccession);

            if (experiment == null) {

                ExperimentType experimentType = ExperimentType.valueOf(resultSet.getString("type"));
                Date lastUpdate = resultSet.getTimestamp("last_update");
                boolean isPrivate = "T".equals(resultSet.getString("private"));
                String accessKeyUUID = resultSet.getString("access_key");
                String title = resultSet.getString("title");

                String pubmed_ids = resultSet.getString("pubmed_Ids");
                Set<String> pumedIds = StringUtils.isBlank(pubmed_ids)? new HashSet<String>() : Sets.newHashSet(Splitter.on(", ").split(pubmed_ids));

                experiment = new ExperimentDTO(experimentAccession
                        , experimentType
                        , pumedIds
                        , title
                        , lastUpdate
                        , isPrivate
                        , accessKeyUUID);


                experimentByAccession.put(experimentAccession, experiment);
            }

            if (!StringUtils.isBlank(specie)) {
                experiment.addSpecie(specie);
            }


        }

        return Lists.newArrayList(experimentByAccession.values());


    }
}
