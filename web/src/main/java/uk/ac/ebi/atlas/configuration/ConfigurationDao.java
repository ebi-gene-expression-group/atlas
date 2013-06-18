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

package uk.ac.ebi.atlas.configuration;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.List;

@Named
@Scope("prototype")
public class ConfigurationDao {

    private static final String EXPERIMENT_CONFIGURATION_SELECT = "SELECT experiment_accession, experiment_type " +
            "FROM experiment_configuration";

    private static final String EXPERIMENT_CONFIGURATION_BY_ACCESSION = "SELECT experiment_accession, experiment_type " +
            "FROM experiment_configuration WHERE experiment_accession = ?";

    private static final String EXPERIMENT_CONFIGURATION_BY_TYPE = "SELECT experiment_accession, experiment_type " +
            "FROM experiment_configuration WHERE experiment_type = ?";

    private static final String EXPERIMENT_CONFIGURATION_INSERT = "INSERT INTO experiment_configuration " +
            "(experiment_accession, experiment_type) VALUES (?, ?)";

    private static final String EXPERIMENT_CONFIGURATION_DELETE = "DELETE FROM experiment_configuration " +
            "WHERE experiment_accession = ?";

    @Inject
    private DataSource dataSource;

    public List<ExperimentConfiguration> getExperimentConfigurations() {

        JdbcTemplate select = new JdbcTemplate(dataSource);

        return select.query(EXPERIMENT_CONFIGURATION_SELECT, new ExperimentConfigurationRowMapper());
    }

    public List<ExperimentConfiguration> getExperimentConfigurations(ExperimentType experimentType) {

        JdbcTemplate select = new JdbcTemplate(dataSource);

        return select.query(EXPERIMENT_CONFIGURATION_BY_TYPE,
                new ExperimentConfigurationRowMapper(), experimentType.name());
    }

    public ExperimentConfiguration getExperimentConfiguration(String experimentAccession) {

        JdbcTemplate select = new JdbcTemplate(dataSource);

        List<ExperimentConfiguration> list = select.query(EXPERIMENT_CONFIGURATION_BY_ACCESSION,
                new ExperimentConfigurationRowMapper(), experimentAccession);
        if (list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public int addExperimentConfiguration(String experimentAccession, ExperimentType experimentType) {

        JdbcTemplate insert = new JdbcTemplate(dataSource);

        return insert.update(EXPERIMENT_CONFIGURATION_INSERT, experimentAccession, experimentType.name());
    }

    public int deleteExperimentConfiguration(String experimentAccession) {

        JdbcTemplate delete = new JdbcTemplate(dataSource);

        return delete.update(EXPERIMENT_CONFIGURATION_DELETE, experimentAccession);
    }

}