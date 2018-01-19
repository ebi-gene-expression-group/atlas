package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import org.apache.solr.client.solrj.SolrClient;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;

public class AnalyticsCollectionProxy extends CollectionProxy {
    enum Field {
        EXPERIMENT_ACCESSION("experiment_accession"),
        BIOENTITY_IDENTIFIER("bioentity_identifier"),
        ASSAY_GROUP_ID("assay_group_id"),
        EXPRESSION_LEVEL("expression_level");
        final String name;

        Field(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public AnalyticsCollectionProxy(SolrClient solrClient) {
        super(solrClient, "analytics");
    }
}
