package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.cloud.SolrZkClient;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;

@Named
public class AnalyticsCloudClient {

    private static final int ZOOKEEPER_TIMEOUT = 10000;
    private static final int LIMIT = 100000;

    private final SolrZkClient solrZkClient;
    private final CloudSolrClient cloudSolrClient;

    public AnalyticsCloudClient(@Value("#{configuration['zkServerAddress']}") String zkServerAddress) {
        solrZkClient = new SolrZkClient(zkServerAddress, ZOOKEEPER_TIMEOUT);
        cloudSolrClient = new CloudSolrClient.Builder().withZkHost(zkServerAddress).build();
    }

    public String getZkServerAddress() {
        return solrZkClient.getZkServerAddress();
    }
}
