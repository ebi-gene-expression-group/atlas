package uk.ac.ebi.atlas.bioentity.geneset;

import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import java.util.regex.Pattern;

public class GeneSetUtil {
    private static final Pattern GO_REGEX = Pattern.compile("GO:\\d+", Pattern.CASE_INSENSITIVE);
    private static final Pattern PO_REGEX = Pattern.compile("PO:\\d+", Pattern.CASE_INSENSITIVE);
    private static final Pattern REACTOME_REGEX = Pattern.compile("R-[A-Z]{3}-\\d+", Pattern.CASE_INSENSITIVE);
    private static final Pattern INTER_PRO_REGEX = Pattern.compile("IPR" + "(\\d)+", Pattern.CASE_INSENSITIVE);
    private static final Pattern PLANT_REACTOME_REGEX = Pattern.compile("[\\d]+", Pattern.CASE_INSENSITIVE);

    private static final String GO_CATEGORY = "go";
    private static final String PO_CATEGORY = "po";
    private static final String REACTOME_CATEGORY = "pathwayid";
    private static final String INTERPRO_CATEGORY = "interpro";

    protected GeneSetUtil() {
        throw new UnsupportedOperationException();
    }

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
        return matchesReactomeID(identifier) || matchesInterProAccession(identifier) ||
                matchesGeneOntologyAccession(identifier) || matchesPlantOntologyAccession(identifier) ||
                matchesPlantReactomeID(identifier);
    }

    private static boolean hasGeneSetCategory(String category) {
        return category.equalsIgnoreCase(GO_CATEGORY) || category.equalsIgnoreCase(PO_CATEGORY) ||
                category.equalsIgnoreCase(REACTOME_CATEGORY) || category.equalsIgnoreCase(INTERPRO_CATEGORY);
    }

    public static boolean matchesGeneSetCategoryOrGeneSetValue(SemanticQuery geneQuery) {
        if (geneQuery.isEmpty() || geneQuery.size() > 1) {
            return false;
        }

        SemanticQueryTerm term = geneQuery.iterator().next();
        return term.category().isPresent() ?
                hasGeneSetCategory(term.category().get()) :
                matchesGeneSetAccession(term.value());
    }
}
