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

package uk.ac.ebi.atlas.dao;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Named
@Scope("singleton")
public class ArrayDesignDAO {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public ArrayDesignDAO(@Qualifier("dataSourceOracle") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
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

    public List<String> getArrayDesignAccessions() {
        String query = "select distinct arraydesign from designelement_mapping";
        return jdbcTemplate.queryForList(query, String.class);
    }

    public Map<String, String> getArrayDesignMapNames() {
        return populateImmutableMap();
    }

    private Map<String, String> populateImmutableMap() {
        ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder();

        String query = "select * from arraydesign";
        List<Map<String, Object>> allArrayDesigns = jdbcTemplate.queryForList(query);

        for (Map<String, Object> arrayDesign : allArrayDesigns) {
            String accession = (String) arrayDesign.get("ACCESSION");
            String name = (String)arrayDesign.get("NAME");
            mapBuilder.put(accession, name);

        }
        return mapBuilder.build();
    }

}
