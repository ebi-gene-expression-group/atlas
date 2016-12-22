package uk.ac.ebi.atlas.species;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SpeciesFactory {

    private final SpeciesPropertiesTrader speciesPropertiesTrader;

    @Inject
    public SpeciesFactory(SpeciesPropertiesTrader speciesPropertiesTrader) {
        this.speciesPropertiesTrader = speciesPropertiesTrader;
    }

    public Species create(String name) {
        return new Species(name, speciesPropertiesTrader.get(name));
    }
}
