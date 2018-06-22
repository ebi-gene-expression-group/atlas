package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

public class SingleCellAnalyticsCollectionProxy extends CollectionProxy {

    public static class SingleCellAnalyticsSchemaField extends SchemaField<SingleCellAnalyticsCollectionProxy> {
        private String displayName;

        private SingleCellAnalyticsSchemaField(String fieldName, String displayName) {
            super(fieldName);
            this.displayName = displayName;
        }

        public String displayName() {
            return displayName;
        }
    }

    public static final SingleCellAnalyticsSchemaField CELL_ID = new SingleCellAnalyticsSchemaField("cell_id", "Cell ID");
    public static final SingleCellAnalyticsSchemaField CHARACTERISTIC_INFERRED_CELL_TYPE = new SingleCellAnalyticsSchemaField("characteristic_inferred_cell_type", "Inferred cell type");
    public static final SingleCellAnalyticsSchemaField CHARACTERISTIC_SPECIES = new SingleCellAnalyticsSchemaField("characteristic_organism", "Species");
    public static final SingleCellAnalyticsSchemaField CHARACTERISTIC_ORGANISM_PART = new SingleCellAnalyticsSchemaField("characteristic_organism_part", "Organism part");
    public static final SingleCellAnalyticsSchemaField FACTORS = new SingleCellAnalyticsSchemaField("factors", "Factors");
    public static final SingleCellAnalyticsSchemaField EXPERIMENT_ACCESSION = new SingleCellAnalyticsSchemaField("experiment_accession", "Experiment accession");

    public SingleCellAnalyticsCollectionProxy(SolrClient solrClient) {
        // scxa-analytics is an alias that points at scxa-analytics-vX
        super(solrClient, "scxa-analytics");
    }

    public static SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField characteristicAsSchemaField(String characteristic) {
        return new SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField(String.format("characteristic_%s", characteristic), characteristicFieldNameToDisplayName(characteristic));
    }

    public static SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField factorAsSchemaField(String factor) {
        return new SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField(String.format("factor_%s", factor), factorFieldNameToDisplayName(factor));
    }

    public static SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField metadataAsSchemaField(String metadata) {
        // The metadata fields are either characteristics or factors
        if (metadata.startsWith("characteristic")) {
            return new SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField(metadata, characteristicFieldNameToDisplayName(metadata));
        }
        else {
            return new SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField(metadata, factorFieldNameToDisplayName(metadata));
        }
    }

    public QueryResponse query(SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder) {
        return query(solrQueryBuilder.build());
    }

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