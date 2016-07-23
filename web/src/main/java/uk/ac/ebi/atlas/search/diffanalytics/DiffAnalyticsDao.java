package uk.ac.ebi.atlas.search.diffanalytics;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.mutable.MutableInt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(DiffAnalyticsDao.class);
    private static final int RESULT_SIZE = 50;

    private final JdbcTemplate jdbcTemplate;
    private final DiffAnalyticsRowMapper dbeRowMapper;
    private final OracleObjectFactory oracleObjectFactory;

    @Inject
    public DiffAnalyticsDao(@Qualifier("dataSourceOracle") DataSource dataSource, DiffAnalyticsRowMapper dbeRowMapper, OracleObjectFactory oracleObjectFactory) {
        this(new JdbcTemplate(dataSource), dbeRowMapper, oracleObjectFactory) ;
    }

    DiffAnalyticsDao(JdbcTemplate jdbcTemplate, DiffAnalyticsRowMapper dbeRowMapper, OracleObjectFactory oracleObjectFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.dbeRowMapper = dbeRowMapper;
        this.oracleObjectFactory = oracleObjectFactory;
    }

    public List<DiffAnalytics> fetchTopExpressions(Collection<IndexedAssayGroup> indexedContrasts, Collection<String> geneIds, String species) {
        ImmutableSet<IndexedAssayGroup> uniqueIndexedContrasts = uniqueIndexedContrasts(indexedContrasts);

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

            LOGGER.debug("fetchTopExpressions returned {} expressions in {} seconds", results.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

        return results;

    }

    // get uniques, as we get contrasts multiple times for each assay group in the contrast
    private ImmutableSet<IndexedAssayGroup> uniqueIndexedContrasts(Collection<IndexedAssayGroup> indexedContrasts) {
        if (indexedContrasts.isEmpty()) {
            return ImmutableSet.of();
        }
        return ImmutableSet.copyOf(indexedContrasts);
    }

    public void visitEachExpression(Collection<IndexedAssayGroup> indexedContrasts, Collection<String> geneIds, final Visitor<DiffAnalytics> visitor, String specie)  {
        ImmutableSet<IndexedAssayGroup> uniqueIndexedContrasts = uniqueIndexedContrasts(indexedContrasts);

        log("visitEachExpression", uniqueIndexedContrasts, geneIds);

        Stopwatch stopwatch = Stopwatch.createStarted();

        String species = "";
        if(StringUtils.isNotBlank(specie)) {
            species = specie;
        }

        DatabaseQuery<Object> indexedContrastQuery = buildSelect(uniqueIndexedContrasts, geneIds, species);

        final MutableInt count = new MutableInt(0);

        JdbcTemplate template = new JdbcTemplate(jdbcTemplate.getDataSource());

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
        LOGGER.debug("visitEachExpression processed {} expressions in {} seconds", count.intValue(), stopwatch.elapsed(TimeUnit.SECONDS));
    }


    public int  fetchResultCount(Collection<IndexedAssayGroup> indexedContrasts, Collection<String> geneIds, String specie) {
        ImmutableSet<IndexedAssayGroup> uniqueIndexedContrasts = uniqueIndexedContrasts(indexedContrasts);

        log("fetchResultCount", uniqueIndexedContrasts, geneIds);

        Stopwatch stopwatch = Stopwatch.createStarted();

        DatabaseQuery databaseQuery = buildCount(uniqueIndexedContrasts, geneIds, specie);

        int count = jdbcTemplate.queryForObject(databaseQuery.getQuery(), Integer.class, databaseQuery.getParameters().toArray());

        LOGGER.debug(String.format("fetchResultCount returned %s in %.2f seconds", count, stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));
        return count;
    }

    private DatabaseQuery<Object> buildCount(Collection<IndexedAssayGroup> indexedContrasts, Collection<String> geneIds, String species) {
        DiffAnalyticsQueryBuilder builder = createDifferentialGeneQueryBuilder(indexedContrasts, geneIds, species);
        return builder.buildCount();
    }

    private DatabaseQuery<Object> buildSelect(Collection<IndexedAssayGroup> indexedContrasts, Collection<String> geneIds, String species) {
        DiffAnalyticsQueryBuilder builder = createDifferentialGeneQueryBuilder(indexedContrasts, geneIds, species);
        return builder.buildSelect();
    }

    private DiffAnalyticsQueryBuilder createDifferentialGeneQueryBuilder(Collection<IndexedAssayGroup> indexedContrasts, Collection<String> geneIds,
                                                                 String species) {

        DiffAnalyticsQueryBuilder builder = new DiffAnalyticsQueryBuilder();

        if (!indexedContrasts.isEmpty()) {
            builder.withExperimentContrasts(oracleObjectFactory.createOracleArrayForIndexedAssayGroup(indexedContrasts)).withSpecies(species);
        }

        if (!geneIds.isEmpty()) {
            builder.withGeneIds(oracleObjectFactory.createOracleArrayForIdentifiers(geneIds)).withSpecies(species);
        }

        return builder;

    }

    private void log(final String methodName, Collection<IndexedAssayGroup> indexedContrasts, Collection<String> geneIds) {
        LOGGER.debug(
                String.format(
                        methodName + " for %s unique contrasts and %s genes",
                        (!indexedContrasts.isEmpty()) ? indexedContrasts.size() : 0,
                        (!geneIds.isEmpty()) ? geneIds.size() : 0));
    }

}
