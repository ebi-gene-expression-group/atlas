package uk.ac.ebi.atlas.bioentity;

import uk.ac.ebi.atlas.web.SemanticQuery;
import uk.ac.ebi.atlas.web.SemanticQueryTerm;

import java.util.regex.Pattern;

public final class GeneSetUtil {

    static final Pattern GO_REGEX = Pattern.compile("GO:\\d+", Pattern.CASE_INSENSITIVE);
    static final Pattern PO_REGEX = Pattern.compile("PO:\\d+", Pattern.CASE_INSENSITIVE);
    static final Pattern REACTOME_REGEX = Pattern.compile("R-[A-Z]{3}-\\d+", Pattern.CASE_INSENSITIVE);
    static final Pattern INTER_PRO_REGEX = Pattern.compile("IPR" + "(\\d)+", Pattern.CASE_INSENSITIVE);
    static final Pattern PLANT_REACTOME_REGEX = Pattern.compile("[\\d]+", Pattern.CASE_INSENSITIVE);

    static final String GO_SOURCE = "go";
    static final String PO_SOURCE = "po";
    static final String REACTOME_SOURCE = "pathwayid";
    static final String INTER_PRO_SOURCE = "interpro";


    public static boolean matchesInterProAccession(String identifier) {
        return INTER_PRO_REGEX.matcher(identifier).matches();
    }

    public static boolean matchesGeneOntologyAccession(String identifier) {
        return GO_REGEX.matcher(identifier).matches();
    }

    public static boolean matchesPlantOntologyAccession(String identifier) {
        return PO_REGEX.matcher(identifier).matches();
    }

    public static boolean matchesReactomeID(String identifier) {
        return REACTOME_REGEX.matcher(identifier).matches();
    }

    public static boolean matchesPlantReactomeID(String identifier) {
        return PLANT_REACTOME_REGEX.matcher(identifier).matches();
    }

    public static boolean matchesGeneSetAccession(String identifier) {
        return matchesReactomeID(identifier) || matchesInterProAccession(identifier) || matchesGeneOntologyAccession(identifier) || matchesPlantOntologyAccession(identifier) || matchesPlantReactomeID(identifier);
    }

    private static boolean isGeneSetSource(String source) {
        return source.equalsIgnoreCase(GO_SOURCE) || source.equalsIgnoreCase(PO_SOURCE) || source.equalsIgnoreCase(REACTOME_SOURCE) || source.equalsIgnoreCase(INTER_PRO_SOURCE);
    }

    public static boolean isGeneSetSourceOrMatchesGeneSetAccession(SemanticQuery geneQuery) {
        for (SemanticQueryTerm term : geneQuery) {
            if (term.hasNoSource()) {
                if (!matchesGeneSetAccession(term.value())) {
                    return false;
                }
            } else if (!isGeneSetSource(term.source())) {
                return false;
            }

        }

        return true;
    }

}
