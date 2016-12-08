package uk.ac.ebi.atlas.species;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSortedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.trader.SpeciesFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Arrays;

@Named
public class SpeciesTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesTrader.class);

    private SpeciesDao speciesDao;
    private ImmutableSortedMap<String, SpeciesConfigurationRecord> nameToSpecies;
    private ImmutableMultimap<String, SpeciesConfigurationRecord> kingdomToSpecies;

    @Inject
    public void setSpeciesDao(SpeciesDao speciesDao) {
        this.speciesDao = speciesDao;
        buildMaps();
    }

    public SpeciesConfigurationRecord getByName(String speciesName) {
        return  nameToSpecies.get(normalise(speciesName));
    }

    private String normalise(String str) {
        if (str.contains(" ")) {
            return Joiner.on(' ').join(Arrays.copyOf(str.split(" "), 2)).replaceAll(" ", "_").toLowerCase();
        } else {
            return str.toLowerCase();
        }

    }

    public ImmutableCollection<SpeciesConfigurationRecord> getByKingdom(String kingdom) {
        return kingdomToSpecies.get(kingdom);
    }

    public void refresh() {
        buildMaps();
    }

    private void buildMaps() {
        try {
            LOGGER.info("Reading species.json to retrieve species list...");

            ImmutableSortedMap.Builder<String, SpeciesConfigurationRecord> nameToSpeciesBuilder = ImmutableSortedMap.naturalOrder();
            ImmutableMultimap.Builder<String, SpeciesConfigurationRecord> kingdomToSpeciesBuilder = ImmutableMultimap.builder();

            for (SpeciesConfigurationRecord species : speciesDao.fetchAllSpecies()) {
                nameToSpeciesBuilder.put(species.name(), species);
                kingdomToSpeciesBuilder.put(species.kingdom(), species);
            }

            nameToSpecies = nameToSpeciesBuilder.build();
            kingdomToSpecies = kingdomToSpeciesBuilder.build();

            LOGGER.info("Read {} species in {} kingdoms", nameToSpecies.size(), kingdomToSpecies.keySet().size());
        } catch (IOException e) {
            LOGGER.error("Error reading species.json");
        }
    }
}
