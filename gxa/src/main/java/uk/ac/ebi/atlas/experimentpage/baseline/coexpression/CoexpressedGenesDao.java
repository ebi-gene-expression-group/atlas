package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import com.google.common.collect.ImmutableList;
import org.apache.velocity.util.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CoexpressedGenesDao {

    private final JdbcTemplate jdbcTemplate;

    public CoexpressedGenesDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public ImmutableList<String> coexpressedGenesFor(String experiment, String identifier){
        List<String> res = jdbcTemplate.queryForList(
                "SELECT CE_IDENTIFIERS " +
                "FROM RNASEQ_BSLN_CE_PROFILES " +
                "WHERE EXPERIMENT=? AND IDENTIFIER=?",
                new String [] {experiment,identifier},
                String.class);

        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for(String resultRow: res){
            for(String geneId: StringUtils.split(resultRow,",")){
                if(!geneId.equalsIgnoreCase(identifier)) {
                    builder.add(geneId);
                }
            }
        }
        return builder.build();
    }

}
