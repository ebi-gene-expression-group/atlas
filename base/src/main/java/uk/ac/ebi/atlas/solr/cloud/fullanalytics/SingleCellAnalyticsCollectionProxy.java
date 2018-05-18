package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

public class SingleCellAnalyticsCollectionProxy extends CollectionProxy {
    public static class SingleCellAnalyticsSchemaField extends SchemaField<SingleCellAnalyticsCollectionProxy> {
        private SingleCellAnalyticsSchemaField(String fieldName) {
            super(fieldName);
        }
    }

    public static final SingleCellAnalyticsSchemaField CELL_ID = new SingleCellAnalyticsSchemaField("cell_id");
    public static final SingleCellAnalyticsSchemaField CHARACTERISTIC_INFERRED_CELL_TYPE = new SingleCellAnalyticsSchemaField("characteristic_inferred_cell_type");
    public static final SingleCellAnalyticsSchemaField CHARACTERISTIC_ORGANISM_PART = new SingleCellAnalyticsSchemaField("characteristic_organism_part");
    public static final SingleCellAnalyticsSchemaField FACTORS = new SingleCellAnalyticsSchemaField("factors");
    public static final SingleCellAnalyticsSchemaField EXPERIMENT_ACCESSION = new SingleCellAnalyticsSchemaField("experiment_accession");

    public SingleCellAnalyticsCollectionProxy(SolrClient solrClient) {
        super(solrClient, "scxa-analytics-v1");
    }

    public static SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField characteristicAsSchemaField(String characteristic) {
        return new SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField(String.format("characteristic_%s", characteristic));
    }

    public static SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField factorAsSchemaField(String factor) {
        return new SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField(String.format("factor_%s", factor));
    }

    public QueryResponse query(SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder) {
        return query(solrQueryBuilder.build());
    }
}