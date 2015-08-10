package uk.ac.ebi.atlas.bioentity;

import java.util.regex.Pattern;

public final class GeneSetUtil {

    static final String GO_PREFIX = "GO:";
    static final String PO_PREFIX = "PO:";
    static final Pattern REACTOME_REGEX = Pattern.compile("R-[A-Z]{3}-\\d+", Pattern.CASE_INSENSITIVE);
    static final Pattern INTER_PRO_REGEX = Pattern.compile("IPR" + "(\\d)+", Pattern.CASE_INSENSITIVE);
    static final Pattern PLANT_REACTOME_REGEX = Pattern.compile("[\\d]+", Pattern.CASE_INSENSITIVE);

    public static boolean isInterPro(String identifier) {
        return INTER_PRO_REGEX.matcher(identifier).matches();
    }

    public static boolean isGeneOntology(String identifier) {
        return identifier.startsWith(GO_PREFIX);
    }

    public static boolean isPlantOntology(String identifier) {
        return identifier.startsWith(PO_PREFIX);
    }

    public static boolean isReactome(String identifier) {
        return REACTOME_REGEX.matcher(identifier).matches();
    }

    public static boolean isPlantReactome(String identifier) {
        return PLANT_REACTOME_REGEX.matcher(identifier).matches();
    }

    public static boolean isGeneSet(String identifier) {
        return isReactome(identifier) || isInterPro(identifier) || isGeneOntology(identifier) || isPlantOntology(identifier) || isPlantReactome(identifier);
    }

}
