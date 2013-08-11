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
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExperimentDAO {

    private static final String INSERT_NEW_EXPERIMENT = "INSERT INTO experiment " +
            "(experiment_accession, experiment_type, private, access_key) VALUES (?, ?, ?, ?)";

    private static final String DELETE_EXPERIMENT = "DELETE FROM experiment WHERE experiment_accession = ?";

    private static final String UPDATE_EXPERIMENT = "UPDATE experiment SET private = ? where experiment_accession = ?";

    private static final String PING_EXPERIMENT = "SELECT COUNT (1) FROM experiment WHERE experiment_accession = ?";

    private static final String SELECT_ALL_EXPERIMENTS = "SELECT * FROM experiment";

    private static final String SELECT_EXPERIMENT_BY_ACCESSION = "SELECT * FROM experiment WHERE experiment_accession = ?";

    private static final String SELECT_PUBLIC_EXPERIMENT_BY_ACCESSION = "SELECT * FROM experiment WHERE experiment_accession = ? and private = FALSE";

    private static final String SELECT_PUBLIC_EXPERIMENTS_BY_EXPERIMENT_TYPE = "SELECT experiment_accession " +
            "FROM public_experiment WHERE experiment_type IN(:experimentTypes)";

    private static final String SELECT_EXPERIMENT_BY_ACCESSION_AND_ACCESS_KEY = "SELECT * FROM experiment WHERE experiment_accession = ? AND access_key = ?";


    @Inject
    @Qualifier("dataSource")
    private DataSource dataSource;

    /**
     * @return All imported experiments, independently from their public and private status
     */
    public List<ExperimentDTO> findAllExperiments() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return jdbcTemplate.query(SELECT_ALL_EXPERIMENTS, new ExperimentDTORowMapper());
    }

    public Set<String> findPublicExperimentAccessions(ExperimentType... experimentTypes) {

        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        Set<String> experimentTypeNames = Sets.newHashSet();
        for (ExperimentType experimentType: experimentTypes){
            experimentTypeNames.add(experimentType.name());
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource("experimentTypes",experimentTypeNames);
        List<String> experimentAccessions = jdbcTemplate.queryForList(SELECT_PUBLIC_EXPERIMENTS_BY_EXPERIMENT_TYPE, parameters, String.class);

        return Sets.newHashSet(experimentAccessions);
    }

    public UUID addExperiment(String experimentAccession, ExperimentType experimentType, boolean isPrivate) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        UUID accessKeyUUID = UUID.randomUUID();

        jdbcTemplate.update(INSERT_NEW_EXPERIMENT, experimentAccession, experimentType.name(), isPrivate, accessKeyUUID);

        return accessKeyUUID;
    }

    public void deleteExperiment(String experimentAccession) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        int deletedRecordsCount =  jdbcTemplate.update(DELETE_EXPERIMENT, experimentAccession);

        if (deletedRecordsCount != 1){
            throw new IllegalArgumentException("Experiment not found for accession " + experimentAccession);
        }
    }

    public ExperimentDTO findPublicExperiment(String experimentAccession) {
        return findExperiment(experimentAccession, false);
    }

    public ExperimentDTO findExperiment(String experimentAccession, String accessKey) {
        try{

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            String findExperimentQuery = SELECT_EXPERIMENT_BY_ACCESSION_AND_ACCESS_KEY;

            return jdbcTemplate.queryForObject(findExperimentQuery, new ExperimentDTORowMapper(), experimentAccession, accessKey);

        } catch(EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Experiments not found for accession: " + experimentAccession);
        }
    }

    public ExperimentDTO findExperiment(String experimentAccession, boolean includePrivates) {
        try{

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            String findExperimentQuery = includePrivates ? SELECT_EXPERIMENT_BY_ACCESSION : SELECT_PUBLIC_EXPERIMENT_BY_ACCESSION;

            return jdbcTemplate.queryForObject(findExperimentQuery, new ExperimentDTORowMapper(), experimentAccession);

        } catch(EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Experiment not found for experiment accession: " + experimentAccession);
        }
    }

    public void updateExperiment(String experimentAccession, boolean isPrivate){

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        int recordsCount = jdbcTemplate.update(UPDATE_EXPERIMENT, isPrivate, experimentAccession);

        if (recordsCount == 0){
            throw new IllegalArgumentException("Experiment not found for accession " + experimentAccession);
        }

    }

    public boolean isImported(String experimentAccession) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        int experimentCount = jdbcTemplate.queryForObject(PING_EXPERIMENT, Integer.class, experimentAccession);

        checkState(experimentCount <= 1, "Multiple experiments with experiment accession " + experimentAccession);

        return experimentCount == 1;

    }
}