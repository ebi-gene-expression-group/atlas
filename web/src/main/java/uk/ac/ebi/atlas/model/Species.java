package uk.ac.ebi.atlas.model;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

//TODO: turn this into a proper domain class, rather than a util class
public final class Species {

    private Species() {
    }

    public static String shortenSpeciesToFirstTwoWords(String species) {

        String[] words = StringUtils.split(species);

        if (ArrayUtils.getLength(words) > 2) {
            return words[0].concat(" ").concat(words[1]);
        }
        return species;
    }

    public static boolean sameSpecies(String species1, String species2) {
        return shortenSpeciesToFirstTwoWords(species1).equalsIgnoreCase(shortenSpeciesToFirstTwoWords(species2));
    }

    public static String convertSpacesToUnderscore(String species) {
        return species.replaceAll(" ", "_");
    }

}
