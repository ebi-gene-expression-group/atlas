package uk.ac.ebi.atlas.bioentity;

import uk.ac.ebi.atlas.web.SemanticQuery;
import uk.ac.ebi.atlas.web.SemanticQueryTerm;

import java.util.regex.Pattern;

public final class GeneSetUtil {

    private static final Pattern GO_REGEX = Pattern.compile("GO:\\d+", Pattern.CASE_INSENSITIVE);
    private static final Pattern PO_REGEX = Pattern.compile("PO:\\d+", Pattern.CASE_INSENSITIVE);
    private static final Pattern REACTOME_REGEX = Pattern.compile("R-[A-Z]{3}-\\d+", Pattern.CASE_INSENSITIVE);
    private static final Pattern INTER_PRO_REGEX = Pattern.compile("IPR" + "(\\d)+", Pattern.CASE_INSENSITIVE);
    private static final Pattern PLANT_REACTOME_REGEX = Pattern.compile("[\\d]+", Pattern.CASE_INSENSITIVE);

    private static final String GO_CATEGORY = "go";
    private static final String PO_CATEGORY = "po";
    private static final String REACTOME_CATEGORY = "pathwayid";
    private static final String INTERPRO_CATEGORY = "interpro";


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
        return source.equalsIgnoreCase(GO_CATEGORY) || source.equalsIgnoreCase(PO_CATEGORY) || source.equalsIgnoreCase(REACTOME_CATEGORY) || source.equalsIgnoreCase(INTERPRO_CATEGORY);
    }

    public static boolean isGeneSetSourceOrMatchesGeneSetAccession(SemanticQuery geneQuery) {
        for (SemanticQueryTerm term : geneQuery) {
            if (term.hasNoCategory()) {
                if (!matchesGeneSetAccession(term.value())) {
                    return false;
                }
            } else if (!isGeneSetSource(term.category())) {
                return false;
            }

        }

        return true;
    }

}
