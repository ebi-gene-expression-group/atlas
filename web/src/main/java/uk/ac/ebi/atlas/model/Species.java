package uk.ac.ebi.atlas.model;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

//TODO: turn this into a proper domain class, rather than a util class
public final class Species {

    private Species() {
    }

    public static String convertToEnsemblSpecies(String species) {
       return firstTwoWords(species).toLowerCase();
    }

    static String firstTwoWords(String sentence) {
        String[] words = StringUtils.split(sentence);

        return (ArrayUtils.getLength(words) > 2) ? words[0].concat(" ").concat(words[1]) : sentence;
    }

    public static boolean sameSpecies(String species1, String species2) {
        return convertToEnsemblSpecies(species1).equals(convertToEnsemblSpecies(species2));
    }

    public static String convertSpacesToUnderscore(String species) {
        return species.replaceAll(" ", "_");
    }

}
