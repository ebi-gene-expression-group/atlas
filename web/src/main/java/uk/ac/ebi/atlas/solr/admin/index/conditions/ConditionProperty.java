package uk.ac.ebi.atlas.solr.admin.index.conditions;

import org.apache.solr.client.solrj.beans.Field;

public class ConditionProperty {
    private String experimentAccession;
    private String groupType;
    private String contrastId;
    @Field("property_value")
    private String value;

    @Field("property_name")
    private String name;
}
