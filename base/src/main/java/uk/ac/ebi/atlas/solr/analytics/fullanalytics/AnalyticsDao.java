package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import uk.ac.ebi.atlas.solr.util.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.util.SolrCloudCollectionProxyFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsDao {

    private final AnalyticsCollectionProxy analyticsSolrCollectionProxy;

    @Inject
    public AnalyticsDao(SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory) {
        analyticsSolrCollectionProxy = solrCloudCollectionProxyFactory.createAnalyticsCollectionProxy();
    }



}
