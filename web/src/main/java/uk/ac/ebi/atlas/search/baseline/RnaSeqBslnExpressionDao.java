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

package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import uk.ac.ebi.atlas.search.DatabaseQuery;
import uk.ac.ebi.atlas.search.OracleObjectFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Named
@Scope("prototype")
public class RnaSeqBslnExpressionDao {

    private static final Logger LOGGER = Logger.getLogger(RnaSeqBslnExpressionDao.class);

    private final JdbcTemplate jdbcTemplate;

    private OracleObjectFactory oracleObjectFactory;

    @Inject
    public RnaSeqBslnExpressionDao(JdbcTemplate jdbcTemplate, OracleObjectFactory oracleObjectFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.oracleObjectFactory = oracleObjectFactory;
    }

    //TODO: allow fetching by species
    public List<RnaSeqBslnExpression> fetchNonSpecificExpression(Collection<String> geneIds) {
        if (geneIds.isEmpty()) {
            return ImmutableList.of();
        }

        LOGGER.debug(String.format("fetchNonSpecificExpression for %s genes", geneIds.size()));

        Stopwatch stopwatch = Stopwatch.createStarted();

        DatabaseQuery<Object> baselineExpressionQuery = buildSelect(geneIds);


        List<RnaSeqBslnExpression> results;

        try {
            results = jdbcTemplate.query(baselineExpressionQuery.getQuery(),
                    new RowMapper<RnaSeqBslnExpression>() {
                        @Override
                        public RnaSeqBslnExpression mapRow(ResultSet rs, int rowNum) throws SQLException {
                            String geneId = rs.getString(RnaSeqBslnQueryBuilder.IDENTIFIER);
                            String experimentAccession = rs.getString(RnaSeqBslnQueryBuilder.EXPERIMENT);
                            String assayGroupId = rs.getString(RnaSeqBslnQueryBuilder.ASSAY_GROUP_ID);
                            double expression = rs.getDouble(RnaSeqBslnQueryBuilder.EXPRESSION);
                            return RnaSeqBslnExpression.create(geneId, experimentAccession, assayGroupId, expression);
                        }
                    },
                    baselineExpressionQuery.getParameters().toArray());

            stopwatch.stop();

            LOGGER.debug(String.format("fetchNonSpecificExpression returned %s results in %.2f seconds", results.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

        return results;

    }

    DatabaseQuery<Object> buildSelect(Collection<String> geneIds) {
        RnaSeqBslnQueryBuilder builder = new RnaSeqBslnQueryBuilder();
        builder.withGeneIds(oracleObjectFactory.createOracleArrayForIdentifiers(geneIds));
        return builder.build();
    }

}
