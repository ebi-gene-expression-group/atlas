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

package uk.ac.ebi.atlas.search.diffanalytics;

import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.mutable.MutableInt;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import uk.ac.ebi.atlas.search.DatabaseQuery;
import uk.ac.ebi.atlas.search.OracleObjectFactory;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;
import uk.ac.ebi.atlas.utils.Visitor;
import uk.ac.ebi.atlas.utils.VisitorException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Named
@Scope("prototype")
public class DiffAnalyticsDao {

    private static final Logger LOGGER = LogManager.getLogger(DiffAnalyticsDao.class);

    static final int RESULT_SIZE = 50;

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    private DiffAnalyticsRowMapper dbeRowMapper;

    private OracleObjectFactory oracleObjectFactory;

    @Inject
    public DiffAnalyticsDao(@Qualifier("dataSourceOracle") DataSource dataSource, DiffAnalyticsRowMapper dbeRowMapper, OracleObjectFactory oracleObjectFactory) {
        this.dbeRowMapper = dbeRowMapper;
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.oracleObjectFactory = oracleObjectFactory;
    }

    public List<DiffAnalytics> fetchTopExpressions(Optional<Collection<IndexedAssayGroup>> indexedContrasts, Optional<? extends Collection<String>> geneIds, String species) {
        Optional<ImmutableSet<IndexedAssayGroup>> uniqueIndexedContrasts = uniqueIndexedContrasts(indexedContrasts);

        log("fetchTopExpressions", uniqueIndexedContrasts, geneIds);

        Stopwatch stopwatch = Stopwatch.createStarted();

        DatabaseQuery<Object> indexedContrastQuery = buildSelect(uniqueIndexedContrasts, geneIds, species);

        jdbcTemplate.setMaxRows(RESULT_SIZE);

        List<DiffAnalytics> results;

        try {
            results = jdbcTemplate.query(indexedContrastQuery.getQuery(),
                    dbeRowMapper,
                    indexedContrastQuery.getParameters().toArray());

            stopwatch.stop();

            LOGGER.debug(String.format("fetchTopExpressions returned %s expressions in %.2f seconds", results.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

        return results;

    }

    // get uniques, as we get contrasts multiple times for each assay group in the contrast
    private Optional<ImmutableSet<IndexedAssayGroup>> uniqueIndexedContrasts(Optional<? extends Collection<IndexedAssayGroup>> indexedContrasts) {
        if (!indexedContrasts.isPresent()) {
            return Optional.absent();
        }
        return Optional.of(ImmutableSet.copyOf(indexedContrasts.get()));
    }

    public void visitEachExpression(Optional<? extends Collection<IndexedAssayGroup>> indexedContrasts, Optional<? extends Collection<String>> geneIds, final Visitor<DiffAnalytics> visitor,
                                    String specie)  {
        Optional<ImmutableSet<IndexedAssayGroup>> uniqueIndexedContrasts = uniqueIndexedContrasts(indexedContrasts);

        log("visitEachExpression", uniqueIndexedContrasts, geneIds);

        Stopwatch stopwatch = Stopwatch.createStarted();

        String species = "";
        if(StringUtils.isNotBlank(specie)) {
            species = specie;
        }

        DatabaseQuery<Object> indexedContrastQuery = buildSelect(uniqueIndexedContrasts, geneIds, species);

        final MutableInt count = new MutableInt(0);

        JdbcTemplate template = new JdbcTemplate(dataSource);

        template.setFetchSize(5000);

        try {
            template.query(indexedContrastQuery.getQuery(), new RowCallbackHandler() {

                @Override
                public void processRow(ResultSet resultSet) throws SQLException {
                    count.increment();

                    DiffAnalytics dbe = dbeRowMapper.mapRow(resultSet, count.intValue());

                    try {
                        visitor.visit(dbe);
                    } catch (VisitorException e) {
                        // throw SQLException so result set is closed
                        throw new SQLException(e);
                    }
                }

            }, indexedContrastQuery.getParameters().toArray());
        } catch (DataAccessException e) {

            if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause() instanceof VisitorException) {
                throw (VisitorException)(e.getCause().getCause());
            } else {
                throw e;
            }
        }

        stopwatch.stop();
        LOGGER.debug(String.format("visitEachExpression processed %s expressions in %s seconds", count.intValue(), stopwatch.elapsed(TimeUnit.SECONDS)));
    }


    public int  fetchResultCount(Optional<? extends Collection<IndexedAssayGroup>> indexedContrasts, Optional<? extends Collection<String>> geneIds, String specie) {
        Optional<ImmutableSet<IndexedAssayGroup>> uniqueIndexedContrasts = uniqueIndexedContrasts(indexedContrasts);

        log("fetchResultCount", uniqueIndexedContrasts, geneIds);

        Stopwatch stopwatch = Stopwatch.createStarted();

        DatabaseQuery databaseQuery = buildCount(uniqueIndexedContrasts, geneIds, specie);

        int count = jdbcTemplate.queryForObject(databaseQuery.getQuery(), Integer.class, databaseQuery.getParameters().toArray());

        LOGGER.debug(String.format("fetchResultCount returned %s in %.2f seconds", count, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));
        return count;
    }

    DatabaseQuery<Object> buildCount(Optional<? extends Collection<IndexedAssayGroup>> indexedContrasts, Optional<? extends Collection<String>> geneIds, String species) {
        DiffAnalyticsQueryBuilder builder = createDifferentialGeneQueryBuilder(indexedContrasts, geneIds, species);
        return builder.buildCount();
    }

    DatabaseQuery<Object> buildSelect(Optional<? extends Collection<IndexedAssayGroup>> indexedContrasts, Optional<? extends Collection<String>> geneIds, String species) {
        DiffAnalyticsQueryBuilder builder = createDifferentialGeneQueryBuilder(indexedContrasts, geneIds, species);
        return builder.buildSelect();
    }

    DiffAnalyticsQueryBuilder createDifferentialGeneQueryBuilder(Optional<? extends Collection<IndexedAssayGroup>> indexedContrasts, Optional<? extends Collection<String>> geneIds,
                                                                 String species) {

        DiffAnalyticsQueryBuilder builder = new DiffAnalyticsQueryBuilder();

        if (indexedContrasts.isPresent() && !indexedContrasts.get().isEmpty()) {
            builder.withExperimentContrasts(oracleObjectFactory.createOracleArrayForIndexedAssayGroup(indexedContrasts.get()))
                    .withSpecies(species);
        }

        if (geneIds.isPresent() && !geneIds.get().isEmpty()) {
            builder.withGeneIds(oracleObjectFactory.createOracleArrayForIdentifiers(geneIds.get()))
                    .withSpecies(species);
        }

        return builder;

    }

    private void log(final String methodName, Optional<? extends Collection<IndexedAssayGroup>> indexedContrasts, Optional<? extends Collection<String>> geneIds) {
        LOGGER.debug(String.format(methodName + " for %s unique contrasts and %s genes", (indexedContrasts.isPresent()) ? indexedContrasts.get().size() : 0,
                (geneIds.isPresent()) ? geneIds.get().size() : 0));
    }

}
