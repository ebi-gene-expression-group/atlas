package uk.ac.ebi.atlas.species;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSortedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Arrays;

@Named
public class SpeciesPropertiesTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesPropertiesTrader.class);

    private SpeciesPropertiesDao speciesPropertiesDao;
    private ImmutableSortedMap<String, SpeciesProperties> nameToSpecies;

    @Inject
    public void setSpeciesPropertiesDao(SpeciesPropertiesDao speciesPropertiesDao) {
        this.speciesPropertiesDao = speciesPropertiesDao;
        buildMap();
    }

    public SpeciesProperties find(String speciesName) {
        return nameToSpecies.get(normalise(speciesName));
    }

    private String normalise(String str) {
        if (str.contains(" ")) {
            return StringUtils.capitalize(Joiner.on(' ').join(Arrays.copyOf(str.toLowerCase().split(" "), 2)))
                    .replace(" ", "_");
        } else {
            return StringUtils.capitalize(str.toLowerCase());
        }

    }

    public void refresh() {
        buildMap();
    }

    private void buildMap() {
        try {
            LOGGER.info("Reading species properties...");

            ImmutableSortedMap.Builder<String, SpeciesProperties> nameToSpeciesPropertiesBuilder =
                    ImmutableSortedMap.naturalOrder();

            for (SpeciesProperties speciesProperties : speciesPropertiesDao.getAll()) {
                nameToSpeciesPropertiesBuilder.put(speciesProperties.canonicalName(), speciesProperties);
            }

            nameToSpecies = nameToSpeciesPropertiesBuilder.build();

            LOGGER.info("Retrieved {} species properties", nameToSpecies.size());
        } catch (IOException e) {
            LOGGER.error("Error reading species properties");
        }
    }

}
