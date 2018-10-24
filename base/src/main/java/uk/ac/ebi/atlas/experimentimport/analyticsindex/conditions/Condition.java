package uk.ac.ebi.atlas.experimentimport.analyticsindex.conditions;

import com.google.common.base.MoreObjects;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

public class Condition {
    private String experimentAccession;
    private String assayGroupId;
    private Collection<String> values;

    public Condition(String experimentAccession, String assayGroupId, Collection<String> values) {
        this.experimentAccession = experimentAccession;
        this.assayGroupId = assayGroupId;
        this.values = values;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public String getAssayGroupId() {
        return assayGroupId;
    }

    public Collection<String> getValues() {
        return values;
    }

    @Override
    public int hashCode() {
        return Objects.hash(experimentAccession, assayGroupId, values);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Condition other = (Condition) obj;
        return Objects.equals(this.experimentAccession, other.experimentAccession) &&
               Objects.equals(this.assayGroupId, other.assayGroupId) &&
               CollectionUtils.isEqualCollection(this.values, other.values);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("experimentAccession", experimentAccession)
                .add("assayGroupId", assayGroupId)
                .add("values", values)
                .toString();
    }
}
