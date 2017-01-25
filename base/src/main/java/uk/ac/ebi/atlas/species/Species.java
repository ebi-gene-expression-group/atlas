package uk.ac.ebi.atlas.species;

import java.util.List;
import java.util.Map;

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

    public String getReferenceName() {
        return mappedProperties.referenceName();
    }

    public String getEnsemblName() {
        return mappedProperties.ensemblName();
    }

    public Map<String, List<String>> getResources() {
        return mappedProperties.resources();
    }

    public String getKingdom() {
        return mappedProperties.kingdom();
    }

    public String getDefaultQueryFactorType() {
        return mappedProperties.defaultQueryFactorType();
    }

    public boolean isPlant() {
        return "plants".equalsIgnoreCase(mappedProperties.kingdom());
    }

    public boolean isUnknown() {
        return mappedProperties == SpeciesProperties.UNKNOWN;
    }
}
