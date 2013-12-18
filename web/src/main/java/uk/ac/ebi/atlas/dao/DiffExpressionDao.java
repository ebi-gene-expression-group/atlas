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

import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import oracle.jdbc.OracleConnection;
import oracle.sql.ArrayDescriptor;
import org.apache.commons.lang.mutable.MutableInt;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;
import uk.ac.ebi.atlas.utils.Visitor;
import uk.ac.ebi.atlas.utils.VisitorException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Named
@Scope("prototype")
public class DiffExpressionDao {

    private static final Logger LOGGER = Logger.getLogger(DiffExpressionDao.class);

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
            .append(DifferentialGeneQueryBuilder.EXPERIMENT).append(", ")
            .append(CONTRASTID).append(", ")
            .append(PVALUE).append(", ")
            .append(LOG_2_FOLD).append(", ")
            .append(TSTAT)
            .append(" FROM VW_DIFFANALYTICS ")
            .toString();

    static final String COUNT_QUERY = "SELECT count(1) FROM VW_DIFFANALYTICS ";

    static final String ORDER_BY_PVAL = "order by PVAL";

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    private DifferentialGeneQueryBuilder differentialGeneQueryBuilder;

    private DifferentialBioentityExpressionRowMapper dbeRowMapper;

    @Inject
    public DiffExpressionDao(@Qualifier("dataSourceOracle") DataSource dataSource, DifferentialGeneQueryBuilder differentialGeneQueryBuilder, DifferentialBioentityExpressionRowMapper dbeRowMapper) {
        this.differentialGeneQueryBuilder = differentialGeneQueryBuilder;
        this.dbeRowMapper = dbeRowMapper;
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<DifferentialBioentityExpression> getTopExpressions(Optional<Collection<IndexedAssayGroup>> indexedContrasts, Optional<Collection<String>> geneIds) {

        log(indexedContrasts, geneIds);

        Stopwatch stopwatch = Stopwatch.createStarted();

        Query<Object> indexedContrastQuery = buildIndexedContrastQuery(indexedContrasts, geneIds, SELECT_QUERY);

        jdbcTemplate.setMaxRows(RESULT_SIZE);

        List<DifferentialBioentityExpression> results = null;

        try {
            results = jdbcTemplate.query(indexedContrastQuery.getQuery(),
                    dbeRowMapper,
                    indexedContrastQuery.getParameters().toArray());

            stopwatch.stop();

            LOGGER.debug(String.format("getTopExpressions returned %s expressions in %s seconds", results.size(), stopwatch.elapsed(TimeUnit.SECONDS)));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

        return results;

    }

    public void foreachExpression(Optional<Collection<IndexedAssayGroup>> indexedContrasts, Optional<Collection<String>> geneIds, final Visitor<DifferentialBioentityExpression> visitor)  {
        log(indexedContrasts, geneIds);

        Stopwatch stopwatch = Stopwatch.createStarted();

        Query<Object> indexedContrastQuery = buildIndexedContrastQuery(indexedContrasts, geneIds, SELECT_QUERY);

        final MutableInt count = new MutableInt(0);

        JdbcTemplate template = new JdbcTemplate(dataSource);

        template.setFetchSize(5000);

        try {
            template.query(indexedContrastQuery.getQuery(), new RowCallbackHandler() {

                @Override
                public void processRow(ResultSet resultSet) throws SQLException {
                    count.increment();

                    DifferentialBioentityExpression dbe = dbeRowMapper.mapRow(resultSet, count.intValue());

                    try {
                        visitor.visit(dbe);
                    } catch (VisitorException e) {
                        // throw SQLException so result set is closed
                        throw new SQLException(e);
                    }
                }

            }, indexedContrastQuery.getParameters().toArray());
        } catch (DataAccessException e) {
            LOGGER.warn(String.format("foreachExpression aborted: " + e.getCause().getMessage()));

            if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause() instanceof VisitorException) {
                throw (VisitorException)(e.getCause().getCause());
            } else {
                throw e;
            }
        }

        stopwatch.stop();
        LOGGER.debug(String.format("foreachExpression processed %s expressions in %s seconds", count.intValue(), stopwatch.elapsed(TimeUnit.SECONDS)));
    }


    public int getResultCount(Optional<Collection<IndexedAssayGroup>> indexedContrasts, Optional<Collection<String>> geneIds) {
        log(indexedContrasts, geneIds);

        Stopwatch stopwatch = Stopwatch.createStarted();

        Query query = buildIndexedContrastQuery(indexedContrasts, geneIds, COUNT_QUERY);

        int count = jdbcTemplate.queryForObject(query.getQuery(), Integer.class, query.getParameters().toArray());

        LOGGER.debug(String.format("getResultCount returned %s in %s seconds", count, stopwatch.elapsed(TimeUnit.SECONDS)));
        return count;
    }

    Query<Object> buildIndexedContrastQuery(Optional<Collection<IndexedAssayGroup>> indexedContrasts, Optional<Collection<String>> geneIds, String queryBeginning) {

        DifferentialGeneQueryBuilder builder = differentialGeneQueryBuilder.withSelectPart(queryBeginning).withSuffix(ORDER_BY_PVAL);

        if (indexedContrasts.isPresent()) {
            builder.withAssayGroups(indexedContrasts.get());
        }

        if (geneIds.isPresent() && !geneIds.get().isEmpty()) {
            builder.withGeneIds(createOracleArrayForIdentifiers(geneIds.get()));
        }

        return builder.build();

    }

    private oracle.sql.ARRAY createOracleArrayForIdentifiers(Collection<String> geneIds) {
        final String[] ids = geneIds.toArray(new String[geneIds.size()]);

        return jdbcTemplate.execute(new ConnectionCallback<oracle.sql.ARRAY>() {
            public oracle.sql.ARRAY doInConnection(Connection connection) throws SQLException, DataAccessException {
                OracleConnection oracleConnection = connection.unwrap(OracleConnection.class);
                ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor("IDENTIFIERS_TABLE", oracleConnection);
                return new oracle.sql.ARRAY(descriptor, oracleConnection, ids);
            }
        });
    }

    private void log(Optional<Collection<IndexedAssayGroup>> indexedContrasts, Optional<Collection<String>> geneIds) {
        LOGGER.debug(String.format("getTopExpressions for %s contrasts and %s genes", (indexedContrasts.isPresent()) ? indexedContrasts.get().size() : 0,
                (geneIds.isPresent()) ? geneIds.get().size() : 0));
    }

}
