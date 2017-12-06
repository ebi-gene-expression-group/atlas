package uk.ac.ebi.atlas.species;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@Named
public class SpeciesPropertiesTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesPropertiesTrader.class);

    private SpeciesPropertiesDao speciesPropertiesDao;
    private ImmutableSortedMap<String, SpeciesProperties> nameToSpecies = ImmutableSortedMap.of();

    @Inject
    public void setSpeciesPropertiesDao(SpeciesPropertiesDao speciesPropertiesDao) {
        this.speciesPropertiesDao = speciesPropertiesDao;
    }

    @PostConstruct
    public String refresh() throws IOException {
        try {
            LOGGER.info("Reading species properties...");

            ImmutableSortedMap<String, SpeciesProperties> newMap = buildMap();
            MapDifference<String, SpeciesProperties> diff = Maps.difference(nameToSpecies, newMap);
            nameToSpecies = newMap;

            LOGGER.info("Retrieved {} species properties", nameToSpecies.size());
            return describeMapDifference(diff);

        } catch (IOException e) {
            LOGGER.error("Error reading species properties");
            throw e;
        }
    }

    private String describeMapDifference(MapDifference<?, ?> mapDifference) {
        if (mapDifference.areEqual()) {
            return "No changes were made to the reference species";
        } else {
            String updatedMessage = mapDifference.entriesDiffering().isEmpty() ?
                    null : Arrays.deepToString(mapDifference.entriesDiffering().keySet().toArray()) + " updated";

            String removedMessage = mapDifference.entriesOnlyOnLeft().isEmpty() ?
                    null : Arrays.deepToString(mapDifference.entriesOnlyOnLeft().keySet().toArray()) + " removed";

            String addedMessage = mapDifference.entriesOnlyOnRight().isEmpty() ?
                    null : Arrays.deepToString(mapDifference.entriesOnlyOnRight().keySet().toArray()) + " added";

            return Joiner.on("; ").skipNulls().join(updatedMessage, removedMessage, addedMessage);
        }
    }

    private ImmutableSortedMap<String, SpeciesProperties> buildMap() throws IOException {
        ImmutableSortedMap.Builder<String, SpeciesProperties> nameToSpeciesPropertiesBuilder =
                ImmutableSortedMap.naturalOrder();

        for (SpeciesProperties speciesProperties : speciesPropertiesDao.fetchAll()) {
            nameToSpeciesPropertiesBuilder.put(normalise(speciesProperties.referenceName()), speciesProperties);
        }

        return nameToSpeciesPropertiesBuilder.build();
    }

    /*
    We require that
     normalise(speciesName) == normalise(speciesProperties.referenceName())
     for all the reasonable species spelling variants we want:
     - underscores or not
     - long variants of names
     */
    public SpeciesProperties get(String speciesName) {
        SpeciesProperties result = nameToSpecies.get(normalise(speciesName));
        return result != null ? result : SpeciesProperties.UNKNOWN;
    }

    public Collection<SpeciesProperties> getAll() {
        return nameToSpecies.values();
    }

    private static String normalise(String str) {
        return applyExceptions(StringUtils.capitalize(
                StringUtils.lowerCase(fromWords(truncate(toWords(nullSafe(str)))))));
    }

    private static String nullSafe(String str){
        return StringUtils.isEmpty(str) ? "" : str;
    }
    private static String toWords(String str){
        return str.toLowerCase().replace("_", " ");
    }
    private static String truncate(String str){
        return Joiner.on(' ').skipNulls().join(Arrays.copyOf(str.split(" "), 2));
    }

    private static String fromWords(String str){
        return str.replace(" ", "_");
    }

    private static final ImmutableMap<String, String> exceptions = ImmutableMap.of("Canis_lupus", "Canis_familiaris");
    private static String applyExceptions(String str) {
        return exceptions.getOrDefault(str, str);
    }
}
