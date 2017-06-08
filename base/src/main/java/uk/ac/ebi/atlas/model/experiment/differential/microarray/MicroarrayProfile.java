package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.ArrayUtils;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;

import java.util.Map;

public class MicroarrayProfile extends DifferentialProfile<MicroarrayExpression, MicroarrayProfile> {

    // is part of the identity of the profile, ie: you can have two profiles for same
    // gene but different design element, eg: http://ves-hx-76:8080/gxa/experiments/E-MTAB-1066?geneQuery=Mbs
    private String designElementName;

    private MicroarrayProfile(){super();}
    /*
    Some experiments have multiple array designs, e.g. E-MEXP-893
    They are read in separately so it should also become part of identity of that profile, but I think it doesn't matter.
     */
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


    @Override
    protected MicroarrayProfile createEmptyCopy() {
        return new MicroarrayProfile(getId(), getName(), designElementName);
    }

    @Override
    public void write(Kryo kryo, Output output) {
        super.write(kryo, output);
        output.writeString(designElementName);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        super.read(kryo, input);
        designElementName = input.readString();
    }

}
