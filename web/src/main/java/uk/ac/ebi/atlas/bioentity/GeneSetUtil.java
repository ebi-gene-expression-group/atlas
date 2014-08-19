package uk.ac.ebi.atlas.bioentity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GeneSetUtil {

    static final Pattern INTER_PRO_REGEX = Pattern.compile("IPR" + "(\\d)+");

    public static boolean isInterPro(String identifier) {
        Matcher m = INTER_PRO_REGEX.matcher(identifier);
        return m.matches();
    }

    public static boolean isGeneOntology(String identifier) {
        return identifier.startsWith("GO:");
    }

    public static boolean isReactome(String identifier) {
        return identifier.startsWith("REACT_");
    }

    public static boolean isGeneSet(String identifier) {
        return isReactome(identifier) || isInterPro(identifier) || isGeneOntology(identifier);
    }

}
