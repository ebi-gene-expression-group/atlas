package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import com.google.common.collect.ImmutableList;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Named;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Named
public class CoexpressedGenesDao {
    private final JdbcTemplate jdbcTemplate;
    protected static final String CE_GENES_SQL_QUERY_TEMPLATE =
            "SELECT CE_IDENTIFIERS FROM RNASEQ_BSLN_CE_PROFILES WHERE EXPERIMENT=? AND IDENTIFIER=?";

    public CoexpressedGenesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ImmutableList<String> coexpressedGenesFor(String experiment, String identifier) {
        try {
            String result =
                    jdbcTemplate.queryForObject(CE_GENES_SQL_QUERY_TEMPLATE, String.class, experiment, identifier);

            return ImmutableList.copyOf(
                    Stream.of(result.split(","))
                           .filter(geneId -> !geneId.equalsIgnoreCase(identifier))
                           .collect(toList()));

        } catch (IncorrectResultSizeDataAccessException e) {
            return ImmutableList.of();
        }
    }
}
