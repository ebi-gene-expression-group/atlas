package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import com.google.common.collect.ImmutableSet;
import org.apache.velocity.util.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public <T extends Profile> Map<String, ImmutableSet<String>> coexpressedGenesForResults(Experiment experiment,
                                                                                             GeneProfilesList<T> baselineProfiles){
        Map<String, ImmutableSet<String>> result = new HashMap<>();
        for(String geneName: baselineProfiles.extractGeneNames()){
            result.put(geneName, coexpressedGenesFor(experiment.getAccession(), geneName));
        }

        return result;
    }
}
