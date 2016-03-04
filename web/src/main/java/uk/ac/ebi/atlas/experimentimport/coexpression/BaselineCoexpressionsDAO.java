/*
 * Copyright 2008-2016 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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
 * Expression Atlas:
 * https://www.ebi.ac.uk/gxa
 *
 * For further details of the Expression Atlas project, including source code,
 * downloads and documentation, please see:
 * https://github.com/gxa/atlas
 */

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 04/03/2016.
 */

package uk.ac.ebi.atlas.experimentimport.coexpression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractInterruptibleBatchPreparedStatementSetter;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Named
@Scope("singleton")
public class BaselineCoexpressionsDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineCoexpressionsDAO.class);

    private static final String COEXPRESSIONS_INSERT = "INSERT INTO RNASEQ_BSLN_COEXPRESSION (EXPERIMENT, IDENTIFIER, CE_STATISTIC, CE_IDENTIFIER) VALUES (?, ?, ?, ?)";
    private static final int EXPERIMENT = 1;
    private static final int IDENTIFIER = 2;
    private static final int CE_STATISTIC = 3;
    private static final int CE_IDENTIFIER = 4;

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public BaselineCoexpressionsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loadCoexpressions(final String experimentAccession, ObjectInputStream<BaselineCoexpression> coexpressionsInputStream)  {
        LOGGER.info(String.format("loadCoexpressions for experiment %s begin", experimentAccession));

        // try with resources: input stream will auto-close if DataAccessException is thrown by jdbcTemplate
        try (ObjectInputStream<BaselineCoexpression> source = coexpressionsInputStream) {
            jdbcTemplate.batchUpdate(COEXPRESSIONS_INSERT, new AbstractInterruptibleBatchPreparedStatementSetter() {

                @Override
                protected boolean setValuesIfAvailable(PreparedStatement ps, int i) throws SQLException {
                    BaselineCoexpression coexpressionProfile = source.readNext();

                    if (coexpressionProfile == null) {
                        return false;
                    }

                    ps.setString(EXPERIMENT, experimentAccession);
                    ps.setString(IDENTIFIER, coexpressionProfile.geneID());
                    ps.setDouble(CE_STATISTIC, coexpressionProfile.ceStatistic());
                    ps.setString(CE_IDENTIFIER, coexpressionProfile.ceGeneID());

                    return true;
                }
            });
        } catch (IOException e) {
            LOGGER.warn("Cannot close BaselineCoexpressionsInputStream: {}", e.getMessage());
        }

        LOGGER.info("loadCoexpressions for experiment {} complete", experimentAccession);
    }

    public void deleteCoexpressions(String experimentAccession) {
        LOGGER.info("deleteCoexpressions for experiment {}", experimentAccession);
        jdbcTemplate.update("DELETE FROM RNASEQ_BSLN_COEXPRESSION WHERE EXPERIMENT=?", experimentAccession);
    }

}
