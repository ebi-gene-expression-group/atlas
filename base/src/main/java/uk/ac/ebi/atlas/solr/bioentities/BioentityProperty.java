package uk.ac.ebi.atlas.solr.bioentities;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.beans.Field;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;

import java.util.Objects;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.ID_PROPERTY_NAMES;

public class BioentityProperty {

    private static final ImmutableMap<String, Integer> PROPERTY_TO_WEIGHTS = ImmutableMap.of(
            "symbol", 20,
            "ensgene", 15,
            "synonym", 10
    );
    private static final int DEFAULT_WEIGHT = 0;

    @Field("bioentity_identifier")
    private String bioentityIdentifier;

    @Field("species")
    private String species;

    @Field("property_value")
    private String value;

    @Field("property_weight")
    private int propertyWeight;

    @Field("property_name_id_weight")
    private double propertyNameIdWeight;

    @Field("property_name")
    private String name;

    //Default constructor required by Solr API
    public BioentityProperty(){
    }

    public BioentityProperty(String bioentityIdentifier, String species, String name, String value) {
        checkArgument(StringUtils.isNotBlank(value), String.format("Invalid blank property value for %s – %s", bioentityIdentifier, name));

        this.species = species;
        this.name = name;
        this.bioentityIdentifier = bioentityIdentifier;
        this.value = value;

        this.propertyWeight = PROPERTY_TO_WEIGHTS.getOrDefault(name, DEFAULT_WEIGHT);
        this.propertyNameIdWeight = BioentityPropertyName.getByName(name).idWeight;
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

    public int getPropertyWeight() {
        return propertyWeight;
    }

    public void setPropertyWeight(int propertyWeight) {
        this.propertyWeight = propertyWeight;
    }

    public double getPropertyNameIdWeight() {
        return propertyNameIdWeight;
    }

    public void setPropertyNameIdWeight(double propertyNameIdWeight) {
        this.propertyNameIdWeight = propertyNameIdWeight;
    }


    public String getBioentityIdentifier() {
        return bioentityIdentifier;
    }

    public void setBioentityIdentifier(String bioentityIdentifier) {
        this.bioentityIdentifier = bioentityIdentifier;
    }

    @Override
    public int hashCode() {return Objects.hash(bioentityIdentifier,
                                                species,
                                                value,
                                                name);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        final BioentityProperty other = (BioentityProperty) obj;
        return Objects.equals(bioentityIdentifier, other.bioentityIdentifier)
                && Objects.equals(species, other.species)
                && Objects.equals(value, other.value)
                && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "BioentityProperty{" +
                "bioentityIdentifier='" + bioentityIdentifier + '\'' +
                ", species='" + species + '\'' +
                ", value='" + value + '\'' +
                ", propertyWeight=" + propertyWeight +
                ", propertyNameIdWeight=" + propertyNameIdWeight +
                ", name='" + name + '\'' +
                '}';
    }
}
