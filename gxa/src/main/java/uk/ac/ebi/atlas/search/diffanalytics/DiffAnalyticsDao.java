package uk.ac.ebi.atlas.search.diffanalytics;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.search.DatabaseQuery;
import uk.ac.ebi.atlas.search.OracleObjectFactory;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
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
    public DiffAnalyticsDao(DataSource dataSource, DiffAnalyticsRowMapper dbeRowMapper, OracleObjectFactory oracleObjectFactory) {
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
