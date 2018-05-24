package uk.ac.ebi.atlas.solr.utils;

import org.apache.commons.lang.StringUtils;

public class SchemaFieldNameUtils {

    // TODO Implement single function that converts factor_*, facet_*, characteristic_* field names to human-human friendly names

    // Converts Solr factor field names to human-friendly names (e.g. factor_biopsy_site => Biopsy site)
    public static String factorFieldNameToDisplayName(String factorFieldName) {
        String displayName = factorFieldName.replace("factor_", "").replace("_", " ");

        return StringUtils.capitalize(displayName);
    }

    // Converts Solr characteristic names to human-friendly names (e.g. characteristic_inferred_cell_type => Inferred cell type)
    public static String characteristicFieldNameToDisplayName(String characteristicFieldName) {
        String displayName = characteristicFieldName.replace("characteristic_", "").replace("_", " ");

        return StringUtils.capitalize(displayName);
    }

    // Converts attribute strings from .idf file to Solr schema field names (e.g. FACS marker => facs_marker)
    public static String attributeNameToFieldName(String attributeName) {
        return attributeName.trim().toLowerCase().replace(" ", "_");
    }
}
