package uk.ac.ebi.atlas.solr.util;

import org.apache.solr.client.solrj.SolrClient;

public class AnalyticsCollectionProxy extends CollectionProxy {
    public AnalyticsCollectionProxy(SolrClient solrClient) {
        super(solrClient, "analytics");
    }
}
