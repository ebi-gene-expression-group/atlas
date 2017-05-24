package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.ArrayUtils;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;

import java.util.Map;

public class MicroarrayProfile extends DifferentialProfile<MicroarrayExpression> {

    // is part of the identity of the profile, ie: you can have two profiles for same
    // gene but different design element, eg: http://ves-hx-76:8080/gxa/experiments/E-MTAB-1066?geneQuery=Mbs
    private final String designElementName;

    public MicroarrayProfile(String geneId, String geneName, String designElementName) {
        super(geneId, geneName);
        this.designElementName = designElementName;
    }

    @Override
    public String[] identifiers(){
        return (String[]) ArrayUtils.add(super.identifiers(), designElementName);
    }

    @Override
    public Map<String,String> properties(){
        return ImmutableMap.<String,String>builder().putAll(super.properties()).put("designElement", designElementName).build();
    }

}
