/*
 * Copyright 2008-2016 Gene Expression Team, EMBL-European Bioinformatics Institute
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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractInterruptibleBatchPreparedStatementSetter;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Named
@Scope("singleton")
public class BaselineCoexpressionProfileDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineCoexpressionProfileDAO.class);

    private static final String COEXPRESSIONS_INSERT = "INSERT INTO RNASEQ_BSLN_CE_PROFILES (EXPERIMENT, IDENTIFIER, CE_IDENTIFIERS) VALUES (?, ?, ?)";
    private static final int EXPERIMENT = 1;
    private static final int IDENTIFIER = 2;
    private static final int CE_IDENTIFIERS = 3;

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public BaselineCoexpressionProfileDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int loadCoexpressionsProfile(final String experimentAccession, ObjectInputStream<BaselineCoexpressionProfile> coexpressionsProfileInputStream)  {
        LOGGER.info(String.format("loadCoexpressions for experiment %s begin", experimentAccession));

        int[] rows = {};

        // try with resources: input stream will auto-close if DataAccessException is thrown by jdbcTemplate
        try (ObjectInputStream<BaselineCoexpressionProfile> source = coexpressionsProfileInputStream) {
            rows = jdbcTemplate.batchUpdate(COEXPRESSIONS_INSERT, new AbstractInterruptibleBatchPreparedStatementSetter() {

                @Override
                protected boolean setValuesIfAvailable(PreparedStatement ps, int i) throws SQLException {
                    BaselineCoexpressionProfile coexpressionProfile = source.readNext();

                    if (coexpressionProfile == null) {
                        return false;
                    }

                    ps.setString(EXPERIMENT, experimentAccession);
                    ps.setString(IDENTIFIER, coexpressionProfile.geneID());
                    ps.setClob(CE_IDENTIFIERS, new StringReader(coexpressionProfile.coexpressedGenesAsString()));

                    return true;
                }

            });
        } catch (IOException e) {
            LOGGER.warn("Cannot close BaselineCoexpressionsInputStream: {}", e.getMessage());
        }

        LOGGER.info("loadCoexpressions for experiment {} complete", experimentAccession);

        return rows.length;
    }

    public int deleteCoexpressionsProfile(String experimentAccession) {
        LOGGER.info("deleteCoexpressions for experiment {}", experimentAccession);
        return jdbcTemplate.update("DELETE FROM RNASEQ_BSLN_CE_PROFILES WHERE EXPERIMENT=?", experimentAccession);
    }

}
