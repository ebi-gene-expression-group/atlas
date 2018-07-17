package uk.ac.ebi.atlas.testutils;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

@Component
public class SpeciesUtils {
    private final SpeciesFactory speciesFactory;
    // private final SpeciesPropertiesTrader speciesPropertiesTrader;

    public SpeciesUtils(SpeciesFactory speciesFactory) {
                        // SpeciesPropertiesTrader speciesPropertiesTrader) {
        this.speciesFactory = speciesFactory;
        // this.speciesPropertiesTrader = speciesPropertiesTrader;
    }

    public Species getHuman() {
        return speciesFactory.create("Homo sapiens");
    }

    public Species getMouse() {
        return speciesFactory.create("Mus musculus");
    }

//    public Species getUnknownSpecies() {
//        return speciesFactory.createUnknownSpecies();
//    }
//
//    public Species getRandomSpecies() {
//        ArrayList<SpeciesProperties> allSpeciesProperties = new ArrayList<>(speciesPropertiesTrader.getAll());
//        Collections.shuffle(allSpeciesProperties);
//        return speciesFactory.create(allSpeciesProperties.get(0).referenceName());
//    }
}
