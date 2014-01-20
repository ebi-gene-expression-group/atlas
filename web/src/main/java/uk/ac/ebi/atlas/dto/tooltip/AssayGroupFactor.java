package uk.ac.ebi.atlas.dto.tooltip;

import uk.ac.ebi.atlas.model.baseline.Factor;

public class AssayGroupFactor implements Comparable<AssayGroupFactor>{

    private String assayGroupId;
    private Factor factor;

    public AssayGroupFactor(String assayGroupId, Factor factor) {
        this.assayGroupId = assayGroupId;
        this.factor = factor;
    }

    //used in heatmap-matrix-gene-oriented.jsp
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

    //used in heatmap-matrix-gene-oriented.jsp
    public String getValueOntologyTerm() {
        return factor.getValueOntologyTerm();
    }

    @Override
    public int compareTo(AssayGroupFactor assayGroupFactor) {
        return factor.compareTo(assayGroupFactor.getFactor());
    }
}
