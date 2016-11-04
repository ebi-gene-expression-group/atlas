package uk.ac.ebi.atlas.model;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

@AutoValue
public abstract class Species2 {

    public abstract String name();
    public abstract String canonicalName();
    public abstract String defaultQueryFactorType();
    public abstract String kingdom();
    public abstract ImmutableMultimap<String, String> resources();

    public static Species2 create(String name, String canonicalName, String defaultQueryFactorType, String kingdom, Multimap<String, String> resources) {
        return new AutoValue_Species2(name, canonicalName, defaultQueryFactorType, kingdom, ImmutableMultimap.copyOf(resources));
    }

    public boolean isPlant() {
        return kingdom().equalsIgnoreCase("plants");
    }

    public Iterable<String> getResource(String type) {
        return resources().get(type);
    }

}
