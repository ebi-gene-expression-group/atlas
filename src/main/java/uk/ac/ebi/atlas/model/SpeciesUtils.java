package uk.ac.ebi.atlas.model;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

//TODO: turn this into a proper domain class, rather than a util class
public final class SpeciesUtils {

    private SpeciesUtils() {
    }

    public static String convertToEnsemblSpecies(Map<String, String> organismToEnsemblSpeciesMapping, String organism) {
        String ensemblSpecies = organismToEnsemblSpeciesMapping.get(organism);
        return ensemblSpecies == null ? SpeciesUtils.convertToEnsemblSpecies(organism) : ensemblSpecies;
    }

    public static String convertToEnsemblSpecies(String species) {
       return species == null ? null : firstTwoWords(species).toLowerCase();
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

    public static String convertUnderscoreToSpaces(String species) {
        return species.replaceAll("_", " ");
    }


}
