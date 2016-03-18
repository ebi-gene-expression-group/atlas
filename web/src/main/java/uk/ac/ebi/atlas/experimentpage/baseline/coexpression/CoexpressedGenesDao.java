package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import com.google.common.collect.ImmutableSet;
import org.apache.velocity.util.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class CoexpressedGenesDao {

    @Inject
    private JdbcTemplate jdbcTemplate;

    public ImmutableSet<String> coexpressedGenesFor(String experiment, String identifier){
        List<String> res = jdbcTemplate.queryForList(
                "SELECT CE_IDENTIFIERS " +
                "FROM RNASEQ_BSLN_CE_PROFILES " +
                "WHERE EXPERIMENT=? AND IDENTIFIER=?",
                new String [] {experiment,identifier},
                String.class);
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for(String resultRow: res){
            for(String splitted: StringUtils.split(resultRow,",")){
                builder.add(splitted);
            }
        }
        return builder.build();
    }
}
