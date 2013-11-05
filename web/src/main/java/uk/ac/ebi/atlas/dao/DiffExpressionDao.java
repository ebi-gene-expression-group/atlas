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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Named
@Scope("prototype")
public class DiffExpressionDao {

    static final String CONTRASTID = "CONTRASTID";
    static final String PVALUE = "PVAL";
    static final String LOG_2_FOLD = "LOG2FOLD";
    static final String TSTAT = "TSTAT";
    static final String IDENTIFIER = "IDENTIFIER";
    static final String NAME = "NAME";
    static final String ORGANISM = "ORGANISM";
    static final String DESIGNELEMENT = "DESIGNELEMENT";

    static final int RESULT_SIZE = 50;

    static final String SELECT_QUERY = new StringBuilder()
            .append("SELECT ").append(IDENTIFIER).append(", ")
            .append(NAME).append(", ")
            .append(DESIGNELEMENT).append(", ")
            .append(ORGANISM).append(", ")
            .append(AssayGroupQueryBuilder.EXPERIMENT).append(", ")
            .append(CONTRASTID).append(", ")
            .append(PVALUE).append(", ")
            .append(LOG_2_FOLD).append(", ")
            .append(TSTAT)
            .append(" FROM VW_DIFFANALYTICS ")
            .toString();

    static final String COUNT_QUERY = "SELECT count(1) FROM VW_DIFFANALYTICS ";

    static final String ORDER_BY_PVAL = "order by PVAL";

    private final DataSource dataSource;

    private AssayGroupQueryBuilder assayGroupQueryBuilder;

    private DifferentialBioentityExpressionRowMapper rowMapper;

    @Inject
    public DiffExpressionDao(@Qualifier("dataSourceOracle") DataSource dataSource, AssayGroupQueryBuilder assayGroupQueryBuilder, DifferentialBioentityExpressionRowMapper rowMapper) {
        this.assayGroupQueryBuilder = assayGroupQueryBuilder;
        this.rowMapper = rowMapper;
        this.dataSource = dataSource;
    }

    public List<DifferentialBioentityExpression> getExpressions(Collection<IndexedAssayGroup> indexedContrasts, Collection<String> geneIds) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        AssayGroupQuery indexedContrastQuery = buildIndexedContrastQuery(indexedContrasts, geneIds, SELECT_QUERY);

        jdbcTemplate.setMaxRows(RESULT_SIZE);
        List<DifferentialBioentityExpression> result = jdbcTemplate.query(indexedContrastQuery.getQuery(),
                rowMapper,
                indexedContrastQuery.getParams());

        return result;
    }

    public int getResultCount(Collection<IndexedAssayGroup> indexedContrasts, Collection<String> geneIds) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        AssayGroupQuery query = buildIndexedContrastQuery(indexedContrasts, geneIds, COUNT_QUERY);

        return jdbcTemplate.queryForObject(query.getQuery(), Integer.class, query.getParams());
    }

    AssayGroupQuery buildIndexedContrastQuery(Collection<IndexedAssayGroup> indexedContrasts, Collection<String> geneIds, String queryBeginning) {

        return assayGroupQueryBuilder.withSelectPart(queryBeginning)
                .withIndexedAssayGroupsOrContrasts(indexedContrasts)
                .withGeneIds(geneIds)
                .withAssayGroupOrContrast(CONTRASTID)
                .withExtraCondition(ORDER_BY_PVAL).build();

    }

}
