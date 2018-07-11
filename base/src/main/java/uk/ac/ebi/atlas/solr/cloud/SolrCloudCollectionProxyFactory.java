package uk.ac.ebi.atlas.solr.cloud;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.cloud.bioentities.BioentitiesCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;

@Component
public class SolrCloudCollectionProxyFactory {
    private final CloudSolrClient cloudSolrClient;

    public SolrCloudCollectionProxyFactory(CloudSolrClient cloudSolrClient) {
        this.cloudSolrClient = cloudSolrClient;
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
