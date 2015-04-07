package uk.ac.ebi.atlas.bioentity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GeneSetUtil {

    static final String GO_PREFIX = "GO:";
    static final String PO_PREFIX = "PO:";
    static final String REACTOME_PREFIX = "REACT_";
    static final Pattern INTER_PRO_REGEX = Pattern.compile("IPR" + "(\\d)+", Pattern.CASE_INSENSITIVE);

    public static boolean isInterPro(String identifier) {
        Matcher m = INTER_PRO_REGEX.matcher(identifier);
        return m.matches();
    }

    public static boolean isGeneOntology(String identifier) {
        return identifier.regionMatches(true, 0, GO_PREFIX, 0, GO_PREFIX.length());
    }

    public static boolean isPlantOntology(String identifier) {
        return identifier.regionMatches(true, 0, PO_PREFIX, 0, PO_PREFIX.length());
    }

    public static boolean isReactome(String identifier) {
        return identifier.regionMatches(true, 0, REACTOME_PREFIX, 0, REACTOME_PREFIX.length());
    }

    public static boolean isPlantReactome(String identifier) {
        return identifier.matches("[\\d]+");
    }

    public static boolean isGeneSet(String identifier) {
        return isReactome(identifier) || isInterPro(identifier) || isGeneOntology(identifier) || isPlantOntology(identifier) || isPlantReactome(identifier);
    }

}
