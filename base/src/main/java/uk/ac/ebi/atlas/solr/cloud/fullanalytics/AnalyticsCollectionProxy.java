package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

import org.apache.solr.client.solrj.SolrClient;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;

public class AnalyticsCollectionProxy extends CollectionProxy {
    public static class AnalyticsSchemaField extends SchemaField<AnalyticsCollectionProxy> {
        private AnalyticsSchemaField(String fieldName) {
            super(fieldName);
        }
    }

    public static final AnalyticsSchemaField BIOENTITY_IDENTIFIER = new AnalyticsSchemaField("bioentity_identifier");
    public static final AnalyticsSchemaField EXPERIMENT_ACCESSION = new AnalyticsSchemaField("experiment_accession");
    public static final AnalyticsSchemaField ASSAY_GROUP_ID = new AnalyticsSchemaField("assay_group_id");
    public static final AnalyticsSchemaField CONTRAST_ID = new AnalyticsSchemaField("contrast_id");
    public static final AnalyticsSchemaField EXPRESSION_LEVEL = new AnalyticsSchemaField("expression_level");
    public static final AnalyticsSchemaField LOG_2_FOLD_CHANGE = new AnalyticsSchemaField("fold_change");
    public static final AnalyticsSchemaField ADJUSTED_P_VALUE = new AnalyticsSchemaField("p_value");

    public AnalyticsCollectionProxy(SolrClient solrClient) {
        super(solrClient, "analytics");
    }
}
