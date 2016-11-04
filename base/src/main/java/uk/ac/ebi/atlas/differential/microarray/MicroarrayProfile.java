
package uk.ac.ebi.atlas.differential.microarray;

import com.google.common.base.Objects;
import uk.ac.ebi.atlas.differential.DifferentialProfile;

public class MicroarrayProfile extends DifferentialProfile<MicroarrayExpression> {

    // is part of the identity of the profile, ie: you can have two profiles for same
    // gene but different design element, eg: http://ves-hx-76:8080/gxa/experiments/E-MTAB-1066?geneQuery=Mbs
    private final String designElementName;

    public MicroarrayProfile(String geneId, String geneName, String designElementName) {
        super(geneId, geneName);
        this.designElementName = designElementName;
    }

    //It's used in jsp EL as well as serialization
    @Override
    public String getDesignElementName() {
        return designElementName;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("geneName", getName())
                .add("designElementName", designElementName)
                .add("expressions", expressionsByCondition)
                .toString();
    }

    @Override
    public MicroarrayProfile add(MicroarrayExpression expression) {
        return (MicroarrayProfile)super.add(expression);
    }

    public static MicroarrayProfile create(String geneId, String geneName, String designElementName) {
        return new MicroarrayProfile(geneId, geneName, designElementName);
    }
}
