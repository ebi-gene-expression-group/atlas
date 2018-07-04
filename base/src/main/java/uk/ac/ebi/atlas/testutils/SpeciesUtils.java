package uk.ac.ebi.atlas.testutils;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

public class SpeciesUtils {
    public static Species getHumanSpecies() {
        SpeciesProperties properties =
                SpeciesProperties.create("Homo_sapiens", "ORGANISM_PART", "animals", ImmutableList.of());

        return new Species("Homo sapiens", properties);
    }

    public static Species getUnknownSpecies() {
        return new Species("Species Mysterii", SpeciesProperties.UNKNOWN);
    }
}
