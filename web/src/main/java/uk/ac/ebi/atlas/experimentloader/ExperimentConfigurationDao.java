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

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

//ToDo: (B) this should become ExperimentDAO

@Named
@Scope("prototype")
public class ExperimentConfigurationDao {

    private static final String EXPERIMENT_CONFIGURATION_SELECT = "SELECT experiment_accession, experiment_type " +
            "FROM experiment_configuration";

    private static final String EXPERIMENT_CONFIGURATION_BY_ACCESSION = "SELECT experiment_accession, experiment_type " +
            "FROM experiment_configuration WHERE experiment_accession = ?";

    private static final String EXPERIMENT_ACCESSIONS_BY_TYPE = "SELECT experiment_accession " +
            "FROM experiment_configuration WHERE experiment_type IN(:experimentTypes)";

    private static final String EXPERIMENT_CONFIGURATION_INSERT = "INSERT INTO experiment_configuration " +
            "(experiment_accession, experiment_type) VALUES (?, ?)";

    private static final String EXPERIMENT_CONFIGURATION_DELETE = "DELETE FROM experiment_configuration " +
            "WHERE experiment_accession = ?";

    @Inject
    @Qualifier("dataSource")
    private DataSource dataSource;

    public List<ExperimentConfiguration> findAllExperimentConfigurations() {

        JdbcTemplate select = new JdbcTemplate(dataSource);

        return select.query(EXPERIMENT_CONFIGURATION_SELECT, new ExperimentConfigurationRowMapper());
    }

    public Set<String> getExperimentAccessions(ExperimentType... experimentTypes) {

        NamedParameterJdbcTemplate select = new NamedParameterJdbcTemplate(dataSource);

        Set<String> experimentTypeNames = Sets.newHashSet();
        for (ExperimentType experimentType: experimentTypes){
            experimentTypeNames.add(experimentType.name());
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource("experimentTypes",experimentTypeNames);
        List<String> experimentAccessions = select.queryForList(EXPERIMENT_ACCESSIONS_BY_TYPE, parameters, String.class);

        return Sets.newHashSet(experimentAccessions);
    }

    public boolean addExperimentConfiguration(String experimentAccession, ExperimentType experimentType) {

        JdbcTemplate insert = new JdbcTemplate(dataSource);

        int updatedRecordsCount = insert.update(EXPERIMENT_CONFIGURATION_INSERT, experimentAccession, experimentType.name());

        return updatedRecordsCount == 1;
    }

    public boolean deleteExperimentConfiguration(String experimentAccession) {

        JdbcTemplate delete = new JdbcTemplate(dataSource);

        int deletedRecordsCount =  delete.update(EXPERIMENT_CONFIGURATION_DELETE, experimentAccession);

        return deletedRecordsCount == 1;
    }

    public ExperimentConfiguration getExperimentConfiguration(String experimentAccession) {
        try {

            JdbcTemplate select = new JdbcTemplate(dataSource);

            ExperimentConfiguration experimentConfiguration = select.queryForObject(EXPERIMENT_CONFIGURATION_BY_ACCESSION,
                    new ExperimentConfigurationRowMapper(), experimentAccession);

            return experimentConfiguration;

        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }
}