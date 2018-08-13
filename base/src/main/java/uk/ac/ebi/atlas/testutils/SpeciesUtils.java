package uk.ac.ebi.atlas.testutils;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

@Component
public class SpeciesUtils {
    private final SpeciesFactory speciesFactory;

    public SpeciesUtils(SpeciesFactory speciesFactory) {
        this.speciesFactory = speciesFactory;
    }

    public Species getHuman() {
        return speciesFactory.create("Homo sapiens");
    }

    public Species getMouse() {
        return speciesFactory.create("Mus musculus");
    }
}
