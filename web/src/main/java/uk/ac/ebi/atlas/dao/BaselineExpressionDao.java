package uk.ac.ebi.atlas.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractInterruptibleBatchPreparedStatementSetter;
import uk.ac.ebi.atlas.dao.dto.BaselineExpressionDto;
import uk.ac.ebi.atlas.dao.dto.BaselineExpressionDtoInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Named
public class BaselineExpressionDao {

    private static final Logger LOGGER = Logger.getLogger(BaselineExpressionDao.class);

    private static final String TRANSCRIPT_PROFILE_INSERT = "INSERT INTO RNASEQ_BSLN_EXPRESSIONS " +
            "(identifier, experiment, assaygroupid, isactive, expression) VALUES (?, ?, ?, ?, ?)";
    private static final int IDENTIFIER = 1;
    private static final int EXPERIMENT = 2;
    private static final int ASSAY_GROUP_ID = 3;
    private static final int IS_ACTIVE = 4;
    private static final int EXPRESSION = 5;
    public static final int BATCH_SIZE = 20000;

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public BaselineExpressionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loadBaselineExpressions(final String experimentAccession, BaselineExpressionDtoInputStream baselineExpressionDtos)  {
        LOGGER.info(String.format("loadBaselineExpressions for experiment %s begin", experimentAccession));

        // will autoclose if DataAccessException thrown by jdbcTemplate
        try (BaselineExpressionDtoInputStream source = baselineExpressionDtos) {

            jdbcTemplate.batchUpdate(TRANSCRIPT_PROFILE_INSERT, new AbstractInterruptibleBatchPreparedStatementSetter() {

                @Override
                protected boolean setValuesIfAvailable(PreparedStatement ps, int i) throws SQLException {
                    BaselineExpressionDto baselineExpressionDto = source.readNext();

                    if (baselineExpressionDto == null) {
                        return false;
                    }

                    ps.setString(IDENTIFIER, baselineExpressionDto.getGeneId());
                    ps.setString(EXPERIMENT, experimentAccession);
                    ps.setString(ASSAY_GROUP_ID, baselineExpressionDto.getAssayGroupId());
                    ps.setString(IS_ACTIVE, "T");
                    ps.setDouble(EXPRESSION, baselineExpressionDto.getExpressionLevel());

                    return true;
                }

                @Override
                public int getBatchSize() {
                    return BATCH_SIZE;
                }
            });
        } catch (IOException e) {
            LOGGER.warn(String.format("Cannot close BaselineExpressionDtoInputStream: %s", e.getMessage()));
        }

        LOGGER.info(String.format("loadBaselineExpressions for experiment %s complete", experimentAccession));
    }

    public void deleteBaselineExpressions(String experimentAccession) {
        LOGGER.info("deleteBaselineExpressions for experiment " + experimentAccession);
        jdbcTemplate.update("UPDATE RNASEQ_BSLN_EXPRESSIONS SET ISACTIVE='F' WHERE experiment = ?",
                experimentAccession);
    }

}
