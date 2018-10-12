package uk.ac.ebi.atlas.solr.cloud.collections;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

public class SingleCellAnalyticsCollectionProxy extends CollectionProxy {

    public static final class SingleCellAnalyticsSchemaField extends SchemaField<SingleCellAnalyticsCollectionProxy> {
        private String displayName;

        private SingleCellAnalyticsSchemaField(String fieldName, String displayName) {
            super(fieldName);
            this.displayName = displayName;
        }

        public String displayName() {
            return displayName;
        }
    }

    public static final SingleCellAnalyticsSchemaField CELL_ID =
            new SingleCellAnalyticsSchemaField("cell_id", "Cell ID");
    public static final SingleCellAnalyticsSchemaField CHARACTERISTIC_INFERRED_CELL_TYPE =
            new SingleCellAnalyticsSchemaField("characteristic_inferred_cell_type", "Inferred cell type");
    public static final SingleCellAnalyticsSchemaField CHARACTERISTIC_SPECIES =
            new SingleCellAnalyticsSchemaField("characteristic_organism", "Species");
    public static final SingleCellAnalyticsSchemaField CHARACTERISTIC_ORGANISM_PART =
            new SingleCellAnalyticsSchemaField("characteristic_organism_part", "Organism part");
    public static final SingleCellAnalyticsSchemaField FACTORS =
            new SingleCellAnalyticsSchemaField("factors", "Factors");
    public static final SingleCellAnalyticsSchemaField EXPERIMENT_ACCESSION =
            new SingleCellAnalyticsSchemaField("experiment_accession", "Experiment accession");

    public SingleCellAnalyticsCollectionProxy(SolrClient solrClient) {
        // scxa-analytics is an alias that points at scxa-analytics-vX
        super(solrClient, "scxa-analytics");
    }

    // Converts names of characteristics into SolrSchemaFields
    // E.g. cell_type => new SingleCellAnalyticsSchemaField("characteristic_cell_type", "Cell type")
    public static SingleCellAnalyticsSchemaField characteristicAsSchemaField(String characteristic) {
        return new SingleCellAnalyticsSchemaField(
                String.format("characteristic_%s", characteristic),
                metadataFieldNameToDisplayName(characteristic, "characteristic_"));
    }

    // Converts names of factors into SolrSchemaFields
    // E.g. biopsy_site => new SingleCellAnalyticsSchemaField("factor_biopsy_site", "Biopsy site")
    public static SingleCellAnalyticsSchemaField factorAsSchemaField(String factor) {
        return new SingleCellAnalyticsSchemaField(
                String.format("factor_%s", factor),
                metadataFieldNameToDisplayName(factor, "characteristic_"));
    }

    // Converts strings representing metadata field names to SolrSchemaField
    // E.g. characteristic_cell_type => new SingleCellAnalyticsSchemaField("characteristic_cell_type", "Cell type"))
    public static SingleCellAnalyticsSchemaField metadataAsSchemaField(String metadata) {
        // The metadata fields are either characteristics or factors
        if (metadata.startsWith("characteristic")) {
            return new SingleCellAnalyticsSchemaField(
                    metadata, metadataFieldNameToDisplayName(metadata, "characteristic_"));
        } else {
            return new SingleCellAnalyticsSchemaField(
                    metadata, metadataFieldNameToDisplayName(metadata, "factor_"));
        }
    }

    // Converts metadata Solr field names to human-friendly names
    // E.g. factor_biopsy_site => Biopsy site, characteristic_cell_type => Cell type)
    public static String metadataFieldNameToDisplayName(String metadataFieldName) {
        // The metadata fields are either characteristics or factors
        if (metadataFieldName.startsWith("characteristic")) {
            return metadataFieldNameToDisplayName(metadataFieldName, "characteristic_");
        } else {
            return metadataFieldNameToDisplayName(metadataFieldName, "factor_");
        }
    }

    private static String metadataFieldNameToDisplayName(String fieldName, String prefix) {
        String displayName = fieldName.replace(prefix, "").replace("_", " ");

        return StringUtils.capitalize(displayName);
    }

    // Converts attribute strings from .idf file to Solr schema field names
    // E.g. FACS marker => facs_marker)
    public static String attributeNameToFieldName(String attributeName) {
        return attributeName.trim().toLowerCase().replace(" ", "_");
    }

    public QueryResponse query(SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder) {
        return query(solrQueryBuilder.build());
    }
}
