package uk.ac.ebi.atlas.species;

import com.google.auto.value.AutoValue;

import java.util.List;
import java.util.Map;

@AutoValue
public abstract class SpeciesProperties {
    static SpeciesProperties create(String humanName, String canonicalName, String defaultQueryFactorType,
                                    String kingdom, Map<String, List<String>> resources) {
        return new AutoValue_SpeciesProperties(humanName, canonicalName, defaultQueryFactorType, kingdom, resources);
    }

    public abstract String humanName();
    public abstract String canonicalName();
    public abstract String defaultQueryFactorType();
    public abstract String kingdom();
    public abstract Map<String, List<String>> resources();

    public boolean isPlant() {
        return "plants".equals(kingdom());
    }
}
