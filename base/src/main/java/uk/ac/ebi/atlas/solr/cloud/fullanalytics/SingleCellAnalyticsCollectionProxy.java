package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
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
    public static final SingleCellAnalyticsSchemaField CHARACTERISTICS = new SingleCellAnalyticsSchemaField("characteristics");
    public static final SingleCellAnalyticsSchemaField CHARACTERISTIC_INFERRED_CELL_TYPE = new SingleCellAnalyticsSchemaField("characteristic_inferred_cell_type");
    public static final SingleCellAnalyticsSchemaField CHARACTERISTIC_INDIVIDUAL = new SingleCellAnalyticsSchemaField("characteristic_individual");
    public static final SingleCellAnalyticsSchemaField FACTORS = new SingleCellAnalyticsSchemaField("factors");
    public static final SingleCellAnalyticsSchemaField EXPERIMENT_ACCESSION = new SingleCellAnalyticsSchemaField("experiment_accession");

    public SingleCellAnalyticsCollectionProxy(SolrClient solrClient) {
        super(solrClient, "scxa-analytics-v1");
    }

    public QueryResponse query(SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder) {
        return query(solrQueryBuilder.build());
    }

    public FieldStatsInfo fieldStats(SchemaField<SingleCellAnalyticsCollectionProxy> field, SolrQuery solrQuery) {
        return super.fieldStats(field.name(), solrQuery);
    }
}