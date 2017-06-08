package uk.ac.ebi.atlas.species;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;

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

    public ImmutableCollection<ImmutableMap<String, String>> getGenomeBrowsers() {
        return mappedProperties.getResourcesOfType(SpeciesProperties.GENOME_BROWSER_TYPE);
    }

    public ImmutableMap<String, String> getAttributes(){
        return ImmutableMap.of("species", name, "speciesReferenceName", getReferenceName());
    }

}
