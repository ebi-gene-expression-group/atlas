package uk.ac.ebi.atlas.experimentimport.analytics.baseline.transcript;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractInterruptibleBatchPreparedStatementSetter;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

@Named
public class RnaSeqBaselineTranscriptDao {

    private static final Logger LOGGER = Logger.getLogger(RnaSeqBaselineTranscriptDao.class);

    private static final String INSERT_QUERY = "INSERT INTO RNASEQ_BSLN_TRANSCRIPTS " +
            "(EXPERIMENT, GENE_IDENTIFIER, TRANSCRIPT_IDENTIFIER, TRANSCRIPT_EXPRESSIONS) VALUES (?, ?, ?, ?)";
    private static final int FIRST_INDEX = 1;
    private static final int SECOND_INDEX = 2;
    private static final int THIRD_INDEX = 3;
    private static final int FOURTH_INDEX = 4;
    public static final String DELETE_QUERY = "UPDATE RNASEQ_BSLN_TRANSCRIPTS SET ISACTIVE='F' WHERE experiment = ?";
    public static final String DELETE_INACTIVE_QUERY = "DELETE FROM RNASEQ_BSLN_TRANSCRIPTS WHERE ISACTIVE = 'F'";

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public RnaSeqBaselineTranscriptDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(final String experimentAccession, final Iterator<RnaSeqBaselineTranscript> source)  {
        LOGGER.info(String.format("insert for experiment %s begin", experimentAccession));

        final MutableInt count = new MutableInt(0);

        jdbcTemplate.batchUpdate(INSERT_QUERY, new AbstractInterruptibleBatchPreparedStatementSetter() {

            @Override
            protected boolean setValuesIfAvailable(PreparedStatement ps, int i) throws SQLException {
                if (!source.hasNext()) {
                    return false;
                }

                count.increment();

                RnaSeqBaselineTranscript transcript = source.next();

                ps.setString(FIRST_INDEX, experimentAccession);
                ps.setString(SECOND_INDEX, transcript.geneId());
                ps.setString(THIRD_INDEX, transcript.transcriptId());
                Double[] levels = transcript.levels();
                String expressionsSerialized = Joiner.on(",").join(levels);
                ps.setString(FOURTH_INDEX, expressionsSerialized);

                return true;
            }
        });

        LOGGER.info(String.format("insert for experiment %s complete: %s transcripts", experimentAccession, count.intValue()));
    }

    public void delete(String experimentAccession) {
        LOGGER.info("delete for experiment " + experimentAccession);
        jdbcTemplate.update(DELETE_QUERY, experimentAccession);
    }

    public void deleteAllInactiveTranscripts() {
        int count = jdbcTemplate.update(DELETE_INACTIVE_QUERY);
        LOGGER.info(String.format("deleteAllInactiveTranscripts %s rows deleted",count));
    }

}
