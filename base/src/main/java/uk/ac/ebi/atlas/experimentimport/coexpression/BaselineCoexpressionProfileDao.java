package uk.ac.ebi.atlas.experimentimport.coexpression;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BaselineCoexpressionProfileDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineCoexpressionProfileDao.class);

    private static final String COEXPRESSIONS_INSERT = "INSERT INTO RNASEQ_BSLN_CE_PROFILES " +
            "(EXPERIMENT, IDENTIFIER, CE_IDENTIFIERS) VALUES (?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public BaselineCoexpressionProfileDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int loadCoexpressionsProfile(final String experimentAccession,
                                        final ObjectInputStream<BaselineCoexpressionProfile>
                                                coexpressionsProfileInputStream)  {
        LOGGER.info("loadCoexpressionsProfile for experiment {} begin", experimentAccession);

        ImmutableList.Builder<Object[]> bcpBuilder = ImmutableList.builder();
        BaselineCoexpressionProfile bcp;
        while ((bcp = coexpressionsProfileInputStream.readNext()) != null) {
            bcpBuilder.add(new Object[] {experimentAccession, bcp.geneID(), bcp.coexpressedGenesAsString()});
        }

        int[] rows = jdbcTemplate.batchUpdate(COEXPRESSIONS_INSERT, bcpBuilder.build());

        LOGGER.info("loadCoexpressionsProfile for experiment {} complete", experimentAccession);
        return rows.length;
    }

    public int deleteCoexpressionsProfile(String experimentAccession) {
        LOGGER.info("deleteCoexpressions for experiment {}", experimentAccession);
        return jdbcTemplate.update("DELETE FROM RNASEQ_BSLN_CE_PROFILES WHERE EXPERIMENT=?", experimentAccession);
    }

}
