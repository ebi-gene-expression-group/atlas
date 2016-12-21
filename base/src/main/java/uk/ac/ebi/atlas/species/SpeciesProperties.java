package uk.ac.ebi.atlas.species;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

@AutoValue
public abstract class SpeciesProperties {
    public static SpeciesProperties create(String referenceName, String ensemblName, String defaultQueryFactorType,
                                    String kingdom, Map<String, List<String>> resources) {
        return new AutoValue_SpeciesProperties(referenceName, ensemblName, defaultQueryFactorType, kingdom, resources);
    }

    public final static SpeciesProperties UNKNOWN =
            SpeciesProperties.create("", "", "", "", ImmutableMap.<String, List<String>>of());

    public abstract String referenceName();
    public abstract String ensemblName();
    public abstract String defaultQueryFactorType();
    public abstract String kingdom();
    public abstract Map<String, List<String>> resources();
}
