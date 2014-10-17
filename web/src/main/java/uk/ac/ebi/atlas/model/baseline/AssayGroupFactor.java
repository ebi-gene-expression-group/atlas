package uk.ac.ebi.atlas.model.baseline;

import javax.annotation.Nullable;

public class AssayGroupFactor implements Comparable<AssayGroupFactor>{

    private String assayGroupId;
    private Factor factor;

    public AssayGroupFactor(String assayGroupId, Factor factor) {
        this.assayGroupId = assayGroupId;
        this.factor = factor;
    }

    //used in heatmap.tag
    public String getAssayGroupId() {
        return assayGroupId;
    }

    public Factor getFactor() {
        return factor;
    }

    public String getValue() {
        return factor.getValue();
    }

    public String getType() {
        return factor.getType();
    }

    //used in heatmap.tag
    public @Nullable String getValueOntologyTermId() {
        return factor.getValueOntologyTermId();
    }

    @Override
    public int compareTo(AssayGroupFactor assayGroupFactor) {
        return factor.compareTo(assayGroupFactor.getFactor());
    }
}
