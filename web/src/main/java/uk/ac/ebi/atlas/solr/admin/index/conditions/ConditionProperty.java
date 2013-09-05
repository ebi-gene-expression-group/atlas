package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Collection;
import java.util.Objects;

public class ConditionProperty {

    @Field("experiment_accession")
    private String experimentAccession;

    @Field("group_type")
    private String groupType;

    @Field("contrast_id")
    private String contrastId;

//    @Field("property_value")
//    private String value;
//
//    @Field("property_name")
//    private String name;

    @Field("property_values")
    private Collection<String> values;

    public ConditionProperty() {
    }

    public ConditionProperty(String experimentAccession, String groupType, String contrastId, Collection<String> values) {
        this.experimentAccession = experimentAccession;
        this.groupType = groupType;
        this.contrastId = contrastId;
        this.values = values;
    }

    @Override
    public int hashCode() {return Objects.hash(experimentAccession, groupType, contrastId, values);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        final ConditionProperty other = (ConditionProperty) obj;
        return Objects.equals(this.experimentAccession, other.experimentAccession) && Objects.equals(this.groupType, other.groupType) && Objects.equals(this.contrastId, other.contrastId) && Objects.equals(this.values, other.values);
    }

    public String getExperimentAccession() {

        return experimentAccession;
    }

    public void setExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getContrastId() {
        return contrastId;
    }

    public void setContrastId(String contrastId) {
        this.contrastId = contrastId;
    }

    public Collection<String> getValues() {
        return values;
    }

    public void setValues(Collection<String> values) {
        this.values = values;
    }
}
