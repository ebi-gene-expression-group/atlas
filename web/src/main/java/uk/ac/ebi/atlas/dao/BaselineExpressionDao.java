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

import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.baseline.BaselineBioentitiesCount;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;

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

    private JdbcTemplate jdbcTemplate;

    private AssayGroupQueryBuilder assayGroupQueryBuilder;

    private BaselineBioentitiesCountRowMapper rowMapper;

    @Inject
    public BaselineExpressionDao(@Qualifier("dataSourceOracle") DataSource dataSource,
                                 AssayGroupQueryBuilder assayGroupQueryBuilder, BaselineBioentitiesCountRowMapper rowMapper) {
        this.assayGroupQueryBuilder = assayGroupQueryBuilder;
        this.rowMapper = rowMapper;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public SortedSet<BaselineBioentitiesCount> getBioentitiesCounts(Collection<IndexedAssayGroup> indexedAssayGroups) {

        List<BaselineBioentitiesCount> baselineBioentitiesCounts = queryForBaselineBioentitiesCounts(indexedAssayGroups);

        return rankBioentityCounts(baselineBioentitiesCounts);

    }

    SortedSet<BaselineBioentitiesCount> rankBioentityCounts(List<BaselineBioentitiesCount> baselineBioentitiesCounts) {

        SortedSet<BaselineBioentitiesCount> rankedBioentityCounts =
                Sets.newTreeSet(new Comparator<BaselineBioentitiesCount>() {
                    @Override
                    public int compare(BaselineBioentitiesCount count, BaselineBioentitiesCount otherCount) {
                        return otherCount.getCount() - count.getCount();
                    }
                });

        for (BaselineBioentitiesCount baselineBioentitiesCount: baselineBioentitiesCounts){
            int count = jdbcTemplate.queryForObject(SELECT_BASELINE_COUNT_QUERY, Integer.class,
                    baselineBioentitiesCount.getExperimentAccession(),
                    baselineBioentitiesCount.getAssayGroupOrContrast());
            if (count > 0){
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
