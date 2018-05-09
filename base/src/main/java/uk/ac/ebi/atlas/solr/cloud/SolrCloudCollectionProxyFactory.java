package uk.ac.ebi.atlas.solr.cloud;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.solr.cloud.bioentities.BioentitiesCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;

import javax.inject.Named;

@Named
public class SolrCloudCollectionProxyFactory {
    private final CloudSolrClient cloudSolrClient;

    public SolrCloudCollectionProxyFactory(@Qualifier("zkHost") String zkHost) {
        cloudSolrClient = new CloudSolrClient.Builder().withZkHost(zkHost).build();
    }

    public AnalyticsCollectionProxy createAnalyticsCollectionProxy() {
        return new AnalyticsCollectionProxy(cloudSolrClient);
    }

    public BioentitiesCollectionProxy createBioentitiesCollectionProxy() {
        return new BioentitiesCollectionProxy(cloudSolrClient);
    }

    public SingleCellAnalyticsCollectionProxy createSingleCellAnalyticsCollectionProxy() {
        return new SingleCellAnalyticsCollectionProxy(cloudSolrClient);
    }
}
