package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import org.apache.solr.client.solrj.SolrClient;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BioentityIdentifierSearchService {

    private final AnalyticsCollectionCloudDao analyticsCollectionCloudDao;

    @Inject
    public BioentityIdentifierSearchService(AnalyticsCollectionCloudDao analyticsCollectionCloudDao) {
        this.analyticsCollectionCloudDao = analyticsCollectionCloudDao;
    }


}
