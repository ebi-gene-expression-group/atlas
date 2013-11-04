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

import com.google.common.collect.Iterables;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Named
@Scope("prototype")
public class BaselineExperimentDao {

    private static final Logger LOGGER = Logger.getLogger(BaselineExperimentDao.class);

    static final String GENEIDS_IN_EXPERIMENT = "select 1 from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) rbe\n" +
            "    where rownum < 2 and rbe.experiment = :experimentAccession\n" +
            "    and exists (select 1 from bioentity_name where identifier = rbe.identifier)\n";


    static final String CONDITIONS_IN_EXPERIMENT = "select 1 from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) rbe \n" +
            "where rbe.experiment = 'E-MTAB-599'\n" +
            "and exists (select 1 from bioentity_name where identifier = rbe.identifier)\n" +
            "and rbe.assaygroupid in ('g2')\n" +
            "and  \n" +
            "( select avg(expression) from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF )\n" +
            "  where assaygroupid in ('g2') and experiment = 'E-MTAB-599' and identifier = rbe.identifier\n" +
            ") >   \n" +
            "( select NVL(max(expression),0) from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) \n" +
            "  where assaygroupid not in ('g2') and experiment = 'E-MTAB-599' and identifier = rbe.identifier\n" +
            ")\n" +
            "and rownum < 2";

    static final String CONDITIONS_AND_GENES_IN_EXPERIMENT = "select 1 from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) rbe \n" +
            "where rbe.experiment = 'E-MTAB-599'\n" +
            "and rbe.identifier in ('ENSMUSG00000054422','ENSMUSG00000066154','ENSMUSG00000018593')\n" +
            "and exists (select 1 from bioentity_name where identifier = rbe.identifier)\n" +
            "and rbe.assaygroupid in ('g2','g5')\n" +
            "and  \n" +
            "( select avg(expression) from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF )\n" +
            "  where assaygroupid in ('g2','g5') and experiment = 'E-MTAB-599' and identifier = rbe.identifier\n" +
            ") >   \n" +
            "( select NVL(max(expression),0) from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) \n" +
            "  where assaygroupid not in ('g2','g5') and experiment = 'E-MTAB-599' and identifier = rbe.identifier\n" +
            ")\n" +
            "and rownum < 2";

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedJdbcTemplate;


    @Inject
    public BaselineExperimentDao(@Qualifier("dataSourceOracle") DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean isExperimentWithCondition(String experimentAccession, Collection<String> assayGroups) {
        List<Integer> result = jdbcTemplate.queryForList(CONDITIONS_IN_EXPERIMENT, Integer.class);
        return result.size() > 0;
    }

    public boolean isExperimentWithGenes(String experimentAccession, Collection<String> geneIds) {
        if (geneIds.size() == 0) return false;

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("experimentAccession", experimentAccession);
        parameters.addValue("geneIds", geneIds);

        StringBuilder sqlQuery = new StringBuilder(GENEIDS_IN_EXPERIMENT);

        sqlQuery.append(" and ");
        buildMultipleIdentifierInClauses(geneIds, parameters, sqlQuery);

        List<Integer> results = namedJdbcTemplate.queryForList(sqlQuery.toString(), parameters, Integer.class);

        // if a row is returned, this indicates one of the gene ids were found in the experiment
        return (results.size() > 0);
    }

    public boolean isExperimentWithConditionsAndGenes(String experimentAccession, Collection<String> assayGroups, Collection<String> geneIds) {
        List<Integer> result = jdbcTemplate.queryForList(CONDITIONS_AND_GENES_IN_EXPERIMENT, Integer.class);
        return result.size() > 0;
    }


    private void buildMultipleIdentifierInClauses(Collection<String> identifiers, MapSqlParameterSource parameters, StringBuilder sqlQuery) {
        int i = 1;

        sqlQuery.append("(");
        for (List<String> sublist : Iterables.partition(identifiers, 1000)) {
            String idsParam = "geneIds" + i;
            parameters.addValue(idsParam, sublist);

            if (i > 1) {
                sqlQuery.append(" OR");
            }

            sqlQuery.append(" rbe.IDENTIFIER IN (:").append(idsParam).append(")");

            i++;
        }
        sqlQuery.append(")");
    }

}
