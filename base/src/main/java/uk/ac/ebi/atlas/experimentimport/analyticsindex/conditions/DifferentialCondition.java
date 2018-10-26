package uk.ac.ebi.atlas.experimentimport.analyticsindex.conditions;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

public class DifferentialCondition extends Condition {
    private String contrastId;

    public DifferentialCondition(String experimentAccession,
                                 String assayGroupId,
                                 String contrastId,
                                 Collection<String> values) {
        super(experimentAccession, assayGroupId, values);
        this.contrastId = contrastId;
    }

    public String getContrastId() {
        return contrastId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contrastId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DifferentialCondition other = (DifferentialCondition) obj;
        return Objects.equals(this.getExperimentAccession(), other.getExperimentAccession()) &&
               Objects.equals(this.getAssayGroupId(), other.getAssayGroupId()) &&
               Objects.equals(this.contrastId, other.contrastId) &&
               CollectionUtils.isEqualCollection(this.getValues(), other.getValues());
    }
}
