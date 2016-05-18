
package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import uk.ac.ebi.atlas.search.DatabaseQuery;
import uk.ac.ebi.atlas.search.OracleObjectFactory;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Named
@Scope("prototype")
public class BaselineExperimentAssayGroupsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineExperimentAssayGroupsDao.class);

    private final JdbcTemplate jdbcTemplate;
    private OracleObjectFactory oracleObjectFactory;

    @Inject
    public BaselineExperimentAssayGroupsDao(JdbcTemplate jdbcTemplate, OracleObjectFactory oracleObjectFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.oracleObjectFactory = oracleObjectFactory;
    }

    boolean isEmpty(Optional<? extends Collection<?>> coll) {
        return (!coll.isPresent() || coll.get().isEmpty());
    }

    // results SetMultimap of experimentAccession, assaygroupids
    public SetMultimap<String, String> fetchExperimentAssayGroupsWithNonSpecificExpression(Optional<? extends Collection<IndexedAssayGroup>> indexedAssayGroups, Optional<? extends Collection<String>> geneIds) {
        if (isEmpty(indexedAssayGroups) && isEmpty(geneIds)) {
            return ImmutableSetMultimap.<String, String>builder().build();
        }

        Optional<ImmutableSet<IndexedAssayGroup>> uniqueIndexedAssayGroups = uniqueIndexedContrasts(indexedAssayGroups);

        log("fetchExperimentAssayGroupsWithNonSpecificExpression", uniqueIndexedAssayGroups, geneIds);

        Stopwatch stopwatch = Stopwatch.createStarted();

        DatabaseQuery<Object> baselineExpressionQuery = buildSelect(uniqueIndexedAssayGroups, geneIds);

        try {
            SetMultimap<String, String> results = jdbcTemplate.query(baselineExpressionQuery.getQuery(),
                    baselineExpressionQuery.getParameters().toArray(),
                    new ResultSetExtractor<SetMultimap<String, String>>() {
                        @Override
                        public SetMultimap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {

                            ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

                            while (rs.next()) {
                                String experimentAccession = rs.getString("experiment");
                                String assayGroupId = rs.getString("assaygroupid");

                                builder.put(experimentAccession, assayGroupId);
                            }

                            return builder.build();
                        }
                    });

            stopwatch.stop();

            LOGGER.debug("fetchExperimentAssayGroupsWithNonSpecificExpression returned {} results in {} seconds", results.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

            return results;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

    }

    // get uniques, as we get contrasts multiple times for each assay group in the contrast
    private Optional<ImmutableSet<IndexedAssayGroup>> uniqueIndexedContrasts(Optional<? extends Collection<IndexedAssayGroup>> indexedContrasts) {
        if (!indexedContrasts.isPresent()) {
            return Optional.absent();
        }
        return Optional.of(ImmutableSet.copyOf(indexedContrasts.get()));
    }


    DatabaseQuery<Object> buildSelect(Optional<? extends Collection<IndexedAssayGroup>> indexedContrasts,
                                           Optional<? extends Collection<String>> geneIds) {
        BaselineExperimentAssayGroupQueryBuilder builder = createBaselineExpressionsQueryBuilder(indexedContrasts, geneIds);
        return builder.build();
    }

    private BaselineExperimentAssayGroupQueryBuilder createBaselineExpressionsQueryBuilder(Optional<? extends
            Collection<IndexedAssayGroup>> indexedAssayGroups, Optional<? extends Collection<String>> geneIds) {

        BaselineExperimentAssayGroupQueryBuilder builder = new BaselineExperimentAssayGroupQueryBuilder();

        if (indexedAssayGroups.isPresent() && !indexedAssayGroups.get().isEmpty()) {
            builder.withExperimentAssayGroups(oracleObjectFactory.createOracleArrayForIndexedAssayGroup(indexedAssayGroups.get()));
        }

        if (geneIds.isPresent() && !geneIds.get().isEmpty()) {
            builder.withGeneIds(oracleObjectFactory.createOracleArrayForIdentifiers(geneIds.get()));
        }

        return builder;

    }


    private void log(final String methodName, Optional<? extends Collection<IndexedAssayGroup>> indexedAssayGroups, Optional<? extends Collection<String>> geneIds) {
        LOGGER.debug(
            "{} for {} unique contrasts and {} genes",
            methodName, indexedAssayGroups.isPresent() ? indexedAssayGroups.get().size() : 0, geneIds.isPresent() ? geneIds.get().size() : 0);
    }

}
