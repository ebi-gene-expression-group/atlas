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

package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.List;

@Named
public class ExperimentDescriptionDAO {

    private final JdbcTemplate jdbcTemplate;

    private final ExperimentDescriptionRowMapper experimentDescriptionRowMapper;

    @Inject
    public ExperimentDescriptionDAO(@Qualifier("dataSourceOracle") DataSource dataSource, ExperimentDescriptionRowMapper experimentDescriptionRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.experimentDescriptionRowMapper = experimentDescriptionRowMapper;
    }

    public List<ExperimentDescription> selectAllPublicExperimentDescriptions() {
        return jdbcTemplate.query("SELECT accession, title FROM experiment WHERE PRIVATE='F'", experimentDescriptionRowMapper);
    }

}