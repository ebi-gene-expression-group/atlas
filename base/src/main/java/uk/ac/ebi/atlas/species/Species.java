package uk.ac.ebi.atlas.species;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class Species {

    private final String name;
    private final SpeciesProperties mappedProperties;

    public Species(String name, SpeciesProperties mappedProperties) {
        this.name = name;
        this.mappedProperties = mappedProperties;
    }

    //the string that came to us
    public String getName() {
        return name;
    }

    //a nice looking version of getEnsemblName
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

    public Map<String, Object> getAttributes(){
        return ImmutableMap.<String, Object>builder()
                .put("species",name)
                .put("speciesReferenceName", getReferenceName())
                .put("resources",getResources())
                .build();
    }
}
