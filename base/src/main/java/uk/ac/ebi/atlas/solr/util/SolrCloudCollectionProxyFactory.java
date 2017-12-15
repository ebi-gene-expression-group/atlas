package uk.ac.ebi.atlas.solr.util;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;

@Named
public class SolrCloudCollectionProxyFactory {
    private final CloudSolrClient cloudSolrClient;

    public SolrCloudCollectionProxyFactory(@Value("#{configuration['solrZkHost']}") String zkHost) {
        cloudSolrClient = new CloudSolrClient.Builder().withZkHost(zkHost).build();
    }

    public AnalyticsCollectionProxy createAnalyticsCollectionProxy() {
        return new AnalyticsCollectionProxy(cloudSolrClient);
    }
}
