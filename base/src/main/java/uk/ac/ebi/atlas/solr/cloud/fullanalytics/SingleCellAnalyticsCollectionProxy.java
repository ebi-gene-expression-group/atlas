package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

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

    public static SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField characteristicAsSchemaField(String characteristic, String displayName) {
        return new SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField(String.format("characteristic_%s", characteristic), displayName);
    }

    public static SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField factorAsSchemaField(String factor, String displayName) {
        return new SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField(String.format("factor_%s", factor), displayName);
    }

    public QueryResponse query(SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder) {
        return query(solrQueryBuilder.build());
    }
}