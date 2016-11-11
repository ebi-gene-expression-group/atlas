package uk.ac.ebi.atlas.solr.admin.index.conditions;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterables;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Collection;
import java.util.Objects;

public class Condition {

    @Field("experiment_accession")
    private String experimentAccession;

    @Field("assay_group_id")
    private String assayGroupId;

    @Field("conditions")
    private Collection<String> values;

    public Condition() {
    }

    public Condition(String experimentAccession, String assayGroupId, Collection<String> values) {
        this.experimentAccession = experimentAccession;
        this.assayGroupId = assayGroupId;
        this.values = values;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public void setExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
    }

    public String getAssayGroupId() {
        return assayGroupId;
    }

    public void setAssayGroupId(String assayGroupId) {
        this.assayGroupId = assayGroupId;
    }

    public Collection<String> getValues() {
        return values;
    }

    public void setValues(Collection<String> values) {
        this.values = values;
    }

    @Override
    public int hashCode() {return Objects.hash(experimentAccession, assayGroupId, values);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        final Condition other = (Condition) obj;
        return Objects.equals(this.experimentAccession, other.experimentAccession) && Objects.equals(this.assayGroupId, other.assayGroupId) && Iterables.elementsEqual(this.values, other.values);
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
