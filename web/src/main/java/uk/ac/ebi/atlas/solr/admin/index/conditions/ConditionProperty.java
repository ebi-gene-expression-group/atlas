package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.solr.client.solrj.beans.Field;

public class ConditionProperty {

    @Field("experiment_accession")
    private String experimentAccession;

    @Field("group_type")
    private String groupType;

    @Field("contrast_id")
    private String contrastId;

    @Field("property_value")
    private String value;

    @Field("property_name")
    private String name;

    public ConditionProperty(String experimentAccession, String groupType, String contrastId, String name, String value) {
        this.experimentAccession = experimentAccession;
        this.groupType = groupType;
        this.contrastId = contrastId;
        this.value = value;
        this.name = name;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
