package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.ArrayUtils;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;

import java.util.Map;

public class MicroarrayProfile extends DifferentialProfile<MicroarrayExpression, MicroarrayProfile> {
    // Part of the identity of the profile, i.e.: you can have two profiles for same gene but different design element
    // E.g.: http://ves-hx-76:8080/gxa/experiments/E-MTAB-1066?geneQuery=Mbs
    private final String designElementName;

    // Some experiments have multiple array designs, e.g. E-MEXP-893
    // They are read in separately so it should also be part of identity of that profile, but I think it doesn’t matter
    public MicroarrayProfile(String geneId, String geneName, String designElementName) {
        super(geneId, geneName);
        this.designElementName = designElementName;
    }

    @Override
    public String[] identifiers() {
        return (String[]) ArrayUtils.add(super.identifiers(), designElementName);
    }

    @Override
    public Map<String, String> properties() {
        return ImmutableMap.<String, String>builder()
                .putAll(super.properties())
                .put("designElement", designElementName)
                .build();
    }


    @Override
    protected MicroarrayProfile createEmptyCopy() {
        return new MicroarrayProfile(getId(), getName(), designElementName);
    }

    public String getDesignElementName() {
        return designElementName;
    }
}
