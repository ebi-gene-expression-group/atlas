package uk.ac.ebi.atlas.species;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.tiles.request.collection.MapEntry;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@AutoValue
public abstract class SpeciesProperties {
    public static SpeciesProperties create(String ensemblName,
                                           String defaultQueryFactorType,
                                           String kingdom,
                                           ImmutableCollection<ImmutableMap<String, String>> resources) {
        return new AutoValue_SpeciesProperties(ensemblName, defaultQueryFactorType, kingdom, resources);
    }

    public final static SpeciesProperties UNKNOWN =
            SpeciesProperties.create("", "", "", ImmutableList.<ImmutableMap<String, String>>of());

    public String referenceName() {
        return StringUtils.lowerCase(ensemblName().replace("_", " "));
    }
    public abstract String ensemblName();
    public abstract String defaultQueryFactorType();
    public abstract String kingdom();
    public abstract ImmutableCollection<ImmutableMap<String, String>> resources();

    public ImmutableCollection<ImmutableMap<String, String>> getResources(String key, String value) {
        ImmutableList.Builder<ImmutableMap<String, String>> matchedResourcesBuilder = ImmutableList.builder();

        for (ImmutableMap<String, String> resource : resources()) {
            if (resource.containsKey(key) && value.equals(resource.get(key))) {
                matchedResourcesBuilder.add(resource);
            }
        }

        return matchedResourcesBuilder.build();
    }
}
