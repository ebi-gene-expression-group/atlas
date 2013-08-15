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

package uk.ac.ebi.atlas.geneannotation;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.experimentloader.ExperimentDTORowMapper;
import uk.ac.ebi.atlas.geneannotation.mirna.MiRNAEntity;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Named
@Scope("singleton")
public class BioEntityAnnotationDao {

    private static final Logger LOGGER = Logger.getLogger(BioEntityAnnotationDao.class);
    private static final int BATCH_SIZE = 1000;
    private static final String INSERT_QUERY = "INSERT INTO bioentity_name(identifier, name, organism, type) VALUES(?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM bioentity_name WHERE organism = ? AND type = ?";
    private static final String SELECT_QUERY = "SELECT name FROM bioentity_name WHERE identifier = ?";

    private JdbcTemplate jdbcTemplate;

    @Inject
    public BioEntityAnnotationDao(@Qualifier("dataSource") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveAnnotations(Map<String, String> annotations, String organism, String type) {

        List<Map.Entry<String, String>> annotationEntries = Lists.newArrayList(annotations.entrySet());

        List<List<Map.Entry<String, String>>> partitionsOfEntries = Lists.partition(annotationEntries, BATCH_SIZE);

        int total = 0;
        for (List<Map.Entry<String, String>> partition : partitionsOfEntries) {
            int[] rows = performBatchStatement(partition, organism, type);
            total += rows.length;
        }

        LOGGER.info("Updated " + total + " bioentities");
    }

    private int[] performBatchStatement(final List<Map.Entry<String, String>> annotationEntries, final String organism, final String type) {

        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, annotationEntries.get(i).getKey());
                ps.setString(2, annotationEntries.get(i).getValue());
                ps.setString(3, organism);
                ps.setString(4, type);
            }

            @Override
            public int getBatchSize() {
                return annotationEntries.size();
            }
        };

        return jdbcTemplate.batchUpdate(INSERT_QUERY, statementSetter);
    }

    public int saveMiRnaAnnotations(List<MiRNAEntity> entities) {


        List<List<MiRNAEntity>> partitionsOfEntries = Lists.partition(entities, BATCH_SIZE);

        int total = 0;
        for (List<MiRNAEntity> partition : partitionsOfEntries) {
            int[] rows = performBatchStatement(partition);
            total += rows.length;
        }

        LOGGER.info("Updated " + total + " bioentities");
        return total;
    }

    private int[] performBatchStatement(final List<MiRNAEntity> entities) {

        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, entities.get(i).getIdentifier());
                ps.setString(2, entities.get(i).getAccession());
                ps.setString(3, entities.get(i).getOrganism());
                ps.setString(4, BioEntityType.MIRNA.getName());
            }

            @Override
            public int getBatchSize() {
                return entities.size();
            }
        };

        return jdbcTemplate.batchUpdate(INSERT_QUERY, statementSetter);
    }


    public int deleteAnnotations(String organism, String type) {
        return jdbcTemplate.update(DELETE_QUERY, new Object[]{organism, type});
    }

    public int deleteAnnotations(String type) {
        String query = "DELETE FROM bioentity_name WHERE  type = ?";
        return jdbcTemplate.update(query, new Object[]{type});
    }

    public String getBioentityName(String identifier) {
        try{

            return jdbcTemplate.queryForObject(SELECT_QUERY, new String[]{identifier}, String.class);

        } catch(IncorrectResultSizeDataAccessException e) {
            return identifier;
        }
    }

}
