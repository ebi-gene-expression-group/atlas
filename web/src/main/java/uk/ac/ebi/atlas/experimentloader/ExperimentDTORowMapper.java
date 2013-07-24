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

import org.springframework.jdbc.core.RowMapper;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ExperimentDTORowMapper implements RowMapper<ExperimentDTO> {

    @Override
    public ExperimentDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        return buildExperimentDTO(resultSet);
    }

    ExperimentDTO buildExperimentDTO(ResultSet resultSet) throws SQLException {
        String experimentAccession = resultSet.getString("experiment_accession");
        ExperimentType experimentType = ExperimentType.valueOf(resultSet.getString("experiment_type"));
        Date lastUpdate = resultSet.getTimestamp("last_update");
        boolean isPrivate = false;
        if (resultSet.getMetaData().getColumnCount() == 4){
            isPrivate = resultSet.getBoolean("private");
        }
        return new ExperimentDTO(experimentAccession
                                            ,experimentType
                                            ,lastUpdate
                                            , isPrivate);
    }

}