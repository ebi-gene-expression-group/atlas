package uk.ac.ebi.atlas.species;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;

@AutoValue
public abstract class SpeciesProperties {
    public static SpeciesProperties create(String ensemblName,
                                           String defaultQueryFactorType,
                                           String kingdom,
                                           ImmutableCollection<ImmutableMap<String, String>> resources) {
        return new AutoValue_SpeciesProperties(ensemblName, defaultQueryFactorType, kingdom, resources);
    }

    public static final SpeciesProperties UNKNOWN =
            SpeciesProperties.create("", "", "", ImmutableList.of());

    public static final String GENOME_BROWSER_TYPE = "genome_browser";

    public String referenceName() {
        return StringUtils.lowerCase(ensemblName().replace("_", " "));
    }
    public abstract String ensemblName();
    public abstract String defaultQueryFactorType();
    public abstract String kingdom();
    public abstract ImmutableCollection<ImmutableMap<String, String>> resources();

    public ImmutableCollection<ImmutableMap<String, String>> getResourcesOfType(String typeValue) {
        ImmutableList.Builder<ImmutableMap<String, String>> matchedTypeResourcesBuilder = ImmutableList.builder();

        resources().stream()
                .filter(resource -> resource.containsKey("type") && typeValue.equals(resource.get("type")))
                .forEach(matchedTypeResourcesBuilder::add);

        return matchedTypeResourcesBuilder.build();
    }
}
