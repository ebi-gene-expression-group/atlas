package uk.ac.ebi.atlas.solr.index;

import org.apache.solr.client.solrj.beans.Field;

public class PropertyDocument {

    @Field("bioentity_identifier")
    private String bioentityIdentifier;

    @Field("bioentity_type")
    private String bioentityType;

    @Field("species")
    private String species;

    @Field("property_value")
    private String value;

    @Field("property_name")
    private String name;

    public String getBioentityType() {
        return bioentityType;
    }

    public void setBioentityType(String bioentityType) {
        this.bioentityType = bioentityType;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
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

    public String getBioentityIdentifier() {
        return bioentityIdentifier;
    }

    public void setBioentityIdentifier(String bioentityIdentifier) {
        this.bioentityIdentifier = bioentityIdentifier;
    }
}
