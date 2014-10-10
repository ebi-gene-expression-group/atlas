package uk.ac.ebi.atlas.model;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public final class Species {

    private Species() {
    }

    public static String limitSpeciesNameToFirstTwoWords(String species) {

        String[] words = StringUtils.split(species);

        if (ArrayUtils.getLength(words) > 2) {
            return words[0].concat(" ").concat(words[1]);
        }
        return species;
    }

    public static String convertSpacesToUnderscore(String species) {
        return species.replaceAll(" ", "_");
    }

}
