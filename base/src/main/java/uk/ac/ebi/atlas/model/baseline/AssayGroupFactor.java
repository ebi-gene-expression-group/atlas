package uk.ac.ebi.atlas.model.baseline;

import com.google.common.base.MoreObjects;

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
        // We are assuming that the only relevant ontology term for tissues is the first one
        return factor.getValueOntologyTerms().isEmpty() ? null : factor.getValueOntologyTerms().iterator().next().accession();
    }

    @Override
    public int compareTo(AssayGroupFactor assayGroupFactor) {
        return factor.compareTo(assayGroupFactor.getFactor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssayGroupFactor that = (AssayGroupFactor) o;

        if (!assayGroupId.equals(that.assayGroupId)) return false;
        if (!factor.equals(that.factor)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = assayGroupId.hashCode();
        result = 31 * result + factor.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("assayGroupId", assayGroupId)
                .add("factor", factor)
                .toString();
    }
}
