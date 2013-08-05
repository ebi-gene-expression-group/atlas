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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@Scope("singleton")
public class ArrayDesignDao extends AnnotationDao {

    private static final Logger LOGGER = Logger.getLogger(ArrayDesignDao.class);

    private JdbcTemplate jdbcTemplate;

    @Inject
    public ArrayDesignDao(@Qualifier("dataSource") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveMappings(final Map<String, String> annotations, final String arrayDesign, final String type) {

        String query = "INSERT INTO designelement_mapping(designelement, identifier, type, arraydesign) VALUES(?, ?, ?,?)";

        final ArrayList<String> keys = Lists.newArrayList(annotations.keySet());
        BatchPreparedStatementSetter statementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, keys.get(i));
                ps.setString(2, annotations.get(keys.get(i)));
                ps.setString(3, type);
                ps.setString(4, arrayDesign);
            }

            @Override
            public int getBatchSize() {
                return annotations.size();
            }
        };

        int[] rows = jdbcTemplate.batchUpdate(query, statementSetter);
        LOGGER.info("Updated " + rows.length + " bioentities");
    }

    public void deleteMappings(String arrayDesign) {
        String query = "delete from designelement_mapping where arraydesign=?";

        jdbcTemplate.update(query, new Object[]{arrayDesign});
    }

    public String getIdentifier(String arrayDesign, String designElement) {
        String query = "select identifier from designelement_mapping where designelement=? and arraydesign=?";

        List<String> names = jdbcTemplate.queryForList(query, new String[]{designElement, arrayDesign}, String.class);

        return getOnly(names);
    }

    public boolean isArrayDesignPresent(String arrayDesign) {
        String query = "select count(*) from designelement_mapping where arraydesign=?";

        int count = jdbcTemplate.queryForObject(query, new String[]{arrayDesign}, Integer.class);

        return count > 0;
    }

    public List<String> getDesignElements(String geneIdentifier) {
        String query = "select designelement from designelement_mapping where identifier=?";
        return jdbcTemplate.queryForList(query, new String[]{geneIdentifier}, String.class);
    }
}
