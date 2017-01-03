package uk.ac.ebi.atlas.species;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSortedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;

@Named
public class SpeciesPropertiesTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesPropertiesTrader.class);

    private SpeciesPropertiesDao speciesPropertiesDao;
    private ImmutableSortedMap<String, SpeciesProperties> nameToSpecies;

    @Inject
    public void setSpeciesPropertiesDao(SpeciesPropertiesDao speciesPropertiesDao) {
        this.speciesPropertiesDao = speciesPropertiesDao;
        nameToSpecies = buildMap();
    }

    public SpeciesProperties get(String speciesName) {
        return StringUtils.isNotEmpty(speciesName) && nameToSpecies.containsKey(normalise(speciesName)) ?
                nameToSpecies.get(normalise(speciesName)) : SpeciesProperties.UNKNOWN;
    }

    public Collection<SpeciesProperties> getAll() {
        return nameToSpecies.values();
    }

    private String normalise(String str) {
        String normalisedString = str.contains("_") ? str.replace("_", " ") : str;

        // Arrays.copyOf pads the array with nulls, which breaks Joiner
        return Joiner.on(' ').join(ArrayUtils.subarray(normalisedString.toLowerCase().split(" "), 0, 2));
    }

    public void refresh() {
        nameToSpecies = buildMap();
    }

    private ImmutableSortedMap<String, SpeciesProperties> buildMap() {
        try {

            LOGGER.info("Reading species properties...");

            ImmutableSortedMap.Builder<String, SpeciesProperties> nameToSpeciesPropertiesBuilder =
                    ImmutableSortedMap.naturalOrder();

            for (SpeciesProperties speciesProperties : speciesPropertiesDao.fetchAll()) {
                nameToSpeciesPropertiesBuilder.put(speciesProperties.referenceName(), speciesProperties);
            }

            ImmutableSortedMap<String, SpeciesProperties> nameToSpeciesProperties = nameToSpeciesPropertiesBuilder.build();
            LOGGER.info("Retrieved {} species properties", nameToSpeciesProperties.size());
            return nameToSpeciesProperties;

        } catch (IOException e) {
            LOGGER.error("Error reading species properties");
            return ImmutableSortedMap.of();
        }
    }

}
