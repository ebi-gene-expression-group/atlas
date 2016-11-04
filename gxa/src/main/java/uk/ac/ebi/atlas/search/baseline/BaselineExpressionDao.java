package uk.ac.ebi.atlas.search.baseline;

import uk.ac.ebi.atlas.search.OracleObjectFactory;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.DatabaseQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Named
public class BaselineExpressionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineExpressionDao.class);

    private final JdbcTemplate jdbcTemplate;

    private final OracleObjectFactory oracleObjectFactory;

    @Inject
    public BaselineExpressionDao(JdbcTemplate jdbcTemplate, OracleObjectFactory oracleObjectFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.oracleObjectFactory = oracleObjectFactory;
    }

    //TODO: allow fetching by species
    public ImmutableList<BaselineExperimentExpression> fetchAverageExpressionByExperimentAssayGroup(final Collection<String> geneIds) {
        if (geneIds.isEmpty()) {
            return ImmutableList.of();
        }

        LOGGER.debug("fetchAverageExpressionByExperimentAssayGroup for {} genes", geneIds.size());

        Stopwatch stopwatch = Stopwatch.createStarted();

        try {

            DatabaseQuery<Object> baselineExpressionQuery = buildSelect(geneIds);

            final ImmutableList.Builder<BaselineExperimentExpression> builder = ImmutableList.builder();

            final MutableInt numberOfGenesExpressedInCurrentExperiment = new MutableInt(0);

            jdbcTemplate.query(baselineExpressionQuery.getQuery(),
                    new RowCallbackHandler() {
                        @Override
                        public void processRow(ResultSet rs) throws SQLException {
                            String experimentAccession = rs.getString(BaselineQueryBuilder.EXPERIMENT);
                            String assayGroupId = rs.getString(BaselineQueryBuilder.ASSAY_GROUP_ID);

                            if (assayGroupId == null) {
                                // every-time we see a null assaygroupid, this is the beginning of rows for another experiment
                                // and this row will contain the experiment level totals
                                double numberOfGenesExpressed = rs.getInt(BaselineQueryBuilder.NUMBER_GENES_EXPRESSED);
                                numberOfGenesExpressedInCurrentExperiment.setValue(numberOfGenesExpressed);
                                return;
                            }

                            double expression = BaselineExpressionLevelRounder.round(rs.getDouble
                                    (BaselineQueryBuilder.EXPRESSION) / numberOfGenesExpressedInCurrentExperiment.intValue());
                            BaselineExperimentExpression bslnExpression = BaselineExperimentExpression.create(experimentAccession, assayGroupId, expression);

                            builder.add(bslnExpression);
                        }
                    },
                    baselineExpressionQuery.getParameters().toArray());

            ImmutableList<BaselineExperimentExpression> results = builder.build();

            stopwatch.stop();
            LOGGER.debug("fetchAverageExpressionByExperimentAssayGroup returned {} results in {} seconds", results.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D);

            return results;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

    }

    DatabaseQuery<Object> buildSelect(Collection<String> geneIds) {
        BaselineQueryBuilder builder = new BaselineQueryBuilder();
        builder.withGeneIds(oracleObjectFactory.createOracleArrayForIdentifiers(geneIds));
        return builder.build();
    }

}
