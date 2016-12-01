package uk.ac.ebi.atlas.species;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSortedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Arrays;

@Named
public class SpeciesTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeciesTrader.class);

    private SpeciesDao speciesDao;
    private ImmutableSortedMap<String, Species> nameToSpecies;
    private ImmutableMultimap<String, Species> kingdomToSpecies;

    @Inject
    public void setSpeciesDao(SpeciesDao speciesDao) {
        this.speciesDao = speciesDao;
        buildMaps();
    }

    public Species getByName(String speciesName) {
        return nameToSpecies.get(normalise(speciesName));
    }

    private String normalise(String str) {
        if (str.contains(" ")) {
            return Joiner.on(' ').join(Arrays.copyOf(str.split(" "), 2)).replaceAll(" ", "_").toLowerCase();
        } else {
            return str.toLowerCase();
        }

    }

    public ImmutableCollection<Species> getByKingdom(String kingdom) {
        return kingdomToSpecies.get(kingdom);
    }

    public void refresh() {
        buildMaps();
    }

    private void buildMaps() {
        try {

            ImmutableSortedMap.Builder<String, Species> nameToSpeciesBuilder = ImmutableSortedMap.naturalOrder();
            ImmutableMultimap.Builder<String, Species> kingdomToSpeciesBuilder = ImmutableMultimap.builder();

            for (Species species : speciesDao.fetchAllSpecies()) {
                nameToSpeciesBuilder.put(species.name(), species);
                kingdomToSpeciesBuilder.put(species.kingdom(), species);
            }

            nameToSpecies = nameToSpeciesBuilder.build();
            kingdomToSpecies = kingdomToSpeciesBuilder.build();

        } catch (IOException e) {
            LOGGER.error("Error reading species.json");
        }
    }
}
