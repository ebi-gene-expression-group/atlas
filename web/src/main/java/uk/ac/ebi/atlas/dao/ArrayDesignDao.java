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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.List;

@Named
@Scope("singleton")
public class ArrayDesignDao {

    private static final Logger LOGGER = Logger.getLogger(ArrayDesignDao.class);

    private JdbcTemplate jdbcTemplate;

    @Inject
    public ArrayDesignDao(@Qualifier("dataSourceOracle") DataSource dataSource) {
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
}
