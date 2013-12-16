package uk.ac.ebi.atlas.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractInterruptibleBatchPreparedStatementSetter;
import uk.ac.ebi.atlas.dao.dto.BaselineExpressionDto;
import uk.ac.ebi.atlas.utils.AutoCloseableIterable;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

@Named
public class BaselineExpressionDao {


    private static final String TRANSCRIPT_PROFILE_INSERT = "INSERT INTO RNASEQ_BSLN_EXPRESSION " +
            "(identifier, experiment, assaygroupid, isactive, expression) VALUES (?, ?, ?, ?, ?, ?)";
    private static final int IDENTIFIER = 1;
    private static final int EXPERIMENT = 2;
    private static final int ASSAY_GROUP_ID = 3;
    private static final int IS_ACTIVE = 4;
    private static final int EXPRESSION = 5;

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public BaselineExpressionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addBaselineExpressions(final String experimentAccession, AutoCloseableIterable<BaselineExpressionDto> baselineExpressionDTOs) throws IOException {

        // will autoclose iterable if DataAccessException thrown by jdbcTemplate
        try (AutoCloseableIterable<BaselineExpressionDto> source = baselineExpressionDTOs) {

            final Iterator<BaselineExpressionDto> iterator = source.iterator();

            jdbcTemplate.batchUpdate(TRANSCRIPT_PROFILE_INSERT, new AbstractInterruptibleBatchPreparedStatementSetter() {

                @Override
                protected boolean setValuesIfAvailable(PreparedStatement ps, int i) throws SQLException {
                    if (!iterator.hasNext()) {
                        return false;
                    }

                    BaselineExpressionDto baselineExpressionDto = iterator.next();

                    ps.setString(IDENTIFIER, baselineExpressionDto.getGeneId());
                    ps.setString(EXPERIMENT, experimentAccession);
                    ps.setString(ASSAY_GROUP_ID, baselineExpressionDto.getAssayGroupId());
                    ps.setString(IS_ACTIVE, "T");
                    ps.setDouble(EXPRESSION, baselineExpressionDto.getExpressionLevel());

                    return true;
                }
            });
        }

    }

}
