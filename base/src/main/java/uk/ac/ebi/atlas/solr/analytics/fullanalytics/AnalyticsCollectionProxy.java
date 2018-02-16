package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import org.apache.solr.client.solrj.SolrClient;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;

public class AnalyticsCollectionProxy extends CollectionProxy {
    private class AnalyticsSchemaField extends SchemaField<AnalyticsCollectionProxy>{
        private AnalyticsSchemaField(String fieldName) {
            super(fieldName);
        }
    }

    public final AnalyticsSchemaField BIOENTITY_IDENTIFIER = new AnalyticsSchemaField("bioentity_identifier");
    public final AnalyticsSchemaField EXPERIMENT_ACCESSION = new AnalyticsSchemaField("experiment_accession");
    public final AnalyticsSchemaField ASSAY_GROUP_ID = new AnalyticsSchemaField("assay_group_id");
    public final AnalyticsSchemaField EXPRESSION_LEVEL = new AnalyticsSchemaField("expression_level");

    public AnalyticsCollectionProxy(SolrClient solrClient) {
        super(solrClient, "analytics");
    }
}
