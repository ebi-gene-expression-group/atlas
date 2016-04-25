package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.velocity.util.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            for(String splitted: StringUtils.split(resultRow,",")){
                if(!splitted.equals(identifier)) {
                    builder.add(splitted);
                } else {
                    ;
                }
            }
        }
        return builder.build();
    }

    public <T extends Profile> Map<String, ImmutableList<String>> coexpressedGenesForResults(Experiment experiment,
                                                                                             GeneProfilesList<T> baselineProfiles){
        Map<String, ImmutableList<String>> result = new HashMap<>();
        for(String geneName: baselineProfiles.extractGeneNames()){
            ImmutableList<String> resultForThisGene = coexpressedGenesFor(experiment.getAccession(), geneName);
            if (resultForThisGene.size()> 0 ){
                result.put(geneName, resultForThisGene);
            }
        }

        return result;
    }

}
