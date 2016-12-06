package uk.ac.ebi.atlas.species;

import java.util.Collection;

public class Species {

    private final String name;
    private final SpeciesProperties mappedProperties;

    public Species(String name, SpeciesProperties mappedProperties) {
        this.name = name;
        this.mappedProperties = mappedProperties;
    }

    public String getName() {
        return name;
    }

    public String getCanonicalName() {
        return mappedProperties.canonicalName();
    }

    public String getKingdom() {
        return mappedProperties.kingdom();
    }

    public String getDefaultQueryFactorType() {
        return mappedProperties.defaultQueryFactorType();
    }

    public Collection<String> getResources(String type) {
        return mappedProperties.resources().get(type);
    }
}
