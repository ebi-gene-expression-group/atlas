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
import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import uk.ac.ebi.atlas.model.baseline.BaselineBioentitiesCount;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.*;

@Named
@Scope("prototype")
public class BaselineExpressionDao {

    private static final Logger LOGGER = Logger.getLogger(BaselineExpressionDao.class);

    static final String ASSAYGROUPID = "ASSAYGROUPID";

    static final String SELECT_QUERY = new StringBuilder()
            .append("SELECT ")
            .append(AssayGroupQueryBuilder.EXPERIMENT).append(", ")
            .append(ASSAYGROUPID)
            .append(" FROM RNASEQ_BSLN_EXPRESSIONS  subpartition( ABOVE_CUTOFF ) ")
            .toString();

    static final String SELECT_BASELINE_COUNT_QUERY = "select getBaselineExpSpecificCount(?,?) from dual";

    static final String GROUP_BY_EXPERIMENT_ASSAYGROUPID = "group by EXPERIMENT, ASSAYGROUPID";

    static final String GENEIDS_IN_EXPERIMENT = "select 1 from RNASEQ_BSLN_EXPRESSIONS subpartition( ABOVE_CUTOFF ) rbe\n" +
            "    where rownum < 2 and rbe.experiment = :experimentAccession\n" +
            "    and exists (select 1 from bioentity_name where identifier = rbe.identifier)\n";

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedJdbcTemplate;

    private AssayGroupQueryBuilder assayGroupQueryBuilder;

    private BaselineBioentitiesCountRowMapper rowMapper;

    @Inject
    public BaselineExpressionDao(@Qualifier("dataSourceOracle") DataSource dataSource,
                                 AssayGroupQueryBuilder assayGroupQueryBuilder, BaselineBioentitiesCountRowMapper rowMapper) {
        this.assayGroupQueryBuilder = assayGroupQueryBuilder;
        this.rowMapper = rowMapper;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public SortedSet<BaselineBioentitiesCount> getBioentitiesCounts(Collection<IndexedAssayGroup> indexedAssayGroups) {

        List<BaselineBioentitiesCount> baselineBioentitiesCounts = queryForBaselineBioentitiesCounts(indexedAssayGroups);

        return rankBioentityCounts(baselineBioentitiesCounts);

    }

    public boolean atLeastOneGeneIdExistsInExperiment(Collection<String> geneIds, String experimentAccession) {
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


    SortedSet<BaselineBioentitiesCount> rankBioentityCounts(List<BaselineBioentitiesCount> baselineBioentitiesCounts) {

        SortedSet<BaselineBioentitiesCount> rankedBioentityCounts =
                Sets.newTreeSet(new Comparator<BaselineBioentitiesCount>() {
                    @Override
                    public int compare(BaselineBioentitiesCount count, BaselineBioentitiesCount otherCount) {
                        return otherCount.getCount() - count.getCount();
                    }
                });

        for (BaselineBioentitiesCount baselineBioentitiesCount : baselineBioentitiesCounts) {
            int count = jdbcTemplate.queryForObject(SELECT_BASELINE_COUNT_QUERY, Integer.class,
                    baselineBioentitiesCount.getExperimentAccession(),
                    baselineBioentitiesCount.getAssayGroupOrContrast());
            if (count > 0) {
                baselineBioentitiesCount.setCount(count);
                rankedBioentityCounts.add(baselineBioentitiesCount);
            }

        }
        return rankedBioentityCounts;
    }

    List<BaselineBioentitiesCount> queryForBaselineBioentitiesCounts(Collection<IndexedAssayGroup> indexedAssayGroups) {
        AssayGroupQuery query = assayGroupQueryBuilder.withSelectPart(SELECT_QUERY)
                .withIndexedAssayGroupsOrContrasts(indexedAssayGroups)
                .withAssayGroupOrContrast(ASSAYGROUPID)
                .withExtraCondition(GROUP_BY_EXPERIMENT_ASSAYGROUPID)
                .build();

        LOGGER.debug("<getBioentitiesCount> select experiments query: " + query);

        return jdbcTemplate.query(query.getQuery(), rowMapper, query.getParams());
    }

}
